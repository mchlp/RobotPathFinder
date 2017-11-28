/*
 * Michael Pu
 * RobotPathFinder - Window
 * ICS3U1 - Mr. Radulovic
 * November 27, 2017
 */

package frontend;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import algorithm.Cell;
import algorithm.InvalidMapException;
import algorithm.Maze;
import algorithm.Path;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Window extends Application {

	private static final double SCORE_BAR_HEIGHT = 40;

	private long prevTime;

	private Stage primaryStage;
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
		dialog.setResizable(false);
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
					outputConsole.appendText(e.toString() + "\nInvalid file. Please select another file");
					fileText.setFill(Color.RED);
					return;
				}

				try {
					maze = loadMap(mapFile);
				} catch (InvalidMapException e) {
					outputConsole.appendText(e.toString() + "\nInvalid map file. Please select another file.");
					fileText.setFill(Color.RED);
					return;
				} catch (IOException e) {
					outputConsole.appendText(e.toString() + "\nInvalid path to map file. Please select another file");
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
				} else {
					double findPathTime = (System.nanoTime() - startFindPath) / 1E6;
					String findPathString = String.format("%.4f milliseconds", findPathTime);
					outputConsole.appendText("Path found in " + findPathString + ". Ready for simulation.");
					startSimulationButton.setDisable(false);

					startSimulationButton.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							openSimulation(maze, path);
							outputConsole.appendText("Opening simulation...");
							dialog.hide();
						}
					});
				}

			}
		});

	}

	private Maze loadMap(File mapFile) throws InvalidMapException, IOException {
		String contents = new String(Files.readAllBytes(mapFile.toPath()));
		return new Maze(contents);
	}

	private Path findPath(Maze maze) {
		return maze.solveMaze();
	}

	private void openSimulation(Maze maze, Path path) {

		allSpriteList = new ArrayList<>();

		BorderPane pane = new BorderPane();
		Scene scene = new Scene(pane);

		Pane root = new Pane();
		pane.setCenter(root);

		HBox topBar = new HBox(5);
		topBar.setPadding(new Insets(0, 5, 0, 5));
		topBar.setPrefHeight(SCORE_BAR_HEIGHT);
		topBar.setAlignment(Pos.CENTER_LEFT);
		pane.setTop(topBar);

		Sprite.setSpriteArrayList(allSpriteList);
		Sprite.setPane(topBar);

		Button restartButton = new Button("Restart Simulation");
		topBar.getChildren().add(restartButton);

		Button otherMapButton = new Button("Select Another Map");
		topBar.getChildren().add(otherMapButton);

		ScoreIndicator scoreIndicator = new ScoreIndicator(new Text());

		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		double minWindowDimension = Math.min(primaryScreenBounds.getWidth(),
				primaryScreenBounds.getHeight() - SCORE_BAR_HEIGHT) * 0.9;
		double squareSideLength = minWindowDimension / Math.max(maze.getWidth(), maze.getHeight());

		Sprite.setPane(root);

		root.setPrefWidth(squareSideLength * maze.getWidth());
		root.setPrefHeight(squareSideLength * maze.getHeight());

		Rectangle[][] mazeRects = new Rectangle[maze.getWidth()][maze.getHeight()];

		for (int col = 0; col < maze.getWidth(); col++) {
			for (int row = 0; row < maze.getHeight(); row++) {

				Cell curCell = maze.getCell(col, row);
				Color squareColor = null;

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

				Rectangle cellRect = new Rectangle();
				cellRect.setStroke(Color.BLACK);
				cellRect.setStrokeWidth(1);
				cellRect.setX(col * squareSideLength);
				cellRect.setY(row * squareSideLength);
				cellRect.setWidth(squareSideLength);
				cellRect.setHeight(squareSideLength);
				cellRect.setFill(squareColor);

				root.getChildren().add(cellRect);
				mazeRects[col][row] = cellRect;
			}
		}

		ImageView robotImageView = new ImageView();
		Robot robot = new Robot(robotImageView, path, maze.getStartingPos(), squareSideLength, mazeRects);
		scoreIndicator.setmRobot(robot);

		primaryStage.setTitle("Simulator");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();

		prevTime = System.nanoTime();
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long curTime) {
				double deltaTime = (curTime - prevTime) / 1E9;
				System.out.println(1 / deltaTime);
				onUpdate(deltaTime);
				prevTime = curTime;
			}
		};

		timer.start();

		restartButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				timer.stop();
				path.resetIterator();
				openSimulation(maze, path);
			}
		});

		otherMapButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				timer.stop();
				allSpriteList.clear();
				primaryStage.hide();
				openSelectMapPopUp();
			}
		});
	}

	private void onUpdate(double deltaTime) {
		for (Sprite sprite : allSpriteList) {
			sprite.update(deltaTime);
		}
	}
}
