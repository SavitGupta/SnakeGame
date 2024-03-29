
//@formatter:on
import static java.lang.Math.abs;
import static java.lang.Math.floor;
import static java.lang.Math.max;
import static java.lang.Math.min;
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

public class Main_Aliens extends Application implements Serializable
{
	private static final long serialVersionUID = 99L;
	private transient Pane root = new Pane();
	private int gameMode = 0;
	private transient Label scoreLabel = new Label();
	private transient Label sizeLabel = new Label();
	private transient Label sizeLabel2 = new Label();
	private transient ComboBox<String> dropdown = new ComboBox<>();
	private transient ArrayList<Rectangle> bullets = new ArrayList<>();
	private transient Rectangle shield;
	private ArrayList<Wall> walls = new ArrayList<>();
	private ArrayList<Token> tokens = new ArrayList<>();
	private ArrayList<BallToken> balls = new ArrayList<>();
	private ArrayList<Enemy> enemies = new ArrayList<>();
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
	private int ShieldCheck = 0;
	private double distSinceBlock = 0;
	private transient ImagePattern explosionImage = new ImagePattern(new Image(getClass().getResourceAsStream("./Images/exp.png")));
	private Player player;
	private transient Stage mainStage;
	private transient Boolean alreadyGameover = false;
	private int pausetill = 0;

	/**
	 * Standard setter
	 * @param player value to be assigned player attribute
	 */
	public void setPlayer(Player player)
	{
		this.player = player;
	}

	/**
	 * Standard setter
	 * @param gameMode 0,1 value. 0 for Normal Mode, 1 for Blind Mode
	 */
	private void setGameMode(int gameMode)
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

	/**
	 *  Serializes the game, while storing the state of all non-serializable GUI elements in serializable elements.
	 *  The object is serialized to file in folder PlayerGameFiles with name: username + "_game.txt"
	 */
	private void serialize()
	{
		try
		{
			ObjectOutputStream out = null;
			try
			{
				for (Enemy b : enemies)
				{
					b.prepareSerialize();
				}
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
					//System.out.println("serialized");
				}
				s.prepareSerialize();
				out = new ObjectOutputStream(new FileOutputStream("PlayerGameFiles/" + player.getName() + "_game2.txt"));
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
			//System.out.println(e.getMessage() + "\nIOException\n" + e.getStackTrace());
		}
	}

