import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Button
{
	public Block(double x, double y, String value)
	{
		super(value);
		this.setLayoutX(x);
		this.setLayoutY(y);
	}
}