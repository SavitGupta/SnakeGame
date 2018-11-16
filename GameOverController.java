
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

public class GameOverController
{
	@FXML
	private Label Score;
	@FXML
	private Label ScoreMsg;
	@FXML
	private Button MainPage;
	@FXML
	private AnchorPane rootLol;
	private int score;
	private Player player;
	
	public void gotoMain(ActionEvent e) throws IOException
	{
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.25));
		fadeTransition.setNode(rootLol);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished((ActionEvent event) -> {
			Parent newParent;
			try
			{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
				newParent = (AnchorPane) loader.load();
				MainScreenController cnt = loader.getController();
				cnt.setPlayer(player);
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
	
	public void setScore(int score, Player player)
	{
		this.score = score;
		this.player = player;
		this.Score.setText(String.valueOf(score));
		if (player.getScores().size() == 1 || player.getScores().get(1).getValue() < score)
		{
			this.ScoreMsg.setVisible(true);
		}
	}
}