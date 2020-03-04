
package com.medparliament.Adapter;


        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;

        import com.asksira.loopingviewpager.LoopingPagerAdapter;
        import com.medparliament.Internet.Models.DashboardGalleryModel;
        import com.medparliament.R;
        import com.medparliament.Utility.Comman;

        import java.util.ArrayList;

public class Dashboard_gallery_adapter_new1 extends LoopingPagerAdapter<DashboardGalleryModel> {

    public Dashboard_gallery_adapter_new1(Context context, ArrayList<DashboardGalleryModel> itemList, boolean isInfinite) {
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
    protected void bindView(View convertView, final int listPosition, int viewType) {

//        final String[] st=new String[itemList.size()+10];

        if(convertView!=null) {

            ImageView imageView;

            imageView=convertView.findViewById(R.id.main_img);

            final DashboardGalleryModel pm= itemList.get(listPosition);
            Comman.setRectangleImage(context,imageView,pm.getImagePath());


//            st[listPosition]=pm.getImagePath();
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    new ImageViewer.Builder(context,st)
////                            .setStartPosition(listPosition)
////                            .show();
//                }
//            });
//

        }
    }
}
