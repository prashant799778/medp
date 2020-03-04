package com.medparliament.Widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

@SuppressLint("AppCompatCustomView")
public class Segow_UI_EditText extends EditText {


    public Segow_UI_EditText(Context context) {
        super(context);
        Typeface face= Typeface.createFromAsset(context.getAssets(), "AtamiRegular.otf");
        this.setTypeface(face);
    }

    public Segow_UI_EditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "AtamiRegular.otf");
        this.setTypeface(face);
    }

    public Segow_UI_EditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "AtamiRegular.otf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);

    }

}

