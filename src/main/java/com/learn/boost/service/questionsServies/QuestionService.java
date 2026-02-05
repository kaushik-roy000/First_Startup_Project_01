package com.learn.boost.service.questionsServies;

import com.learn.boost.exception.UserNotFoundException;
import com.learn.boost.model.Answer;
import com.learn.boost.model.Question;
import com.learn.boost.repository.AnswerRepository;
import com.learn.boost.repository.QuestionRepository;
import com.learn.boost.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class QuestionService {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Value("${question.answer.path}")
    private String answerfolder;

    @Value("${openrouter.api.key}")
    private String apiKey;

    @Value("${openrouter.api.url:https://openrouter.ai/api/v1/chat/completions}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public QuestionService(
            UserRepository userRepository,
            QuestionRepository questionRepository,
            AnswerRepository answerRepository
    ) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @PostConstruct
    public void createRootfolder() {
        try {
            Files.createDirectories(Paths.get(answerfolder));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String questionToAnswer(String answerId, String userId, String questionId) throws IOException {
        Path userfolder = Paths.get(answerfolder, userId);
        Files.createDirectories(userfolder);

        String answerText = getAns(questionId);
        if (answerText == null || answerText.isBlank()) {
            throw new RuntimeException("Answer generation failed");
        }

        Path ansfile = userfolder.resolve(answerId + ".txt");
        Files.writeString(ansfile, answerText);
        return ansfile.toString();
    }

    public UrlResource saveQuestionAndAnswer(String userId, String question) throws IOException {

        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user not found"));

        Question savedQuestion = null;
        String answerPath = null;

        try {
            savedQuestion = new Question();
            savedQuestion.setId(UUID.randomUUID().toString());
            savedQuestion.setUserId(userId);
            savedQuestion.setContent(question);
            savedQuestion.setAskedAt(LocalDateTime.now());
            questionRepository.save(savedQuestion);

            Answer answer = new Answer();
            answer.setId(UUID.randomUUID().toString());
            answer.setQuestionId(savedQuestion.getId());
            answer.setUserId(userId);
            answer.setAnswerdAt(LocalDateTime.now());

            answerPath = questionToAnswer(
                    answer.getId(),
                    userId,
                    savedQuestion.getId()
            );

            answer.setAnswerPath(answerPath);
            answerRepository.save(answer);

            return new UrlResource(Paths.get(answerPath).toUri());

        } catch (Exception e) {
            if (answerPath != null) {
                Files.deleteIfExists(Paths.get(answerPath));
            }
            if (savedQuestion != null) {
                questionRepository.deleteById(savedQuestion.getId());
            }
            throw new RuntimeException("Failed to save question & answer", e);
        }
    }

    public String getAns(String questionId) {

        Question questionObj = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("question not found"));

        String question = questionObj.getContent();

        String prompt = "Answer the following question clearly and in detail:\n" + question;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = Map.of(
                "model", "openai/gpt-4o-mini",
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", prompt
                        )
                )
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                apiUrl,
                request,
                Map.class
        );

        if (response.getBody() == null) {
            throw new RuntimeException("Empty response from AI");
        }

        List<Map<String, Object>> choices =
                (List<Map<String, Object>>) response.getBody().get("choices");

        if (choices == null || choices.isEmpty()) {
            throw new RuntimeException("No answer generated");
        }

        Map<String, Object> message =
                (Map<String, Object>) choices.get(0).get("message");

        return message.get("content").toString();
    }
}
