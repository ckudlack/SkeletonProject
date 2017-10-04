package com.cdk.skeletonproject.utils;

import android.support.v7.graphics.Palette;

public class BitmapUtils {

    // Return a palette's vibrant swatch after checking that it exists
    public static Palette.Swatch checkVibrantSwatch(Palette p) {
        Palette.Swatch vibrant = p.getMutedSwatch();
        if (vibrant != null) {
            return vibrant;
        }
        // Throw error

        return null;
    }
}
