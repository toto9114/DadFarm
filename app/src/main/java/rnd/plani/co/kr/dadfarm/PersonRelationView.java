package rnd.plani.co.kr.dadfarm;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import rnd.plani.co.kr.dadfarm.Data.PersonalData;

/**
 * Created by RND on 2016-09-27.
 */

public class PersonRelationView extends FrameLayout {
    ImageView profileView;
    TextView nameView,relationView;
    ImageView smsView, phoneCallView;
    public PersonRelationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(),R.layout.view_person_relation,this);
        profileView = (ImageView) findViewById(R.id.image_profile);
        nameView = (TextView) findViewById(R.id.text_name);
        relationView = (TextView) findViewById(R.id.text_relation);
        smsView = (ImageView) findViewById(R.id.image_sms);
        phoneCallView = (ImageView) findViewById(R.id.image_phone_call);
    }

    public void setRelationView(PersonalData personalData,String relation){
        if(!TextUtils.isEmpty(personalData.profile.image_url)) {
            Glide.with(getContext()).load(personalData.profile.image_url).into(profileView);
        }
        nameView.setText(personalData.last_name+personalData.first_name);
        relationView.setText(relation);
    }
}
