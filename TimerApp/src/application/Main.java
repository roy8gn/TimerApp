package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	
	static Scene timerScene;
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("TimerGUI.fxml"));
			timerScene = new Scene(root,505,367);
			
			timerScene.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
			primaryStage.setTitle("TimerApp");
			primaryStage.setScene(timerScene);
			primaryStage.setResizable(false);
			primaryStage.show();
	        
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