	/**
	 * Deserializes object from given filename, uses non-GUI serializable state variables to initialize non-serializable GUI elements
	 * @param filename filename from which the object is read
	 * @return The object that was read from the file, with GUI elements also initialized
	 */
	public static Main_Aliens deserialize(String filename)
	{
		ObjectInputStream in = null;
		Main_Aliens m1 = null;
		try
		{
			try
			{
				in = new ObjectInputStream(new FileInputStream("PlayerGameFiles/" + filename));
				m1 = (Main_Aliens) in.readObject();
				m1.root = new Pane();
				m1.GameOn = true;
				m1.burst = new ArrayList<>();
				m1.bullets = new ArrayList<>();
				m1.alreadyGameover = false;
				for (BallToken b : m1.balls)
				{
					b.deserialize();
					m1.root.getChildren().add(b);
					m1.root.getChildren().add(b.getValueLabel());
				}
				for (Enemy b : m1.enemies)
				{
					b.deserialize();
					m1.root.getChildren().add(b);
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
					//System.out.println("deserialized");
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
				//System.out.println(e.getMessage() + "\nIOException in in.readobject()\n" + e.getStackTrace());
			}
			catch (ClassNotFoundException e)
			{
				//System.out.println(e.getMessage() + "\nClassNotFoundException\n" + e.getStackTrace());
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
			//System.out.println(e.getMessage() + "\nIOException in in.close()\n" + e.getStackTrace());
		}
		return m1;
	}

	/**
	 * Creates initial GUI elements present on all Games
	 */
	private void createContent()
	{
		explosionImage = new ImagePattern(new Image(getClass().getResourceAsStream("./Images/exp.png")));
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
		a.setSpacing(60);
		a.setPadding(new Insets(10, 10, 10, 10));
		scoreLabel.setTextFill(Color.DEEPPINK);
		scoreLabel.setStyle("-fx-font-weight: bold;");
		a.getChildren().add(scoreLabel);
		shield = new Rectangle(20, 20);
		if (ShieldOn)
		{
			Image mag = new Image(getClass().getResourceAsStream("./Images/shieldon.png"));
			shield.setFill(new ImagePattern(mag));
		}
		else
		{
			Image mag2 = new Image(getClass().getResourceAsStream("./Images/shieldoff.png"));
			shield.setFill(new ImagePattern(mag2));
		}
		a.getChildren().add(shield);
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
		if (timer == null)
		{
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
	}

	/**
	 * Method for taking action on the basis on option selected in the dropdown menu on the game screen.
	 * @param dropdown ComboBox whose options are to be evaluated
	 */
	private void getChoice(ComboBox<String> dropdown, ActionEvent e)
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

	/**
	 * Method serializing and then exiting the from game screen to main screen
	 */
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

	/**
	 * Restarts the game, resetting all state variables.
	 */
	private void restart()
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
		ShieldCheck = 0;
		distSinceBlock = 0;
		createContent();
	}

	/**
	 * Adds a BallToken at given x, y co-ordinates with given point value
	 * @param x x co-ordinate of the BallToken
	 * @param y y-co-ordinate of the BallToke
	 * @param value point value
	 * @return True if the BallToken was successfully added, false otherwise
	 */
	private boolean addBallToken(double x, double y, int value)
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
		root.getChildren().add(b1.getValueLabel());
		return true;
	}

	/**
	 * Adds an Enemy at given x, y co-ordinates with given point value
	 * @param x x co-ordinate of the Enemy
	 * @param y y-co-ordinate of the Enemy
	 * @return True if the Enemy was successfully added, false otherwise
	 */
	private boolean addEnemy(double x, double y)
	{
		Enemy b1 = new Enemy(x, y);
		if (checkAlreadyOccupied(b1))
		{
			return false;
		}
		enemies.add(b1);
		root.getChildren().add(b1);
		return true;
	}

	/**
	 * Adds a Token of specified type at specified x, y co-ordinates.
	 * @param x x co-ordinate of the Token
	 * @param y y-co-ordinate of the Token
	 * @param type Type of Token to be added
	 * @return True if the Token was successfully added, false otherwise
	 */
	private boolean addToken(double x, double y, String type)
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
			// Coins s1 = new Coins(x, y);
			// if (checkAlreadyOccupied(s1))
			// {
			// // //System.out.println("skipped");
			// return false;
			// }
			// tokens.add(s1);
			// root.getChildren().add(s1);
			addEnemy(x, y);
		}
		else if (type.equalsIgnoreCase("Magnet"))
		{
			// Magnet s1 = new Magnet(x, y);
			// if (checkAlreadyOccupied(s1))
			// {
			// // //System.out.println("skipped");
			// return false;
			// }
			// tokens.add(s1);
			// root.getChildren().add(s1);
		}
		else if (type.equalsIgnoreCase("BrickBuster"))
		{
			BrickBuster s1 = new BrickBuster(x, y);
			if (checkAlreadyOccupied(s1))
			{
				// //System.out.println("skipped");
				return false;
			}
			tokens.add(s1);
			root.getChildren().add(s1);
		}
		return true;
	}

	/**
	 * Adds a RowOfBlock at the top of screen.
	 */
	private void addBlocks()
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
		for (Enemy b1 : enemies)
		{
			if (rBlocks.intersection(b1.getBoundsInParent()))
			{
				enemies.remove(b1);
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

	/**
	 * Adds a Wall of specified height at specified x, y co-ordinates.
	 * @param x x co-ordinate of the Token
	 * @param y y-co-ordinate of the Token
	 * @param height height of wall to be added
	 * @return True if the wall was successfully added, false otherwise
	 */
	private boolean addWall(int x, double y, double height)
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

	/**
	 * Generates content on the basis, probabilistic models that depend upon the time since an object of same type was added, as well as snake size
	 * Increasing the chances of objects that haven't occurred recently
	 */
	private void generateContent()
	{
		Random random = new Random();
		int guess = random.nextInt(150);
		if (distSinceBlock > 350 && distSinceBlock + guess > 500)
		{
			t = 0;
			// //System.out.println("generating blocks");
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
				guess = random.nextInt(650);
				int guessx = random.nextInt(440) + 30;
				int guessy = random.nextInt(30) + 60;
				if (guess < 325)
				{
					if (!addToken(guessx, guessy, "coin"))
						i -= 1;
				}
				else if (guess < 450)
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
				else if (guess < 550)
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

	/**
	 * moves the GUI components of the snake to left.
	 * @param amt value(in pixel) by which the snake is moved
	 */
	private void moveLeft(double amt)
	{
		if (amt > 15)
		{
			//System.out.println("67152 moved left by " + amt);
			return;
		}
		s.moveLeft(amt);
		boolean flag = false;
		double dist = 0;
		for (Wall w : walls)
		{
			flag = s.intersection(w);
			if (flag)
			{
				//System.out.println(String.valueOf(s.getx()) + " : " + String.valueOf(w.getTranslateX()));
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
				flag = s.intersection(b) && b.getTranslateY() + 50 > s.gety();
				if (flag)
				{
					dist = abs(s.getx() - b.getTranslateX() - 67); // width of
					//System.out.println(String.valueOf("intersection with BLOCL left" + s.getx()) + " : " + String.valueOf(b.getTranslateX()) + " dist is " + dist);
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

	/**
	 * moves the GUI components of the snake to Right.
	 * @param amt value(in pixel) by which the snake is moved
	 */
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
				//System.out.println(String.valueOf(s.getx()) + " : " + String.valueOf(w.getTranslateX()));
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
				flag = s.intersection(b) && b.getTranslateY() + 50 > s.gety();
				if (flag)
				{
					dist = abs(s.getx() + 7 - b.getTranslateX()); // 7 is radius
					//System.out.println("intersection with BLOCL Right" + s.getx() + " : " + String.valueOf(b.getTranslateX()) + " dist is " + dist);
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
			//System.out.println("dist moved left " + String.valueOf(dist));
			s.moveLeft(dist);
		}
	}

	/**
	 * Check if specified object intersects with any of the already existing object in the main pane.
	 * @param first The Node whose collision is to be checked
	 * @return true, if there is an intersection, false otherwise
	 */
	private boolean checkAlreadyOccupied(Node first)
	{
		boolean flag = false;
		for (Wall w : walls)
		{
			flag = w.getBoundsInParent().intersects(first.getBoundsInParent());
			if (flag)
			{
				//System.out.println("SKIPPED " + first.getClass() + " " + w.getClass() + " " + w.getBoundsInParent());
				return true;
			}
		}
		for (Enemy e : enemies)
		{
			flag = e.getBoundsInParent().intersects(first.getBoundsInParent());
			if (flag)
			{
				//System.out.println("SKIPPED " + first.getClass() + " " + e.getClass() + " " + e.getBoundsInParent());
				return true;
			}
		}
		for (Token t1 : tokens)
		{
			flag = t1.getBoundsInParent().intersects(first.getBoundsInParent());
			if (flag)
			{
				//System.out.println("SKIPPED " + first.getClass() + " " + t1.getClass() + " " + t1.getBoundsInParent());
				return true;
			}
		}
		for (BallToken b1 : balls)
		{
			flag = b1.getBoundsInParent().intersects(first.getBoundsInParent());
			if (flag)
			{
				//System.out.println("SKIPPED " + first.getClass() + " " + b1.getClass() + " " + b1.getBoundsInParent());
				return true;
			}
		}
		for (RowOfBlocks b2 : blocks)
		{
			flag = b2.intersection(first.getBoundsInParent());
			if (flag)
			{
				//System.out.println("SKIPPED " + first.getClass() + " " + b2.getClass());
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks collision of snake with walls, and shifts it if required.
	 */
	private void deflectFromWalls()
	{
		Wall hitter;
		for (Wall w : walls)
		{
			if (s.intersection(w))
			{
				hitter = w;
				double dist = hitter.getTranslateX() - s.getx() + 5;
				//System.out.println("Inersection with wall head " + String.valueOf(dist));
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

	/**
	 * Checks collision of snake with Blocks, shifting it or reducing its size and removing the Block from the Main Pane of the game
	 */
	private void deflectFromBlocks()
	{
		for (RowOfBlocks w1 : blocks)
		{
			for (Block w : w1.getBlockrow())
			{
				if (s.intersection(w))
				{
					if (w.getTranslateY() < 390)
					{
						//System.out.println("location of block is " + String.valueOf(w.getTranslateY()));
						Block hitter = w;
						int value = hitter.getValue();
						int value2 = hitter.getInitialValue();
						//System.out.println("Value of block " + String.valueOf(value));
						//System.out.println("Value of snake " + String.valueOf(s.getSize()));
						if (!ShieldOn)
						{
							if (s.getSize() > 0)
							{
								if (value2 > 5)
								{
									s.decLenghtBy(1);
									moveUp();
									pausetill = 5;
									score += 1;
									scoreLabel.setText(Integer.toString(score));
									value = value - 1;
									hitter.getValueLabel().setText(Integer.toString(value));
									hitter.setValue(value);
								}
								else
								{
									//System.out.println(" attempt to decrease snake size 89212");
									int decvalby = min(value, s.getSize());
									s.decLenghtBy(decvalby);
									//System.out.println("successful attempt to decrease snake size 1723");
									score += decvalby;
									value -= decvalby;
								}
								if (value == 0)
								{
									//System.out.println("Size of children " + String.valueOf(root.getChildren().size()));
									//System.out.println("hitter is removed " + String.valueOf(hitter));
									Rectangle r1 = new Rectangle(hitter.getTranslateX() + 15, hitter.getTranslateY() + 30, 20, 20);
									root.getChildren().remove(hitter);
									root.getChildren().remove(hitter.getValueLabel());
									w1.getBlockrow().remove(hitter);
									Image mag = new Image(getClass().getResourceAsStream("./Images/exp.png"));
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
							root.getChildren().remove(hitter.getValueLabel());
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
	}

	/**
	 * Checks collision of snake with BallTokens, collecting and then removing them.
	 */
	private void deflectFromBalls()
	{
		BallToken hitter;
		for (BallToken w : balls)
		{
			if (s.intersection(w))
			{
				hitter = w;
				int value = Integer.parseInt(hitter.getValue());
				//System.out.println("Value of circle " + String.valueOf(value));
				//System.out.println("Value of snake " + String.valueOf(s.getSize()));
				s.incLenghtBy(value);
				Rectangle r2 = new Rectangle(w.getTranslateX() + 10, w.getTranslateY() + 20, 10, 10);
				Image mag2 = new Image(getClass().getResourceAsStream("./Images/expcoin.png"));
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
				root.getChildren().remove(hitter.getValueLabel());
				balls.remove(hitter);
			}
		}
	}

	/**
	 * Checks collision of snake with Tokens, collecting and then removing them.
	 */
	private void deflectFromTokens()
	{
		for (int i = 0; i < tokens.size(); i++)
		{
			Token t1 = tokens.get(i);
			if (s.intersection(t1))
			{
				//System.out.println("Token of type " + t1.getType());
				if (t1.getType().equals("Coin"))
				{
				}
				else if (t1.getType().equals("Magnet"))
				{
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
							root.getChildren().remove(blocks.get(j).getBlockrow().get(m).getValueLabel());
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
					Image mag2 = new Image(getClass().getResourceAsStream("./Images/expshield.png"));
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
					Image mag = new Image(getClass().getResourceAsStream("./Images/shieldon.png"));
					shield.setFill(new ImagePattern(mag));
					root.getChildren().remove(t1);
					tokens.remove(t1);
				}
			}
		}
	}

	/**
	 * Checks collision of snake with Enemy, set snake size to zero in case of collision
	 */
	private void deflectFromEnemy()
	{
		for (int i = 0; i < enemies.size(); i++)
		{
			Enemy t1 = enemies.get(i);
			if (s.intersection(t1))
			{
				//System.out.println("Enemy is hit by snake");
				s.decLenghtBy(s.getSize());
				// Rectangle r2 = new Rectangle(t1.getTranslateX(), t1.getTranslateY(), 10, 10);
				// Image mag2 = new Image(getClass().getResourceAsStream("./Images/expenemy.png"));
				// r2.setFill(new ImagePattern(mag2));
				// burst.add(r2);
				// root.getChildren().add(r2);
				// ScaleTransition scale2 = new ScaleTransition(Duration.seconds(1), r2);
				// scale2.setToX(5);
				// scale2.setToY(5);
				// scale2.setOnFinished((ActionEvent event) -> {
				// burst.remove(r2);
				// root.getChildren().remove(r2);
				// });
				// scale2.play();
				root.getChildren().remove(t1);
				enemies.remove(t1);
			}
		}
	}


	/**
	 * Checks collision of Bullets with Enemy, removing them incase of collision
	 */
	private void deflectFromEnemyBullet()
	{
		for (int i = 0; i < enemies.size(); i++)
		{
			Enemy t1 = enemies.get(i);
			for (int j = 0; j < bullets.size(); j++)
			{
				if (t1.intersection(bullets.get(j)))
				{
					//System.out.println("Enemy is hit by bullet");
					Rectangle r2 = new Rectangle(t1.getTranslateX(), t1.getTranslateY(), 8, 8);
					Image mag2 = new Image(getClass().getResourceAsStream("./Images/expenemy.png"));
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
					root.getChildren().remove(t1);
					enemies.remove(t1);
					root.getChildren().remove(bullets.get(j));
					bullets.remove(j);
				}
			}
		}
	}

	/**
	 * Method for adding score to player score, changing screen to gameOverScreen.
	 */
	private void gameover()
	{
		timer.stop();
		if (alreadyGameover)
		{
			return;
		}
		alreadyGameover = true;
		//System.out.println("in dgameover function 97123");
		player.addScore(score);
		for (int i = 0; i < player.getScores().size(); i++)
		{
			//System.out.println("Score " + String.valueOf(i) + " is " + String.valueOf(player.getScores().get(i).getValue()));
		}
		player.serialize();
		File file = new File("PlayerGameFiles/" + player.getName() + "_game2.txt");
		if (file.delete())
		{
			//System.out.println("File deleted successfully");
		}
		else
		{
			//System.out.println("Failed to delete the file");
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
		mainStage.setOnCloseRequest(e -> {
			mainStage.close();
		});
		fadeTransition.play();
		// System.exit(0);
	}

	/**
	 * Method for moving walls, BallTokens, Tokens, Blocks,Enemies upwards
	 * to simulate the effect of the snake being pushed back from collsion with row of block
	 */
	private void moveUp()
	{
		double movUpAmt = 1.2 * speedScale;
		distSinceBlock -= movUpAmt;
		for (int i = 0; i < walls.size(); i++)
		{
			Wall w = walls.get(i);
			w.setTranslateY(w.getTranslateY() - movUpAmt);
			if (w.getTranslateY() > 800)
			{
				root.getChildren().remove(w);
				walls.remove(w);
			}
		}
		for (int i = 0; i < balls.size(); i++)
		{
			BallToken w = balls.get(i);
			w.setTranslateY(w.getTranslateY() - movUpAmt);
			w.getValueLabel().setTranslateY(w.getTranslateY() - movUpAmt);
			if (w.getTranslateY() > 800)
			{
				root.getChildren().remove(w);
				balls.remove(w);
			}
		}
		for (int i = 0; i < tokens.size(); i++)
		{
			Token t1 = tokens.get(i);
			t1.moveDown(-(movUpAmt));
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
				w.setTranslateY(w.getTranslateY() - movUpAmt);
				w.getValueLabel().setTranslateY(w.getTranslateY() - movUpAmt);
			}
		}
		for (int i = 0; i < enemies.size(); i++)
		{
			Enemy w = enemies.get(i);
			w.setTranslateY(w.getTranslateY() - movUpAmt);
			if (w.getTranslateY() > 800)
			{
				root.getChildren().remove(w);
				enemies.remove(w);
			}
		}
	}

	/**
	 * Method for moving walls, BallTokens, Tokens, Blocks,Enemies downwards
	 * and removing them once they are off the screen
	 */
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
			w.getValueLabel().setTranslateY(w.getTranslateY() + 0.5 * speedScale);
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
				w.getValueLabel().setTranslateY(w.getTranslateY() + 0.5 * speedScale);
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
		for (int i = 0; i < bullets.size(); i++)
		{
			Rectangle w = bullets.get(i);
			w.setTranslateY(w.getTranslateY() - 1 * speedScale);
			if (w.getTranslateY() < 50)
			{
				root.getChildren().remove(w);
				bullets.remove(w);
				//System.out.println("Bullet Removed");
			}
		}
		for (int i = 0; i < enemies.size(); i++)
		{
			Enemy w = enemies.get(i);
			w.setTranslateY(w.getTranslateY() + 0.5 * speedScale);
			if (w.getTranslateY() > 750)
			{
				root.getChildren().remove(w);
				enemies.remove(w);
				score -= 2;
				if (score < 0)
				{
					score = 0;
				}
			}
		}
	}

	/**
	 * Updates the GameScreen at every call from animation timer, moving all BallTokens, walls, Tokens,Enemies and Blocks downward,
	 * checking for collisions, checking and updating timer for temporary power-ups
	 * @throws ConcurrentModificationException
	 */
	private void update() throws ConcurrentModificationException
	{
		if (s.getSize() <= 0)
		{
			gameover();
		}
		if (pausetill > 0)
		{
			pausetill -= 1;
			return;
		}
		distSinceBlock += 0.5 * speedScale;
		last -= 0.4 * speedScale;
		speedScale = max(2 * sqrt(s.getSize()), 5);
		t += 0.05;
		ColorCheck += 1;
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
			Image mag = new Image(getClass().getResourceAsStream("./Images/shieldoff.png"));
			shield.setFill(new ImagePattern(mag));
		}
		deflectFromTokens();
		deflectFromWalls();
		deflectFromBlocks();
		deflectFromEnemy();
		deflectFromEnemyBullet();
		if (t > 2)
		{
			generateContent();
		}
		if (s.getSize() <= 0)
		{
			//System.out.println("exited deflect from blocks 98230");
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

	/**
	 * Creates an Upward moving bullet, originating from head of the snake.
	 */
	public void shoot()
	{
		Rectangle r1 = new Rectangle(10, 20, Color.BLUE);
		ImagePattern img = new ImagePattern(new Image(getClass().getResourceAsStream("./Images/bullet.png")));
		r1.setFill(img);
		r1.setTranslateX(s.getx() - 5);
		r1.setTranslateY(s.gety() - 10);
		bullets.add(r1);
		root.getChildren().add(r1);
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
					break;
				case Z:
					if (GameOn)
					{
						shoot();
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
	

}