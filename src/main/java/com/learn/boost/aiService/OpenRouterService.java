package com.learn.boost.aiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.boost.helper.OpenRouterResponse;
import com.learn.boost.mapper.MCQResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.List;

@Service
public class OpenRouterService {

    @Value("${openrouter.api.key}")
    private String apiKey;

    @Value("${openrouter.api.url:https://openrouter.ai/api/v1/chat/completions}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

//    public String askQuestion(String question) {
//        // JSON body
//        Map<String, Object> body = Map.of(
//                "model", "arcee-ai/trinity-large-preview:free",
//                "messages", List.of(Map.of("role", "user", "content", question)),
//                "reasoning", Map.of("enabled", true)
//        );
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(apiKey); // sets Authorization: Bearer <apiKey>
//
//        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                apiUrl,
//                HttpMethod.POST,
//                entity,
//                String.class
//        );
//
//        return response.getBody();
//    }
    public String askQuestion(String question) {
        Map<String, Object> body = Map.of(
                "model", "arcee-ai/trinity-large-preview:free",
                "messages", List.of(Map.of("role", "user", "content", question)),
                "reasoning", Map.of("enabled", true)
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                String.class
        );

        try {
            ObjectMapper mapper = new ObjectMapper();
            OpenRouterResponse orResponse = mapper.readValue(response.getBody(), OpenRouterResponse.class);
            // Return just the assistant text
            return orResponse.getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing response";
        }
    }

    public String generateQuestionAnswer(String topic) {
        // Create a prompt: "Generate 1 question about <topic> and give the answer"
        String prompt = "Generate 1 question about \"" + topic + "\" and provide the answer.";

        Map<String, Object> body = Map.of(
                "model", "arcee-ai/trinity-large-preview:free",
                "messages", List.of(Map.of("role", "user", "content", prompt)),
                "reasoning", Map.of("enabled", true)
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                String.class
        );

        try {
            ObjectMapper mapper = new ObjectMapper();
            OpenRouterResponse orResponse = mapper.readValue(response.getBody(), OpenRouterResponse.class);
            // Return just the assistant text
            return orResponse.getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing response";
        }
    }

    public String generateMCQs(String paragraph) {
        // Create a prompt to generate MCQs from the paragraph
        String prompt = "Read the following paragraph and create 3 multiple-choice questions (MCQs) with 4 options each and indicate the correct answer.\n\n"
                + paragraph;

        Map<String, Object> body = Map.of(
                "model", "arcee-ai/trinity-large-preview:free",
                "messages", List.of(Map.of("role", "user", "content", prompt)),
                "reasoning", Map.of("enabled", true)
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                String.class
        );

        try {
            ObjectMapper mapper = new ObjectMapper();
            OpenRouterResponse orResponse = mapper.readValue(response.getBody(), OpenRouterResponse.class);
            // Return just the assistant text (MCQs)
            return orResponse.getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing response";
        }
    }

    public MCQResponse generateMCQJson(String paragraph) {
        String prompt = "Read the following paragraph and create 3 multiple-choice questions (MCQs) in strict JSON format ONLY. " +
                "Do not add any extra text or explanation. " +
                "Example format:\n" +
                "{\n" +
                "  \"questions\": [\n" +
                "    {\n" +
                "      \"question\": \"...\",\n" +
                "      \"options\": [\"A\", \"B\", \"C\", \"D\"],\n" +
                "      \"answer\": \"B\"\n" +
                "    }\n" +
                "  ]\n" +
                "}\n\n" +
                "Paragraph: " + paragraph;

        Map<String, Object> body = Map.of(
                "model", "arcee-ai/trinity-large-preview:free",
                "messages", List.of(Map.of("role", "user", "content", prompt)),
                "reasoning", Map.of("enabled", true)
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                String.class
        );

        try {
            ObjectMapper mapper = new ObjectMapper();
            OpenRouterResponse orResponse = mapper.readValue(response.getBody(), OpenRouterResponse.class);

            String jsonText = orResponse.getChoices().get(0).getMessage().getContent();
            // Extract JSON block
            int start = jsonText.indexOf("{");
            int end = jsonText.lastIndexOf("}") + 1;
            if (start >= 0 && end > start) {
                jsonText = jsonText.substring(start, end);
            }

            return mapper.readValue(jsonText, MCQResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
            return new MCQResponse(); // empty if fails
        }
    }





}
