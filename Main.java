import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.lang.reflect.Type;
import java.util.ArrayList;
import static java.lang.Math.abs;

public class Main extends Application {
    private Pane root = new Pane();
    private double t = 0;
    private ArrayList<Wall> walls = new ArrayList<>();
    private ArrayList<Token> tokens = new ArrayList<>();
    //private ArrayList<RowOfBlocks> blocks;

    Snake s = new Snake(250,300,5,root);

    private Parent createContent() {
        root.setPrefSize(500, 600);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        timer.start();
        nextLevel();
        addToken(100,300,"Shield");
        addToken(150,300,"coin");
        addToken(200,300,"magnet");
        addToken(350,300,"brickbuster");

        return root;
    }
    public void addToken(double x, double y,String type){
        if(type.equalsIgnoreCase("Shield")) {
            Shield s1 = new Shield(x, y);
            tokens.add(s1);
            root.getChildren().add(s1);
        }
        else if(type.equalsIgnoreCase("coin")) {
            Coins s1 = new Coins(x, y);
            tokens.add(s1);
            root.getChildren().add(s1);
        }
        else if(type.equalsIgnoreCase("Magnet")) {
            Magnet s1 = new Magnet(x, y);
            tokens.add(s1);
            root.getChildren().add(s1);
        }
        else if(type.equalsIgnoreCase("BrickBuster")) {
            BrickBuster s1 = new BrickBuster(x, y);
            tokens.add(s1);
            root.getChildren().add(s1);
        }
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
        double dist = 0;
        for(Wall w: walls){
            flag |= s.intersection(w);
            if(flag) {
                System.out.println(String.valueOf(s.getx()) + " : " + String.valueOf(w.getTranslateX()));
                dist = abs(s.getx() - w.getTranslateX() - 14);
                break;
            }
        }
        if(flag){
            s.moveRight(dist);
        }


    }
    private void moveRight(double amt){
        s.moveRight(amt);
        boolean flag = false;
        double dist = 0;
        for(Wall w: walls){
            flag |= s.intersection(w);
            if(flag) {
                System.out.println(String.valueOf(s.getx()) + " : " + String.valueOf(w.getTranslateX()));
                dist = abs(s.getx() - w.getTranslateX() + 4);
                break;
            }
        }
        if(flag){
            s.moveLeft(dist);
        }
    }

    public void deflectFromWalls(){
        boolean flag = false;
        Wall hitter;
        for(Wall w: walls){
            if(s.intersection(w)){
                flag = true;
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
        if(flag){

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

    private void collectTokens(){
        for(int i=0;i<tokens.size();i++){
            Token t1 = tokens.get(i);
            if(s.intersection(t1)){
                System.out.println("Token of type " + t1.getType());
                root.getChildren().remove(t1);
                tokens.remove(t1);
            }
        }
    }
    private void update() {
        t += 0.016;

        collectTokens();

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
