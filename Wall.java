
//@formatter:on
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class Wall extends Rectangle implements Serializable
{
	public Wall(double x, double y, double h)
	{
		super(0, 0, 4, h);
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.setFill(Color.PERU);
	}
}