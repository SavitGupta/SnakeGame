//tryingchanges2
//tryingchanges
import static java.lang.Integer.min;
import static java.lang.Math.abs;
import static java.lang.Math.floor;
import static java.lang.Math.max;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application
{
	private Pane root = new Pane();
	private Label scoreLabel = new Label();
	private Label sizeLabel = new Label();
	private ComboBox<String> dropdown = new ComboBox<>();
	private int score = 0;
	private double t = 0;
	private ArrayList<Wall> walls = new ArrayList<>();
	private ArrayList<Token> tokens = new ArrayList<>();
	private ArrayList<BallToken> balls = new ArrayList<>();
	private ArrayList<Block> blocks = new ArrayList<>();
	private double speedScale = 1;
	// private ArrayList<RowOfBlocks> blocks;
	private double last;
	Snake s = new Snake(250, 300, 5, root);
	
	private Parent createContent()
	{
		root.setPrefSize(500, 600);
		root.getChildren().add(sizeLabel);
		last = 0;
		AnimationTimer timer = new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{
				update();
			}
		};
		timer.start();
		HBox a = new HBox();
		a.setPrefHeight(30);
		a.setPrefWidth(500);
		a.getChildren().add(scoreLabel);
		a.setStyle("-fx-background-color: #008000");
		dropdown.getItems().add("Pause");
		dropdown.getItems().add("Restart");
		dropdown.getItems().add("Exit");
		dropdown.setPromptText("Options");
		a.getChildren().add(dropdown);
		a.setSpacing(300);
		a.setPadding(new Insets(10, 10, 10, 10));
		root.getChildren().add(a);
		return root;
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
		else if (type.equalsIgnoreCase("coin"))
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
			Integer loc = locs.get(i);
			int val = vals.get(i);
			Block b1 = new Block(loc * 500 / 8, 45, String.valueOf(val));
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
		int guess = random.nextInt(81);
		if (t > 3)
		{
			t = 0;
			guess = random.nextInt(6) + 2;
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
				int num = (int) (3 * random.nextGaussian() + 5);
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
				dist = abs(s.getx() - w.getTranslateX() - 14);
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
				dist = abs(s.getx() - w.getTranslateX() + 4);
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
		boolean flag = false;
		Wall hitter;
		for (Wall w : walls)
		{
			if (s.intersection(w))
			{
				flag = true;
				hitter = w;
				double dist = hitter.getTranslateX() - s.getx() + 5;
				System.out.println("Inersection with wall head " + String.valueOf(dist));
				if (dist > 0)
				{
					s.moveLeft(9 - dist);
				}
				else
				{
					s.moveRight(9 + dist);
				}
				break;
			}
		}
	}
	
	public void deflectFromBlocks()
	{
		Block hitter;
		boolean flag = true;
		for (Block w : blocks)
		{
			if (s.intersection(w))
			{
				flag = false;
				System.out.println("LOL");
				hitter = w;
				int value = hitter.getValue();
				System.out.println("Value of block " + String.valueOf(value));
				System.out.println("Value of snake " + String.valueOf(s.getSize()));
				if (value > s.getSize())
				{
					for (int i = 0; i < value; i++)
					{
						hitter.getA().setText(Integer.toString(value - 1));
						if (s.getSize() > 0)
						{
							s.decLenghtBy(1);
						}
					}
				}
				else
				{
					for (int i = 0; i < value; i++)
					{
						hitter.getA().setText(Integer.toString(value - 1));
						if (s.getSize() > 0)
						{
							s.decLenghtBy(1);
						}
					}
					System.out.println("Size of children " + String.valueOf(root.getChildren().size()));
					System.out.println("hitter is removed " + String.valueOf(hitter));
					root.getChildren().remove(hitter);
					root.getChildren().remove(hitter.getA());
					blocks.remove(hitter);
				}
			}
			if (!flag)
			{
				break;
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
		boolean flag = true;
		for (BallToken w : balls)
		{
			if (s.intersection(w))
			{
				System.out.println("HAHA");
				flag = false;
				hitter = w;
				int value = Integer.parseInt(hitter.getValue());
				System.out.println("Value of circle " + String.valueOf(value));
				System.out.println("Value of snake " + String.valueOf(s.getSize()));
				s.incLenghtBy(value);
				root.getChildren().remove(hitter);
				root.getChildren().remove(hitter.getA());
				balls.remove(hitter);
			}
			if (!flag)
			{
				break;
			}
		}
	}
	
	private void collectTokens()
	{
		for (int i = 0; i < tokens.size(); i++)
		{
			Token t1 = tokens.get(i);
			if (s.intersection(t1))
			{
				System.out.println("Token of type " + t1.getType());
				root.getChildren().remove(t1);
				tokens.remove(t1);
			}
		}
	}
	
	private void update()
	{
		speedScale = max(2 * sqrt(s.getSize()) / sqrt(5), 1.5);
		t += 0.03 * speedScale;
		if (t > 1.5)
		{
			generateContent();/*
								 * System.out.println(t);
								 * System.out.println(last);
								 */
		}
		collectTokens();
		for (int i = 0; i < walls.size(); i++)
		{
			Wall w = walls.get(i);
			w.setTranslateY(w.getTranslateY() + 0.5 * speedScale);
			if (w.getTranslateY() > 800)
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
			if (w.getTranslateY() > 800)
			{
				root.getChildren().remove(w);
				balls.remove(w);
			}
		}
		for (int i = 0; i < tokens.size(); i++)
		{
			Token t1 = tokens.get(i);
			t1.moveDown(0.5 * speedScale);
			if (t1.getTranslateY() > 800)
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
			if (w.getTranslateY() > 800)
			{
				root.getChildren().remove(w);
				blocks.remove(w);
			}
		}
		deflectFromWalls();
		deflectFromBlocks();
		deflectFromBalls();
		scoreLabel.setText("Score " + Integer.toString(score));
		sizeLabel.setText(Integer.toString(s.size));
		sizeLabel.setTranslateX(s.getx() - 3);
		sizeLabel.setTranslateY(275);
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
					moveLeft(10);
					System.out.println("left");
					break;
				case D:
					moveRight(10);
					System.out.println("Right");
					break;
			}
		});
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
