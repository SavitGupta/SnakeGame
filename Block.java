import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Button
{
	private boolean alive = true;
	private int value;
	public Block(double x, double y, String value)
	{
		super(value);
		this.value = Integer.parseInt(value);
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.setPrefHeight(15);
		this.setPrefWidth(15);
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
}