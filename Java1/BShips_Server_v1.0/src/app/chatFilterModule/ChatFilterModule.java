package app.chatFilterModule;

import app.Player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatFilterModule {

    private static final String FILE_PATH = "res/text.txt";
    private static final String REPLACEMENT = "***";

    private HashMap<Player, Integer> violations = new HashMap<>();
    private ArrayList<Pattern> forbiddenWords = new ArrayList<>();

    public ChatFilterModule() {
        readFile();
    }

    private void readFile() {
            Path path = Paths.get(FILE_PATH);
        try {
            Files.lines(path).forEach(new Consumer<String>() {
                @Override
                public void accept(String s) {
                    forbiddenWords.add(Pattern.compile(s, Pattern.CASE_INSENSITIVE));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String clearMessage(String s) {
        String result = s;
        Matcher matcher;
        for (Pattern pat : forbiddenWords) {
            matcher = pat.matcher(result);
            while (matcher.find()) {
                System.err.println("match found! pattern: " + pat.pattern());
                result = matcher.replaceAll(REPLACEMENT);
            }
        }
        return result;
    }
}
