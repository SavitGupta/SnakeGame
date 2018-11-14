
//@formatter:on
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Shield extends Token
{
	public Shield(double x, double y)
	{
		super(x, y, "Shield","shield.png");
		Image mag = new Image(getClass().getResourceAsStream("shield.png"));
		this.setFill(new ImagePattern(mag));
	}
}