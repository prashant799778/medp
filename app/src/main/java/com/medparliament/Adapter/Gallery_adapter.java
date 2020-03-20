package com.medparliament.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.medparliament.Internet.Models.DashboardGalleryModel;
import com.medparliament.Internet.NewModel.NewModel;
import com.medparliament.R;
import com.medparliament.Utility.Comman;

import java.util.ArrayList;


public class Gallery_adapter  extends PagerAdapter {

    private Context context;
    ArrayList<NewModel> itemList;
    public Gallery_adapter(Context context, ArrayList<NewModel> itemList,boolean flag) {
        this.context = context;
        this.itemList=itemList;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        Log.d("aaa","aaaaa");
        final NewModel pm = itemList.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup convertView = (ViewGroup) inflater.inflate(R.layout.gallery_layout, collection, false);
        ImageView imageView;
        imageView=convertView.findViewById(R.id.main_img);
        Comman.setRectangleImage(context,imageView,pm.getImagePath());
        collection.addView(convertView);
        return convertView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        final NewModel pm = itemList.get(position);
        return "";
    }


    public void updateList(ArrayList<NewModel> itemList){
        this.itemList=itemList;
        super.notifyDataSetChanged();
    }

}