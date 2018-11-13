
//@formatter:on
import static java.lang.Integer.min;
import static java.lang.Math.abs;
import static java.lang.Math.floor;
import static java.lang.Math.max;
import static java.lang.Math.sqrt;

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

public class Main extends Application
{
	private Pane root = new Pane();
	private Label scoreLabel = new Label();
	private Label sizeLabel = new Label();
	private ComboBox<String> dropdown = new ComboBox<>();
	private ArrayList<Wall> walls = new ArrayList<>();
	private ArrayList<Token> tokens = new ArrayList<>();
	private ArrayList<BallToken> balls = new ArrayList<>();
	private ArrayList<Block> blocks = new ArrayList<>();
	private ArrayList<Rectangle> burst = new ArrayList<>();
	private ArrayList<Rectangle> burst2 = new ArrayList<>();
	private double speedScale = 1;
	private Snake s = new Snake(250, 450, 8, root);
	private double last = 0;
	private int score = 0;
	private double t = 0;
	private AnimationTimer timer;
	private int ColorCheck = 0;
	private boolean GameOn = true;
	private boolean ShieldOn = false;
	private int ShieldCheck = 0;
	private double distSinceBlock = 0;
	ImagePattern mag1 = new ImagePattern(new Image(getClass().getResourceAsStream("exp.png")));
	private Parent createContent()
	{
		root.setPrefSize(500, 700);
		root.setStyle("-fx-background-color: #000000;");
		sizeLabel.setTextFill(Color.DEEPPINK);
		sizeLabel.setStyle("-fx-font-weight: bold;");
		root.getChildren().add(sizeLabel);
		HBox a = new HBox();
		a.setPrefHeight(30);
		a.setPrefWidth(500);
		a.setStyle("-fx-background-color: #000000");
		a.setSpacing(300);
		a.setPadding(new Insets(10, 10, 10, 10));
		scoreLabel.setTextFill(Color.DEEPPINK);
		scoreLabel.setStyle("-fx-font-weight: bold;");
		a.getChildren().add(scoreLabel);
		dropdown.getItems().add("Pause");
		dropdown.getItems().add("Resume");
		dropdown.getItems().add("Restart");
		dropdown.getItems().add("Exit");
		dropdown.setPromptText("Options");
		dropdown.setOnAction(e -> getChoice(dropdown, e));
		a.getChildren().add(dropdown);
		root.getChildren().add(a);
		// ScaleTransition r1s = new ScaleTransition(Duration.seconds(5), r1);
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
			System.exit(0);
		}
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
	
