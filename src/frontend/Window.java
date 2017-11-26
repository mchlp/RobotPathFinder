/*
 * Michael Pu
 * BallPhysics - Game
 * November 2017
 */

package frontend;

import backend.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Window extends Application {

    private long prevTime;

    private Stage primaryStage;
    private Pane root;
    private ArrayList<Sprite> allSpriteList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        primaryStage = stage;

        openSelectMapPopUp();

        allSpriteList = new ArrayList<>();

        root = new Pane();
        Scene scene = new Scene(root);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double minWindowDimension = Math.min(primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight());
        double newWindowDimension = minWindowDimension * 0.9;

        primaryStage.setTitle("Ball Physics");
        primaryStage.setWidth(newWindowDimension);
        primaryStage.setHeight(newWindowDimension);
        primaryStage.setResizable(false);
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

    private void openSelectMapPopUp() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setTitle("Select a Map");
        dialog.setWidth(350);
        dialog.setAlwaysOnTop(true);

        VBox dialogRoot = new VBox(10);
        Scene dialogScene = new Scene(dialogRoot);
        dialogRoot.setAlignment(Pos.TOP_LEFT);
        dialogRoot.setPadding(new Insets(15));
        dialog.setScene(dialogScene);

        File defaultDir = new File(System.getProperty("user.home"));
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(defaultDir);
        fileChooser.setTitle("Open Map File");
        File file = null;

        TextArea outputConsole = new TextArea();
        outputConsole.setEditable(false);
        outputConsole.setWrapText(true);
        // TODO add separator between lines
        outputConsole.setText("Please choose a map file to use.\n");

        Button selectFileButton = new Button("Choose File");

        Text fileText = new Text("No file has been selected.");
        fileText.setWrappingWidth(dialog.getWidth() * 0.9);
        fileText.setFill(Color.RED);

        dialogRoot.getChildren().add(outputConsole);
        dialogRoot.getChildren().add(selectFileButton);
        dialogRoot.getChildren().add(fileText);

        dialog.show();

        selectFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                File file = fileChooser.showOpenDialog(dialog);
                fileText.setText(file.getAbsolutePath());
                fileText.setFill(Color.BLACK);
                try {
                    outputConsole.appendText("Reading file: " + file.getCanonicalPath() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void onUpdate(double deltaTime) {

        for (Sprite sprite : allSpriteList) {
            sprite.update(deltaTime);
        }
    }
}
