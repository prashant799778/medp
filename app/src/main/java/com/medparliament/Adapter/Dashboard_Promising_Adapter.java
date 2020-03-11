

package com.medparliament.Adapter;


        import android.content.Context;
        import android.content.Intent;
        import android.text.Html;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;

        import androidx.viewpager.widget.PagerAdapter;

        import com.medparliament.Activity.Login_Signup_Activity;
        import com.medparliament.Activity.New_News_Details_Activity;
        import com.medparliament.Activity.NewsDetails_Activity;
        import com.medparliament.Internet.Models.Dashboard_News_Model;
        import com.medparliament.Internet.NewModel.Result;
        import com.medparliament.R;
        import com.medparliament.Utility.Comman;
        import com.medparliament.Utility.PrettyTimeClass;
        import com.medparliament.Widget.Open_Sans_Regular_Font;
        import com.medparliament.Widget.Segow_UI_Font;
        import com.medparliament.Widget.Segow_UI_Semi_Font;

        import java.util.ArrayList;

public class Dashboard_Promising_Adapter  extends PagerAdapter {

    private Context context;
    ArrayList<Result> itemList;
    public Dashboard_Promising_Adapter(Context context, ArrayList<Result> itemList,boolean flag) {

        this.context = context;
        this.itemList=itemList;
        Log.d("aaa1","aaaaa"+itemList.size());
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        Log.d("aaa","aaaaa"+itemList.size());
        final Result pm = itemList.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup convertView = (ViewGroup) inflater.inflate(R.layout.dashboard_event_layout, collection, false);




        Comman.setImageWithCondition(context, (ImageView) convertView.findViewById(R.id.mainimg), pm.getImagePath());
        ((Segow_UI_Font) convertView.findViewById(R.id.title)).setText(pm.getNewsTitle());
        ((Open_Sans_Regular_Font) convertView.findViewById(R.id.msg)).setText(Html.fromHtml(pm.getNewsDesc()));
        (( Segow_UI_Font) convertView.findViewById(R.id.date)).setText(PrettyTimeClass.PrettyTime(Comman.timeInms(pm.getDateCreate1())));


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(Comman.Check_Login(context)){
                Intent intent = new Intent(context, New_News_Details_Activity.class);
                intent.putExtra("data", pm);

                context.startActivity(intent); } else {

                    Intent intent = new Intent(context, Login_Signup_Activity.class);


                    context.startActivity(intent);
                }
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
        final Result pm = itemList.get(position);
        return "";
    }


    public void updateList(ArrayList<Result> itemList){

        Comman.log("SizeNNN",""+itemList.size());
        this.itemList=itemList;
        super.notifyDataSetChanged();
    }

}