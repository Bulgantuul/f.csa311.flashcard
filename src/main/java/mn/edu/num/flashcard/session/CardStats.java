package mn.edu.num.flashcard.session;

public class CardStats {
    public int correct = 0;
    public int attempts = 0;
    public void record(boolean ok) { attempts++; if (ok) correct++; }
    public int mistakes() { return attempts - correct; }
}