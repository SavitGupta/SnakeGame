
//@formatter:on
import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
/**
 * Parent class of every Powerup in the game
 */
public abstract class Token extends Circle implements Serializable
{
	private static final long serialVersionUID = 105L;
	private String type;
	private double x, y;
	private String imageName;
	private boolean enabled;

	/**
	 * initializes with given parameters
	 * @param x
	 * @param y
	 * @param type
	 * @param imageName
	 */
	public Token(double x, double y, String type, String imageName)
	{
		super(0, 0, 10);
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.type = type;
		this.imageName = imageName;
		this.enabled = false;
	}

	/**
	 * moves the token down
	 * @param amt value(in pixel0) by which it is moved down
	 */
	public void moveDown(double amt)
	{
		this.setTranslateY(this.getTranslateY() + amt);
	}


	/**
	 * Saves state of non-serializable graphical elements into, serializable state variables,
	 * saves x,y co-ordinate of the token to respective double variables.
	 */
	public void prepareSerilize()
	{
		x = this.getTranslateX();
		y = this.getTranslateY();
	}


	/**
	 * Sets/creates graphical components using non graphical state variable, specifically
	 * set x,y co-ordinates, image
	 */
	public void deserialize()
	{
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.setRadius(10);
		Image mag = new Image(getClass().getResourceAsStream(imageName));
		this.setFill(new ImagePattern(mag));
		System.out.println(" Token deserialize" + String.valueOf(x) + " " + String.valueOf(y));
	}

	/**
	 * standard getter
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @return value of enabled variable
	 */
	public boolean isEnabled()
	{
		return enabled;
	}

	/**
	 * standard setter
	 */
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
}