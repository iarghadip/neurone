import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rosetta {

    private Map<String, Integer> vocabulary = new HashMap<>();
    private int vocabIndex = 0;

    Rosetta() {
        memorize(read());
    }

    public List<String[]> read() {
        List<String[]> rows = new ArrayList<>();
        File dir = new File("Database");
        File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".csv"));
        if (files != null) {
            for (File file : files) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] fields = line.split(",", -1);
                        rows.add(fields);
                    }
                } catch (IOException e) {
                    System.err.println("ERROR: Failed to read file: " + file.getName());
                }
            }
        }
        return rows;
    }

    public void memorize(
        List<String[]> rows
    ) {
        for (String[] row : rows) {
            for (String token : tokenize(row[0])) {
                memorize(token);
            }
        }
    }

    public void memorize(
        String token
    ) {
        if (!vocabulary.containsKey(token)) {
            vocabulary.put(token, vocabIndex++);
        }
    }

    public String[] tokenize(
        String sentence
    ) {
        List<String> tokens = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        for (char c : sentence.toCharArray()) {
            if (Character.isLetter(c)) {
                buffer.append(Character.toLowerCase(c));
            } else {
                if (!buffer.isEmpty()) {
                    tokens.add(buffer.toString());
                    buffer.setLength(0);
                }
            }
        }
        if (!buffer.isEmpty()) {
            tokens.add(buffer.toString());
        }
        return tokens.toArray(new String[0]);
    }

    public double[] vectorize(
        String sentence
    ) {
        String[] tokens = tokenize(sentence);
        for (String token : tokens) {
            if (!vocabulary.containsKey(token)) {
                memorize(token);
            }
        }
        int size = vocabulary.size();
        List<Double> vector = new ArrayList<>(Collections.nCopies(size, 0.0));
        for (String token : tokens) {
            int index = vocabulary.get(token);
            vector.set(index, vector.get(index) + 1.0);
        }
        return vector.stream().mapToDouble(Double::doubleValue).toArray();
    }

}