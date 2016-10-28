package rnd.plani.co.kr.dadfarm.Home;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import rnd.plani.co.kr.dadfarm.Data.PersonalData;
import rnd.plani.co.kr.dadfarm.Data.ProductData;
import rnd.plani.co.kr.dadfarm.OnProfileClickListener;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by toto9114 on 2016-10-27.
 */

public class TwoRelationView extends FrameLayout {
    private OnProfileClickListener profileClickListener;

    public void setOnProfileClickListener(OnProfileClickListener listener) {
        profileClickListener = listener;
    }

    ImageView myProfileView, sellerProfileView;
    TextView myNameView, sellerNameView, relationView;

    public TwoRelationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.view_two_relation, this);
        myProfileView = (ImageView) findViewById(R.id.image_me);
        sellerProfileView = (ImageView) findViewById(R.id.image_seller);
        myNameView = (TextView) findViewById(R.id.text_my_name);
        sellerNameView = (TextView) findViewById(R.id.text_seller_name);
        relationView = (TextView) findViewById(R.id.text_me_seller_relation);

        myProfileView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileClickListener != null) {
                    profileClickListener.OnProfileClick(me);
                }
            }
        });

        sellerProfileView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileClickListener != null) {
                    profileClickListener.OnProfileClick(seller);
                }
            }
        });
    }

    PersonalData seller;
    PersonalData me;

    public void setTwoRelation(PersonalData me, ProductData data, String relation) {
        this.seller = data.seller;
        this.me = me;
        if (!TextUtils.isEmpty(me.profile.image_url)) {
            Glide.with(getContext()).load(me.profile.image_url).into(myProfileView);
        }
        if (!TextUtils.isEmpty(data.seller.profile.image_url)) {
            Glide.with(getContext()).load(data.seller.profile.image_url).into(sellerProfileView);
        }
        myNameView.setText(me.last_name + me.first_name);
        sellerNameView.setText(data.seller.last_name + data.seller.first_name);
        relationView.setText(relation);
    }
}
