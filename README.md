# RobotPathFinder
**Arrays Assignment for ICS3U1<br>**
_Michael Pu - December 2017<br>
Mr. Radulovic - Don Mills Collegiate Institute<br>_
Work log can be found in the commit history of this [GitHub repository](https://github.com/mchlp/RobotPathFinder)

## Description
- Reads and parses a map file into 2D array format
- Computes the shortest path from the starting position to the goal
- Displays the map and animates the shortest path

## Project Organization
Package                          | Description
---                              | ---
[algorithm](src/algorithm)       | files related to calculating the shortest path in the maze
[backend](src/backend)           | files related to the processing and calculations that is not part of the algorithm
[frontend](src/frontend)         | files related to the JavaFX components displayed on screen
[tests](src/tests)               | test cases files and JUnit test files for the algorithm

## Features
- Application icon
- A file select dialog to select the map
- Validates the map to ensure that it is of the proper dimensions, there are no invalid characters, and there is a possible path
- Buttons at the top of the animation window to easily restart the animation or select another map
- Window resizes automatically according to the size of the screen (90% of the height or width of the screen, depending on which is smaller)
- The size of each cell changes automatically depending on the dimensions of the map and the size of the window
- Keeps track of the number of moves made by the robot


## Map Legend
Cell Contents   | Meaning
---             | ---
0               | Obstacle
1               | Empty Space
R               | Starting Position
G               | Goal Position


