package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

public class TimerController {

    @FXML
    private TextField note;

    @FXML
    private Button resetTimerButton;

    @FXML
    private TextField seconds;

    @FXML
    private TextField hour;

    @FXML
    private Button startButton;

    @FXML
    private TextField minutes;

    @FXML
    private ProgressBar progressionBar;

    @FXML
    private Button darkButton;

    @FXML
    private Button lightButton;

    @FXML
    void darkMode(ActionEvent event) {
    	lightButton.setDisable(false);
    	darkButton.setDisable(true);
    }

    @FXML
    void lightMode(ActionEvent event) {
    	lightButton.setDisable(true);
    	darkButton.setDisable(false);
    }

    @FXML
    void startTimer(ActionEvent event) {

    }

    @FXML
    void resetTimer(ActionEvent event) {

    }

}
