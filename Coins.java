
//@formatter:on
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Coins extends Token
{
	public Coins(double x, double y)
	{
		super(x, y, "Coin", "coin.png");
		Image mag = new Image(getClass().getResourceAsStream("coin.png"));
		this.setFill(new ImagePattern(mag));
	}
}