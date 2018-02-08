package com.example.henrikhoang.letsbake.utility;

import android.content.Context;

import com.example.henrikhoang.letsbake.R;

/**
 * Created by henrikhoang on 1/24/18.
 */

public class ThumbnailUtil {
    public static String TAG = ThumbnailUtil.class.getSimpleName();

    public static int getImageResByName(Context context, String recipeName) {
        if (recipeName.equals(context.getResources().getString(R.string.brownies))) {
            return R.drawable.brownies;
        }
        if (recipeName.equals(context.getResources().getString(R.string.cheese_cake))) {
            return R.drawable.cheese_cake;
        }
        if (recipeName.equals(context.getResources().getString(R.string.nutella_pie))) {
            return R.drawable.nutella_pie;
        }
        else{
            return R.drawable.yellow_cake;
        }

    }
}
