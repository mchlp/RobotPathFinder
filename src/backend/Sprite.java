/*
 * Michael Pu
 * RobotPathFinder - Sprite
 * ICS3U1 - Mr. Radulovic
 * November 27, 2017
 */

package backend;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

//TODO remove abstract class

/**
 * Represents a updatable node in JavaFX
 */
public abstract class Sprite {

    // the pane to add nodes to
    private static Pane sPane;

    // the list to add sprite to
    private static ArrayList<Sprite> sSpriteList;

    // position of current node
    protected Coordinate mPosition;

    // node of sprite to be displayed
    protected Node mNode;

    protected Sprite(Node node) {
        mNode = node;
        mPosition = new Coordinate();
        sPane.getChildren().add(mNode);
        sSpriteList.add(this);
    }

    /**
     * To be run every frame to update the sprite
     *
     * @param deltaTime time in seconds since last frame
     */
    public abstract void update(double deltaTime);

    public Node getmNode() {
        return mNode;
    }

    /**
     * @param pane pane to add the nodes of all sprites to
     */
    public static void setPane(Pane pane) {
        sPane = pane;
    }

    /**
     * @param spriteList list to add all sprites to
     */
    public static void setSpriteArrayList(ArrayList<Sprite> spriteList) {
        sSpriteList = spriteList;
    }
}
