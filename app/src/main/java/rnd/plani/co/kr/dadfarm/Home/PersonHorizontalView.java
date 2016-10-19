package rnd.plani.co.kr.dadfarm.Home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import rnd.plani.co.kr.dadfarm.OnProfileClickListener;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by toto9114 on 2016-10-06.
 */

public class PersonHorizontalView extends RecyclerView.ViewHolder {
    private OnProfileClickListener profileClickListener;
    public void setOnProfileClickListener(OnProfileClickListener listener){
        profileClickListener = listener;
    }
    ImageView profileView;
    public PersonHorizontalView(View itemView) {
        super(itemView);
        profileView  = (ImageView)itemView.findViewById(R.id.image_profile);
        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profileClickListener != null){
                    profileClickListener.OnProfileClick();
                }
            }
        });
    }
    public void setProfile(){

    }

}
