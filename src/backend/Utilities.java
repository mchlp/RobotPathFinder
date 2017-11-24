/*
 * Michael Pu
 * BallPhysics - Utilities
 * November 2017
 */

package backend;

import javafx.scene.paint.Color;

import java.io.InputStream;

public final class Utilities {

    public static final double EPSILON = 1E-5;
    public static final String IMAGE_DIRECTORY = "/images/";
    public static final String AUDIO_DIRECTORY = "/audio/";

    public static final Color BACKGROUND_GREY = Color.rgb(100, 100, 100);


    public static String getResource(String name) {
        return Utilities.class.getResource(name).toString();
    }

    public static InputStream getResourceAsStream(String name) {
        return Utilities.class.getResourceAsStream(name);
    }

    public static String round(double number, int places) {
        int roundedDouble = (int) (number * Math.pow(10, places));
        return Double.toString((double) roundedDouble / Math.pow(10, places));
    }
}

