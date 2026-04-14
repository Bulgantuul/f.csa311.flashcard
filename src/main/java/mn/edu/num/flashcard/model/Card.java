package mn.edu.num.flashcard.model;

public class Card {
    public final String question;
    public final String answer;
    public Card(String q, String a) { question = q; answer = a; }
    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }
}