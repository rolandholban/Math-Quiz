import java.util.Date;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Project: Exam03HandsOn
 * Date:    4/19/17
 * Author:  rolandholban
 */

public class MathQuiz extends Application {
    
    // Create the two toggle groups for the radio button sets
    private final ToggleGroup operationGroup = new ToggleGroup();
    private final ToggleGroup difficultyGroup = new ToggleGroup();
    
    // Variable to hold the correct answer
    private int correctAnswer = 0;

    // Create a TextField to display score
    private final TextField tfScore = new TextField("0");

    // Create a TextField to display the problem
    private final TextField tfProblem = new TextField();

    // Create a TextField to display elapsed time
    private final TextField tfTime = new TextField("0");

    // Create a TextField for entering the answer
    private final TextField tfAnswer = new TextField();
    
    // Declare a timer
    private Timer timer;

    @Override
    public void start(Stage primaryStage) {

        // Create start/stop buttons
        Button btStart = new Button("Start");
        Button btStop = new Button("Stop");

        // Create radio buttons for each operation
        RadioButton rbAdd = new RadioButton("Add");
        RadioButton rbSubtract = new RadioButton("Subtract");
        RadioButton rbMultiply = new RadioButton("Multiply");
        RadioButton rbDivide = new RadioButton("Divide");

        // Set the toggle group for the operation buttons
        rbAdd.setToggleGroup(operationGroup);
        rbSubtract.setToggleGroup(operationGroup);
        rbMultiply.setToggleGroup(operationGroup);
        rbDivide.setToggleGroup(operationGroup);

        // Create a VBox to hold the operation buttons
        VBox vBox1 = new VBox(5, new Label("Choose an operation:"),
                rbAdd, rbSubtract, rbMultiply, rbDivide);
        vBox1.setPadding(new Insets(5,5,5,5));
        vBox1.setStyle("-fx-border-width: 1px; -fx-border-color: black");

        // Create radio buttons for difficulty
        RadioButton rbDif1 = new RadioButton("Numbers from 0 to 5");
        RadioButton rbDif2 = new RadioButton("Numbers from 3 to 9");
        RadioButton rbDif3 = new RadioButton("Numbers from 0 to 20");
        RadioButton rbDif4 = new RadioButton("Any two digits");

        // Set the toggle group for the difficulty buttons
        rbDif1.setToggleGroup(difficultyGroup);
        rbDif2.setToggleGroup(difficultyGroup);
        rbDif3.setToggleGroup(difficultyGroup);
        rbDif4.setToggleGroup(difficultyGroup);

        // Create a VBox to hold the difficulty buttons
        VBox vBox2 = new VBox(5, new Label("Choose a difficulty:"),
                rbDif1, rbDif2, rbDif3, rbDif4);
        vBox2.setPadding(new Insets(5,5,5,5));
        vBox2.setStyle("-fx-border-width: 1px; -fx-border-color: black");

        // Create a hBox1 to hold the two button panes
        HBox hBox1 = new HBox(5, vBox1, vBox2);
        hBox1.setPadding(new Insets(5,5,5,5));

        // Adjust TextField settings
        tfScore.setPrefColumnCount(10);
        tfScore.setDisable(true);
        tfProblem.setPrefColumnCount(10);
        tfProblem.setDisable(true);
        tfProblem.setAlignment(Pos.CENTER_RIGHT);
        tfTime.setDisable(true);

        // Create the bottom left VBox
        VBox vBox3 = new VBox(5, new Label("Question:"), tfProblem);

        // Create bottom right VBox
        VBox vBox4 = new VBox(5, new Label("Answer:"),
                tfAnswer, new HBox(5, btStart, btStop));
        
        // Create a hBox1 to hold the two bottom VBoxes
        HBox hBox2 = new HBox(5, vBox3, vBox4);
        hBox2.setPadding(new Insets(5,5,5,5));

        // Create the main pane
        HBox hBox3 = new HBox(5,
                new VBox(5, new Label ("Correct count:"), tfScore),
                new VBox(5, new Label("Elapsed time:"), tfTime));
        hBox3.setPadding(new Insets(5,5,5,5));
        BorderPane subMainPane =
                new BorderPane(null, hBox2, null, hBox3, null);
        BorderPane mainPane = new BorderPane();
        mainPane.setTop(hBox1);
        mainPane.setBottom(subMainPane);

        // Create a scene and place it in the stage
        Scene scene = new Scene(mainPane);
        primaryStage.setTitle("Math Quiz");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Start/Stop button event handlers
        btStart.setOnAction(e -> {
            startTimer();
            getQuestion();
            startQuiz();
        });
        btStop.setOnAction(e -> {
            stopQuiz();         
        });
    }

    // Calculates and displays the quiz elapsed time
    private void startTimer() {

        // Initialize a new timer
        timer = new Timer();
        
        // Create a timer task
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                tfTime.setText(Integer.toString(
                        Integer.parseInt(tfTime.getText()) + 1));
            }
        };
        // Execute the timer task every second
        timer.scheduleAtFixedRate(timerTask, new Date(), 1000L);
    }

    // Displays a new question
    private void getQuestion() {
        
        // Operands
        int x = 0;
        int y = 0;
        
        // Initialize the operands based on user specification
        switch (((RadioButton)(difficultyGroup.getSelectedToggle())).getText()) {
            case "Numbers from 0 to 5": 
                x = (int)(Math.random() * 6);
                y = (int)(Math.random() * 6);
                break;
            case "Numbers from 3 to 9":
                x = ((int)(Math.random() * 7)) + 3;
                y = ((int)(Math.random() * 7)) + 3;
                break;
            case "Numbers from 0 to 20": 
                x = (int)(Math.random() * 21);
                y = (int)(Math.random() * 21);
                break;
            case "Any two digits":
                x = (int)(Math.random() * 100);
                y = (int)(Math.random() * 100);
                break;
        }

        // Prevent problems prompting division by 0
        if (((RadioButton)(operationGroup.getSelectedToggle())).getText().equals("Divide"))
            while (y == 0)
                switch (((RadioButton)(difficultyGroup.getSelectedToggle())).getText()) {
                    case "Numbers from 0 to 5": y = (int)(Math.random() * 6); break;
                    case "Numbers from 0 to 20": y = (int)(Math.random() * 21); break;
                    case "Any two digits": y = (int)(Math.random() * 100); break;
                }

        // Display the correct operation based on user selection
        switch (((RadioButton)(operationGroup.getSelectedToggle())).getText()) {
            case "Add": tfProblem.setText(x + " + " + y + " =");
                        correctAnswer = x + y;
                        break;
            case "Subtract": tfProblem.setText(x + " - " + y + " =");
                        correctAnswer = x - y;
                        break;
            case "Multiply": tfProblem.setText(x + " * " + y + " ="); 
                             correctAnswer = x * y;
                             break;
            case "Divide": tfProblem.setText(x + " / " + y + " =");
                           correctAnswer = x / y;
                           break;
        }
    }

    // Start the quiz
    private void startQuiz() {
        // Increment score if answer is correct
        tfAnswer.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) 
                if (Integer.parseInt(tfAnswer.getText()) == correctAnswer) {
                    tfScore.setText(Integer.toString(
                            Integer.parseInt(tfScore.getText()) + 1));
                    // Display a new question
                    getQuestion();
                }
        });
    }
    
    // Resets score and timer
    private void stopQuiz() {
        tfScore.setText("0");
        timer.cancel();
        tfTime.setText("0");
    }
    
    // Main method
    public static void main(String[] args) {
        launch();
    }
}