


package com.medparliament.Adapter;







        import android.content.Context;
        import android.content.Intent;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;

        import androidx.viewpager.widget.PagerAdapter;

        import com.medparliament.Activity.NewsDetails_Activity;
        import com.medparliament.Internet.Models.DashboardAnnouncedModel;
        import com.medparliament.Internet.NewModel.NewModel;
        import com.medparliament.R;
        import com.medparliament.Utility.Comman;
        import com.medparliament.Widget.Open_Sans_Regular_Font;
        import com.medparliament.Widget.Segow_UI_Font;

        import java.util.ArrayList;

public class Dashboard_annoncement_adapter_new extends PagerAdapter {

    private Context context;
    ArrayList<NewModel> itemList;
    
    public  Dashboard_annoncement_adapter_new (Context context, ArrayList<NewModel> itemList, boolean flag) {
        this.context = context;
        this.itemList=itemList;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        //Log.d("aaa","aaaaa");
        final NewModel pm = itemList.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup convertView = (ViewGroup) inflater.inflate(R.layout.announced_layout, collection, false);

        ImageView mainimg;
        Segow_UI_Font date,title,place;
        Open_Sans_Regular_Font msg;

        title=convertView.findViewById(R.id.title);
        date=convertView.findViewById(R.id.date);
        msg=convertView.findViewById(R.id.msg);
        place=convertView.findViewById(R.id.place);
        mainimg=convertView.findViewById(R.id.mainimg);



//        msg.setText(pm.getSummary());
//        title.setText(pm.getTitle());


        Comman.log("ImagePath",""+pm.getImagePath());
        Comman.setRectangleImage(context,mainimg,pm.getImagePath());

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, NewsDetails_Activity.class);
//                intent.putExtra("ann",pm);
//                context.startActivity(intent);
//            }
//        });

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