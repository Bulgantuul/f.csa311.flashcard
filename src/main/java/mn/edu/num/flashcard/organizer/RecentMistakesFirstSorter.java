package mn.edu.num.flashcard.organizer;

import mn.edu.num.flashcard.model.Card;
import java.util.*;

public class RecentMistakesFirstSorter implements CardOrganizer {
    @Override
    public List<Card> organize(List<Card> cards, Map<Card, Integer> mistakes, List<Card> recentMistakes) {
        List<Card> wrong = new ArrayList<>();
        // Буруу картуудыг сүүлийнхээс эхэнд нь эрэмбэлэх
        List<Card> reversed = new ArrayList<>(recentMistakes);
        Collections.reverse(reversed);
        for (Card c : reversed) {
            wrong.add(c);
        }
        // Үлдсэн картуудыг (зөв эсвэл буруу биш) анхны дарааллаар нэмэх
        for (Card c : cards) {
            if (!recentMistakes.contains(c)) {
                wrong.add(c);
            }
        }
        return wrong;
    }
}