package rnd.plani.co.kr.dadfarm.DetailProductInfo.DetailInfo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-28.
 */

public class ImageListView extends RecyclerView.ViewHolder {

    LinearLayout imageListView;
    public ImageListView(View itemView) {
        super(itemView);
        imageListView = (LinearLayout) itemView.findViewById(R.id.linear_image_list);
    }

    public void setImageList(){
        for(int i = 0 ; i < 3 ; i ++) {
            ProductImageView imageView = new ProductImageView(itemView.getContext());
            imageView.setImage(R.drawable.test_bg);
            imageListView.addView(imageView);
        }
    }
}
