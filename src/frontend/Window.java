/*
 * Michael Pu
 * BallPhysics - Game
 * November 2017
 */

package frontend;

import algorithm.*;
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
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
    }

    private void openSelectMapPopUp() {

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setTitle("Select a Map");
        dialog.setWidth(500);
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

        OutputConsole outputConsole = new OutputConsole();
        outputConsole.setPrefHeight(300);
        outputConsole.setEditable(false);
        outputConsole.setWrapText(true);
        outputConsole.setInitalText("Please choose a map file to use.");

        HBox buttons = new HBox(10);

        Button selectFileButton = new Button("Choose File");
        buttons.getChildren().add(selectFileButton);

        Button startSimulationButton = new Button("Start Simulation");
        startSimulationButton.setDisable(true);
        buttons.getChildren().add(startSimulationButton);


        Text fileText = new Text("No file has been selected.");
        fileText.setWrappingWidth(dialog.getWidth() * 0.9);
        fileText.setFill(Color.RED);

        dialogRoot.getChildren().add(outputConsole);
        dialogRoot.getChildren().add(buttons);
        dialogRoot.getChildren().add(fileText);

        dialog.show();

        selectFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Maze maze;

                File mapFile = fileChooser.showOpenDialog(dialog);

                if (mapFile == null) {
                    return;
                }

                fileText.setText(mapFile.getAbsolutePath());
                fileText.setFill(Color.BLACK);

                try {
                    outputConsole.appendText("Reading file: " + mapFile.getCanonicalPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    maze = loadMap(mapFile);
                } catch (InvalidMapException e) {
                    outputConsole.appendText("Invalid map file. Please select another file.");
                    fileText.setFill(Color.RED);
                    return;
                } catch (IOException e) {
                    outputConsole.appendText("Invalid path to map file. Please select another file");
                    fileText.setFill(Color.RED);
                    return;
                }

                outputConsole.appendText("Successfully read and parsed the map file.");
                fileText.setFill(Color.GREEN);
                outputConsole.appendText("Attempting to find shortest path in map...");
                long startFindPath = System.nanoTime();
                Path path = findPath(maze);

                if (path == null) {
                    outputConsole.appendText("No path found. Please select another map.");
                    fileText.setFill(Color.RED);
                    return;
                } else {
                    double findPathTime = (System.nanoTime()-startFindPath) / 1E6;
                    String findPathString = String.format("%.4f milliseconds", findPathTime);
                    outputConsole.appendText("Path found in " + findPathString + ". Ready for simulation.");
                    startSimulationButton.setDisable(false);

                    startSimulationButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            openSimulation(maze, path);
                            dialog.hide();
                        }
                    });
                }

            }
        });

    }

    private Maze loadMap(File mapFile) throws InvalidMapException, IOException {
        String contents = new String(Files.readAllBytes(mapFile.toPath()));
        return MazeParser.parseMaze(contents);
    }

    private Path findPath(Maze maze) {
        return MazeSolver.solve(maze);
    }


    private void openSimulation(Maze maze, Path path) {

        allSpriteList = new ArrayList<>();

        root = new Pane();
        Scene scene = new Scene(root);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double minWindowDimension = Math.min(primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight());
        double newWindowDimension = minWindowDimension * 0.9;

        primaryStage.setTitle("Simulator");
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

    private void onUpdate(double deltaTime) {
        for (Sprite sprite : allSpriteList) {
            sprite.update(deltaTime);
        }
    }
}
