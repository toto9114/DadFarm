package rnd.plani.co.kr.dadfarm;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by RND on 2016-09-27.
 */

public class PersonVerticalRelationView extends FrameLayout {
    ImageView profileView;
    TextView nameView,relationView;
    ImageView smsView, phoneCallView;
    public PersonVerticalRelationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(),R.layout.view_vertical_person_relation,this);
        profileView = (ImageView) findViewById(R.id.image_profile);
        nameView = (TextView) findViewById(R.id.text_name);
        relationView = (TextView) findViewById(R.id.text_relation);
        smsView = (ImageView) findViewById(R.id.image_sms);
        phoneCallView = (ImageView) findViewById(R.id.image_phone_call);
    }

    public void setRelationView(){

    }
}
