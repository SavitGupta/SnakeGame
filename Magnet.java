
//@formatter:on
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Magnet extends Token
{
	public Magnet(double x, double y)
	{
		super(x, y, "Magnet","magnet.jpg");
		Image mag = new Image(getClass().getResourceAsStream("magnet.jpg"));
		this.setFill(new ImagePattern(mag));
	}
}