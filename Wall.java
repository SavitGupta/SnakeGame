import javafx.scene.shape.Rectangle;

public class Wall extends Rectangle {
    public Wall(double x,double y,double h){
        super(0,0,10,h);
        this.setTranslateX(x);
        this.setTranslateY(y);
    }
}