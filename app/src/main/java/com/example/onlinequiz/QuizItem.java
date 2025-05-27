package com.example.onlinequiz;

public class QuizItem {
    int id;
    String question, choice1, choice2, choice3, choice4, answer;

    // Constructor with ID (for update/editing purposes)
    public QuizItem(int id, String question, String choice1, String choice2, String choice3, String choice4, String answer) {
        this.id = id;
        this.question = question;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        this.answer = answer;
    }

    // Constructor without ID (for inserting new questions)
//    public QuizItem(String question, String choice1, String choice2, String choice3, String choice4, String answer) {
//        this.question = question;
//        this.choice1 = choice1;
//        this.choice2 = choice2;
//        this.choice3 = choice3;
//        this.choice4 = choice4;
//        this.answer = answer;
//    }

    // Getters
    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getChoices() {
        return "1. " + choice1 + "\n2. " + choice2 + "\n3. " + choice3 + "\n4. " + choice4;
    }

    public String getAnswer() {
        return answer;
    }

    public String getChoice1() {
        return choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public String getChoice4() {
        return choice4;
    }
}
