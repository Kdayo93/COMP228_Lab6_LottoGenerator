import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.security.SecureRandom;
import java.util.*;

public class MainApp extends Application {

    private TextField minTextField;
    private TextField maxTextField;
    private TextField quantityTextField;
    private TextArea resultArea;

    private SecureRandom random = new SecureRandom();
    private static final int TOTAL_RUNS = 5; 

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        minTextField = new TextField();
        maxTextField = new TextField();
        quantityTextField = new TextField();
        resultArea = new TextArea();

        resultArea.setEditable(false);
        resultArea.setPrefHeight(200);

        Button generateButton = new Button("Generate Lotto Numbers");
        generateButton.setOnAction(e -> generateNumbers());

        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);

        inputGrid.add(new Label("Min:"), 0, 0);
        inputGrid.add(minTextField, 1, 0);
        inputGrid.add(new Label("Max:"), 0, 1);
        inputGrid.add(maxTextField, 1, 1);
        inputGrid.add(new Label("Quantity:"), 0, 2);
        inputGrid.add(quantityTextField, 1, 2);

        VBox root = new VBox(15, inputGrid, generateButton, resultArea);
        root.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(root, 400, 350);
        primaryStage.setTitle("Lotto Generator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void generateNumbers() {
        try {
            int min = Integer.parseInt(minTextField.getText());
            int max = Integer.parseInt(maxTextField.getText());
            int quantity = Integer.parseInt(quantityTextField.getText());

            StringBuilder output = new StringBuilder();
            List<List<Integer>> allResults = new ArrayList<>();

            for (int r = 1; r <= TOTAL_RUNS; r++) {
                Set<Integer> numbers = new TreeSet<>();
                while (numbers.size() < quantity) {
                    int num = random.nextInt(max - min + 1) + min;
                    numbers.add(num);
                }

                List<Integer> runResult = new ArrayList<>(numbers);
                allResults.add(runResult);

                output.append("Run ").append(r).append(": ")
                      .append(runResult.toString().replace("[", "").replace("]", ""))
                      .append("\n");
            }

            // Save to database
            for (List<Integer> result : allResults) {
                DatabaseHelper.saveResult(result);
            }

            resultArea.setText(output.toString());

        } catch (NumberFormatException ex) {
            resultArea.setText("❌ Invalid input. Please enter valid numbers.");
        }
    }
}

