package mn.edu.num.flashcard.organizer;

import mn.edu.num.flashcard.model.Card;
import java.util.*;

public class WorstFirstOrganizer implements CardOrganizer {
    @Override
    public List<Card> organize(List<Card> cards, Map<Card, Integer> mistakes, List<Card> recentMistakes) {
        List<Card> list = new ArrayList<>(cards);
        list.sort((a,b) -> Integer.compare(
            mistakes.getOrDefault(b,0), mistakes.getOrDefault(a,0)));
        return list;
    }
}