# RobotPathFinder
Arrays Assignment for ICS3U1
Work log can be found in the commit history here: https://github.com/mchlp/RobotPathFinder

## Description
- Reads and parses a map file into 2D array format
- Computes the shortest path from the starting position to the goal
- Displays the map and animates the shortest path

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


