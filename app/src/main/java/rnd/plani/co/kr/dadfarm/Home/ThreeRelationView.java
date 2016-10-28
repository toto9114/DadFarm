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

public class ThreeRelationView extends FrameLayout {
    private OnProfileClickListener profileClickListener;

    public void setOnProfileClickListener(OnProfileClickListener listener) {
        profileClickListener = listener;
    }

    ImageView myProfileView, managerProfileView, sellerProfileView;
    TextView myNameView, manageNameView, sellerNameView, meManagerRelationView, managerSellerRelationView;

    public ThreeRelationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.view_three_relation, this);

        myProfileView = (ImageView) findViewById(R.id.image_me);
        managerProfileView = (ImageView) findViewById(R.id.image_manager);
        sellerProfileView = (ImageView) findViewById(R.id.image_seller);
        myNameView = (TextView) findViewById(R.id.text_my_name);
        manageNameView = (TextView) findViewById(R.id.text_manager_name);
        sellerNameView = (TextView) findViewById(R.id.text_seller_name);
        meManagerRelationView = (TextView) findViewById(R.id.text_me_manager_relation);
        managerSellerRelationView = (TextView) findViewById(R.id.text_manager_seller_relation);


        myProfileView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileClickListener != null) {
                    profileClickListener.OnProfileClick(me);
                }
            }
        });

        managerProfileView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileClickListener != null) {
                    profileClickListener.OnProfileClick(manager);
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

    PersonalData me;
    PersonalData manager;
    PersonalData seller;

    public void setThreeRelation(PersonalData me, ProductData data, String meManagerrelation, String managerSellerRelation) {
        this.me = me;
        this.manager = data.manager;
        this.seller = data.seller;
        if (!TextUtils.isEmpty(me.profile.image_url)) {
            Glide.with(getContext()).load(me.profile.image_url).into(myProfileView);
        }
        if (!TextUtils.isEmpty(data.manager.profile.image_url)) {
            Glide.with(getContext()).load(data.manager.profile.image_url).into(managerProfileView);
        }
        if (!TextUtils.isEmpty(data.seller.profile.image_url)) {
            Glide.with(getContext()).load(data.seller.profile.image_url).into(sellerProfileView);
        }
        myNameView.setText(me.last_name + me.first_name);
        manageNameView.setText(data.manager.last_name + data.manager.first_name);
        sellerNameView.setText(data.seller.last_name + data.seller.first_name);

        meManagerRelationView.setText(meManagerrelation);
        managerSellerRelationView.setText(managerSellerRelation);
    }
}
