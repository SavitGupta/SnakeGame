
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
		URL fxmlfile = getClass().getResource("CreateAccount.fxml");
		URL cssfile = getClass().getResource("CreateAccount.css");
		ScreenLoader.loadScreen(fxmlfile,cssfile,player,rootLol);
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
			return;
		}
		System.out.println(" in login controller name is " + player.getName());
		URL fxmlfile = getClass().getResource("MainScreen.fxml");
		URL cssfile = getClass().getResource("MainScreen.css");
		ScreenLoader.loadScreen(fxmlfile,cssfile,player,rootLol);
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