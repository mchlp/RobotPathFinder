/*
 * Michael Pu
 * RobotPathFinder - Window
 * ICS3U1 - Mr. Radulovic
 * January 07, 2018
 */

package frontend;

import algorithm.Cell;
import algorithm.InvalidMazeException;
import algorithm.Maze;
import algorithm.Path;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * JavaFX Application to be displayed to user.
 */
public class Window extends Application {

    // the width of the file select dialog
    private static final int DIALOG_WIDTH = 500;

    // format to display timestamp
    private static final SimpleDateFormat TIME_STAMP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS a\n");

    // the height of the menu bar on top of the display in pixels
    private static final double SCORE_BAR_HEIGHT = 40;
    private static final double WINDOW_PERCENTAGE_OF_FULL_SCREEN = 0.9;

    // path to application icon
    private static final String ICON = "/images/icon.png";

    // the last time the frame was updated
    private long prevTime;

    // the stage the application will be displayed in
    private Stage primaryStage;

    // member variables for elements on screen that need to be updated
    private Robot robot;
    private ScoreIndicator scoreIndicator;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        // opens the map select window
        openSelectMapPopUp();
    }

    /**
     * Opens up the map select window for the user to select a map and to process the selected map.
     */
    private void openSelectMapPopUp() {

        // new stage for the map select pop up
        Stage dialog = new Stage();

        // set up the stage for the map pop up
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setTitle("Select a Map");
        dialog.setWidth(DIALOG_WIDTH);
        dialog.setResizable(false);
        dialog.setAlwaysOnTop(true);
        dialog.getIcons().add(new Image(ICON));

        // set up the pane and scene for the map pop up
        VBox dialogRoot = new VBox(10);
        Scene dialogScene = new Scene(dialogRoot);
        dialogRoot.setAlignment(Pos.TOP_LEFT);
        dialogRoot.setPadding(new Insets(15));
        dialog.setScene(dialogScene);

        // set the default directory for the file chooser
        File defaultDir = new File(System.getProperty("user.home"));

        // set up the file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(defaultDir);
        fileChooser.setTitle("Open Map File");

        // set up the output console
        TextArea outputConsole = new TextArea();
        outputConsole.setPrefHeight(300);
        outputConsole.setEditable(false);
        outputConsole.setWrapText(true);
        outputConsole.setText(getTextWithTimeStamp("Please choose a map file to use.", true));

        // set up HBox for buttons
        HBox buttons = new HBox(10);

        // set up buttons
        Button selectFileButton = new Button("Choose File");

        Button startSimulationButton = new Button("Start Simulation");
        startSimulationButton.setDisable(true);

        buttons.getChildren().add(selectFileButton);
        buttons.getChildren().add(startSimulationButton);

        // set up the label display the path of the file selected
        Text fileText = new Text("No file has been selected.");
        fileText.setWrappingWidth(dialog.getWidth() * 0.9);
        fileText.setFill(Color.RED);

        // add everything to the root
        dialogRoot.getChildren().add(outputConsole);
        dialogRoot.getChildren().add(buttons);
        dialogRoot.getChildren().add(fileText);

        // show the dialog
        dialog.show();

        // set the action for when the select file button is pressed
        selectFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                // initialize variable to store maze
                Maze maze;

                // get a file from the file chooser dialog
                File mazeFile = fileChooser.showOpenDialog(dialog);

                // if no file was chosen, stop and return
                if (mazeFile == null) {
                    return;
                }

                // update the label with the path to the file
                fileText.setText(mazeFile.getAbsolutePath());
                fileText.setFill(Color.BLACK);

                // update output console text
                try {
                    outputConsole.appendText(getTextWithTimeStamp("Reading file: " + mazeFile.getCanonicalPath(), false));
                } catch (IOException e) {
                    outputConsole.appendText(getTextWithTimeStamp(e.toString() + "\nInvalid file. Please select another file", false));
                    fileText.setFill(Color.RED);
                    startSimulationButton.setDisable(true);
                    return;
                }

                // attempt to load and validate maze and handle errors
                try {
                    // reads the entire file into a string
                    String contents = new String(Files.readAllBytes(mazeFile.toPath()));
                    // parses the string into a maze
                    maze = new Maze(contents);
                } catch (InvalidMazeException e) {
                    // if an invalid maze file was read
                    outputConsole.appendText(getTextWithTimeStamp(e.toString() + "\nInvalid maze file. Please select another file.", false));
                    fileText.setFill(Color.RED);
                    startSimulationButton.setDisable(true);
                    return;
                } catch (IOException e) {
                    // if the maze file could not be accessed
                    outputConsole.appendText(getTextWithTimeStamp(e.toString() + "\nInvalid path to maze file. Please select another file", false));
                    fileText.setFill(Color.RED);
                    startSimulationButton.setDisable(true);
                    return;
                }

                // when the maze has been successfully loaded and validated
                outputConsole.appendText(getTextWithTimeStamp("Successfully read and parsed the maze file.", false));
                fileText.setFill(Color.GREEN);
                outputConsole.appendText(getTextWithTimeStamp("Attempting to find shortest path in maze...", false));

                // keeps track of when the path finding was started
                long startFindPath = System.nanoTime();

                //find the shortest path in the maze
                Path path = maze.solveMaze();

                if (path == null) {
                    // if no path from start point to goal was found
                    outputConsole.appendText(getTextWithTimeStamp("No path found. Please select another maze.", false));
                    fileText.setFill(Color.RED);
                    startSimulationButton.setDisable(true);

                } else {
                    // if a path was found
                    // calculate the time used to compute the shortest path
                    double findPathTime = (System.nanoTime() - startFindPath) / 1E6;
                    String findPathString = String.format("%.4f milliseconds", findPathTime);
                    outputConsole.appendText(getTextWithTimeStamp("Path found in " + findPathString + ". Ready for simulation.", false));

                    // enable start simulation button
                    startSimulationButton.setDisable(false);

                    // set action for when start simulation button is pressed
                    startSimulationButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            // starts the simulation screen
                            outputConsole.appendText(getTextWithTimeStamp("Opening simulation...", false));
                            dialog.close();
                            openSimulation(maze, path);
                        }
                    });
                }

            }
        });

    }

    /**
     * Opens up the simulation window to show a robot moving through the maze from the starting point to the goal point
     * using the shortest path.
     *
     * @param maze the maze to be displayed
     * @param path the shortest path from the starting point to the goal point
     */
    private void openSimulation(Maze maze, Path path) {

        // set up pane and screen
        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane);

        // set up HBox for display score and buttons in the menu bar (above root pane)
        HBox topBar = new HBox(5);
        topBar.setPadding(new Insets(0, 5, 0, 5));
        topBar.setPrefHeight(SCORE_BAR_HEIGHT);
        topBar.setAlignment(Pos.CENTER_LEFT);
        pane.setTop(topBar);

        // set up buttons in the HBox
        Button restartButton = new Button("Restart Simulation");
        topBar.getChildren().add(restartButton);

        Button otherMapButton = new Button("Select Another Map");
        topBar.getChildren().add(otherMapButton);

        // set up the score indicator in the HBox
        Text scoreIndicatorText = new Text();
        scoreIndicator = new ScoreIndicator(scoreIndicatorText);
        topBar.getChildren().add(scoreIndicatorText);

        // set up root pane for displaying simulation
        Pane root = new Pane();
        pane.setCenter(root);

        /* calculate the size of the window and the size of each square */

        // gets the minimum dimension of the screen
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double minWindowDimension = Math.min(primaryScreenBounds.getWidth(),
                primaryScreenBounds.getHeight() - SCORE_BAR_HEIGHT);

        // calculates the dimension of the window based on the smallest dimension
        double windowDimension = minWindowDimension * WINDOW_PERCENTAGE_OF_FULL_SCREEN;

        // calculates the dimensions of each cell in the map
        double squareSideLength = Math.floor(windowDimension / Math.max(maze.getWidth(), maze.getHeight()));

        // set up the size of the root pane using the calculated dimensions
        root.setPrefWidth(squareSideLength * maze.getWidth());
        root.setPrefHeight(squareSideLength * maze.getHeight());

        // set up canvas and add to the root
        Canvas canvas = new Canvas(root.getPrefWidth(), root.getPrefHeight());
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawGrid(gc, maze, squareSideLength);


        // set up robot
        ImageView robotImageView = new ImageView();
        robot = new Robot(robotImageView, path, maze.getStartingPos(), squareSideLength, gc);
        scoreIndicator.setmRobot(robot);
        root.getChildren().add(robotImageView);

        // set up primary stage
        primaryStage.setTitle("Simulator");
        primaryStage.getIcons().add(new Image(ICON));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

        // set up time frame was last updated
        prevTime = System.nanoTime();

        // set up animation timer
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long curTime) {
                // calculate the number of seconds that has elapsed since screen was last updated
                double deltaTime = (curTime - prevTime) / 1E9;

                // display FPS for debugging
                // System.out.println(1 / deltaTime);

                // update the screen
                onUpdate(deltaTime);

                // update the prevTime with current time
                prevTime = curTime;
            }
        };

        // set action for when restart button is pressed
        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timer.stop();
                path.resetIterator();
                openSimulation(maze, path);
            }
        });

        // set action for when use other map button is pressed
        otherMapButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timer.stop();
                primaryStage.close();
                openSelectMapPopUp();
            }
        });

        // start the timer
        timer.start();
    }


    /**
     * Draws the squares of each cell onto the canvas
     */
    private void drawGrid(GraphicsContext gc, Maze maze, double squareSideLength) {

        gc.setStroke(Color.BLACK);

        for (int col = 0; col < maze.getWidth(); col++) {
            for (int row = 0; row < maze.getHeight(); row++) {

                // get cell contents
                Cell curCell = maze.getCell(col, row);
                Color squareColor = null;

                // set colour of Rectangle
                switch (curCell) {
                    case GOAL:
                        squareColor = Color.RED;
                        break;
                    case START:
                        squareColor = Color.GREEN;
                        break;
                    case EMPTY:
                        squareColor = Color.LIGHTGREY;
                        break;
                    case WALL:
                        squareColor = Color.BLUE;
                }

                // draw on the canvas
                gc.setFill(squareColor);
                gc.fillRect(col * squareSideLength, row * squareSideLength, squareSideLength, squareSideLength);
                gc.strokeRect(col * squareSideLength, row * squareSideLength, squareSideLength, squareSideLength);
            }
        }
    }

    /**
     * Run every frame and updates the contents of the screen
     *
     * @param deltaTime the number of seconds elapsed since the last update
     */
    private void onUpdate(double deltaTime) {
        //System.out.println(1/deltaTime);
        robot.update(deltaTime);
        scoreIndicator.update();
    }

    /**
     * Takes a string of text and adds a timestamp in front of it and formats it to be displayed in a {@link TextArea}.
     *
     * @param text      The text to display.
     * @param firstLine If this is the first line in the {@link TextArea}.
     * @return The formatted string with a timestamp.
     */
    private String getTextWithTimeStamp(String text, boolean firstLine) {
        String timeStamp = TIME_STAMP.format(new Timestamp(System.currentTimeMillis()));
        if (firstLine) {
            return (timeStamp + text);
        } else {
            return ("\n-\n" + timeStamp + text);
        }
    }
}
