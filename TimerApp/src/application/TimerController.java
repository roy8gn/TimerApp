package application;


import java.net.URL;
import java.util.ConcurrentModificationException;
import java.util.ResourceBundle;

import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;

public class TimerController implements Initializable {

    @FXML
    private TextField note;

    @FXML
    private Button resetTimerButton;

    @FXML
    private TextField seconds;

    @FXML
    private TextField hours;

    @FXML
    private Button startButton;

    @FXML
    private TextField minutes;

    @FXML
    private ProgressBar timerProgressionBar;

    @FXML
    private ProgressIndicator TimerIndicator;
    
    @FXML
    private Button darkButton;

    @FXML
    private Button lightButton;

    private boolean isRunning;
    private TimerController.Timer timer;
    private int secondesToRun;
    private MediaPlayer mediaPlayer;
    private Alert alert;
    private Alert wrongInputAlert;

    @Override
	public void initialize(URL location, ResourceBundle resources) { // Initialize Controller
		isRunning = false;

		lightButton.setDisable(true);
    	darkButton.setDisable(false);
    	
    	alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("TimerApp Reminder");
    	alert.setHeaderText("Time's Up.");
    	
    	wrongInputAlert = new Alert(AlertType.ERROR);
    	wrongInputAlert.setTitle("TimerApp Reminder");
    	wrongInputAlert.setHeaderText("Invalid Input");
    	wrongInputAlert.setContentText("Input Must be only numbers!\n"
    			+ "Hours allowed range: 0-99\n"
    			+ "Minutes and Seconds Allowed range: 0-59");
    	
    	try {
			String mp3 = "pokemon_route_1.mp3";
			URL resource = getClass().getResource(mp3);
			System.out.println(resource.toString());
			Media media = new Media(resource.toString());
			mediaPlayer = new MediaPlayer(media);
		}
		catch(MediaException ex) {
			System.out.println("ERROR while locating the alarm sound.");
		}
	}
    
    @FXML
    void darkMode(ActionEvent event) {
    	lightButton.setDisable(false);
    	darkButton.setDisable(true);
    	Main.timerScene.getStylesheets().clear();
    	Main.timerScene.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
    }

    @FXML
    void lightMode(ActionEvent event) {
    	lightButton.setDisable(true);
    	darkButton.setDisable(false);
    	Main.timerScene.getStylesheets().clear();
    	Main.timerScene.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
    }

    @FXML
    void startTimer(ActionEvent event) throws InterruptedException {
    	if(!(onlyNumbers(hours, 99) && onlyNumbers(minutes, 59) && onlyNumbers(seconds, 59))){
			wrongInputAlert.show();
			resetTimer();
			return;
		}
    	Thread taskThread = new Thread(new Runnable() {
    		@Override
    		public void run() {
    			
    			secondesToRun = getInt(seconds) + getInt(minutes)*60 + getInt(hours)*60*60;

    			hours.setEditable(false);
    			minutes.setEditable(false);
    			seconds.setEditable(false);
    			note.setEditable(false);
    			timer = new Timer(secondesToRun);
    			System.out.println(secondesToRun);
    			isRunning = true;

    			try {
    				timer.start();
    			}
    			catch(ConcurrentModificationException ex){}   
    			startButton.setDisable(true);

    		}});
    	taskThread.start();	
    }
    
    public void resetTimer() {
    	hours.setText("00");
    	minutes.setText("00");
    	seconds.setText("00");
    	note.clear();
    	hours.setEditable(true);
    	minutes.setEditable(true);
    	seconds.setEditable(true);
    	note.setEditable(true);
    	isRunning = false;
    	mediaPlayer.stop();
    	timerProgressionBar.setProgress(0);
    	TimerIndicator.setProgress(0);
    	startButton.setDisable(false);
    }
    
    @FXML
    void resetTimer(ActionEvent event){ // Reset Button is pressed	
    	resetTimer();
    }
	
    // Show the the number in his TextField.
    // If the number is lower than 10, it adds a 0 on the left (5 becomes 05).
	public void setTextForTextfield(TextField tf, int time) {
		if(time<10) {
			tf.setText("0"+time);
		}
		else {
			tf.setText(""+time);
		}
	}
	
	public int getInt(TextField tf) { // get the int value from a TextField
		 return Integer.parseInt(tf.getText().toString());
	}
	
	public boolean onlyNumbers(TextField tf, int max){ // Validation for the right input
		try {
		     int number = getInt(tf);
		     if(number>max || number<0) return false;
		}
		catch (NumberFormatException e) {
		     return false;
		}
		return true;
	}
	
	public class Timer extends Thread{ // the Timer thread

		private int secondsCount;
		private int minutesCount;
		private int hoursCount;
		private int originalNumberOfSeconds;
		public Timer(int seconds) {
			originalNumberOfSeconds = seconds;
		}
		
		@Override
		public void run() throws ConcurrentModificationException {
			double precentage;
			while(isRunning && secondesToRun>-1) {
				secondsCount = secondesToRun%60;
				minutesCount = (secondesToRun/60)%60; //getInt(minutes);
				hoursCount = secondesToRun/60/60;//getInt(hours);
				precentage = 1.0-((double)secondesToRun/(double)originalNumberOfSeconds);
				timerProgressionBar.setProgress(precentage);
				TimerIndicator.setProgress(precentage);
				
				setTextForTextfield(seconds, secondsCount);
				setTextForTextfield(minutes, minutesCount);
				setTextForTextfield(hours, hoursCount);
				secondesToRun--;
				System.out.println(secondesToRun);
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					System.out.println("ERROR");
				}
			}
			
			if(secondesToRun<1) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if(note.getText().toString().equals("")) {
							alert.setContentText("Time's Up.");
						}
						else {
							alert.setContentText(note.getText().toString());
						}
						mediaPlayer.play();
						alert.show();
					}
				});
			}
		}
	}
}
