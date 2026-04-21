package mn.edu.num.flashcard.session;

import mn.edu.num.flashcard.model.Card;
import mn.edu.num.flashcard.organizer.CardOrganizer;
import java.util.*;

public class StudySession {
    List<Card> allCards;
    CardOrganizer organizer;
    int needCorrect;
    boolean invert;
    Map<Card, CardStats> stats = new HashMap<>();
    Set<Card> mastered = new HashSet<>();
    List<Card> recentMistakes = new ArrayList<>(); // өөрчлөлт
    boolean lastRoundPerfect = false;

    public StudySession(List<Card> cards, CardOrganizer org, int reps, boolean invert) {
        this.allCards = cards;
        this.organizer = org;
        this.needCorrect = reps;
        this.invert = invert;
        for (Card c : cards) stats.put(c, new CardStats());
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        long sessionStart = System.currentTimeMillis();
        long totalResponseMs = 0;

        while (mastered.size() < allCards.size()) {
            List<Card> active = new ArrayList<>();
            for (Card c : allCards) if (!mastered.contains(c)) active.add(c);
            Map<Card, Integer> mistakeMap = new HashMap<>();
            for (Card c : active) mistakeMap.put(c, stats.get(c).mistakes());
            List<Card> ordered = organizer.organize(active, mistakeMap, recentMistakes);
            boolean roundHasMistake = false;
            List<Card> newMistakes = new ArrayList<>(); // өөрчлөлт

            for (Card card : ordered) {
                if (mastered.contains(card)) continue;
                String question = invert ? card.answer : card.question;
                String correctAns = invert ? card.question : card.answer;
                System.out.print("\nQ: " + question + "\nYour answer: ");
                long start = System.currentTimeMillis();
                String ans = sc.nextLine().trim();
                long elapsed = System.currentTimeMillis() - start;
                totalResponseMs += elapsed;
                boolean ok = ans.equalsIgnoreCase(correctAns);
                stats.get(card).record(ok);
                if (ok) {
                    System.out.println("Correct!");
                    if (stats.get(card).correct >= needCorrect) {
                        mastered.add(card);
                        System.out.println("*** Mastered! ***");
                    }
                } else {
                    System.out.println("Wrong! Correct: " + correctAns);
                    roundHasMistake = true;
                    newMistakes.add(card);
                }
                if (stats.get(card).attempts > 5) System.out.println("🏆 REPEAT achievement!");
                if (stats.get(card).correct >= 3) System.out.println("🏆 CONFIDENT achievement!");
            }
            recentMistakes = newMistakes;
            if (!roundHasMistake && !active.isEmpty()) lastRoundPerfect = true;
        }

        long totalTime = System.currentTimeMillis() - sessionStart;
        double avgSec = totalResponseMs / 1000.0 / allCards.size();
        if (avgSec < 5) System.out.println("🏆 AVERAGE_TIME achievement!");
        if (lastRoundPerfect) System.out.println("🏆 CORRECT achievement!");
        System.out.println("Session complete!");
    }
}