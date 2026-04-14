package mn.edu.num.flashcard.organizer;

import mn.edu.num.flashcard.model.Card;
import java.util.*;

public class RandomOrganizer implements CardOrganizer {
    private Random rand = new Random();
    @Override
    public List<Card> organize(List<Card> cards, Map<Card, Integer> mistakes, Set<Card> recentMistakes) {
        List<Card> shuffled = new ArrayList<>(cards);
        Collections.shuffle(shuffled, rand);
        return shuffled;
    }
}