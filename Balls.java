import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Balls extends Circle {
    ImagePattern imagePattern;
    public Balls(double x,double y){

        super(0,0,5);
        //System.out.println(" x for balls" + String.valueOf(x));
        //System.out.println("gettranslatex" + String.valueOf(this.getTranslateX()));
        this.setTranslateX(x);
        this.setTranslateY(y);
        //this.setFill(imagePattern);
    }
    //Ap sucks
    public void moveRight(int amt){
        double x = this.getTranslateX();
        System.out.println("x is " + String.valueOf(x));
        if(x > 500 - amt){
            this.setTranslateX(500);
        }
        else{
            this.setTranslateX(x + amt);
        }
    }
    public void moveLeft(int amt){
        double x = this.getTranslateX();
        if(x  < amt){
            this.setTranslateX(0);
        }
        else{
            this.setTranslateX(x - amt);
        }
    }
}