
package com.example.medparliament.Adapter;


        import android.content.Context;
        import android.content.Intent;
        import android.text.Html;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.asksira.loopingviewpager.LoopingPagerAdapter;
        import com.example.medparliament.Activity.NewsDetails_Activity;
        import com.example.medparliament.Internet.Models.Dashboard_News_Model;
        import com.example.medparliament.Internet.Models.Dashbooard_eventModel;
        import com.example.medparliament.R;
        import com.example.medparliament.Utility.Comman;
        import com.example.medparliament.Utility.PrettyTimeClass;
        import com.example.medparliament.Widget.Open_Sans_Regular_Font;
        import com.example.medparliament.Widget.Segow_UI_Font;
        import com.example.medparliament.Widget.Segow_UI_Semi_Font;

        import java.util.ArrayList;

public class Dashboard_events_adapter_new  extends LoopingPagerAdapter<Dashbooard_eventModel> {

    public Dashboard_events_adapter_new (Context context, ArrayList<Dashbooard_eventModel> itemList, boolean isInfinite) {
        super(context, itemList, isInfinite);
    }

    //This method will be triggered if the item View has not been inflated before.
    @Override
    protected View inflateView(int viewType, ViewGroup container, int listPosition) {
        return LayoutInflater.from(context).inflate(R.layout.dashboard_event_layout, container, false);
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

            final Dashbooard_eventModel pm=itemList.get(listPosition);
            Comman.setRectangleImage(context,mainimg,pm.getImagePath());
            msg.setText(pm.getEventSummary());
          title.setText(pm.getEventTitle());
           place.setText(pm.getEventLocation());
           date.setText(PrettyTimeClass.PrettyTime(Comman.timeInms(pm.getDateCreate())));
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NewsDetails_Activity.class);
                    intent.putExtra("event",pm);
                    context.startActivity(intent);
                }
            });



        }
    }
}
