
//@formatter:on
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
/**
 * A temporary power-up in the game that prevents the Blocks from reducing snake length
 */
public class Shield extends Token
{
	private static Image defaultImg;
	/**
	 * Instantiates default Image, by loading it from pre-determined filePath
	 */
	private void instantiateImages()
	{
		defaultImg = new Image(getClass().getResourceAsStream("./Images/shield.png"));
	}

	/**
	 * Sets default Image, Initializes with given parameters,
	 * @param x x co-ordinate of graphical component(circle)
	 * @param y y co-ordinate of graphical component(circle)
	 */
	public Shield(double x, double y)
	{
		super(x, y, "Shield", "./Images/shield.png");
		if(defaultImg == null){
			instantiateImages();
		}
		this.setFill(new ImagePattern(defaultImg));
	}
}