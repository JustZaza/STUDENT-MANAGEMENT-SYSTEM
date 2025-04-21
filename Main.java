import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String inputTXT = "students.txt";
        String outputTXT = "results.txt";

        ArrayList<Integer> scores = new ArrayList<>();
        ArrayList<String> results = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("students.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                // ---split() splits the words in the string
                String[] parts = line.split(",");

                // ---.trim() removes whitespaces
                String name = parts[0].trim();

                /* ---line 28 converts the string into an int using
                        ---"Integer.parseInt(...)"---
                ---Line 29 adds scores into the scores arraylist
                */
                int score = Integer.parseInt(parts[1].trim());
                scores.add(score);

                String status = (score >= 50) ? "PASS" : "FAIL";
                results.add(name + ", " + score + ", " + status);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return;
        }

        //Calculate average

        /* ---scores.stream()
        *A stream allows you to process collections in a functional style
        (e.g. filtering, mapping, reducing, etc.)
         *This turns the scores list (which is an arraylist<Integer>)
          into a stream.
        */

        /* ---.mapToInt(Integer::intValue)
         *It converts each Integer to a primitive int using the method reference
          Integer::intValue
        */

        /* ---.average()
         *It returns an OptionalDouble, because
          the average might not exist (e.g., if the list is empty).
         *This calculates the average (mean) of all the int
          values in the stream.
        */

        /* ---.orElse(0.0)
         *If the stream was empty and there was no average to return,
          .orElse(0.0) gives a default value of 0.0.
         *This avoids errors or exceptions when trying to use the result
        */

        //Summary
        //This entire line is a safe and concise way to calculate the
        /*average score from a list of integers. If the list is empty,
        the average will just be 0.0.
        */
        double average = scores.stream().mapToInt(Integer::intValue).average().orElse(0.0);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt"))) {
            for (String result : results) {
                writer.write(result);
                writer.newLine();
            }
            writer.write(("Class Average: " + String.format("%.2f", average) + "%"));
        } catch (IOException e) {
            System.out.println("Error writing the file: " + e.getMessage());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("results.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
