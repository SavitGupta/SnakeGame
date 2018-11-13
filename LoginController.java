
//@formatter:on
import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController
{
	@FXML
	private TextField username;
	@FXML
	private Button Login;
	@FXML
	private Button CreateAccount;
	@FXML
	private AnchorPane rootLol;
	private static String name;
	
	public void createAccount(ActionEvent e) throws IOException
	{
		// ((javafx.scene.Node) e.getSource()).getScene().getWindow().hide();
		// Stage primaryStage = new Stage();
		// Parent root =
		// FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
		// Scene scene = new Scene(root);
		// scene.getStylesheets().add(getClass().getResource("CreateAccount.css").toExternalForm());
		// primaryStage.setScene(scene);
		// primaryStage.show();
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.25));
		fadeTransition.setNode(rootLol);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished((ActionEvent event) -> {
			Parent newParent;
			try
			{
				newParent = (AnchorPane) FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
				Scene newScene = new Scene(newParent);
				newScene.getStylesheets().add(getClass().getResource("CreateAccount.css").toExternalForm());
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
	
	public void createLogin(ActionEvent e) throws IOException
	{
		if (username.getText() != null)
		{
			this.name = username.getText();
		}
		// ((javafx.scene.Node) e.getSource()).getScene().getWindow().hide();
		// Stage primaryStage = new Stage();
		// Parent root =
		// FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
		// Scene scene = new Scene(root);
		// scene.getStylesheets().add(getClass().getResource("MainScreen.css").toExternalForm());
		// primaryStage.setScene(scene);
		// primaryStage.show();
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.25));
		fadeTransition.setNode(rootLol);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished((ActionEvent event) -> {
			Parent newParent;
			try
			{
				newParent = (AnchorPane) FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
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
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
}