
//@formatter:on
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class BrickBuster extends Token
{

	private static Image mag;
	private void instantiateImages()
	{
		mag = new Image(getClass().getResourceAsStream("bomb.jpg"));
	}
	public BrickBuster(double x, double y)
	{

		super(x, y, "BrickBuster", "bomb.jpg");
		if(mag == null){
			instantiateImages();
		}
		this.setFill(new ImagePattern(mag));
	}
}