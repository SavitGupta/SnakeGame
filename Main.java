
//@formatter:on
import static java.lang.Math.abs;
import static java.lang.Math.floor;
import static java.lang.Math.max;
import static java.lang.Math.sqrt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;

import com.sun.istack.internal.Nullable;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application implements Serializable
{
	private static long serialVersionUID = 101L;
	private transient Pane root = new Pane();
	private int gameMode = 0;
	private transient Label scoreLabel = new Label();
	private transient Label sizeLabel = new Label();
	private transient Label sizeLabel2 = new Label();
	private transient ComboBox<String> dropdown = new ComboBox<>();
	private transient Rectangle shield;
	private transient Rectangle magnet;
	private ArrayList<Wall> walls = new ArrayList<>();
	private ArrayList<Token> tokens = new ArrayList<>();
	private ArrayList<BallToken> balls = new ArrayList<>();
	private ArrayList<RowOfBlocks> blocks = new ArrayList<>();
	private transient ArrayList<Rectangle> burst = new ArrayList<>();
	private double speedScale = 1;
	private Snake s = new Snake(250, 450, 8, root, gameMode);
	private double last = 0;
	private int score = 0;
	private double t = 0;
	private transient AnimationTimer timer;
	private int ColorCheck = 0;
	private boolean GameOn = true;
	private boolean ShieldOn = false;
	private boolean MagnetOn = false;
	private int ShieldCheck = 0;
	private int MagnetCheck = 0;
	private double distSinceBlock = 0;
	private transient ImagePattern explosionImage = new ImagePattern(new Image(getClass().getResourceAsStream("exp.png")));
	private Player player;
	private Stage mainStage;
	
	public void setPlayer(Player player)
	{
		this.player = player;
	}
	
	public void setGameMode(int gameMode)
	{
		if (gameMode == 0)
		{
			sizeLabel.setVisible(true);
			sizeLabel2.setVisible(false);
		}
		else
		{
			sizeLabel2.setVisible(true);
			sizeLabel.setVisible(false);
		}
		this.gameMode = gameMode;
	}
	
	private void serialize()
	{
		try
		{
			ObjectOutputStream out = null;
			try
			{
				for (BallToken b : balls)
				{
					b.prepareSerialize();
				}
				for (Token t1 : tokens)
				{
					t1.prepareSerilize();
				}
				for (Wall w : walls)
				{
					w.prepareSerialize();
				}
				for (RowOfBlocks r : blocks)
				{
					r.prepareSerialize();
					System.out.println("serialized");
				}
				s.prepareSerialize();
				out = new ObjectOutputStream(new FileOutputStream(player.getName() + "_game.txt"));
				out.writeObject(this);
			}
			finally
			{
				if (out != null)
				{
					out.close();
				}
			}
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage() + "\nIOException\n" + e.getStackTrace());
		}
	}
	
	@Nullable
	public static Main deserialize(String filename)
	{
		ObjectInputStream in = null;
		Main m1 = null;
		try
		{
			try
			{
				in = new ObjectInputStream(new FileInputStream(filename));
				m1 = (Main) in.readObject();
				m1.root = new Pane();
				m1.GameOn = true;
				m1.burst = new ArrayList<>();
				for (BallToken b : m1.balls)
				{
					b.deserialize();
					m1.root.getChildren().add(b);
					m1.root.getChildren().add(b.getA());
				}
				for (Token t1 : m1.tokens)
				{
					t1.deserialize();
				}
				for (Wall w : m1.walls)
				{
					w.deserialize();
				}
				for (RowOfBlocks r : m1.blocks)
				{
					r.deserialize(m1.root);
					System.out.println("deserialized");
				}
				m1.root.getChildren().addAll(m1.tokens);
				m1.root.getChildren().addAll(m1.walls);
				m1.sizeLabel = new Label();
				m1.sizeLabel.setText(String.valueOf(m1.s.getSize()));
				m1.sizeLabel2 = new Label();
				m1.sizeLabel2.setText(String.valueOf(m1.s.getSize()));
				m1.sizeLabel.setTranslateY(m1.s.gety());
				m1.sizeLabel.setTranslateY(m1.s.getx());
				m1.sizeLabel2 = new Label();
				m1.sizeLabel2.setText(String.valueOf(m1.s.getSize()));
				m1.scoreLabel = new Label();
				m1.scoreLabel.setText(String.valueOf(m1.score));
				m1.dropdown = new ComboBox<>();
				m1.s.deserialize(m1.root);
				m1.setGameMode(m1.gameMode);
			}
			catch (FileNotFoundException e)
			{
				return null;
			}
			catch (IOException e)
			{
				System.out.println(e.getMessage() + "\nIOException in in.readobject()\n" + e.getStackTrace());
			}
			catch (ClassNotFoundException e)
			{
				System.out.println(e.getMessage() + "\nClassNotFoundException\n" + e.getStackTrace());
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
			System.out.println(e.getMessage() + "\nIOException in in.close()\n" + e.getStackTrace());
		}
		return m1;
	}
	
	private void createContent()
	{
		explosionImage = new ImagePattern(new Image(getClass().getResourceAsStream("exp.png")));
		root.setPrefSize(500, 700);
		root.setStyle("-fx-background-color: #000000;");
		sizeLabel.setTextFill(Color.DEEPPINK);
		sizeLabel.setStyle("-fx-font-weight: bold;");
		root.getChildren().add(sizeLabel);
		sizeLabel2.setTextFill(Color.DEEPPINK);
		sizeLabel2.setStyle("-fx-font-weight: bold;");
		root.getChildren().add(sizeLabel2);
		HBox a = new HBox();
		a.setPrefHeight(30);
		a.setPrefWidth(500);
		a.setStyle("-fx-background-color: #000000");
		a.setSpacing(40);
		a.setPadding(new Insets(10, 10, 10, 10));
		scoreLabel.setTextFill(Color.DEEPPINK);
		scoreLabel.setStyle("-fx-font-weight: bold;");
		a.getChildren().add(scoreLabel);
		shield = new Rectangle(20, 20);
		if (ShieldOn)
		{
			Image mag = new Image(getClass().getResourceAsStream("shieldon.png"));
			shield.setFill(new ImagePattern(mag));
		}
		else
		{
			Image mag2 = new Image(getClass().getResourceAsStream("shieldoff.png"));
			shield.setFill(new ImagePattern(mag2));
		}
		a.getChildren().add(shield);
		magnet = new Rectangle(20, 20);
		if (MagnetOn)
		{
			Image mag = new Image(getClass().getResourceAsStream("magneton.png"));
			magnet.setFill(new ImagePattern(mag));
		}
		else
		{
			Image mag2 = new Image(getClass().getResourceAsStream("magnetoff.png"));
			magnet.setFill(new ImagePattern(mag2));
		}
		a.getChildren().add(magnet);
		a.getChildren().add(sizeLabel2);
		dropdown.getItems().add("Pause");
		dropdown.getItems().add("Resume");
		dropdown.getItems().add("Restart");
		dropdown.getItems().add("Exit");
		dropdown.setPromptText("Options");
		dropdown.setOnAction(e -> getChoice(dropdown, e));
		a.getChildren().add(dropdown);
		root.getChildren().add(a);
		setGameMode(gameMode);
		timer = new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{
				try
				{
					update();
				}
				catch (Exception e)
				{
				}
			}
		};
		timer.start();
	}
	
	public void getChoice(ComboBox<String> dropdown, ActionEvent e)
	{
		if (dropdown.getValue().equals("Pause"))
		{
			timer.stop();
			GameOn = false;
		}
		else if (dropdown.getValue().equals("Restart"))
		{
			restart();
			timer.start();
			GameOn = true;
		}
		else if (dropdown.getValue().equals("Resume"))
		{
			timer.start();
			GameOn = true;
		}
		else if (dropdown.getValue().equals("Exit"))
		{
			forceExit();
		}
	}
	
	private void forceExit()
	{
		timer.stop();
		GameOn = false;
		serialize();
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.25));
		fadeTransition.setNode(root);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished((ActionEvent event) -> {
			Parent newParent;
			try
			{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
				newParent = (AnchorPane) loader.load();
				MainScreenController cnt = loader.getController();
				cnt.setPlayer(player);
				Scene newScene = new Scene(newParent);
				newScene.getStylesheets().add(getClass().getResource("MainScreen.css").toExternalForm());
				Stage primaryStage = (Stage) root.getScene().getWindow();
				primaryStage.setScene(newScene);
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		});
		// mainStage.removeEventHandler(eventType, eventHandler);(null);
		mainStage.setOnCloseRequest(e -> {
			mainStage.close();
		});
		fadeTransition.play();
	}
	
	public void restart()
	{
		root.getChildren().clear();
		s = new Snake(250, 450, 8, root, gameMode);
		blocks.clear();
		walls.clear();
		burst.clear();
		tokens.clear();
		dropdown = new ComboBox<>();
		score = 0;
		t = 0;
		ColorCheck = 0;
		GameOn = true;
		ShieldOn = false;
		MagnetOn = false;
		ShieldCheck = 0;
		MagnetCheck = 0;
		distSinceBlock = 0;
		root.setPrefSize(500, 700);
		root.setStyle("-fx-background-color: #000000;");
		sizeLabel.setTextFill(Color.DEEPPINK);
		sizeLabel.setStyle("-fx-font-weight: bold;");
		root.getChildren().add(sizeLabel);
		sizeLabel2.setTextFill(Color.DEEPPINK);
		sizeLabel2.setStyle("-fx-font-weight: bold;");
		root.getChildren().add(sizeLabel2);
		HBox a = new HBox();
		a.setPrefHeight(30);
		a.setPrefWidth(500);
		a.setStyle("-fx-background-color: #000000");
		a.setSpacing(40);
		a.setPadding(new Insets(10, 10, 10, 10));
		scoreLabel.setTextFill(Color.DEEPPINK);
		scoreLabel.setStyle("-fx-font-weight: bold;");
		a.getChildren().add(scoreLabel);
		shield = new Rectangle(20, 20);
		if (ShieldOn)
		{
			Image mag = new Image(getClass().getResourceAsStream("shieldon.png"));
			shield.setFill(new ImagePattern(mag));
		}
		else
		{
			Image mag2 = new Image(getClass().getResourceAsStream("shieldoff.png"));
			shield.setFill(new ImagePattern(mag2));
		}
		a.getChildren().add(shield);
		magnet = new Rectangle(20, 20);
		if (MagnetOn)
		{
			Image mag = new Image(getClass().getResourceAsStream("magneton.png"));
			magnet.setFill(new ImagePattern(mag));
		}
		else
		{
			Image mag2 = new Image(getClass().getResourceAsStream("magnetoff.png"));
			magnet.setFill(new ImagePattern(mag2));
		}
		a.getChildren().add(magnet);
		a.getChildren().add(sizeLabel2);
		dropdown.getItems().add("Pause");
		dropdown.getItems().add("Resume");
		dropdown.getItems().add("Restart");
		dropdown.getItems().add("Exit");
		dropdown.setPromptText("Options");
		dropdown.setOnAction(e -> getChoice(dropdown, e));
		a.getChildren().add(dropdown);
		root.getChildren().add(a);
		setGameMode(gameMode);
	}
	
	public boolean addBallToken(double x, double y, int value)
	{
		if (value <= 0)
		{
			return false;
		}
		if (value >= 10)
		{
			return false;
		}
		BallToken b1 = new BallToken(x, y, String.valueOf(value));
		if (checkAlreadyOccupied(b1))
		{
			return false;
		}
		balls.add(b1);
		root.getChildren().add(b1);
		root.getChildren().add(b1.getA());
		return true;
	}
	
	public boolean addToken(double x, double y, String type)
	{
		if (type.equalsIgnoreCase("Shield"))
		{
			Shield s1 = new Shield(x, y);
			if (checkAlreadyOccupied(s1))
			{
				return false;
			}
			tokens.add(s1);
			root.getChildren().add(s1);
		}
		else if (type.equalsIgnoreCase("Coin"))
		{
			Coins s1 = new Coins(x, y);
			if (checkAlreadyOccupied(s1))
			{
				// System.out.println("skipped");
				return false;
			}
			tokens.add(s1);
			root.getChildren().add(s1);
		}
		else if (type.equalsIgnoreCase("Magnet"))
		{
			Magnet s1 = new Magnet(x, y);
			if (checkAlreadyOccupied(s1))
			{
				// System.out.println("skipped");
				return false;
			}
			tokens.add(s1);
			root.getChildren().add(s1);
		}
		else if (type.equalsIgnoreCase("BrickBuster"))
		{
			BrickBuster s1 = new BrickBuster(x, y);
			if (checkAlreadyOccupied(s1))
			{
				// System.out.println("skipped");
				return false;
			}
			tokens.add(s1);
			root.getChildren().add(s1);
		}
		return true;
	}
	
	public void addBlocks()
	{
		RowOfBlocks rBlocks = new RowOfBlocks(s.getSize(), root);
		blocks.add(rBlocks);
		for (Token t1 : tokens)
		{
			if (rBlocks.intersection(t1.getBoundsInParent()))
			{
				tokens.remove(t1);
				root.getChildren().remove(t1);
			}
		}
		for (BallToken b1 : balls)
		{
			if (rBlocks.intersection(b1.getBoundsInParent()))
			{
				balls.remove(b1);
				root.getChildren().remove(b1);
			}
		}
		for (Wall w : walls)
		{
			if (rBlocks.intersection(w.getBoundsInParent()))
			{
				walls.remove(w);
				root.getChildren().remove(w);
			}
		}
	}
	
	public boolean addWall(int x, double y, double height)
	{
		Wall w1 = new Wall(x * 500 / 8, y, height);
		if (checkAlreadyOccupied(w1))
		{
			return false;
		}
		walls.add(w1);
		root.getChildren().add(w1);
		return true;
	}
	
	public void generateContent()
	{
		Random random = new Random();
		int guess = random.nextInt(150);
		if (distSinceBlock > 350 && distSinceBlock + guess > 500)
		{
			t = 0;
			// System.out.println("generating blocks");
			distSinceBlock = 0;
			addBlocks();
		}
		guess = random.nextInt(100);
		if (guess >= 75 + last)
		{
			t = 0;
			last = 0;
			guess = random.nextInt(100);
			int numofwalls = guess / 40;
			int cnt = 0;
			for (int i = 0; i < numofwalls; i++)
			{
				if (distSinceBlock < 40)
				{
					break;
				}
				int guessx = random.nextInt(6) + 1;
				cnt++;
				double guessHeight = Math.min(random.nextInt(150) + 40, floor(distSinceBlock));
				if (!addWall(guessx, 45, guessHeight))
				{
					i -= 1;
				}
				if (cnt > 25)
				{
					break;
				}
			}
			// last -= numofwalls*0.01;
			guess = random.nextInt(60);
			int numoftokens = guess / 19;
			int cnt1 = 0;
			for (int i = 0; i < numoftokens; i++)
			{
				cnt1++;
				if (cnt1 > 30)
				{
					break;
				}
				guess = random.nextInt(1000);
				int guessx = random.nextInt(440) + 30;
				int guessy = random.nextInt(30) + 60;
				if (guess < 600)
				{
					if (!addToken(guessx, guessy, "coin"))
						i -= 1;
				}
				else if (guess < 800)
				{
					int guessval = -1;
					while (guessval <= 0)
					{
						guessval = (int) floor(random.nextGaussian() + s.getSize() / 4);
					}
					if (!addBallToken(guessx, guessy, guessval))
						i -= 1;
					else
						last += guessval;
				}
				else if (guess < 900)
				{
					if (!addToken(guessx, guessy, "magnet"))
						i -= 1;
					else
						last += 2;
				}
				else if (guess < 950)
				{
					if (!addToken(guessx, guessy, "brickbuster"))
						i -= 1;
					else
						last += 10;
				}
				else
				{
					if (!addToken(guessx, guessy, "shield"))
						i -= 1;
					else
						last += 5;
				}
			}
		}
	}
	
	private void moveLeft(double amt)
	{
		s.moveLeft(amt);
		boolean flag = false;
		double dist = 0;
		for (Wall w : walls)
		{
			flag = s.intersection(w);
			if (flag)
			{
				System.out.println(String.valueOf(s.getx()) + " : " + String.valueOf(w.getTranslateX()));
				dist = abs(s.getx() - w.getTranslateX() - 10);
				break;
			}
		}
		if (flag)
		{
			s.moveRight(dist);
			return;
		}
		for (RowOfBlocks b1 : blocks)
		{
			for (Block b : b1.getBlockrow())
			{
				flag = s.intersection(b);
				if (flag)
				{
					System.out.println(String.valueOf("intersection with BLOCL" + s.getx()) + " : " + String.valueOf(b.getTranslateX()));
					dist = abs(s.getx() - b.getTranslateX() - 67); // width of
					// block
					// - radius
					// ( as
					// translatex
					// is
					// measured
					// form
					// top-l
					break;
				}
			}
			if (flag)
			{
				break;
			}
		}
		if (flag)
		{
			s.moveRight(dist);
		}
	}
	
	private void moveRight(double amt)
	{
		s.moveRight(amt);
		boolean flag = false;
		double dist = 0;
		for (Wall w : walls)
		{
			flag = s.intersection(w);
			if (flag)
			{
				System.out.println(String.valueOf(s.getx()) + " : " + String.valueOf(w.getTranslateX()));
				dist = abs(s.getx() - w.getTranslateX() + 8);
				break;
			}
		}
		if (flag)
		{
			s.moveLeft(dist);
			return;
		}
		for (RowOfBlocks b1 : blocks)
		{
			for (Block b : b1.getBlockrow())
			{
				flag = s.intersection(b);
				if (flag)
				{
					System.out.println(String.valueOf("intersection with BLOCL" + s.getx()) + " : " + String.valueOf(b.getTranslateX()));
					dist = abs(s.getx() + 7 - b.getTranslateX()); // 7 is radius
					// of
					// snake
					break;
				}
			}
			if (flag)
			{
				break;
			}
		}
		if (flag)
		{
			System.out.println("dist moved left " + String.valueOf(dist));
			s.moveLeft(dist);
		}
	}
	
	public boolean checkAlreadyOccupied(Node first)
	{
		boolean flag = false;
		for (Wall w : walls)
		{
			flag = w.getBoundsInParent().intersects(first.getBoundsInParent());
			if (flag)
			{
				System.out.println("SKIPPED " + first.getClass() + " " + w.getClass() + " " + w.getBoundsInParent());
				return true;
			}
		}
		for (Token t1 : tokens)
		{
			flag = t1.getBoundsInParent().intersects(first.getBoundsInParent());
			if (flag)
			{
				System.out.println("SKIPPED " + first.getClass() + " " + t1.getClass() + " " + t1.getBoundsInParent());
				return true;
			}
		}
		for (BallToken b1 : balls)
		{
			flag = b1.getBoundsInParent().intersects(first.getBoundsInParent());
			if (flag)
			{
				System.out.println("SKIPPED " + first.getClass() + " " + b1.getClass() + " " + b1.getBoundsInParent());
				return true;
			}
		}
		for (RowOfBlocks b2 : blocks)
		{
			flag = b2.intersection(first.getBoundsInParent());
			if (flag)
			{
				System.out.println("SKIPPED " + first.getClass() + " " + b2.getClass());
				return true;
			}
		}
		return false;
	}
	
	public void deflectFromWalls()
	{
		Wall hitter;
		for (Wall w : walls)
		{
			if (s.intersection(w))
			{
				hitter = w;
				double dist = hitter.getTranslateX() - s.getx() + 5;
				System.out.println("Inersection with wall head " + String.valueOf(dist));
				if (dist > 0)
				{
					s.moveLeft(12 - dist);
				}
				else
				{
					s.moveRight(6 + dist);
				}
				break;
			}
		}
	}
	
	public void deflectFromBlocks()
	{
		for (RowOfBlocks w1 : blocks)
		{
			for (Block w : w1.getBlockrow())
			{
				if (s.intersection(w))
				{
					if (w.getTranslateY() < 390)
					{
						System.out.println("location of block is " + String.valueOf(w.getTranslateY()));
						Block hitter = w;
						int value = hitter.getValue();
						int value2 = hitter.getInitialValue();
						System.out.println("Value of block " + String.valueOf(value));
						System.out.println("Value of snake " + String.valueOf(s.getSize()));
						if (!ShieldOn)
						{
							if (s.getSize() > 0)
							{
								if (value2 > 5)
								{
									s.decLenghtBy(1);
									moveUp();
									score += 1;
									scoreLabel.setText(Integer.toString(score));
									value = value - 1;
									hitter.getA().setText(Integer.toString(value));
									hitter.setValue(value);
								}
								else
								{
									System.out.println(" attempt to decrease snake size 89212");
									s.decLenghtBy(value);
									System.out.println("successful attempt to decrease snake size 1723");
									score += value;
									value = 0;
								}
								if (value == 0)
								{
									System.out.println("Size of children " + String.valueOf(root.getChildren().size()));
									System.out.println("hitter is removed " + String.valueOf(hitter));
									Rectangle r1 = new Rectangle(hitter.getTranslateX() + 15, hitter.getTranslateY() + 30, 20, 20);
									root.getChildren().remove(hitter);
									root.getChildren().remove(hitter.getA());
									w1.getBlockrow().remove(hitter);
									Image mag = new Image(getClass().getResourceAsStream("exp.png"));
									r1.setFill(new ImagePattern(mag));
									burst.add(r1);
									root.getChildren().add(r1);
									ScaleTransition scale1 = new ScaleTransition(Duration.seconds(1), r1);
									scale1.setToX(5);
									scale1.setToY(5);
									scale1.setOnFinished((ActionEvent event) -> {
										burst.remove(r1);
										root.getChildren().remove(r1);
									});
									scale1.play();
								}
							}
						}
						else
						{
							score += value;
							Rectangle r1 = new Rectangle(hitter.getTranslateX() + 15, hitter.getTranslateY() + 30, 20, 20);
							root.getChildren().remove(hitter);
							root.getChildren().remove(hitter.getA());
							w1.getBlockrow().remove(hitter);
							r1.setFill(explosionImage);
							burst.add(r1);
							root.getChildren().add(r1);
							ScaleTransition scale1 = new ScaleTransition(Duration.seconds(1), r1);
							scale1.setToX(5);
							scale1.setToY(5);
							scale1.setOnFinished((ActionEvent event) -> {
								burst.remove(r1);
								root.getChildren().remove(r1);
							});
							scale1.play();
						}
					}
				}
			}
		}
		if (s.getSize() == 0)
		{
			gameover();
		}
	}
	
	public void deflectFromBalls()
	{
		BallToken hitter;
		for (BallToken w : balls)
		{
			if (s.intersection(w))
			{
				hitter = w;
				int value = Integer.parseInt(hitter.getValue());
				System.out.println("Value of circle " + String.valueOf(value));
				System.out.println("Value of snake " + String.valueOf(s.getSize()));
				s.incLenghtBy(value);
				Rectangle r2 = new Rectangle(w.getTranslateX() + 10, w.getTranslateY() + 20, 10, 10);
				Image mag2 = new Image(getClass().getResourceAsStream("expcoin.png"));
				r2.setFill(new ImagePattern(mag2));
				burst.add(r2);
				root.getChildren().add(r2);
				ScaleTransition scale2 = new ScaleTransition(Duration.seconds(1), r2);
				scale2.setToX(5);
				scale2.setToY(5);
				scale2.setOnFinished((ActionEvent event) -> {
					burst.remove(r2);
					root.getChildren().remove(r2);
				});
				scale2.play();
				root.getChildren().remove(hitter);
				root.getChildren().remove(hitter.getA());
				balls.remove(hitter);
			}
		}
	}
	
	private void deflectFromTokens()
	{
		for (int i = 0; i < tokens.size(); i++)
		{
			Token t1 = tokens.get(i);
			if (s.intersection(t1))
			{
				System.out.println("Token of type " + t1.getType());
				if (t1.getType().equals("Coin"))
				{
					score += 2;
					root.getChildren().remove(t1);
					tokens.remove(t1);
				}
				else if (t1.getType().equals("Magnet"))
				{
					Rectangle r2 = new Rectangle(t1.getTranslateX() - 10, t1.getTranslateY(), 10, 10);
					Image mag2 = new Image(getClass().getResourceAsStream("expmagnet.png"));
					r2.setFill(new ImagePattern(mag2));
					burst.add(r2);
					root.getChildren().add(r2);
					ScaleTransition scale2 = new ScaleTransition(Duration.seconds(1), r2);
					scale2.setToX(5);
					scale2.setToY(5);
					scale2.setOnFinished((ActionEvent event) -> {
						burst.remove(r2);
						root.getChildren().remove(r2);
					});
					scale2.play();
					MagnetOn = true;
					Image mag = new Image(getClass().getResourceAsStream("magneton.png"));
					magnet.setFill(new ImagePattern(mag));
					MagnetCheck = 0;
					root.getChildren().remove(t1);
					tokens.remove(t1);
				}
				else if (t1.getType().equals("BrickBuster"))
				{
					int j = 0;
					while (blocks.size() > 0)
					{
						int m = 0;
						while (blocks.get(j).getBlockrow().size() > 0)
						{
							score += blocks.get(j).getBlockrow().get(m).getValue();
							Rectangle r1 = new Rectangle(blocks.get(j).getBlockrow().get(m).getTranslateX() + 15, blocks.get(j).getBlockrow().get(m).getTranslateY() + 30, 20, 20);
							root.getChildren().remove(blocks.get(j).getBlockrow().get(m));
							root.getChildren().remove(blocks.get(j).getBlockrow().get(m).getA());
							blocks.get(j).getBlockrow().remove(blocks.get(j).getBlockrow().get(m));
							r1.setFill(explosionImage);
							burst.add(r1);
							root.getChildren().add(r1);
							ScaleTransition scale1 = new ScaleTransition(Duration.seconds(1), r1);
							scale1.setToX(5);
							scale1.setToY(5);
							scale1.setOnFinished((ActionEvent event) -> {
								burst.remove(r1);
								root.getChildren().remove(r1);
							});
							scale1.play();
						}
						blocks.remove(blocks.get(j));
					}
					root.getChildren().remove(t1);
					tokens.remove(t1);
				}
				else if (t1.getType().equals("Shield"))
				{
					ShieldCheck = 0;
					Rectangle r2 = new Rectangle(t1.getTranslateX(), t1.getTranslateY(), 10, 10);
					Image mag2 = new Image(getClass().getResourceAsStream("expshield.png"));
					r2.setFill(new ImagePattern(mag2));
					burst.add(r2);
					root.getChildren().add(r2);
					ScaleTransition scale2 = new ScaleTransition(Duration.seconds(1), r2);
					scale2.setToX(5);
					scale2.setToY(5);
					scale2.setOnFinished((ActionEvent event) -> {
						burst.remove(r2);
						root.getChildren().remove(r2);
					});
					scale2.play();
					ShieldOn = true;
					Image mag = new Image(getClass().getResourceAsStream("shieldon.png"));
					shield.setFill(new ImagePattern(mag));
					root.getChildren().remove(t1);
					tokens.remove(t1);
				}
			}
		}
	}
	
	public void gameover()
	{
		timer.stop();
		System.out.println("in dgameover function 97123");
		player.addScore(score);
		for (int i = 0; i < player.getScores().size(); i++)
		{
			System.out.println("Score " + String.valueOf(i) + " is " + String.valueOf(player.getScores().get(i).getValue()));
		}
		player.serialize();
		File file = new File(player.getName() + "_game.txt");
		if (file.delete())
		{
			System.out.println("File deleted successfully");
		}
		else
		{
			System.out.println("Failed to delete the file");
		}
		GameOn = false;
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.25));
		fadeTransition.setNode(root);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setOnFinished((ActionEvent event) -> {
			Parent newParent;
			try
			{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOver.fxml"));
				newParent = (AnchorPane) loader.load();
				GameOverController cnt = loader.getController();
				cnt.setScore(score, player);
				Scene newScene = new Scene(newParent);
				newScene.getStylesheets().add(getClass().getResource("GameOver.css").toExternalForm());
				Stage primaryStage = (Stage) root.getScene().getWindow();
				primaryStage.setScene(newScene);
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		});
		fadeTransition.play();
		// System.exit(0);
	}
	
	public void moveUp()
	{
		distSinceBlock -= 3 * speedScale;
		for (int i = 0; i < walls.size(); i++)
		{
			Wall w = walls.get(i);
			w.setTranslateY(w.getTranslateY() - 3 * speedScale);
			if (w.getTranslateY() > 800)
			{
				root.getChildren().remove(w);
				walls.remove(w);
			}
		}
		for (int i = 0; i < balls.size(); i++)
		{
			BallToken w = balls.get(i);
			w.setTranslateY(w.getTranslateY() - 3 * speedScale);
			w.getA().setTranslateY(w.getTranslateY() - 3 * speedScale);
			if (w.getTranslateY() > 800)
			{
				root.getChildren().remove(w);
				balls.remove(w);
			}
		}
		for (int i = 0; i < tokens.size(); i++)
		{
			Token t1 = tokens.get(i);
			t1.moveDown(-(3 * speedScale));
			if (t1.getTranslateY() > 800)
			{
				root.getChildren().remove(t1);
				tokens.remove(t1);
			}
		}
		for (int i = 0; i < blocks.size(); i++)
		{
			for (int j = 0; j < blocks.get(i).getBlockrow().size(); j++)
			{
				Block w = blocks.get(i).getBlockrow().get(j);
				w.setTranslateY(w.getTranslateY() - 3 * speedScale);
				w.getA().setTranslateY(w.getTranslateY() - 3 * speedScale);
			}
		}
	}
	
	private void removeItems()
	{
		for (int i = 0; i < walls.size(); i++)
		{
			Wall w = walls.get(i);
			w.setTranslateY(w.getTranslateY() + 0.5 * speedScale);
			if (w.getTranslateY() > 750)
			{
				root.getChildren().remove(w);
				walls.remove(w);
			}
		}
		for (int i = 0; i < balls.size(); i++)
		{
			BallToken w = balls.get(i);
			w.setTranslateY(w.getTranslateY() + 0.5 * speedScale);
			w.getA().setTranslateY(w.getTranslateY() + 0.5 * speedScale);
			if (w.getTranslateY() > 750)
			{
				root.getChildren().remove(w);
				balls.remove(w);
			}
		}
		for (int i = 0; i < tokens.size(); i++)
		{
			Token t1 = tokens.get(i);
			t1.moveDown(0.5 * speedScale);
			if (t1.getTranslateY() > 750)
			{
				root.getChildren().remove(t1);
				tokens.remove(t1);
			}
		}
		for (int i = 0; i < blocks.size(); i++)
		{
			for (int j = 0; j < blocks.get(i).getBlockrow().size(); j++)
			{
				Block w = blocks.get(i).getBlockrow().get(j);
				w.setTranslateY(w.getTranslateY() + 0.5 * speedScale);
				w.getA().setTranslateY(w.getTranslateY() + 0.5 * speedScale);
				if (w.getTranslateY() > 750)
				{
					root.getChildren().remove(w);
					blocks.get(i).getBlockrow().remove(w);
				}
			}
			if (blocks.get(i).getBlockrow().size() == 0)
			{
				blocks.remove(i);
			}
		}
		for (int i = 0; i < burst.size(); i++)
		{
			Rectangle w = burst.get(i);
			w.setTranslateY(w.getTranslateY() + 0.5 * speedScale);
			if (w.getTranslateY() > 750)
			{
				root.getChildren().remove(w);
				burst.remove(w);
			}
		}
	}
	
	private void update() throws ConcurrentModificationException
	{
		if (s.getSize() <= 0)
		{
			gameover();
		}
		distSinceBlock += 0.5 * speedScale;
		last -= 0.4 * speedScale;
		speedScale = max(2 * sqrt(s.getSize()), 5);
		t += 0.05;
		ColorCheck += 1;
		if (MagnetOn)
		{
			for (int j = 0; j < tokens.size(); j++)
			{
				if (s.getSize() <= 0)
				{
					System.out.println("about to check movement 12356");
					double bullshit = s.getx();
					System.out.println("int got 7y23");
					boolean temp = tokens.get(j).getType().equals("Coin") && abs(tokens.get(j).getTranslateX() - s.getx()) < 250 && abs(tokens.get(j).getTranslateY() - s.gety()) < 250
							&& !tokens.get(j).isEnabled();
					System.out.println("checked the condition successfully 23e87");
				}
				if (tokens.get(j).getType().equals("Coin") && abs(tokens.get(j).getTranslateX() - s.getx()) < 250 && abs(tokens.get(j).getTranslateY() - s.gety()) < 250 && !tokens.get(j).isEnabled())
				{
					tokens.get(j).setEnabled(true);
					TranslateTransition transition = new TranslateTransition();
					transition.setDuration(Duration.millis(500));
					transition.setToX(250);
					transition.setToY(710);
					transition.setNode(tokens.get(j));
					score += 2;
					transition.play();
				}
			}
			MagnetCheck += 1;
		}
		if (MagnetCheck == 301)
		{
			MagnetCheck = 0;
			MagnetOn = false;
			Image mag = new Image(getClass().getResourceAsStream("magnetoff.png"));
			magnet.setFill(new ImagePattern(mag));
		}
		if (ShieldOn)
		{
			ShieldCheck += 1;
		}
		if (ColorCheck == 180)
		{
			s.animate(gameMode);
			ColorCheck = 0;
		}
		if (ShieldCheck == 301)
		{
			ShieldCheck = 0;
			ShieldOn = false;
			Image mag = new Image(getClass().getResourceAsStream("shieldoff.png"));
			shield.setFill(new ImagePattern(mag));
		}
		if (t > 2)
		{
			generateContent();
		}
		deflectFromTokens();
		deflectFromWalls();
		deflectFromBlocks();
		if (s.getSize() <= 0)
		{
			System.out.println("exited deflect from blocks 98230");
		}
		deflectFromBalls();
		removeItems();
		scoreLabel.setText("Score " + Integer.toString(score));
		sizeLabel.setText(Integer.toString(s.getSize()));
		sizeLabel2.setText("Size " + Integer.toString(s.getSize()));
		if (s.getSize() < 10)
		{
			sizeLabel.setTranslateX(s.getx() - 4);
		}
		else
		{
			sizeLabel.setTranslateX(s.getx() - 8);
		}
		sizeLabel.setTranslateY(420);
	}
	
	@Override
	public void start(Stage stage) throws Exception
	{
		mainStage = stage;
		createContent();
		Scene scene = new Scene(root);
		scene.setOnKeyPressed(e -> {
			switch (e.getCode())
			{
				case A:
					if (GameOn)
					{
						moveLeft(10);
					}
					break;
				case LEFT:
					if (GameOn)
					{
						moveLeft(10);
					}
					break;
				case D:
					if (GameOn)
					{
						moveRight(10);
					}
					break;
				case RIGHT:
					if (GameOn)
					{
						moveRight(10);
					}
					break;
				case ENTER:
					if (GameOn)
					{
						timer.stop();
						GameOn = false;
					}
					else
					{
						timer.start();
						GameOn = true;
					}
					break;
			}
		});
		stage.setOnCloseRequest(e -> {
			// e.consume();
			forceExit();
		});
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}