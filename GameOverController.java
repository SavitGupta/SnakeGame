
//@formatter:on
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * A screen which is displayed when the game gets over, that is your snake has died. It displays your last score along with a congratulations message if you set a global high score.
 *
 * @author AYUSH SHRIVASTAVA
 *
 */
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
	private Player player;
	private ArrayList<Score> scores;


	/**
	 * method for Handling event for Main Screen button, and returning to main menu
	 * @param e An action event to know when the Go to Main button is pressed.
	 * @throws IOException
	 */
	public void gotoMain(ActionEvent e) throws IOException
	{
		URL fxmlfile = getClass().getResource("MainScreen.fxml");
		URL cssfile = getClass().getResource("MainScreen.css");
		ScreenLoader.loadScreen(fxmlfile, cssfile, player, rootLol);
	}

	/**
	 * Add player score to score arrayList
	 * @param value
	 */
	public void addScore(Integer value)
	{
		scores = LeaderboardControllerGlobal.deserialize();
		scores.add(new Score(value, player.getName()));
		Collections.sort(scores, ScoreComparator.getInstance());
		LeaderboardControllerGlobal.serialize(scores);
	}

	/**
	 * set, score and player according to parameters
	 */
	public void setScore(int score, Player player)
	{
		this.player = player;
		addScore(score);
		this.Score.setText(String.valueOf(score));
		if (player.getScores().size() == 1 || player.getScores().get(1).getValue() < score)
		{
			this.ScoreMsg.setVisible(true);
		}
	}
}