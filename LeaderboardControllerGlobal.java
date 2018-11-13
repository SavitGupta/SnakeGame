
//@formatter:on
import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LeaderboardControllerGlobal
{
	@FXML
	private Button Main;
	@FXML
	private AnchorPane rootLol;
	
	public void returnToMain(ActionEvent e) throws IOException
	{
		// ((javafx.scene.Node) e.getSource()).getScene().getWindow().hide();
		// Stage primaryStage = new Stage();
		// Parent root =
		// FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
		// Scene scene = new Scene(root);
		// scene.getStylesheets().add(getClass().getResource("MainScreen.css").toExternalForm());
		// primaryStage.setScene(scene);
		// primaryStage.show();
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.25));
		fadeTransition.setNode(rootLol);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished((ActionEvent event) -> {
			Parent newParent;
			try
			{
				newParent = (AnchorPane) FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
				Scene newScene = new Scene(newParent);
				newScene.getStylesheets().add(getClass().getResource("MainScreen.css").toExternalForm());
				Stage primaryStage = (Stage) rootLol.getScene().getWindow();
				primaryStage.setScene(newScene);
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		});
		fadeTransition.play();
	}
}