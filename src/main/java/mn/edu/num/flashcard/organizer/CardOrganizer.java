package mn.edu.num.flashcard.organizer;

import mn.edu.num.flashcard.model.Card;
import java.util.List;
import java.util.Map;

public interface CardOrganizer {
    List<Card> organize(List<Card> cards, Map<Card, Integer> mistakes, List<Card> recentMistakes);
}