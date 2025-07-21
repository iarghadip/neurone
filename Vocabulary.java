import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vocabulary {

    private Map<String, Integer> vocabulary = new HashMap<>();
    private int vocabIndex = 0;

    Vocabulary() {

        try (BufferedReader br = new BufferedReader(new FileReader("database/training.csv"))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length != 2) continue;

                String label  = parts[0].trim();
                String sentence = parts[1].trim();

                for (String token : tokenize(sentence)) {
                    memorize(token, false);
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to train from CSV: " + e.getMessage());
        }

    }

    public void memorize(
        String token,
        boolean save
    ) {
        if (!vocabulary.containsKey(token)) {
            vocabulary.put(token, vocabIndex++);
            if (save && false) {
                
                // try (BufferedWriter bw = new BufferedWriter(new FileWriter("database/learned.txt", true))) {
                //     bw.write(token);
                //     bw.newLine();
                // } catch (IOException e) {
                //     System.err.println("Error saving vocabulary to file: " + e.getMessage());
                // }
            }
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

    public double[] vectorize(String sentence) {
        String[] tokens = tokenize(sentence);
        int size = vocabulary.size();
        List<Double> vector = new ArrayList<>(Collections.nCopies(size, 0.0));
        for (String token : tokens) {
            Integer index = vocabulary.get(token);
            if (index != null) {
                vector.set(index, 1.0); // Binary presence instead of count
            }
        }
        return vector.stream().mapToDouble(Double::doubleValue).toArray();
    }

    public int size() {
        return vocabulary.size();
    }

}
