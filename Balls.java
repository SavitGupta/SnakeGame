
//@formatter:on
import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.Serializable;

public class Balls extends Circle implements Serializable
{
	public Balls(double x, double y)
	{
		super(0, 0, 7);
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.setFill(Color.BLUEVIOLET);
	}
	
	public void moveRight(double amt)
	{
		double x = this.getTranslateX();
		System.out.println("x is " + String.valueOf(x));
		if (x > 500 - amt)
		{
			this.setTranslateX(500);
		}
		else
		{
			this.setTranslateX(x + amt);
		}
		// TranslateTransition transition = new TranslateTransition();
		// transition.setDuration(Duration.millis(10));
		// if (this.getTranslateX() > 500 - amt)
		// {
		// transition.setToX(500);
		// }
		// else
		// {
		// transition.setToX(this.getTranslateX() + amt);
		// }
		// transition.setToY(this.getTranslateY());
		// transition.setNode(this);
		// transition.play();
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
	
	public void animate()
	{
		FillTransition fillTransition = new FillTransition(Duration.seconds(1.5), this, Color.BLUEVIOLET,
				Color.DEEPPINK);
		fillTransition.setCycleCount(Animation.INDEFINITE);
		fillTransition.setAutoReverse(true);
		fillTransition.play();
	}
}