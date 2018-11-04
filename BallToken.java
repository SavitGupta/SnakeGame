//Eclipse and GIT
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class BallToken extends Circle
{
	private String value;
	private String value2;
	private Label a;
	public BallToken(double x, double y, String value)
	{
		super(x, y, 12);
		this.value = value;
		this.setFill(Color.YELLOW);
		a = new Label(value);
		a.setLayoutX(x-5);
		a.setLayoutY(y-11);
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
