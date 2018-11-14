
//@formatter:on
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class Block extends Rectangle implements Serializable
{
	private int value;
	private transient Label a;
	private  int InitialValue;
	private double x,y;
	private int type;
	public Block(double x, double y, String value, int type)
	{
		super(0, 0, 60, 60);
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.value = Integer.parseInt(value);
		this.InitialValue = Integer.parseInt(value);
		this.type = type;
		Image mag;
		if (type == 1)
		{
			mag = new Image(getClass().getResourceAsStream("square1.png"));
		}
		else if (type == 2)
		{
			mag = new Image(getClass().getResourceAsStream("square2.png"));
		}
		else if (type == 3)
		{
			mag = new Image(getClass().getResourceAsStream("square3.png"));
		}
		else
		{
			mag = new Image(getClass().getResourceAsStream("square4.png"));
		}
		this.setFill(new ImagePattern(mag));
		if (this.value - 10 < 0)
		{
			value = "0" + value;
		}
		a = new Label(value);
		a.setPrefHeight(60);
		a.setPrefWidth(60);
		a.setTranslateX(x);
		a.setTranslateY(y);
		a.setAlignment(Pos.CENTER);
		a.setTextFill(Color.WHITE);
		a.setStyle("-fx-font-weight: bold;");
	}

	public int getInitialValue() {
		return InitialValue;
	}

	public int getValue()
	{
		return value;
	}
	
	public void setValue(int value)
	{
		this.value = value;
	}
	
	public Label getA()
	{
		return a;
	}

	public void prepareSerialize(){
		x = this.getTranslateX();
		y = this.getTranslateY();
	}
	public void deserialize(){
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.setWidth(60);
		this.setHeight(60);
		Image mag;
		if (type == 1)
		{
			mag = new Image(getClass().getResourceAsStream("square1.png"));
		}
		else if (type == 2)
		{
			mag = new Image(getClass().getResourceAsStream("square2.png"));
		}
		else if (type == 3)
		{
			mag = new Image(getClass().getResourceAsStream("square3.png"));
		}
		else
		{
			mag = new Image(getClass().getResourceAsStream("square4.png"));
		}
		this.setFill(new ImagePattern(mag));
		a = new Label(String.valueOf(value));
		a.setPrefHeight(60);
		a.setPrefWidth(60);
		a.setTranslateX(x);
		a.setTranslateY(y);
		a.setAlignment(Pos.CENTER);
		a.setTextFill(Color.WHITE);
		a.setStyle("-fx-font-weight: bold;");
	}
}