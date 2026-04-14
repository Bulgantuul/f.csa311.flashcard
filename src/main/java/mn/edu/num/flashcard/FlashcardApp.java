package mn.edu.num.flashcard;

import mn.edu.num.flashcard.cli.CommandLineArgs;
import mn.edu.num.flashcard.loader.CardLoader;
import mn.edu.num.flashcard.model.Card;
import mn.edu.num.flashcard.organizer.*;
import mn.edu.num.flashcard.session.StudySession;
import picocli.CommandLine;
import java.util.List;

public class FlashcardApp {
    public static void main(String[] args) {
        CommandLineArgs cmd = new CommandLineArgs();
        CommandLine line = new CommandLine(cmd);
        try {
            line.parseArgs(args);
            if (cmd.help) {
                line.usage(System.out);
                return;
            }
            // validate order
            String order = cmd.order;
            if (!order.equals("random") && !order.equals("worst-first") && !order.equals("recent-mistakes-first")) {
                System.err.println("Invalid order. Use random, worst-first, recent-mistakes-first");
                System.exit(1);
            }
            if (cmd.repetitions < 1) {
                System.err.println("Repetitions must be >= 1");
                System.exit(1);
            }
            // load cards
            List<Card> cards = CardLoader.load(cmd.cardsFile);
            if (cards.isEmpty()) {
                System.err.println("No cards loaded.");
                System.exit(1);
            }
            // pick organizer
            CardOrganizer organizer;
            if (order.equals("random")) organizer = new RandomOrganizer();
            else if (order.equals("worst-first")) organizer = new WorstFirstOrganizer();
            else organizer = new RecentMistakesFirstSorter();
            // start session
            StudySession session = new StudySession(cards, organizer, cmd.repetitions, cmd.invertCards);
            session.run();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            line.usage(System.err);
            System.exit(1);
        }
    }
}