import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import jdk.nashorn.internal.parser.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Main extends Application {
    private Pane root = new Pane();
    private double t = 0;
    private ArrayList<Wall> walls = new ArrayList<>();
    private ArrayList<Block> blocks = new ArrayList<>();
    private ArrayList<BallToken> balls = new ArrayList<>();
    //private ArrayList<Token> tokens;
    //private ArrayList<RowOfBlocks> blocks;

    Snake s = new Snake(250,300,5,root);

    private Parent createContent() {
        root.setPrefSize(500, 600);
        root.setStyle("-fx-background-color: #cd853f;");

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        timer.start();
        nextLevel();

        return root;
    }

    private void nextLevel() {
        for (int i = 0; i < 5; i++) {
            Wall s = new Wall(90 + i*100, 150, 50);
            walls.add(s);
            root.getChildren().add(s);
        }
        for (int i = 0; i < 5; i++) {
        	Block b = new Block(90 + i*100, 300, "1");
            blocks.add(b);
            root.getChildren().add(b);
        }
        for (int i = 0; i < 5; i++) {
        	BallToken b = new BallToken(90 + i*100, 350, "1");
            balls.add(b);
            root.getChildren().add(b);
        }
    }

    private void moveLeft(double amt){
        s.moveLeft(amt);
        boolean flag = false;
        for(Wall w: walls){
            flag |= s.intersection(w);
        }
        if(flag){
            s.moveRight(amt);
        }
    }

    private void moveRight(double amt){
        s.moveRight(amt);
        boolean flag = false;
        for(Wall w: walls){
            flag |= s.intersection(w);
        }
        if(flag){
            s.moveLeft(amt);
        }
    }

    public void deflectFromWalls(){
        boolean flag = false;
        Wall hitter;
        for(Wall w: walls){
            if(s.intersection(w)){
                hitter = w;
                double dist = hitter.getTranslateX() - s.getx() + 5 ;
                System.out.println("Inersection with wall head " + String.valueOf(dist));
                if(dist > 0){
                    s.moveLeft(9 - dist);
                }
                else{
                    s.moveRight(9 + dist);
                }
                break;
            }
        }
    }

    public void deflectFromBlocks(){
        Block hitter;
        boolean flag = true;
        for(Block w: blocks){
            if(s.intersection(w))
            {
            	flag = false;
            	System.out.println("LOL");
                hitter = w;
                int value = hitter.getValue();
                System.out.println("Value of block " + String.valueOf(value));
                System.out.println("Value of snake " + String.valueOf(s.getSize()));
                if(value > s.getSize())
                {

                	 for(int i = 0; i < value; i++)
                     {
                     	hitter.setText(Integer.toString(value - 1));
                     	if(s.getSize() > 0)
                     	{
                     		s.decLenghtBy(1);
                     	}
                     }
                }
                else
                {
                	for(int i = 0; i < value; i++)
                    {
                    	hitter.setText(Integer.toString(value - 1));
                    	if(s.getSize() > 0)
                     	{
                     		s.decLenghtBy(1);
                     	}
                    }
                	System.out.println("Size of children " + String.valueOf(root.getChildren().size()));
                	System.out.println("hitter is removed " + String.valueOf(hitter));
                	root.getChildren().remove(hitter);
                	blocks.remove(hitter);
                }
            }
            if(flag == false)
            {
            	break;
            }
        }
        if(s.getSize() == 0)
        {
        	System.exit(0);
        }
    }

    private void collectTokens()
    {

    }

    private void update()
    {
        t += 0.016;

        for(Wall w: walls){
            w.setTranslateY(w.getTranslateY() + 0.5);
        }

        deflectFromWalls();
        deflectFromBlocks();

        boolean flag = false;
        if(flag)
            System.out.println("intersection");

        if (t > 2) {
            t = 0;
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        createContent();
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A:
                    moveLeft(10);
                    System.out.println("left");
                    break;
                case D:
                    moveRight(10);
                    System.out.println("Right");
                    break;
            }
        });

        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}