	public void addBlocks(ArrayList<Integer> locs, ArrayList<Integer> vals)
	{
		for (int i = 0; i < locs.size(); i++)
		{
			int loc = locs.get(i);
			int val = vals.get(i);
			Block b1;
			if (val <= s.getSize() / 3)
			{
				b1 = new Block(loc * 500 / 8, 45, String.valueOf(val), 1);
			}
			else if (val > s.getSize() / 3 && val <= (s.getSize() - s.getSize() / 3))
			{
				b1 = new Block(loc * 500 / 8, 45, String.valueOf(val), 2);
			}
			else if (val > s.getSize())
			{
				b1 = new Block(loc * 500 / 8, 45, String.valueOf(val), 4);
			}
			else
			{
				b1 = new Block(loc * 500 / 8, 45, String.valueOf(val), 3);
			}
			root.getChildren().addAll(b1, b1.getA());
			blocks.add(b1);
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
		if (distSinceBlock > 350 && distSinceBlock + guess > 500 )
		{
			t = 0;
			guess = random.nextInt(7) + 1;
			distSinceBlock = 0;
			ArrayList<Integer> locs = new ArrayList<>();
			if (guess < 8)
			{
				for (int i = 0; i < guess; i++)
				{
					int num = random.nextInt(8);
					if (locs.contains(num))
					{
						i -= 1;
					}
					else
					{
						locs.add(num);
					}
				}
			}
			else
			{
				locs.clear();
				for (int i = 1; i <= 8; i++)
				{
					locs.add(i);
				}
			}
			int min1 = 100000;
			int sum1 = 0;
			ArrayList<Integer> vals = new ArrayList<>();
			for (int i = 0; i < guess; i++)
			{
				int num = (int) (random.nextInt(s.getSize() + s.getSize() / 3)) + 1;
				if (num <= 0)
				{
					i -= 1;
					continue;
				}
				min1 = min(min1, num);
				sum1 += num;
				vals.add(num);
			}
			addBlocks(locs, vals);
			if (min1 >= s.getSize())
			{
				vals.remove(0);
				vals.add(s.getSize() - 1);
			}
			last -= sum1 * 0.3;
		}
		guess = random.nextInt(81);
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
				guess = random.nextInt(100);
				int guessx = random.nextInt(440) + 30;
				int guessy = random.nextInt(30) + 60;
				if (guess < 60)
				{
					if (!addToken(guessx, guessy, "coin"))
						i -= 1;
				}
				else if (guess < 80)
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
		for (Block b : blocks)
		{
			flag |= s.intersection(b);
			if (flag) {
				System.out.println(String.valueOf("intersection with BLOCL" + s.getx()) + " : " + String.valueOf(b.getTranslateX()));
				dist = abs(s.getx() - b.getTranslateX() - 53); // width of block - radius ( as translatex is measured form top-l
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
		for (Block b : blocks)
		{
			flag |= s.intersection(b);
			if (flag) {
				System.out.println(String.valueOf("intersection with BLOCL" + s.getx()) + " : " + String.valueOf(b.getTranslateX()));
				dist = abs(s.getx() + 7 - b.getTranslateX()); // 7 is radius of snake
				break;
			}

		}
		if (flag)
		{
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
		for (Block b1 : blocks)
		{
			flag |= b1.getBoundsInParent().intersects(first.getBoundsInParent());
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
		for (Block w : blocks)
		{
			if (s.intersection(w))
			{

				if(w.getTranslateY() < 390) {
					System.out.println("location of block is " + String.valueOf(w.getTranslateY()));
					System.out.println("LOL");
					Block hitter = w;
					int value = hitter.getValue();
					int value2 = hitter.getInitialValue();
					System.out.println("Value of block " + String.valueOf(value));
					System.out.println("Value of snake " + String.valueOf(s.getSize()));
					if (ShieldOn == false) {
						if (s.getSize() > 0) {
							s.decLenghtBy(1);
							if (value2 > 5) {
								moveUp();
							}
							score += 1;
							scoreLabel.setText(Integer.toString(score));
							value = value - 1;
							hitter.getA().setText(Integer.toString(value));
							hitter.setValue(value);
							if (value == 0) {
								System.out.println("Size of children " + String.valueOf(root.getChildren().size()));
								System.out.println("hitter is removed " + String.valueOf(hitter));
								Rectangle r1 = new Rectangle(hitter.getTranslateX() + 15, hitter.getTranslateY() + 30, 20,
										20);
								root.getChildren().remove(hitter);
								root.getChildren().remove(hitter.getA());
								blocks.remove(hitter);
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
					} else {
						score += value;
						Rectangle r1 = new Rectangle(hitter.getTranslateX() + 15, hitter.getTranslateY() + 30, 20, 20);
						root.getChildren().remove(hitter);
						root.getChildren().remove(hitter.getA());
						blocks.remove(hitter);

						r1.setFill(mag1);
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
				else{

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
					Rectangle r2 = new Rectangle(t1.getTranslateX() - 10, t1.getTranslateY(), 10, 10);
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
						score += blocks.get(j).getValue();
						Rectangle r1 = new Rectangle(blocks.get(j).getTranslateX() + 15,
								blocks.get(j).getTranslateY() + 30, 20, 20);
						root.getChildren().remove(blocks.get(j));
						root.getChildren().remove(blocks.get(j).getA());
						blocks.remove(blocks.get(j));
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
					root.getChildren().remove(t1);
					tokens.remove(t1);
				}
				else if (t1.getType().equals("Shield"))
				{
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
			Block w = blocks.get(i);
			w.setTranslateY(w.getTranslateY() - 3 * speedScale);
			w.getA().setTranslateY(w.getTranslateY() - 3 * speedScale);
			if (w.getTranslateY() > 800)
			{
				root.getChildren().remove(w);
				blocks.remove(w);
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
			Block w = blocks.get(i);
			w.setTranslateY(w.getTranslateY() + 0.5 * speedScale);
			w.getA().setTranslateY(w.getTranslateY() + 0.5 * speedScale);
			if (w.getTranslateY() > 750)
			{
				root.getChildren().remove(w);
				root.getChildren().remove(w.getA());
				blocks.remove(w);
			}
		}
	}
	
	private void update() throws ConcurrentModificationException
	{
		distSinceBlock += 0.5 * speedScale;
		speedScale = max(2 * sqrt(s.getSize()) / sqrt(5), 1.5);
		t += 0.05;
		ColorCheck += 1;
		if (ShieldOn == true)
		{
			ShieldCheck += 1;
		}
		if (ColorCheck == 180)
		{
			s.animate();
			ColorCheck = 0;
		}
		if (ShieldCheck == 301)
		{
			ShieldCheck = 0;
			ShieldOn = false;
		}
		if (t > 1.5)
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
				case D:
					if (GameOn == true)
					{
						moveRight(10);
					}
					System.out.println("Right");
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
