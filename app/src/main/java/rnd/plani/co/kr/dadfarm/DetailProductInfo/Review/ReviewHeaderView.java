package rnd.plani.co.kr.dadfarm.DetailProductInfo.Review;

import android.content.Context;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import rnd.plani.co.kr.dadfarm.Data.PersonalData;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-28.
 */

public class ReviewHeaderView extends FrameLayout {
    ImageView profileView;
    TextView nameView, roleView;

    public ReviewHeaderView(Context context) {
        super(context);
        inflate(getContext(), R.layout.view_review_header, this);
        profileView = (ImageView) findViewById(R.id.image_profile);
        nameView = (TextView) findViewById(R.id.text_name);
    }

    public void setReviewHeader(PersonalData seller) {
        if (!TextUtils.isEmpty(seller.profile.image_url)) {
            Glide.with(getContext()).load(seller.profile.image_url).into(profileView);
        }
        nameView.setText(seller.last_name + seller.first_name);
    }

}
