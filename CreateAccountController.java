
//@formatter:on
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * A page for creating your account by entering your user name.
 *
 * @author AYUSH SHRIVASTAVA
 *
 */
public class CreateAccountController
{
	@FXML
	private TextField username;
	@FXML
	private Button Login;
	private String name;
	
	/**
	 *
	 * @param e An action event to know when the login button is pressed and to login as the player. Only allows new users to create accounts.
	 * @throws IOException
	 */
	public void createLogin(ActionEvent e) throws IOException
	{
		if (username.getText().equals(""))
		{
			AlertBox.display("WARNING", "ENTER A NON EMPTY USERNAME");
			return;
		}
		if (username.getText() != null)
		{
			this.name = username.getText();
		}
		System.out.println(this.name);
		((javafx.scene.Node) e.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Parent root;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
		root = loader.load();
		MainScreenController cnt = loader.getController();
		Player player = new Player(this.name);
		player.serialize();
		cnt.setPlayer(player);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("MainScreen.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Standard getter
	 * @return player name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * standard setter
	 * @param name sets name attribute
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}