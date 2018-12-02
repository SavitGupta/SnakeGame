
//@formatter:on
import java.io.Serializable;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Rectangular obstacle that prevents the snake from passing through it.
 */
public class Wall extends Rectangle implements Serializable
{
	double x, y, h;
	private static final long serialVersionUID = 106L;

	/**
	 * Sets default Image, creates value label to show value and circle to represent graphical component, Initializes the ballToken with given parameters,
	 * @param x x co-ordinate of top-left corner graphical component(rectangle)
	 * @param y y co-ordinate of top-left corner graphical component(rectange)
	 * @param h height of wall
	 */
	public Wall(double x, double y, double h)
	{
		super(0, 0, 4, h);
		this.h = h;
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.setFill(Color.PERU);
	}

	/**
	 * Saves state of non-serializable graphical elements into, serializable state variables,
	 * saves x,y co-ordinate of the token to respective double variables.
	 */
	public void prepareSerialize()
	{
		x = this.getTranslateX();
		y = this.getTranslateY();
	}

	/**
	 * Sets/creates graphical components using non graphical state variable, specifically
	 * set x,y co-ordinates
	 */
	public void deserialize()
	{
		this.setTranslateY(y);
		this.setTranslateX(x);
		this.setWidth(4);
		this.setHeight(h);
		this.setFill(Color.PERU);
	}
}