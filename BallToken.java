
//@formatter:on
import java.io.Serializable;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BallToken extends Circle implements Serializable
{
	private String value;
	private transient Label a;
	private double x,y;

	public BallToken(double x, double y, String value)
	{
		super(x, y, 12);
		this.value = value;
		this.setFill(Color.YELLOW);
		a = new Label(value);
		a.setLayoutX(x - 5);
		a.setLayoutY(y - 11);
		a.setAlignment(Pos.CENTER);
	}

	public void prepareSerialize(){
		x = this.getCenterX();
		y = this.getCenterY();
		System.out.println("in prepare Serialize " + String.valueOf(x)  + " " + String.valueOf(y));
	}
	public void deserialize(){
		this.setCenterX(x);
		this.setCenterY(y);
		this.setRadius(12);
		this.setFill(Color.YELLOW);
		a = new Label(value);
		System.out.println("in deserialize BallToken" + String.valueOf(this.x) + " " + String.valueOf(y));
		a.setLayoutX(x - 5);
		a.setLayoutY(y - 11);
		a.setAlignment(Pos.CENTER);
	}

	public Label getA()
	{
		return a;
	}
	
	public String getValue()
	{
		return value;
	}
}