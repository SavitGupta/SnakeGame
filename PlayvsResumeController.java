
//@formatter:on
import java.io.IOException;
import java.net.URL;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PlayvsResumeController
{
	@FXML
	private Button startNormal;
	@FXML
	private Button startBlind;
	@FXML
	private Button startAlien;
	@FXML
	private Button resume;
	@FXML
	private Button Main;
	@FXML
	private AnchorPane rootLol;
	private Main Game;
	private Main_Aliens Game2;
	private Player player;
	
	public void initialize()
	{
		resume.setDisable(Game == null);
	}
	
	public void setGame(Main game)
	{
		Game = game;
		resume.setDisable(Game == null);
	}
	
	public void setGame2(Main_Aliens game)
	{
		Game2 = game;
		resume.setDisable(Game2 == null);
	}
	
	public void setPlayer(Player player)
	{
		this.player = player;
	}
	
	public void startGame(ActionEvent e) throws Exception
	{
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.25));
		fadeTransition.setNode(rootLol);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished((ActionEvent event) -> {
			try
			{
				((javafx.scene.Node) e.getSource()).getScene().getWindow().hide();
				Game = new Main();
				Stage primaryStage = new Stage();
				Game.setGameMode(1);
				Game.setPlayer(player);
				Game.start(primaryStage);
				Game.restart();
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		});
		fadeTransition.play();
	}
	
	public void startGameNormal(ActionEvent e) throws Exception
	{
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.25));
		fadeTransition.setNode(rootLol);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished((ActionEvent event) -> {
			try
			{
				((javafx.scene.Node) e.getSource()).getScene().getWindow().hide();
				Game = new Main();
				Game.setPlayer(player);
				Stage primaryStage = new Stage();
				Game.start(primaryStage);
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		});
		fadeTransition.play();
	}
	
	public void startGameAlien(ActionEvent e) throws Exception
	{
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.25));
		fadeTransition.setNode(rootLol);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished((ActionEvent event) -> {
			try
			{
				((javafx.scene.Node) e.getSource()).getScene().getWindow().hide();
				Game2 = new Main_Aliens();
				Game2.setPlayer(player);
				Stage primaryStage = new Stage();
				Game2.start(primaryStage);
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		});
		fadeTransition.play();
	}
	
	public void resumeGame(ActionEvent e) throws Exception
	{
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.25));
		fadeTransition.setNode(rootLol);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished((ActionEvent event) -> {
			try
			{
				((javafx.scene.Node) e.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				if (Game != null)
				{
					Game.start(primaryStage);
				}
				else
				{
					Game2.start(primaryStage);
				}
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		});
		fadeTransition.play();
	}
	
	public void returnToMain(ActionEvent e) throws IOException
	{
		URL fxmlfile = getClass().getResource("MainScreen.fxml");
		URL cssfile = getClass().getResource("MainScreen.css");
		ScreenLoader.loadScreen(fxmlfile, cssfile, player, rootLol);
	}
}