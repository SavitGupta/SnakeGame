
//@formatter:on
import java.io.Serializable;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Balls extends Circle implements Serializable
{
	private static final long serialVersionUID = 107L;
	
	public Balls(double x, double y, int i)
	{
		super(0, 0, 7);
		this.setTranslateX(x);
		this.setTranslateY(y);
		if (i == 0)
		{
			this.setFill(Color.BLUEVIOLET);
		}
		else if (i == 1)
		{
			this.setFill(Color.BLACK);
		}
	}
	
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
	
	public void animate(int i)
	{
		if (i == 0)
		{
			FillTransition fillTransition = new FillTransition(Duration.seconds(1.5), this, Color.BLUEVIOLET, Color.DEEPPINK);
			fillTransition.setCycleCount(Animation.INDEFINITE);
			fillTransition.setAutoReverse(true);
			fillTransition.play();
		}
		else if (i == 1)
		{
			FillTransition fillTransition = new FillTransition(Duration.seconds(1.5), this, Color.BLACK, Color.DEEPPINK);
			fillTransition.setCycleCount(Animation.INDEFINITE);
			fillTransition.setAutoReverse(true);
			fillTransition.play();
		}
	}
}