package mn.edu.num.flashcard;

import mn.edu.num.flashcard.cli.CommandLineArgs;
import mn.edu.num.flashcard.loader.CardLoader;
import mn.edu.num.flashcard.model.Card;
import mn.edu.num.flashcard.organizer.*;
import mn.edu.num.flashcard.session.StudySession;
import picocli.CommandLine;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class FlashcardApp {
    public static void main(String[] args) {
        // Хэрэв зөвхөн --interactive өгсөн эсвэл аргумент огт өгөөгүй бол interactive горим
        if (args.length == 0 || (args.length == 1 && args[0].equals("--interactive"))) {
            runInteractiveMode();
            return;
        }

        // Ердийн CLI горим (командын мөрийн аргументуудтай)
        CommandLineArgs cmd = new CommandLineArgs();
        CommandLine line = new CommandLine(cmd);
        try {
            line.parseArgs(args);
            if (cmd.help) {
                line.usage(System.out);
                return;
            }
            // validation
            String order = cmd.order;
            if (!order.equals("random") && !order.equals("worst-first") && !order.equals("recent-mistakes-first")) {
                System.err.println("Invalid order. Use random, worst-first, recent-mistakes-first");
                System.exit(1);
            }
            if (cmd.repetitions < 1) {
                System.err.println("Repetitions must be >= 1");
                System.exit(1);
            }
            List<Card> cards = CardLoader.load(cmd.cardsFile);
            if (cards.isEmpty()) {
                System.err.println("No cards loaded.");
                System.exit(1);
            }
            CardOrganizer organizer = getOrganizer(order);
            StudySession session = new StudySession(cards, organizer, cmd.repetitions, cmd.invertCards);
            session.run();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            line.usage(System.err);
            System.exit(1);
        }
    }

    // Интерактив горим – файлын замыг асуухгүй, шууд cards.txt-г ашиглана
    private static void runInteractiveMode() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Flashcard System - Interactive Menu ===");
        
        // 1. Картын файлыг тодорхойлох (энгийнээр cards.txt гэж үзнэ)
        File cardsFile = new File("cards.txt");
        List<Card> cards;
        try {
            cards = CardLoader.load(cardsFile);
            if (cards.isEmpty()) {
                System.err.println("Error: cards.txt not found or empty. Please make sure cards.txt exists in the current directory.");
                return;
            }
            System.out.println("Loaded " + cards.size() + " cards from cards.txt");
        } catch (Exception e) {
            System.err.println("Error loading cards.txt: " + e.getMessage());
            return;
        }
        
        // 2. Order сонголт – цэсээр
        System.out.println("\nChoose order:");
        System.out.println("  1. random");
        System.out.println("  2. worst-first");
        System.out.println("  3. recent-mistakes-first");
        System.out.print("Enter number (1-3) or name: ");
        String orderInput = scanner.nextLine().trim().toLowerCase();
        String order;
        if (orderInput.equals("1") || orderInput.equals("random")) order = "random";
        else if (orderInput.equals("2") || orderInput.equals("worst-first")) order = "worst-first";
        else if (orderInput.equals("3") || orderInput.equals("recent-mistakes-first")) order = "recent-mistakes-first";
        else {
            System.out.println("Invalid choice, using random.");
            order = "random";
        }
        System.out.println("Selected order: " + order);
        
        // 3. Repetitions
        System.out.print("Repetitions per card (default 1): ");
        String repStr = scanner.nextLine().trim();
        int repetitions = 1;
        if (!repStr.isEmpty()) {
            try {
                repetitions = Integer.parseInt(repStr);
                if (repetitions < 1) repetitions = 1;
            } catch (NumberFormatException e) {}
        }
        System.out.println("Repetitions: " + repetitions);
        
        // 4. Invert cards
        System.out.print("Invert cards (question/answer swap)? (y/n): ");
        String invertStr = scanner.nextLine().trim().toLowerCase();
        boolean invert = invertStr.equals("y") || invertStr.equals("yes");
        System.out.println("Invert cards: " + (invert ? "ON" : "OFF"));
        
        // 5. Organizer сонгох
        CardOrganizer organizer = getOrganizer(order);
        
        System.out.println("\nStarting study session...\n");
        StudySession session = new StudySession(cards, organizer, repetitions, invert);
        session.run();
    }
    
    private static CardOrganizer getOrganizer(String order) {
        switch (order) {
            case "worst-first":
                return new WorstFirstOrganizer();
            case "recent-mistakes-first":
                return new RecentMistakesFirstSorter();
            default:
                return new RandomOrganizer();
        }
    }
}