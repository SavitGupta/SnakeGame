
//@formatter:on
import java.io.IOException;
import java.net.URL;

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
	private String name;
	private Player player;
	
	public void createAccount(ActionEvent e) throws IOException
	{
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
		player = deserialize();
		if (player == null)
		{
			AlertBox.display("WARNING", "ENTER A VALID USERNAME");
			return;
		}
		System.out.println(" in login controller name is" + player.getName());
		URL fxmlfile = getClass().getResource("MainScreen.fxml");
		URL cssfile = getClass().getResource("MainScreen.css");
		ScreenLoader.loadScreen(fxmlfile, cssfile, player, rootLol);
	}
	
	public Player deserialize()
	{
		String filename = new String(name + ".txt");
		try
		{
			return Player.deserialize(filename);
		}
		catch (NoSuchPlayerException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
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