package rnd.plani.co.kr.dadfarm.Home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import rnd.plani.co.kr.dadfarm.Data.PersonalData;
import rnd.plani.co.kr.dadfarm.OnProfileClickListener;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by toto9114 on 2016-10-27.
 */

public class OneRelationView extends FrameLayout {
    private OnProfileClickListener profileClickListener;

    public void setOnProfileClickListener(OnProfileClickListener listener) {
        profileClickListener = listener;
    }

    ImageView profileView;
    TextView nameView;

    public OneRelationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.view_one_relation, this);
        profileView = (ImageView) findViewById(R.id.image_me);
        nameView = (TextView) findViewById(R.id.text_my_name);

        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileClickListener != null) {
                    profileClickListener.OnProfileClick(me);
                }
            }
        });
    }

    PersonalData me;

    public void setOneRelation(PersonalData me) {
        this.me = me;
        Glide.with(getContext()).load(me.profile.image_url).into(profileView);
        nameView.setText(me.last_name + me.first_name);
    }
}
