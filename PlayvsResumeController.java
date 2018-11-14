
//@formatter:on
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
	private Button start;
	@FXML
	private Button resume;
	@FXML
	private AnchorPane rootLol;
	private Main Game;
	
	public void startGame(ActionEvent e) throws Exception
	{
		// ((javafx.scene.Node) e.getSource()).getScene().getWindow().hide();
		// Game = new Main();
		// Stage primaryStage = new Stage();
		// Game.start(primaryStage);
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
	
	public void resumeGame(ActionEvent e) throws Exception
	{
		// ((javafx.scene.Node) e.getSource()).getScene().getWindow().hide();
		// Game = new Main();
		// Stage primaryStage = new Stage();
		// Game.start(primaryStage);
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.25));
		fadeTransition.setNode(rootLol);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished((ActionEvent event) -> {
			try
			{
				((javafx.scene.Node) e.getSource()).getScene().getWindow().hide();
				Game = Main.deserialize();
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