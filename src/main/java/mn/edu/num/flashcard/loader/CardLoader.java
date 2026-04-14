package mn.edu.num.flashcard.loader;

import mn.edu.num.flashcard.model.Card;
import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class CardLoader {
    public static List<Card> load(File file) throws Exception {
        return Files.lines(file.toPath())
                .filter(l -> l.contains("::"))
                .map(l -> {
                    String[] parts = l.split("::", 2);
                    return new Card(parts[0].trim(), parts[1].trim());
                })
                .collect(Collectors.toList());
    }
}