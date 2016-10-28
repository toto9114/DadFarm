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

public class FourRelationView extends FrameLayout {
    private OnProfileClickListener profileClickListener;

    public void setOnProfileClickListener(OnProfileClickListener listener) {
        profileClickListener = listener;
    }

    ImageView myProfileView, friendProfileView, managerProfileView, sellerProfileView;
    TextView myNameView, friendNameView, manageNameView, sellerNameView, meFriendRelationView, friendManagerRelationView, managerSellerRelationView;

    public FourRelationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.view_four_relation, this);

        myProfileView = (ImageView) findViewById(R.id.image_me);
        friendProfileView = (ImageView) findViewById(R.id.image_friend);
        managerProfileView = (ImageView) findViewById(R.id.image_manager);
        sellerProfileView = (ImageView) findViewById(R.id.image_seller);
        myNameView = (TextView) findViewById(R.id.text_my_name);
        friendNameView = (TextView) findViewById(R.id.text_friend_name);
        manageNameView = (TextView) findViewById(R.id.text_manager_name);
        sellerNameView = (TextView) findViewById(R.id.text_seller_name);
        meFriendRelationView = (TextView) findViewById(R.id.text_me_friend_relation);
        friendManagerRelationView = (TextView) findViewById(R.id.text_friend_manager_relation);
        managerSellerRelationView = (TextView) findViewById(R.id.text_manager_seller_relation);

        myProfileView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileClickListener != null) {
                    profileClickListener.OnProfileClick(me);
                }
            }
        });

        friendProfileView.setOnClickListener(new OnClickListener() {
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
    PersonalData friend;
    PersonalData manager;
    PersonalData seller;

    public void setFourRelation(PersonalData me, ProductData data, String meFriendRelation, String friendManagerRelation, String managerSellerRelation) {
        this.me = me;
        this.friend = data.friend;
        this.manager = data.manager;
        this.seller = data.seller;
        if (!TextUtils.isEmpty(me.profile.image_url)) {
            Glide.with(getContext()).load(me.profile.image_url).into(myProfileView);
        }
        if (TextUtils.isEmpty(data.friend.profile.image_url)) {
            Glide.with(getContext()).load(data.friend.profile.image_url).into(friendProfileView);
        }
        if (!TextUtils.isEmpty(data.manager.profile.image_url)) {
            Glide.with(getContext()).load(data.manager.profile.image_url).into(managerProfileView);
        }
        if (!TextUtils.isEmpty(data.seller.profile.image_url)) {
            Glide.with(getContext()).load(data.seller.profile.image_url).into(sellerProfileView);
        }
        myNameView.setText(me.last_name + me.first_name);
        manageNameView.setText(data.friend.last_name + data.friend.first_name);
        manageNameView.setText(data.manager.last_name + data.manager.first_name);
        sellerNameView.setText(data.seller.last_name + data.seller.first_name);
        meFriendRelationView.setText(data.relationships.get(meFriendRelation));
        friendManagerRelationView.setText(friendManagerRelation);
        managerSellerRelationView.setText(managerSellerRelation);
    }
}
