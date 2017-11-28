/*
 * Michael Pu
 * RobotPathFinder - Utilities
 * ICS3U1 - Mr. Radulovic
 * November 27, 2017
 */

package backend;

import java.io.InputStream;

//TODO remove final classs

/**
 * Utilities for various functions
 */
public final class Utilities {

    // precision for comparing doubles
    public static final double EPSILON = 1E-5;

    // directory where images are stored
    public static final String IMAGE_DIRECTORY = "/images/";

    /**
     * Gets a resource from the resource folder
     *
     * @param path path to resource
     * @return the resource as an {@link InputStream}
     */
    public static InputStream getResourceAsStream(String path) {
        return Utilities.class.getResourceAsStream(path);
    }

    /**
     * Truncates a double to the specified number of decimal places
     *
     * @param number the double to truncate
     * @param places the number of decimal places to keep
     * @return the new truncated double
     */
    public static String truncate(double number, int places) {
        int roundedDouble = (int) (number * Math.pow(10, places));
        return Double.toString((double) roundedDouble / Math.pow(10, places));
    }
}

