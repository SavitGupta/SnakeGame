
//@formatter:on
import java.io.Serializable;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
/**
 * Represents each individual piece of snake in the game.
 */

public class Balls extends Circle implements Serializable
{
	private static final long serialVersionUID = 107L;

	/**
	 * Initializes a ball with its appropriate color and gameType
	 * @param x x co-ordinate of the ball
	 * @param y y co-ordinate of the ball
	 * @param gameType takes 0,1 as input, 0: normal mode 1: Blind Mode. To decide its color.
	 */
	public Balls(double x, double y, int gameType)
	{
		super(0, 0, 7);
		this.setTranslateX(x);
		this.setTranslateY(y);
		if (gameType == 0)
		{
			this.setFill(Color.BLUEVIOLET);
		}
		else if (gameType == 1)
		{
			this.setFill(Color.BLACK);
		}
	}

	/**
	 * move the ball right.
	 * @param amt value (in pixels) by which the ball is moved left
	 */
	public void moveRight(double amt)
	{
		double x = this.getTranslateX();
		if (x > 500 - amt)
		{
			this.setTranslateX(500);
		}
		else
		{
			this.setTranslateX(x + amt);
		}
	}

	/**
	 * move the ball left.
	 * @param amt value (in pixels) by which the ball is moved left
	 */
	public void moveLeft(double amt)
	{
		double x = this.getTranslateX();
		if (x < amt)
		{
			this.setTranslateX(0);
		}
		else
		{
			this.setTranslateX(x - amt);
		}
	}

	/**
	 * Creates and starts animation for color change of balls
	 * @param gameType takes 0,1 as input, 0: normal mode 1: Blind Mode. To decide its color.
	 */

	public void animate(int gameType)
	{
		if (gameType == 0)
		{
			FillTransition fillTransition = new FillTransition(Duration.seconds(1.5), this, Color.BLUEVIOLET, Color.DEEPPINK);
			fillTransition.setCycleCount(Animation.INDEFINITE);
			fillTransition.setAutoReverse(true);
			fillTransition.play();
		}
		else if (gameType == 1)
		{
			FillTransition fillTransition = new FillTransition(Duration.seconds(1.5), this, Color.BLACK, Color.DEEPPINK);
			fillTransition.setCycleCount(Animation.INDEFINITE);
			fillTransition.setAutoReverse(true);
			fillTransition.play();
		}
	}
}
