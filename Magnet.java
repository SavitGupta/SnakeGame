
//@formatter:on
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
/**
 * A temporary power-up in the game that collects all coins within a radius of the snake
 */
public class Magnet extends Token
{

	private static Image defaultImg;
	/**
	 * Instantiates default Image, by loading it from pre-determined filePath
	 */
	private void instantiateImages()
	{
		defaultImg = new Image(getClass().getResourceAsStream("./Images/magnet.jpg"));
	}


	/**
	 * Sets default Image, Initializes with given parameters,
	 * @param x x co-ordinate of graphical component(circle)
	 * @param y y co-ordinate of graphical component(circle)
	 */
	public Magnet(double x, double y)
	{
		super(x, y, "Magnet", "./Images/magnet.jpg");
		if(defaultImg == null){
			instantiateImages();
		}
		this.setFill(new ImagePattern(defaultImg));
	}
}