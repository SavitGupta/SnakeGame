
//@formatter:on
import java.io.Serializable;

import javafx.scene.shape.Circle;

public class Token extends Circle implements Serializable
{
	private String type;
	
	public Token(double x, double y, String type)
	{
		super(0, 0, 10);
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.type = type;
	}
	
	public void moveDown(double amt)
	{
		this.setTranslateY(this.getTranslateY() + amt);
	}
	
	public String getType()
	{
		return type;
	}
}