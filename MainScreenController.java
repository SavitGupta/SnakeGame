
//@formatter:on
import java.io.IOException;

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
 * This is the controller for the main screen having 4 buttons that are start game, our both leader boards and a button to next screen.
 *
 * @author AYUSH SHRIVASTAVA
 *
 */
public class MainScreenController
{
	@FXML
	private Label username;
	@FXML
	private Button start;
	@FXML
	private Button gl;
	@FXML
	private Button il;
	@FXML
	private Button nx;
	@FXML
	private AnchorPane rootLol;
	private Main_Aliens game2;
	private Main game;
	private Player player;
	
	public void setPlayer(Player player)
	{
		username.setText(player.getName());
		this.player = player;
	}
	
	public void deserialize()
	{
		String filename2 = player.getName() + "_game2.txt";
		game2 = Main_Aliens.deserialize(filename2);
		String filename = player.getName() + "_game.txt";
		game = Main.deserialize(filename);
	}
	
	/**
	 * Takes you to the page for choosing game modes.
	 *
	 * @param e
	 *            Runs action on button press
	 * @throws Exception
	 */
	public void startGame(ActionEvent e) throws Exception
	{
		deserialize();
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.25));
		fadeTransition.setNode(rootLol);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished((ActionEvent event) -> {
			Parent newParent;
			try
			{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayvsResume.fxml"));
				newParent = (AnchorPane) loader.load();
				PlayvsResumeController cnt = loader.getController();
				cnt.setPlayer(player);
				if (game != null)
				{
					cnt.setGame(game);
				}
				else if (game2 != null)
				{
					cnt.setGame2(game2);
				}
				Scene newScene = new Scene(newParent);
				newScene.getStylesheets().add(getClass().getResource("PlayvsResume.css").toExternalForm());
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
	 * Opens the Individual leaderboard.
	 *
	 * @param e
	 * @throws IOException
	 */
	public void ILeader(ActionEvent e) throws IOException
	{
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.25));
		fadeTransition.setNode(rootLol);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished((ActionEvent event) -> {
			Parent newParent;
			try
			{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("LeaderboardInd.fxml"));
				newParent = (AnchorPane) loader.load();
				LeaderboardControllerIndividual leaderBoard = loader.getController();
				leaderBoard.setPlayer(player);
				Scene newScene = new Scene(newParent);
				newScene.getStylesheets().add(getClass().getResource("LeaderboardInd.css").toExternalForm());
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
	 * Opens the Global leaderboard.
	 *
	 * @param e
	 * @throws IOException
	 */
	public void GLeader(ActionEvent e) throws IOException
	{
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.25));
		fadeTransition.setNode(rootLol);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished((ActionEvent event) -> {
			Parent newParent;
			try
			{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("LeaderboardGrp.fxml"));
				newParent = (AnchorPane) loader.load();
				LeaderboardControllerGlobal leaderBoard = loader.getController();
				leaderBoard.setPlayer(player);
				Scene newScene = new Scene(newParent);
				newScene.getStylesheets().add(getClass().getResource("LeaderboardGrp.css").toExternalForm());
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
	
	public void Next(ActionEvent e)
	{
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.25));
		fadeTransition.setNode(rootLol);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished((ActionEvent event) -> {
			Parent newParent;
			try
			{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen2.fxml"));
				newParent = (AnchorPane) loader.load();
				MainScreen2Controller cnt = loader.getController();
				cnt.setPlayer(player);
				Scene newScene = new Scene(newParent);
				newScene.getStylesheets().add(getClass().getResource("MainScreen2.css").toExternalForm());
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
	
	public String getName()
	{
		return player.getName();
	}
}