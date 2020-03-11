package com.medparliament.carouselrecyclerview;


import androidx.annotation.DrawableRes;

public class ItemData {

    private final String mItemName;
    @DrawableRes
    private final int mDrawable;

    public ItemData(String itemName, int drawable) {
        mItemName = itemName;
        mDrawable = drawable;
    }

    public String getItemName() {
        return mItemName;
    }

    public int getDrawable() {
        return mDrawable;
    }
}
