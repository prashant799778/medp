


package com.medparliament.Adapter;


        import android.content.Context;
        import android.content.Intent;
        import android.icu.text.SimpleDateFormat;
        import android.os.Build;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;

        import androidx.annotation.RequiresApi;

        import com.asksira.loopingviewpager.LoopingPagerAdapter;
        import com.medparliament.Activity.NewsDetails_Activity;
        import com.medparliament.Internet.Models.DashboardAnnouncedModel;
        import com.medparliament.R;
        import com.medparliament.Utility.Comman;
        import com.medparliament.Widget.Open_Sans_Regular_Font;
        import com.medparliament.Widget.Segow_UI_Font;

        import java.util.ArrayList;
        import java.util.Date;

public class Dashboard_annoncement_adapter_new1 extends LoopingPagerAdapter<DashboardAnnouncedModel> {

    public Dashboard_annoncement_adapter_new1(Context context, ArrayList<DashboardAnnouncedModel> itemList, boolean isInfinite) {
        super(context, itemList, isInfinite);
    }

    //This method will be triggered if the item View has not been inflated before.
    @Override
    protected View inflateView(int viewType, ViewGroup container, int listPosition) {
        return LayoutInflater.from(context).inflate(R.layout.announced_layout, container, false);
    }

    //Bind your data with your item View here.
    //Below is just an example in the demo app.
    //You can assume convertView will not be null here.
    //You may also consider using a ViewHolder pattern.
    @Override
    protected void bindView(View convertView, int listPosition, int viewType) {
        if(convertView!=null) {

            ImageView mainimg;
            Segow_UI_Font date,title,place;
            Open_Sans_Regular_Font msg;

            title=convertView.findViewById(R.id.title);
            date=convertView.findViewById(R.id.date);
            msg=convertView.findViewById(R.id.msg);
            place=convertView.findViewById(R.id.place);
            mainimg=convertView.findViewById(R.id.mainimg);
            final DashboardAnnouncedModel pm = itemList.get(listPosition);


            msg.setText(pm.getSummary());
            title.setText(pm.getTitle());


            Comman.log("ImagePath",""+pm.getImagePath());
            Comman.setRectangleImage(context,mainimg,pm.getImagePath());

            convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetails_Activity.class);
                intent.putExtra("ann",pm);
                context.startActivity(intent);
            }
        });



                   }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public String setDate(String date)
    {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");

        Date d = new Date();
        try {
            d = input.parse(date);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        String formatted="";
        if(output.format(d)!=null)
            formatted= output.format(d);
        return formatted;
    }
}
