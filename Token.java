import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import javafx.scene.shape.Circle;

public class Token extends Circle
{
	private String type;
	public Token(double x, double y, String type)
	{
		super(x, y, 8);
		this.type = type;
	}

	public void MoveDown(double amt)
	{
		this.setTranslateY(this.getTranslateY() + amt);
	}
}
