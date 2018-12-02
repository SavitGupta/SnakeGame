
//@formatter:on
import java.io.Serializable;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * Obstacle in game that reduces snake size
 */
public class Block extends Rectangle implements Serializable
{
	private int value;
	private transient Label valueLabel;
	private int InitialValue;
	private double x, y;
	private int type;
	private static Image[] blockImages;

	/**
	 * Instantiates default Images arrayList, by loading them from pre-determined filePaths.
	 */
	private void instantiateImages(){
		blockImages = new Image[4];
		blockImages[0] = new Image(getClass().getResourceAsStream("Images/square1.png"));
		blockImages[1] = new Image(getClass().getResourceAsStream("Images/square2.png"));
		blockImages[2] = new Image(getClass().getResourceAsStream("Images/square3.png"));
		blockImages[3] = new Image(getClass().getResourceAsStream("Images/square4.png"));
	}


	/**
	 * Sets Image on the basis of param type, creates value label to show value and rectangle to represent graphical component, Initializes the ballToken with given parameters,
	 * @param x x co-ordinate of top-left graphical component(rectangle)
	 * @param y y co-ordinate of top-left graphical component(rectangle)
	 * @param value number of balls that will be removed from the snake when collided with it.
	 * @param type specifies the image to be used.
	 */
	public Block(double x, double y, String value, int type)
	{
		super(0, 0, 60, 60);
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.value = Integer.parseInt(value);
		this.InitialValue = Integer.parseInt(value);
		this.type = type;
		Image mag;
		if(blockImages == null){
			instantiateImages();
			System.out.println("Images instatiated in block");
		}

		mag = blockImages[type-1];
		this.setFill(new ImagePattern(mag));
		if (this.value - 10 < 0)
		{
			value = "0" + value;
		}
		valueLabel = new Label(value);
		valueLabel.setPrefHeight(60);
		valueLabel.setPrefWidth(60);
		valueLabel.setTranslateX(x);
		valueLabel.setTranslateY(y);
		valueLabel.setAlignment(Pos.CENTER);
		valueLabel.setTextFill(Color.WHITE);
		valueLabel.setStyle("-fx-font-weight: bold;");
	}

	/**
	 * standard getter
	 * @return Initialvalue
	 */
	public int getInitialValue()
	{
		return InitialValue;
	}

	/**
	 * standard getter
	 * @return value
	 */
	public int getValue()
	{
		return value;
	}

	/**
	 * standard setter.
	 */
	public void setValue(int value)
	{
		this.value = value;
	}

	/**
	 * standard getter
	 * @return valueLabel
	 */
	public Label getValueLabel()
	{
		return valueLabel;
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
		this.setWidth(60);
		this.setHeight(60);
		Image mag;
        if(blockImages == null){
            instantiateImages();
            System.out.println("Images instatiated in block");
        }

        mag = blockImages[type-1];
		this.setFill(new ImagePattern(mag));
		valueLabel = new Label(String.valueOf(value));
		valueLabel.setPrefHeight(60);
		valueLabel.setPrefWidth(60);
		valueLabel.setTranslateX(x);
		valueLabel.setTranslateY(y);
		valueLabel.setAlignment(Pos.CENTER);
		valueLabel.setTextFill(Color.WHITE);
		valueLabel.setStyle("-fx-font-weight: bold;");
	}
}
