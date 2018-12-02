
//@formatter:on
import java.io.Serializable;
import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 *
 */
public class Snake implements Serializable
{
	private static final long serialVersionUID = 104L;
	private int size;
	private transient Pane root;
	private ArrayList<Balls> l1 = new ArrayList<>();
	private double x;
	private double y;
	private int type;

	/**
	 * Initializes a Snake with following parameters on a given pane
	 * @param x x co-ordinate of the head of the snake
	 * @param y y co-ordinate of the head of the snake
	 * @param size Length of snake ( number of balls in it)
	 * @param root Pane on which GUI elements are placed
	 * @param gameType Takes 0,1 as input to define what gameType the Snake belongs to. 0 : normal Mode, 1: Blind Mode
	 *
	 */
	public Snake(double x, double y, int size, Pane root, int gameType)
	{
		this.x = x;
		this.y = y;
		this.size = size;
		this.root = root;
		this.type = gameType;
		System.out.println("x is " + String.valueOf(x));
		for (int i = 0; i < size; i++)
		{
			Balls b = new Balls(x, y, type);
			root.getChildren().add(b);
			l1.add(b);
			y += 13;
		}
		System.out.println("Size1 is " + String.valueOf(this.size));
		System.out.println("Size2 is " + String.valueOf(l1.size()));
		assert (size == l1.size());
	}

	/**
	 * Places the GUI components of the snake onto a container pane, on the basis of serialized non-GUI class attributes
	 * @param root the Pane on which the GUI components are placed.
	 */
	public void deserialize(Pane root)
	{
		this.root = root;
		l1.clear();
		double tempy = y;
		for (int i = 0; i < size; i++)
		{
			Balls b = new Balls(x, tempy, type);
			root.getChildren().add(b);
			l1.add(b);
			tempy += 13;
		}
	}

	/**
	 * Increases the length of the snake, by adding new balls at the bottom to it.
	 * @param amt Number of balls to be addded
	 */
	public void incLenghtBy(int amt)
	{
		for (int i = 0; i < amt; i++)
		{
			Balls b = new Balls(l1.get(0).getTranslateX(), y + 13 * size, type);
			root.getChildren().add(b);
			l1.add(b);
			size += 1;
		}
		assert (size == l1.size());
	}

	/**
	 * Decreases the length of the snake, by removing bottom-most balls from it.
	 * @param amt Number of balls to be removed.
	 */
	public void decLenghtBy(int amt)
	{
		if (amt > l1.size())
		{
			System.out.println("amt is greater 83401");
			amt = l1.size();
		}
		for (int i = 0; i < amt; i++)
		{
			root.getChildren().remove(l1.get(size - 1));
			l1.remove(size - 1);
			size -= 1;
		}
		assert (size == l1.size());
	}

	/**
	 * Saves the state of various GUI components into Serializable state variables/
	 */
	public void prepareSerialize()
	{
		x = l1.get(size - 1).getTranslateX();
	}

	/**
	 * Shifts snake to the left by specified amount
	 * @param amt value(in pixels) by which it is moved to the left.
	 */
	public void moveLeft(double amt)
	{
		System.out.println(l1.size());
		for (Balls b : l1)
		{
			b.moveLeft(amt);
		}
	}

	/**
	 * Shifts snake to the Right by specified amount
	 * @param amt value(in pixels) by which it is moved to the Right.
	 */
	public void moveRight(double amt)
	{
		for (Balls b : l1)
		{
			b.moveRight(amt);
		}
	}

	/**
	 * Check Intersection between the GUI components of the snake with the given GUI component.
	 * @param other the GUI component against which intersection is checked.
	 * @return true if there is an intersection, false otherwise.
	 */
	public boolean intersection(Node other)
	{
		if (l1.size() == 0)
		{
			System.out.println("Snake size is zero");
			return false;
		}
		return l1.get(0).getBoundsInParent().intersects(other.getBoundsInParent());
	}


	/**
	 * getter Function
	 * @return x co-ordinate of the head of snake
	 */
	public double getx()
	{
		return l1.get(0).getTranslateX();
	}

	/**
	 * getter Function
	 * @return y co-ordinate of the head of snake
	 */
	public double gety()
	{
		return l1.get(0).getTranslateY();
	}

	/**
	 * getter Function
	 * @return size of snake (number of balls in snake).
	 */
	public int getSize()
	{
		return this.size;
	}

	/**
	 * getter Function
	 * @return Entire Arraylist of balls, represents the Graphical component snake
	 */
	public ArrayList<Balls> getL1()
	{
		return l1;
	}


	/**
	 * begins the animation for color chages of the balls in the snake
	 * @param gameType specifies the gameType for color changes
	 */
	public void animate(int gameType)
	{
		for (int i = 0; i < l1.size(); i++)
		{
			l1.get(i).animate(gameType);
		}
	}
}