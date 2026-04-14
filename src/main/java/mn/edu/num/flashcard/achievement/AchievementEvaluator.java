package mn.edu.num.flashcard.achievement;

import mn.edu.num.flashcard.model.Card;
import mn.edu.num.flashcard.session.CardStats;
import java.util.Map;

public class AchievementEvaluator {
    public static void checkAndPrint(Map<Card, CardStats> stats, boolean lastRoundPerfect, double avgSec) {
        if (lastRoundPerfect) System.out.println(" CORRECT achievement!");
        for (CardStats s : stats.values()) {
            if (s.attempts > 5) { System.out.println(" REPEAT achievement!"); break; }
        }
        for (CardStats s : stats.values()) {
            if (s.correct >= 3) { System.out.println(" CONFIDENT achievement!"); break; }
        }
        if (avgSec < 5) System.out.println(" AVERAGE_TIME achievement!");
    }
}