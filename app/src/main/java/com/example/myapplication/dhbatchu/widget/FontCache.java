package com.example.myapplication.dhbatchu.widget;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

public class FontCache {
    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    public static Typeface getTypeface(Context context, String fontname) {
        Typeface typeface = fontCache.get(fontname);

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), fontname + ".ttf");
            } catch (Exception e) {
                try {
                    typeface = Typeface.createFromAsset(context.getAssets(), fontname + ".otf");
                } catch (Exception e2) {
                    return null;
                }
            }

            fontCache.put(fontname, typeface);

        }

        return typeface;
    }
}
