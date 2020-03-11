package com.medparliament.Widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class Open_Sans_Regular_Font extends TextView {


    public Open_Sans_Regular_Font(Context context) {
        super(context);
        Typeface face= Typeface.createFromAsset(context.getAssets(), "Lato_Medium.ttf");
        this.setTypeface(face);
    }

    public Open_Sans_Regular_Font(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Lato_Medium.ttf");
        this.setTypeface(face);
    }

    public Open_Sans_Regular_Font(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Lato_Medium.ttf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);

    }

}
