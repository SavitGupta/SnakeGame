
//@formatter:on
import java.io.Serializable;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Enemy extends Rectangle implements Serializable
{
	private double x, y;
	
	public Enemy(double x, double y)
	{
		super(0, 0, 30, 30);
		this.setTranslateX(x);
		this.setTranslateY(y);
		Image mag;
		mag = new Image(getClass().getResourceAsStream("./Images/enemy.png"));
		this.setFill(new ImagePattern(mag));
	}
	
	public void prepareSerialize()
	{
		x = this.getTranslateX();
		y = this.getTranslateY();
	}
	
	public boolean intersection(Node other)
	{
		return this.getBoundsInParent().intersects(other.getBoundsInParent());
	}
	
	public void deserialize()
	{
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.setWidth(25);
		this.setHeight(25);
		Image mag;
		mag = new Image(getClass().getResourceAsStream("./Images/enemy.png"));
		this.setFill(new ImagePattern(mag));
	}
}