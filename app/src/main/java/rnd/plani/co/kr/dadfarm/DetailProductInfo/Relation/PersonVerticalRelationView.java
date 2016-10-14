package rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import rnd.plani.co.kr.dadfarm.Data.RelationData;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-28.
 */

public class PersonVerticalRelationView extends FrameLayout {

    private OnSmsClickListener smsClickListener;
    public void setOnSmsClickListener(OnSmsClickListener listener){
        smsClickListener = listener;
    }
    private OnPhoneCallClickListener phoneCallClickListener;
    public void setOnPhoneCallClickListener(OnPhoneCallClickListener listener){
        phoneCallClickListener = listener;
    }

    ImageView profileView;
    TextView nameView,roleView;
    ImageView smsView, phoneCallView;

    public PersonVerticalRelationView(Context context) {
        super(context);
        inflate(getContext(),R.layout.view_vertical_person_relation,this);
        profileView = (ImageView) findViewById(R.id.image_profile);
        nameView = (TextView) findViewById(R.id.text_name);
        roleView = (TextView) findViewById(R.id.text_role);
        smsView = (ImageView) findViewById(R.id.image_sms);
        phoneCallView = (ImageView) findViewById(R.id.image_phone_call);

        smsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(smsClickListener!=null){
                    smsClickListener.OnSmsClick(relationData.phone);
                }
            }
        });

        phoneCallView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneCallClickListener != null){
                    phoneCallClickListener.OnPhoneCallClick(relationData.phone);
                }
            }
        });
    }

    RelationData relationData;
    public void setPersonView(RelationData data){
        relationData = data;
        nameView.setText(data.name);
        roleView.setText(data.role);
    }

}
