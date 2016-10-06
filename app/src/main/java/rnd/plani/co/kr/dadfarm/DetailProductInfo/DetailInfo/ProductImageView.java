package rnd.plani.co.kr.dadfarm.DetailProductInfo.DetailInfo;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;

import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-28.
 */

public class ProductImageView extends FrameLayout {
    ImageView pictureView;
    public ProductImageView(Context context) {
        super(context);
        pictureView = (ImageView) findViewById(R.id.image_picture);
    }
    public void setImage(){

    }
}
