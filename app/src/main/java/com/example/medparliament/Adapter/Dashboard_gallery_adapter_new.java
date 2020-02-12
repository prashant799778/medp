
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
        import com.example.medparliament.Internet.Models.DashboardGalleryModel;
        import com.example.medparliament.Internet.Models.Dashboard_News_Model;
        import com.example.medparliament.R;
        import com.example.medparliament.Utility.Comman;
        import com.example.medparliament.Utility.PrettyTimeClass;
        import com.example.medparliament.Widget.Open_Sans_Regular_Font;
        import com.example.medparliament.Widget.Segow_UI_Semi_Font;

        import java.util.ArrayList;

public class Dashboard_gallery_adapter_new  extends LoopingPagerAdapter<DashboardGalleryModel> {

    public Dashboard_gallery_adapter_new (Context context, ArrayList<DashboardGalleryModel> itemList, boolean isInfinite) {
        super(context, itemList, isInfinite);
    }

    //This method will be triggered if the item View has not been inflated before.
    @Override
    protected View inflateView(int viewType, ViewGroup container, int listPosition) {
        return LayoutInflater.from(context).inflate(R.layout.gallery_layout, container, false);
    }

    //Bind your data with your item View here.
    //Below is just an example in the demo app.
    //You can assume convertView will not be null here.
    //You may also consider using a ViewHolder pattern.
    @Override
    protected void bindView(View convertView, int listPosition, int viewType) {
        if(convertView!=null) {

            ImageView imageView;

            imageView=convertView.findViewById(R.id.main_img);

            final DashboardGalleryModel pm= itemList.get(listPosition);
            Comman.setRectangleImage(context,imageView,pm.getImagePath());


        }
    }
}
