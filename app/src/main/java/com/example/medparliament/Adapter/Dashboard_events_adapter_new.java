

package com.example.medparliament.Adapter;







        import android.content.Context;
        import android.content.Intent;
        import android.text.Html;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;

        import androidx.viewpager.widget.PagerAdapter;

        import com.example.medparliament.Activity.DashBoard_Activity;
        import com.example.medparliament.Activity.Login_Signup_Activity;
        import com.example.medparliament.Activity.NewsDetails_Activity;
        import com.example.medparliament.Internet.Models.DashboardGalleryModel;
        import com.example.medparliament.Internet.Models.Dashboard_News_Model;
        import com.example.medparliament.Internet.Models.Dashbooard_eventModel;
        import com.example.medparliament.Internet.Models.Video_Model;
        import com.example.medparliament.R;
        import com.example.medparliament.Utility.Comman;
        import com.example.medparliament.Utility.PrettyTimeClass;
        import com.example.medparliament.Widget.Open_Sans_Regular_Font;
        import com.example.medparliament.Widget.Segow_UI_Font;
        import com.example.medparliament.Widget.Segow_UI_Semi_Font;
        import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
        import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
        import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
        import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

        import org.jetbrains.annotations.NotNull;

        import java.util.ArrayList;

public class Dashboard_events_adapter_new extends PagerAdapter {

    private Context context;
    ArrayList<Dashbooard_eventModel> itemList;
    public  Dashboard_events_adapter_new (Context context, ArrayList<Dashbooard_eventModel> itemList, boolean flag) {
        this.context = context;
        this.itemList=itemList;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        Log.d("aaa","aaaaa");
        final Dashbooard_eventModel pm = itemList.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup convertView = (ViewGroup) inflater.inflate(R.layout.dashboard_event_layout, collection, false);

        ImageView mainimg;
        Segow_UI_Font date,title,place;
        Open_Sans_Regular_Font msg;

        title=convertView.findViewById(R.id.title);
        date=convertView.findViewById(R.id.date);
        msg=convertView.findViewById(R.id.msg);
        place=convertView.findViewById(R.id.place);
        mainimg=convertView.findViewById(R.id.mainimg);

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
        final Dashbooard_eventModel pm = itemList.get(position);
        return "";
    }


    public void updateList(ArrayList<Dashbooard_eventModel> itemList){

        this.itemList=itemList;
        super.notifyDataSetChanged();
    }

}