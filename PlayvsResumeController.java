
//@formatter:on
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PlayvsResumeController
{
	@FXML
	private Button startNormal;
	@FXML
	private Button startBlind;
	@FXML
	private Button resume;
	@FXML
	private AnchorPane rootLol;
	private Main Game;
	private Player player;

	public void initialize(){
		resume.setDisable(Game == null);
	}

	public void setGame(Main game) {
		Game = game;
		resume.setDisable(Game == null);
	}

	public void setPlayer(Player player) {
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
				Game.start(primaryStage);
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		});
		fadeTransition.play();
	}
}