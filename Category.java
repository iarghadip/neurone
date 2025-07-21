import java.io.*;
import java.util.*;

public class Category {

    private List<String> categories;

    public Category() {
        categories = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("database/categories.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                categories.add(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Failed to load categories: " + e.getMessage());
        }
    }

    public double[] targetHotVector(String categoryLabel) {
        if (categories.isEmpty()) {
            throw new IllegalStateException("No categories loaded.");
        }

        int index = categories.indexOf(categoryLabel);
        if (index == -1) {
            throw new IllegalArgumentException(
                "Category not found: " + categoryLabel
            );
        }

        double[] hot = new double[categories.size()];
        hot[index] = 1.0;
        return hot;
    }

    public int getCategoryCount() {
        return categories.size();
    }

    public List<String> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public String getLabelFromVector(double[] hotVector) {
        if (hotVector == null || hotVector.length == 0 || categories.isEmpty()) {
            return "Unknown";
        }

        int index = -1;
        double max = -1;
        for (int i = 0; i < hotVector.length && i < categories.size(); i++) {
            if (hotVector[i] > max) {
                max = hotVector[i];
                index = i;
            }
        }

        return (index >= 0 && index < categories.size()) ? categories.get(index) : "Unknown";
    }
}
