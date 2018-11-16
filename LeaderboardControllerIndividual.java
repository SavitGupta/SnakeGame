
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LeaderboardControllerIndividual
{
	@FXML
	private Button Main;
	@FXML
	private VBox vbox;
	@FXML
	private AnchorPane rootLol;
	private Player player;
	
	public void returnToMain(ActionEvent e) throws IOException
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
	
	public void setPlayer(Player player)
	{
		this.player = player;
		for (Score score : player.getScores())
		{
			if (vbox.getChildren().size() > 10)
			{
				break;
			}
			HBox h1 = new HBox();
			Label l1 = new Label(player.getName());
			l1.setPrefHeight(51);
			l1.setPrefWidth(140);
			Label l2 = new Label(score.getValue().toString());
			l2.setPrefHeight(51);
			l2.setPrefWidth(90);
			Label l3 = new Label(score.getDate().toString());
			l3.setPrefHeight(51);
			l3.setPrefWidth(140);
			l1.setTextFill(Color.WHITE);
			l1.setStyle("-fx-font-weight: bold;");
			l2.setTextFill(Color.WHITE);
			l2.setStyle("-fx-font-weight: bold;");
			l3.setTextFill(Color.WHITE);
			l3.setStyle("-fx-font-weight: bold;");
			h1.getChildren().addAll(l1, l2, l3);
			vbox.getChildren().add(h1);
		}
	}
}