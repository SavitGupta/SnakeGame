import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class g extends Application {
    private Pane root = new Pane();
    private double t = 0;
    private Sprite player = new Sprite(300, 350, 40, 40, "player", Color.BLUE);
    private ArrayList<Sprite> sprites = new ArrayList<>();
    Snake s = new Snake(250,300,5,root);

    private Parent createContent() {
        root.setPrefSize(500, 600);

        root.getChildren().add(player);

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
            Sprite s = new Sprite(90 + i*100, 150, 30, 30, "enemy", Color.RED);
            sprites.add(s);
            root.getChildren().add(s);
        }
    }


    private void update() {
        t += 0.016;
//        for(int i=0;i<root.getChildren().size(); i++){
//
//
//        }
        boolean flag = false;
        for(Sprite s1: sprites){
            flag |= s.intersection(s1);
        }
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
                    s.moveLeft(10);
                    System.out.println("left");
                    break;
                case D:
                    s.moveRight(10);
                    System.out.println("Right");
                    break;
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    private static class Sprite extends Rectangle {
        boolean dead = false;
        final String type;

        Sprite(int x, int y, int w, int h, String type, Color color) {
            super(w, h, color);

            this.type = type;
            setTranslateX(x);
            setTranslateY(y);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}