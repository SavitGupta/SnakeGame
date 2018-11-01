import javafx.scene.control.Button;
import javafx.scene.shape.Circle;

public class BallToken extends Button
{
	public BallToken(double x, double y, String value)
	{
		super(value);
		double r=15;
		this.setShape(new Circle(r));
		this.setMinSize(2*r, 2*r);
		this.setMaxSize(2*r, 2*r);
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.setStyle(
				"-fx-focus-color: transparent;" +
				"-fx-faint-focus-color: transparent;"
				);
	}
}