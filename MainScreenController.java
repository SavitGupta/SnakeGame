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

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.lang.reflect.Type;
import java.util.ArrayList;
import static java.lang.Math.abs;

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
	private Button ex;

	private String name;
	private CreateAccountController lol;
	private LoginController lol2;
	private Main badiya;

	public void printName()
	{
		lol = new CreateAccountController();
		if(lol.getName()!= null)
		{
			this.username.setText(lol.getName());
		}
	}

	public void printName2()
	{
		lol2 = new LoginController();
		if(lol2.getName()!= null)
		{
			this.username.setText(lol2.getName());
		}
	}

	@FXML
	public void initialize()
	{
		printName();
		printName2();
    }

	public void startGame(ActionEvent e) throws IOException
	{
		((javafx.scene.Node)e.getSource()).getScene().getWindow().hide();
		badiya = new Main();
//
//		Stage primaryStage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
//        root.setStyle("-fx-background-color: indigo");
//        Scene scene = new Scene(root);
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }

	public void ILeader(ActionEvent e) throws IOException
	{
		((javafx.scene.Node)e.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("LeaderboardInd.fxml"));
        root.setStyle("-fx-background-color: honeydew ");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

	public void GLeader(ActionEvent e) throws IOException
	{
		((javafx.scene.Node)e.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("LeaderboardGrp.fxml"));
        root.setStyle("-fx-background-color: honeydew ");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

	public void Exit(ActionEvent e)
	{
		System.exit(0);
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