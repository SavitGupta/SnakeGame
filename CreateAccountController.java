
//@formatter:on
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CreateAccountController
{
	@FXML
	private TextField username;
	@FXML
	private Button Login;
	private String name;
	
	public void createLogin(ActionEvent e) throws IOException
	{
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
		MainScreenController cnt= loader.getController();
		Player player = new Player(this.name);
		player.serialize();
		cnt.setPlayer(player);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("MainScreen.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
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