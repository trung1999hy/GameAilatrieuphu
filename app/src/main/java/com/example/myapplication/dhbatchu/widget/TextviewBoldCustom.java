package com.example.myapplication.dhbatchu.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;

import android.util.AttributeSet;

import com.example.myapplication.R;


public class TextviewBoldCustom extends androidx.appcompat.widget.AppCompatTextView {


    private static final String TYPE_FONT_DEFAULT = "fonts/ok1";
    private int currentColor = Color.BLACK;
    private String typeFont;

    public TextviewBoldCustom(Context context) {
        super(context);
    }

    public TextviewBoldCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        currentColor = getCurrentTextColor();
        try {
            TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.CustomTextView, 0, 0);

            typeFont = a.getString(R.styleable.CustomTextView_fonts);
            if (typeFont == null) {
                typeFont = TYPE_FONT_DEFAULT;
            }
            setTypeface(FontCache.getTypeface(context, typeFont));
            a.recycle();
        } catch (Exception ex) {
            try {
                setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/ok1"));
            } catch (Exception ex1) {
                setTypeface(Typeface.DEFAULT);
            }
        }
    }

    public void setTypeFontByName(Context context, String fontName) {
        String fontFile = String.format("fonts/%s", fontName);
        setTypeFont(context, fontFile);
    }

    public void setTypeFont(Context context, String typeFont) {
        this.typeFont = typeFont;
        setTypeface(FontCache.getTypeface(context, typeFont));
    }

    public String getTypeFont() {
        return typeFont;
    }
}
