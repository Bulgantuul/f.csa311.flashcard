package mn.edu.num.flashcard.organizer;

import mn.edu.num.flashcard.model.Card;
import java.util.*;

public class RecentMistakesFirstSorter implements CardOrganizer {
    @Override
    public List<Card> organize(List<Card> cards, Map<Card, Integer> mistakes, Set<Card> recentMistakes) {
        List<Card> wrong = new ArrayList<>();
        List<Card> correct = new ArrayList<>();
        for (Card c : cards) {
            if (recentMistakes.contains(c)) wrong.add(c);
            else correct.add(c);
        }
        wrong.addAll(correct);
        return wrong;
    }
}