import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle
{
	private boolean alive = true;
	private int value;
	private Label a;
	public Block(double x, double y, String value)
	{
		super(x,y,40,40);
		Image mag = new Image(getClass().getResourceAsStream("square3.png"));
		this.setFill(new ImagePattern(mag));
		a = new Label(value);
		this.value = Integer.parseInt(value);
		a.setLayoutX(x+10);
		a.setLayoutY(y+10);
		a.setAlignment(Pos.CENTER);
		//a.setStyle("-fx-background-color: #008000");
	}

	public int getValue()
	{
		return value;
	}

	public boolean isAlive()
	{
		return alive;
	}

	public void setAlive(boolean alive)
	{
		this.alive = alive;
	}

	public boolean isDead()
	{
		return !this.alive;
	}

	public Label getA()
	{
		return a;
	}
}