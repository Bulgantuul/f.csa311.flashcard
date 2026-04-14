package mn.edu.num.flashcard.cli;

import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import java.io.File;

public class CommandLineArgs {
    @Parameters(index = "0", description = "Cards file")
    public File cardsFile;

    @Option(names = "--help", usageHelp = true)
    public boolean help;

    @Option(names = "--order", defaultValue = "random")
    public String order;

    @Option(names = "--repetitions", defaultValue = "1")
    public int repetitions;

    @Option(names = "--invertCards", defaultValue = "false")
    public boolean invertCards;
}