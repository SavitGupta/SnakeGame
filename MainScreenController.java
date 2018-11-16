
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
	private CreateAccountController lol;
	private LoginController lol2;
	private Main game;
	private Player player;

	public void setPlayer(Player player) {
		username.setText(player.getName());
		this.player = player;
	}
	public void deserialize(){

		String filename = new String(player.getName()  + "_game.txt");
		game = Main.deserialize(filename);

	}

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
				PlayvsResumeController cnt= loader.getController();
				cnt.setPlayer(player);
				if(game != null){
					cnt.setGame(game);
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
//		}
//		else
//		{
//
//			FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.25));
//			fadeTransition.setNode(rootLol);
//			fadeTransition.setFromValue(1);
//			fadeTransition.setToValue(0);
//			fadeTransition.setOnFinished((ActionEvent event) -> {
//				try
//				{
//					game = new Main();
//					game.setPlayer(player);
//					((javafx.scene.Node) e.getSource()).getScene().getWindow().hide();
//					Stage primaryStage = new Stage();
//					game.startNormal(primaryStage);
//				}
//				catch (Exception e1)
//				{
//					e1.printStackTrace();
//				}
//			});
//			fadeTransition.play();
//		}
	}
	
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
				newParent = (AnchorPane) FXMLLoader.load(getClass().getResource("LeaderboardInd.fxml"));
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
				newParent = (AnchorPane) FXMLLoader.load(getClass().getResource("LeaderboardGrp.fxml"));
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
				newParent = (AnchorPane) FXMLLoader.load(getClass().getResource("MainScreen2.fxml"));
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