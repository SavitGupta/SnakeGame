
//@formatter:on
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import java.io.Serializable;
public class Token extends Circle implements Serializable
{
	private String type;
	private double x,y;
	private String imageName;

	public Token(double x, double y, String type,String imageName)
	{
		super(0, 0, 10);
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.type = type;
		this.imageName = imageName;
	}
	
	public void moveDown(double amt)
	{
		this.setTranslateY(this.getTranslateY() + amt);
	}
	public void prepareSerilize(){
		x = this.getTranslateX();
		y = this.getTranslateY();
	}
	public void deserialize(){
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.setRadius(10);
		Image mag = new Image(getClass().getResourceAsStream(imageName));
		this.setFill(new ImagePattern(mag));
		System.out.println(" Token deserialize" + String.valueOf(x) + " " + String.valueOf(y));
	}



	public String getType()
	{
		return type;
	}
}