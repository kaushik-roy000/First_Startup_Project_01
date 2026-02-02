package com.learn.boost.mapper;



import java.util.List;

public class MCQResponse {

    private List<Question> questions;

    public MCQResponse() {}

    public MCQResponse(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() { return questions; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }

    public static class Question {
        private String question;
        private List<String> options;
        private String answer;

        public Question() {}

        public Question(String question, List<String> options, String answer) {
            this.question = question;
            this.options = options;
            this.answer = answer;
        }

        public String getQuestion() { return question; }
        public void setQuestion(String question) { this.question = question; }

        public List<String> getOptions() { return options; }
        public void setOptions(List<String> options) { this.options = options; }

        public String getAnswer() { return answer; }
        public void setAnswer(String answer) { this.answer = answer; }
    }
}