package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	
	static Scene timerScene;
	@Override
	public void start(Stage primaryStage) {
		try {
			/*FXMLLoader loader = new FXMLLoader(getClass().getResource("TimerGUI.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root,505,367);
			scene.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();*/
			
			Parent root = FXMLLoader.load(getClass().getResource("TimerGUI.fxml"));
			timerScene = new Scene(root,505,367);
			
			timerScene.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
			primaryStage.setTitle("TimerApp");
			primaryStage.setScene(timerScene);
			primaryStage.show();
	        
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
