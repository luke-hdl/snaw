package com.snaw.pixMapUtils;

public class CoordinateFlipper {
    //look...
    public static int flipOverCenter(int current, int min, int max) {
        int height = max - min;
        int flipped = min + (height - current);
        return flipped;
    }

    public static int flipOverSignedMagnitude(int current, int min, int max) {
        //Returns a value that, when added to current, will flip it -- e.g. in the range 0-100
        //current = 5 returns 90 (to get 95)
        //current = 95 returns -90 (to get 5)
        int height = max - min;
        boolean isShort = current < height / 2;
        int distanceFromNearestEdge = isShort ? current - min : max - current;
        return isShort ? max - distanceFromNearestEdge - current : -1 * (current - distanceFromNearestEdge);
    }
}
