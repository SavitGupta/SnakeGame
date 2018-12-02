
//@formatter:on
import java.io.Serializable;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class BallToken extends Rectangle implements Serializable
{
	private static final long serialVersionUID = 108L;
	private int value;
	private transient Label a;
	private double x, y;
	
	public BallToken(double x, double y, String value)
	{
		super(0, 0, 25, 25);
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.value = Integer.parseInt(value);
		Image mag;
		mag = new Image(getClass().getResourceAsStream("yellowball.png"));
		this.setFill(new ImagePattern(mag));
		a = new Label(value);
		a.setPrefHeight(25);
		a.setPrefWidth(25);
		a.setTranslateX(x);
		a.setTranslateY(y);
		a.setAlignment(Pos.CENTER);
		a.setTextFill(Color.BLACK);
	}
	
	public void prepareSerialize()
	{
		x = this.getTranslateX();
		y = this.getTranslateY();
	}
	
	public void deserialize()
	{
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.setWidth(25);
		this.setHeight(25);
		Image mag;
		mag = new Image(getClass().getResourceAsStream("yellowball.png"));
		this.setFill(new ImagePattern(mag));
		a = new Label(String.valueOf(value));
		a.setPrefHeight(25);
		a.setPrefWidth(25);
		a.setTranslateX(x);
		a.setTranslateY(y);
		a.setAlignment(Pos.CENTER);
		a.setTextFill(Color.BLACK);
	}
	
	public Label getA()
	{
		return a;
	}
	
	public String getValue()
	{
		return String.valueOf(value);
	}
}