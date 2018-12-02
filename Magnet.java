
//@formatter:on
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Magnet extends Token
{

	private static Image mag;
	private void instantiateImages()
	{
		mag = new Image(getClass().getResourceAsStream("magnet.jpg"));
	}



	public Magnet(double x, double y)
	{
		super(x, y, "Magnet", "magnet.jpg");
		if(mag == null){
			instantiateImages();
		}
		this.setFill(new ImagePattern(mag));
	}
}