
//@formatter:on
import java.io.IOException;
import java.net.URL;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller for Second page of main menu
 */
public class MainScreen2Controller
{
	@FXML
	private Label username;
	@FXML
	private Button lo;
	@FXML
	private Button cr;
	@FXML
	private Button pr;
	@FXML
	private Button ex;
	@FXML
	private AnchorPane rootLol;
	private String name;
	private Player player;
	private Main Game;

	/**
	 * standard setter
	 * @param player
	 */
	public void setPlayer(Player player)
	{
		this.player = player;
		this.username.setText(player.getName());
	}

	/**
	 * Opens instruction page.
	 * @param e An action event to know when the Instruction Button is pressed.
	 * @throws Exception
	 */
	public void openInstruction(ActionEvent e) throws Exception
	{
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.25));
		fadeTransition.setNode(rootLol);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished((ActionEvent event) -> {
			Parent newParent;
			try
			{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("Instructions.fxml"));
				newParent = (AnchorPane) loader.load();
				InstructionsController instructionBoard = loader.getController();
				instructionBoard.setPlayer(player);
				Scene newScene = new Scene(newParent);
				newScene.getStylesheets().add(getClass().getResource("Instructions.css").toExternalForm());
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

	/**
	 * open MainScreen page.
	 * @param e An action event to know when the Main Screen button is pressed.
	 * @throws IOException
	 */
	public void Previous(ActionEvent e) throws IOException
	{
		URL fxmlfile = getClass().getResource("MainScreen.fxml");
		URL cssfile = getClass().getResource("MainScreen.css");
		ScreenLoader.loadScreen(fxmlfile, cssfile, player, rootLol);
	}

	/**
	 *  Shift to Login Page
	 * @param e An action event to know when the Login Page button is pressed.
	 * @throws IOException
	 */
	public void LoginAgain(ActionEvent e) throws IOException
	{
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.25));
		fadeTransition.setNode(rootLol);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished((ActionEvent event) -> {
			Parent newParent;
			try
			{
				newParent = (AnchorPane) FXMLLoader.load(getClass().getResource("Login.fxml"));
				Scene newScene = new Scene(newParent);
				newScene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
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

	/**
	 *  Close the application
	 * @param e An action event to know when the Exit Page button is pressed.
	 * @throws IOException
	 */
	public void Exit(ActionEvent e)
	{
		System.exit(0);
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
}