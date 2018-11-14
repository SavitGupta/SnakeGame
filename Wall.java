
//@formatter:on
import java.io.Serializable;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall extends Rectangle implements Serializable
{
	double x,y,h;
	public Wall(double x, double y, double h)
	{
		super(0, 0, 4, h);
		this.h = h;
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.setFill(Color.PERU);
	}

	public void prepareSerialize(){
		x = this.getTranslateX();
		y = this.getTranslateY();
	}
	public void deserialize(){
		this.setTranslateY(y);
		this.setTranslateX(x);
		this.setWidth(4);
		this.setHeight(h);
		this.setFill(Color.PERU);

	}

}