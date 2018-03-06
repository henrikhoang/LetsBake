package com.example.henrikhoang.letsbake.data;

import android.net.Uri;

/**
 * Created by henrikhoang
 */

public class DbContract {
    public static final String AUTHORITY = "com.example.henrikhoang.letsbake";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_RECIPES = "recipes";


}
