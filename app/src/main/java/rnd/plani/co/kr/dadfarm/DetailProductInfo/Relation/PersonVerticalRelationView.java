package rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import rnd.plani.co.kr.dadfarm.Data.PersonalData;
import rnd.plani.co.kr.dadfarm.Manager.PropertyManager;
import rnd.plani.co.kr.dadfarm.OnProfileClickListener;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-28.
 */

public class PersonVerticalRelationView extends FrameLayout {

    private OnSmsClickListener smsClickListener;

    public void setOnSmsClickListener(OnSmsClickListener listener) {
        smsClickListener = listener;
    }

    private OnPhoneCallClickListener phoneCallClickListener;

    public void setOnPhoneCallClickListener(OnPhoneCallClickListener listener) {
        phoneCallClickListener = listener;
    }

    private OnProfileClickListener profileClickListener;

    public void setOnProfileClickListener(OnProfileClickListener listener) {
        profileClickListener = listener;
    }

    ImageView profileView;
    TextView nameView, roleView;
    ImageView badgeView, smsView, phoneCallView;

    public PersonVerticalRelationView(Context context) {
        super(context);
        inflate(getContext(), R.layout.view_vertical_person_relation, this);
        profileView = (ImageView) findViewById(R.id.image_profile);
        nameView = (TextView) findViewById(R.id.text_name);
        roleView = (TextView) findViewById(R.id.text_role);
        badgeView = (ImageView) findViewById(R.id.image_seller_badge);
        smsView = (ImageView) findViewById(R.id.image_sms);
        phoneCallView = (ImageView) findViewById(R.id.image_phone_call);

        smsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (smsClickListener != null) {
                    smsClickListener.OnSmsClick(personalData.profile.phone_number);
                }
            }
        });

        phoneCallView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneCallClickListener != null) {
                    phoneCallClickListener.OnPhoneCallClick(personalData.profile.phone_number);
                }
            }
        });

        profileView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileClickListener != null) {
                    profileClickListener.OnProfileClick(personalData);
                }
            }
        });
    }

    PersonalData personalData = null;

    public void setPersonView(PersonalData personalData, int position) {
        this.personalData = personalData;
        if (personalData.id == PropertyManager.getInstance().getUserId()) {
            phoneCallView.setVisibility(GONE);
            smsView.setVisibility(GONE);
        }
        nameView.setText(personalData.last_name + personalData.first_name);
        switch (position) {
            case 0:
                roleView.setText("판매자");
                badgeView.setVisibility(VISIBLE);
                break;
            case 2:
                roleView.setText("중계자");
                badgeView.setVisibility(GONE);
                break;
            case 4:
                roleView.setVisibility(INVISIBLE);
                badgeView.setVisibility(GONE);
                break;
            case 6:
                roleView.setVisibility(INVISIBLE);
                badgeView.setVisibility(GONE);
                break;
        }
        if (!TextUtils.isEmpty(personalData.profile.image_url)) {
            Glide.with(getContext()).load(personalData.profile.image_url).into(profileView);
        }
    }

}
