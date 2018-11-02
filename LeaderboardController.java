import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.util.*;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LeaderboardController
{
	@FXML
	private Button Main;

	public void returnToMain(ActionEvent e) throws IOException
	{
		((javafx.scene.Node)e.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        root.setStyle("-fx-background-color: honeydew ");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}