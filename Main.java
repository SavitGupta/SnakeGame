
//@formatter:on
import static java.lang.Math.abs;
import static java.lang.Math.floor;
import static java.lang.Math.max;
import static java.lang.Math.sqrt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Main extends Application implements Serializable
{
	private transient Pane root = new Pane();
	private int gameMode = 0;
	private transient Label scoreLabel = new Label();
	private transient Label sizeLabel = new Label();
	private transient Label sizeLabel2 = new Label();
	private transient ComboBox<String> dropdown = new ComboBox<>();
	private transient Rectangle shield;
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
	private int ShieldCheck = 0;
	private double distSinceBlock = 0;
	transient ImagePattern explosionImage = new ImagePattern(new Image(getClass().getResourceAsStream("exp.png")));

	public void setGameMode(int gameMode) {
		if(gameMode == 0){
			sizeLabel.setVisible(true);
			sizeLabel2.setVisible(false);
		}
		else{
			sizeLabel2.setVisible(true);
			sizeLabel.setVisible(false);
		}
		this.gameMode = gameMode;
	}

	public void serialize()
	{
		try
		{
			ObjectOutputStream out = null;
			try
			{
				for (BallToken b : balls)
				{
					b.prepareSerialize();
					// System.out.println(" needs to deserialize" +
					// String.valueOf(b.getTranslateX()) + " "
					// + String.valueOf(b.getCenterX()));
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
				out = new ObjectOutputStream(new FileOutputStream("game.txt"));
				out.writeObject(this);
			}
			finally
			{
				out.close();
			}
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage() + "\nIOException\n" + e.getStackTrace());
		}
	}
	
	public static Main deserialize()
	{
		ObjectInputStream in = null;
		Main m1 = null;
		try
		{
			try
			{
				in = new ObjectInputStream(new FileInputStream("game.txt"));
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
				m1.sizeLabel.setTranslateY(m1.s.gety());
				m1.sizeLabel.setTranslateY(m1.s.getx());
				m1.scoreLabel = new Label();
				m1.scoreLabel.setText(String.valueOf(m1.score));
				m1.dropdown = new ComboBox<>();
				m1.s.deserialize(m1.root);
				m1.setGameMode(m1.gameMode);
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
				in.close();
			}
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage() + "\nIOException in in.close()\n" + e.getStackTrace());
		}
		return m1;
	}
	
	private Parent createContent()
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
		a.setSpacing(60);
		a.setPadding(new Insets(10, 10, 10, 10));
		scoreLabel.setTextFill(Color.DEEPPINK);
		scoreLabel.setStyle("-fx-font-weight: bold;");
		a.getChildren().add(scoreLabel);
		shield = new Rectangle(20, 20);
		Image mag2 = new Image(getClass().getResourceAsStream("shieldoff.png"));
		shield.setFill(new ImagePattern(mag2));
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
		return root;
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
			serialize();
			System.exit(0);
		}
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
		ShieldCheck = 0;
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
		a.setSpacing(60);
		a.setPadding(new Insets(10, 10, 10, 10));
		scoreLabel.setTextFill(Color.DEEPPINK);
		scoreLabel.setStyle("-fx-font-weight: bold;");
		a.getChildren().add(scoreLabel);
		shield = new Rectangle(20, 20);
		Image mag2 = new Image(getClass().getResourceAsStream("shieldoff.png"));
		shield.setFill(new ImagePattern(mag2));
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
			guess = random.nextInt(7) + 1;
			distSinceBlock = 0;
			addBlocks();
		}
		guess = random.nextInt(100);
		if (guess >= 75 + last)
		{
			t = 0;
			last = 0;
			guess = random.nextInt(82);
			int numofwalls = guess / 40;
			int cnt = 0;
			for (int i = 0; i < numofwalls; i++)
			{
				int guessx = random.nextInt(6) + 1;
				cnt++;
				int guessHeight = random.nextInt(50) + 40;
				if (!addWall(guessx, 60, guessHeight))
				{
					i -= 1;
				}
				if (cnt > 15)
				{
					break;
				}
			}
			// last -= numofwalls*0.01;
			guess = random.nextInt(40);
			int numoftokens = guess / 19;
			for (int i = 0; i < numoftokens; i++)
			{
				guess = random.nextInt(100000);
				int guessx = random.nextInt(440) + 30;
				int guessy = random.nextInt(30) + 60;
				if (guess < 60)
				{
					if (!addToken(guessx, guessy, "coin"))
						i -= 1;
				}
				else if (true)
				{
					int guessval = (int) floor(random.nextGaussian());
					if (!addBallToken(guessx, guessy, guessval + 3))
						i -= 1;
					else
						last += 0.1 * guessval;
				}
				else if (guess < 87)
				{
					if (!addToken(guessx, guessy, "magnet"))
						i -= 1;
					else
						last += 0.2;
				}
				else if (guess < 94)
				{
					if (!addToken(guessx, guessy, "brickbuster"))
						i -= 1;
					else
						last += 1;
				}
				else
				{
					if (!addToken(guessx, guessy, "shield"))
						i -= 1;
					else
						last += 1;
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
			flag |= s.intersection(w);
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
				flag |= s.intersection(b);
				if (flag)
				{
					System.out.println(String.valueOf("intersection with BLOCL" + s.getx()) + " : "
							+ String.valueOf(b.getTranslateX()));
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
			int cnt1 = 0;
			for (Block b : b1.getBlockrow())
			{
				System.out.println("cnt is " + String.valueOf(cnt1));
				flag |= s.intersection(b);
				cnt1++;
				if (flag)
				{
					System.out.println(String.valueOf("intersection with BLOCL" + s.getx()) + " : "
							+ String.valueOf(b.getTranslateX()));
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
			flag |= w.getBoundsInParent().intersects(first.getBoundsInParent());
		}
		for (Token t1 : tokens)
		{
			flag |= t1.getBoundsInParent().intersects(first.getBoundsInParent());
		}
		for (BallToken b1 : balls)
		{
			flag |= b1.getBoundsInParent().intersects(first.getBoundsInParent());
		}
		for (RowOfBlocks b2 : blocks)
		{
			for (Block b1 : b2.getBlockrow())
			{
				flag |= b1.getBoundsInParent().intersects(first.getBoundsInParent());
			}
		}
		if (flag)
		{
			System.out.println("SKIPPED");
		}
		return flag;
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
						if (ShieldOn == false)
						{
							if (s.getSize() > 0)
							{
								s.decLenghtBy(1);
								if (value2 > 5)
								{
									moveUp();
								}
								score += 1;
								scoreLabel.setText(Integer.toString(score));
								value = value - 1;
								hitter.getA().setText(Integer.toString(value));
								hitter.setValue(value);
								if (value == 0)
								{
									System.out.println("Size of children " + String.valueOf(root.getChildren().size()));
									System.out.println("hitter is removed " + String.valueOf(hitter));
									Rectangle r1 = new Rectangle(hitter.getTranslateX() + 15,
											hitter.getTranslateY() + 30, 20, 20);
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
							Rectangle r1 = new Rectangle(hitter.getTranslateX() + 15, hitter.getTranslateY() + 30, 20,
									20);
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
			System.exit(0);
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
					ShieldOn = true;
					root.getChildren().remove(t1);
					tokens.remove(t1);
					for (int j = 0; j < tokens.size(); j++)
					{
						if (tokens.get(j).getType().equals("Coin"))
						{
							TranslateTransition transition = new TranslateTransition();
							transition.setDuration(Duration.millis(500));
							transition.setToX(250);
							transition.setToY(710);
							transition.setNode(tokens.get(j));
							score += 2;
							transition.play();
						}
					}
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
							Rectangle r1 = new Rectangle(blocks.get(j).getBlockrow().get(m).getTranslateX() + 15,
									blocks.get(j).getBlockrow().get(m).getTranslateY() + 30, 20, 20);
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
	
	public void moveUp()
	{
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
		distSinceBlock += 0.5 * speedScale;
		speedScale = max(2 * sqrt(s.getSize()) / sqrt(4), 2);
		t += 0.05;
		ColorCheck += 1;
		if (ShieldOn == true)
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
		createContent();
		Scene scene = new Scene(root);
		scene.setOnKeyPressed(e -> {
			switch (e.getCode())
			{
				case A:
					if (GameOn == true)
					{
						moveLeft(10);
					}
					System.out.println("left");
					break;
				case LEFT:
					if (GameOn == true)
					{
						moveLeft(10);
					}
					System.out.println("left");
					break;
				case D:
					if (GameOn == true)
					{
						moveRight(10);
					}
					System.out.println("Right");
					break;
				case RIGHT:
					if (GameOn == true)
					{
						moveRight(10);
					}
					System.out.println("Right");
					break;
				case ENTER:
					if (GameOn == true)
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
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
