
//@formatter:on
import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class InstructionsController
{
	@FXML
	private Button Main;
	@FXML
	private AnchorPane rootLol;
	@FXML
	private Player player;
	/**
	 * method for Handling event for Main Screen button, and returning to main menu
	 * @param e An action event to know when the Main Screen button is pressed.
	 * @throws IOException
	 */
	public void returnToMain(ActionEvent e) throws IOException
	{
		URL fxmlfile = getClass().getResource("MainScreen.fxml");
		URL cssfile = getClass().getResource("MainScreen.css");
		ScreenLoader.loadScreen(fxmlfile, cssfile, player, rootLol);
	}

	/**
	 * standard setter
	 * @param player sets player attribute to player
	 */
	public void setPlayer(Player player)
	{
		this.player = player;
	}
}