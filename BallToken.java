
//@formatter:on
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.Serializable;

public class BallToken extends Circle implements Serializable
{
	private String value;
	private transient Label a;

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
	public void deserialize(){
		a = new Label(value);
		System.out.println("in deserialize BallToken" + String.valueOf(this.getTranslateX()));
		a.setLayoutX(this.getTranslateX() - 5);
		a.setLayoutY(this.getTranslateY() - 11);
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