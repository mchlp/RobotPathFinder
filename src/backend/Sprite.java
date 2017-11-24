/*
 * Michael Pu
 * BallPhysics - Sprite
 * November 2017
 */

package backend;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public abstract class Sprite {

    private static Pane sPane;
    private static ArrayList<Sprite> sSpriteList;

    protected Coordinate mPosition;
    protected Node mNode;

    protected Sprite(Node node) {
        mNode = node;
        sPane.getChildren().add(mNode);
        sSpriteList.add(this);
    }

    public abstract void update(double deltaTime);

    public Node getmNode() {
        return mNode;
    }

    public static void setPane(Pane pane) {
        sPane = pane;
    }

    public static void setSpriteArrayList(ArrayList<Sprite> spriteList) {
        sSpriteList = spriteList;
    }
}
