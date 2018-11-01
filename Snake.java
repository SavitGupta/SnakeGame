
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Snake{
    int size;
    Pane root;
    ArrayList<balls> l1 = new ArrayList<>();
    double x;
    double y;

    public Snake(double x,double y,int size,Pane root) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.root = root;
        System.out.println("x is " + String.valueOf(x));
        for (int i = 0; i < size; i++) {
            balls b = new balls(x,y);
            root.getChildren().add(b);
            l1.add(b);
            y += 8;
        }
    }
    public void incLenghtBy (int amt){
        for(int i=0;i<amt;i++){
            balls b = new balls(x,y + 8*size);
            root.getChildren().add(b);
            l1.add(b);
            size+=1;
        }
    }

    public void decLenghtBy (int amt){
        for(int i=0;i<amt;i++){
            root.getChildren().remove(l1.get(size - 1));
            l1.remove(size -1);
            size-=1;
        }
    }
    public void moveLeft(int amt){
        System.out.println(l1.size());
        for(balls b : l1){
            System.out.println("hereaa");
            b.moveLeft(amt);
        }
    }
    public boolean intersection(Node other){
        return l1.get(0).getBoundsInParent().intersects(other.getBoundsInParent());
    }


    public void moveRight(int amt) {
        for (balls b : l1) {
            b.moveRight(amt);
        }
    }
}
