
//@formatter:on
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class LeaderboardControllerGlobal
{
	@FXML
	private Button Main;
	@FXML
	private AnchorPane rootLol;
	@FXML
	private VBox vbox;
	private Player player;
	
	public static ArrayList<Score> deserialize()
	{
		ObjectInputStream in = null;
		try
		{
			try
			{
				in = new ObjectInputStream(new FileInputStream("globalLeaderBoard.txt"));
				ArrayList<Score> scores = (ArrayList<Score>) in.readObject();
				System.out.println("size is global LeaderBoard is " + scores.size());
				return scores;
			}
			catch (FileNotFoundException e)
			{
				System.out.println("LeaderBoard not Found, New leaderBoard created.");
				return new ArrayList<>();
			}
			catch (IOException | ClassNotFoundException e)
			{
				System.out.println("LeaderBoard corrupted New leaderBoard created.");
				return new ArrayList<>();
			}
			finally
			{
				if (in != null)
				{
					in.close();
				}
			}
		}
		catch (IOException e)
		{
			return new ArrayList<>();
		}
	}
	
	public static void serialize(ArrayList<Score> toSerialize)
	{
		ObjectOutputStream out = null;
		try
		{
			out = new ObjectOutputStream(new FileOutputStream("globalLeaderBoard.txt"));
			out.writeObject(toSerialize);
		}
		catch (IOException e)
		{
			System.out.println("Serialization of LeaderBoard Failed");
		}
	}
	
	public void initialize()
	{
		ArrayList<Score> scores = deserialize();
		for (Score score : scores)
		{
			if (vbox.getChildren().size() > 10)
			{
				break;
			}
			HBox h1 = new HBox();
			Label l1 = new Label(score.getName());
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
	
	public void returnToMain(ActionEvent e) throws IOException
	{
		URL fxmlfile = getClass().getResource("MainScreen.fxml");
		URL cssfile = getClass().getResource("MainScreen.css");
		ScreenLoader.loadScreen(fxmlfile, cssfile, player, rootLol);
	}
	
	public void setPlayer(Player player)
	{
		this.player = player;
	}
}