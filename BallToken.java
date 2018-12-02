
//@formatter:on
import java.io.Serializable;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * A power-up in the game that increase the length of the snake.
 */
public class BallToken extends Rectangle implements Serializable
{
	private static final long serialVersionUID = 108L;
	private int value;
	private transient Label valueLabel;
	private double x, y;
	private static Image defualtImg;

	/**
	 * Instantiates default Image, by loading it from pre-determined filePath
	 */
	private void instantiateImages()
	{
		defualtImg = new Image(getClass().getResourceAsStream("./Images/yellowball.png"));
	}

	/**
	 * Sets default Image, creates value label to show value and circle to represent graphical component, Initializes the ballToken with given parameters,
	 * @param x x co-ordinate of graphical component(circle)
	 * @param y y co-ordinate of graphical component(circle)
	 * @param value number of balls that will be added to the snake when this token is collected.
	 */
	public BallToken(double x, double y, String value)
	{
		super(0, 0, 25, 25);
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.value = Integer.parseInt(value);
		if(defualtImg == null){
			instantiateImages();
		}
		this.setFill(new ImagePattern(defualtImg));
		valueLabel = new Label(value);
		valueLabel.setPrefHeight(25);
		valueLabel.setPrefWidth(25);
		valueLabel.setTranslateX(x);
		valueLabel.setTranslateY(y);
		valueLabel.setAlignment(Pos.CENTER);
		valueLabel.setTextFill(Color.BLACK);
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
	 * set x,y co-ordinates, image
	 * creates valueLabel
	 */
	public void deserialize()
	{
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.setWidth(25);
		this.setHeight(25);
		Image mag;
		mag = new Image(getClass().getResourceAsStream("./Images/yellowball.png"));
		this.setFill(new ImagePattern(mag));
		valueLabel = new Label(String.valueOf(value));
		valueLabel.setPrefHeight(25);
		valueLabel.setPrefWidth(25);
		valueLabel.setTranslateX(x);
		valueLabel.setTranslateY(y);
		valueLabel.setAlignment(Pos.CENTER);
		valueLabel.setTextFill(Color.BLACK);
	}

	/**
	 *
	 * @return returns reference to valueLabel
	 */
	public Label getValueLabel()
	{
		return valueLabel;
	}

	/**
	 *
	 * @return value of BallToken as String
	 */
	public String getValue()
	{
		return String.valueOf(value);
	}
}