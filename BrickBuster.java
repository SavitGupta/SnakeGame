
//@formatter:on
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
/**
 * A power-up in the game that destroys all Block currently on it.
 */
public class BrickBuster extends Token
{

	private static Image defaultImg;

	/**
	 * Instantiates default Image, by loading it from pre-determined filePath
	 */
	private void instantiateImages()
	{
		defaultImg = new Image(getClass().getResourceAsStream("./Images/bomb.jpg"));
	}

	/**
	 * Sets default Image, Initializes with given parameters,
	 * @param x x co-ordinate of graphical component(circle)
	 * @param y y co-ordinate of graphical component(circle)
	 */
	public BrickBuster(double x, double y)
	{

		super(x, y, "BrickBuster", "./Images/bomb.jpg");
		if(defaultImg == null){
			instantiateImages();
		}
		this.setFill(new ImagePattern(defaultImg));
	}
}