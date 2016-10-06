package rnd.plani.co.kr.dadfarm.DetailProductInfo.Review;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-28.
 */

public class ReviewHeaderView extends FrameLayout {
    ImageView profileView;
    TextView nameView, roleView;

    public ReviewHeaderView(Context context) {
        super(context);
        inflate(getContext(), R.layout.view_review_header,this);
        profileView = (ImageView) findViewById(R.id.image_profile);
        nameView = (TextView) findViewById(R.id.text_name);
        roleView = (TextView) findViewById(R.id.text_role);

        nameView.setText("백우진");
        roleView.setText("판매자");
    }

    public void setReviewHeader(){

    }

}
