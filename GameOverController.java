
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
	
	public void gotoMain(ActionEvent e) throws IOException
	{
		URL fxmlfile = getClass().getResource("MainScreen.fxml");
		URL cssfile = getClass().getResource("MainScreen.css");
		ScreenLoader.loadScreen(fxmlfile, cssfile, player, rootLol);
	}
	
	public void addScore(Integer value)
	{
		scores = LeaderboardControllerGlobal.deserialize();
		scores.add(new Score(value, player.getName()));
		Collections.sort(scores, new ScoreComparator());
		LeaderboardControllerGlobal.serialize(scores);
	}
	
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