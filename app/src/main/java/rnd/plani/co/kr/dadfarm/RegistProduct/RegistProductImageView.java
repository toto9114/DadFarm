package rnd.plani.co.kr.dadfarm.RegistProduct;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by toto9114 on 2016-10-11.
 */

public class RegistProductImageView extends FrameLayout {
    private OnCloseClickListener closeClickListener;

    public void setOnCloseClickListener(OnCloseClickListener listener) {
        closeClickListener = listener;
    }

    ImageView pictureView;
    ImageView closeView;

    public RegistProductImageView(Context context) {
        super(context);
        inflate(getContext(), R.layout.view_regist_product_image, this);
        pictureView = (ImageView) findViewById(R.id.image_picture);
        closeView = (ImageView) findViewById(R.id.image_close);
        closeView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (closeClickListener != null) {
                    closeClickListener.onCloseClick(imageUri);
                }
            }
        });
    }

    String imageUri = null;

    public void setImageFromUri(String imageUri) {
        this.imageUri = imageUri;
        Glide.with(getContext()).load(imageUri).into(pictureView);
    }
}
