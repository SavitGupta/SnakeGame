
//@formatter:on
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Shield extends Token
{
	private static Image mag;
	private void instantiateImages()
	{
		mag = new Image(getClass().getResourceAsStream("shield.png"));
	}
	public Shield(double x, double y)
	{
		super(x, y, "Shield", "shield.png");
		if(mag == null){
			instantiateImages();
		}
		this.setFill(new ImagePattern(mag));
	}
}