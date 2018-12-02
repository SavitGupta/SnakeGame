
//@formatter:on
import java.io.IOException;
import java.net.URL;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Utility class for loading Screens with Fade Animation effect
 */
public class ScreenLoader
{
	/**
	 * Opens a new screen, with given FXML, CSS files on given pane and passes them the reference of player
	 * @param fxmlFile FXMLfile name, containing page layout
	 * @param cssFile CSS file name, containing page designs
	 * @param player Reference of Player, that opened the screen, to be passed to the next screen
	 * @param anchorPane Reference of AnchorPane on which the new screen is loaded
	 */
	public static void loadScreen(URL fxmlFile, URL cssFile, Player player, AnchorPane anchorPane)
	{
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.25));
		fadeTransition.setNode(anchorPane);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished((ActionEvent event) -> {
			Parent newParent;
			try
			{
				FXMLLoader loader = new FXMLLoader(fxmlFile);
				newParent = (AnchorPane) loader.load();
				MainScreenController cnt = loader.getController();
				cnt.setPlayer(player);
				Scene newScene = new Scene(newParent);
				newScene.getStylesheets().add(cssFile.toExternalForm());
				Stage primaryStage = (Stage) anchorPane.getScene().getWindow();
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