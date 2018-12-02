
//@formatter:on
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Coins extends Token
{
	private static Image mag;
	private void instantiateImages()
	{
		mag = new Image(getClass().getResourceAsStream("Images/coin.png"));
	}
	public Coins(double x, double y)
	{

		super(x, y, "Coin", "Images/coin.png");
		if(mag == null){
			instantiateImages();
		}
		this.setFill(new ImagePattern(mag));
	}
}