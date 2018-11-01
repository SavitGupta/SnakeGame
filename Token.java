import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Token extends Circle
{
	private String type;
	public Token(double x, double y, String type)
	{
		super(x, y, 8);
		this.type = type;
	}

	public void moveDown(double amt)
	{
		this.setTranslateY(this.getTranslateY() + amt);
	}

    public String getType() {
        return type;
    }
}
