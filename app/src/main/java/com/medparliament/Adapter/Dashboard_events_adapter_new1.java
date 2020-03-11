
package com.medparliament.Adapter;


        import android.content.Context;
        import android.content.Intent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;

        import com.asksira.loopingviewpager.LoopingPagerAdapter;
        import com.medparliament.Activity.NewsDetails_Activity;
        import com.medparliament.Internet.Models.Dashbooard_eventModel;
        import com.medparliament.R;
        import com.medparliament.Utility.Comman;
        import com.medparliament.Utility.PrettyTimeClass;
        import com.medparliament.Widget.Open_Sans_Regular_Font;
        import com.medparliament.Widget.Segow_UI_Font;

        import java.util.ArrayList;

public class Dashboard_events_adapter_new1 extends LoopingPagerAdapter<Dashbooard_eventModel> {

    public Dashboard_events_adapter_new1(Context context, ArrayList<Dashbooard_eventModel> itemList, boolean isInfinite) {
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
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, NewsDetails_Activity.class);
//                    intent.putExtra("event",pm);
//                    context.startActivity(intent);
//                }
//            });



        }
    }
}
