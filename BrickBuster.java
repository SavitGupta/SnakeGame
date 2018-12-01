
//@formatter:on
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class BrickBuster extends Token
{
	public BrickBuster(double x, double y)
	{
		super(x, y, "BrickBuster", "bomb.png");
		Image mag = new Image(getClass().getResourceAsStream("bomb.png"));
		this.setFill(new ImagePattern(mag));
	}
}