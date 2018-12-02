
//@formatter:on
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Shield extends Token
{
	private static Image mag;
	private void instantiateImages()
	{
		mag = new Image(getClass().getResourceAsStream("./Images/shield.png"));
	}
	public Shield(double x, double y)
	{
		super(x, y, "Shield", "./Images/shield.png");
		if(mag == null){
			instantiateImages();
		}
		this.setFill(new ImagePattern(mag));
	}
}