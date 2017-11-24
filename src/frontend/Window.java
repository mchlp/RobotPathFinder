/*
 * Michael Pu
 * BallPhysics - Game
 * November 2017
 */

package frontend;

import backend.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Window extends Application {

    private long prevTime;

    private Pane root;
    private ArrayList<Sprite> allSpriteList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        allSpriteList = new ArrayList<>();

        root = new Pane();
        Scene scene = new Scene(root);


        primaryStage.setTitle("Ball Physics");
        //primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();

        Sprite.setPane(root);
        Sprite.setSpriteArrayList(allSpriteList);

        prevTime = System.nanoTime();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long curTime) {
                double deltaTime = (curTime - prevTime) / 1E9;
                //System.out.println(1 / deltaTime);
                onUpdate(deltaTime);
                prevTime = curTime;
            }
        };

        timer.start();
    }

    private void onUpdate(double deltaTime) {
        for (Sprite sprite : allSpriteList) {
            sprite.update(deltaTime);
        }
    }
}
