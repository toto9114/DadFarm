package rnd.plani.co.kr.dadfarm.Home;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import rnd.plani.co.kr.dadfarm.Data.PersonalData;
import rnd.plani.co.kr.dadfarm.OnProfileClickListener;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by toto9114 on 2016-10-06.
 */

public class PersonHorizontalView extends RecyclerView.ViewHolder {
    private OnProfileClickListener profileClickListener;

    public void setOnProfileClickListener(OnProfileClickListener listener) {
        profileClickListener = listener;
    }

    ImageView profileView;
    TextView nameView;

    public PersonHorizontalView(View itemView) {
        super(itemView);
        profileView = (ImageView) itemView.findViewById(R.id.image_profile);
        nameView = (TextView) itemView.findViewById(R.id.text_name);
        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileClickListener != null) {
                    profileClickListener.OnProfileClick(data);
                }
            }
        });
    }

    PersonalData data = null;

    public void setProfile(PersonalData data) {
        this.data = data;
        if (data != null) {
            if (!TextUtils.isEmpty(data.profile.image_url)) {
                Glide.with(itemView.getContext()).load(data.profile.image_url).into(profileView);
                nameView.setText(data.last_name + data.first_name);
            }
        }
    }

}
