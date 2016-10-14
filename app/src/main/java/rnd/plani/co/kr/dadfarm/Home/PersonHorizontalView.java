package rnd.plani.co.kr.dadfarm.Home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by toto9114 on 2016-10-06.
 */

public class PersonHorizontalView extends RecyclerView.ViewHolder {
    ImageView profileView;
    public PersonHorizontalView(View itemView) {
        super(itemView);
        profileView  = (ImageView)itemView.findViewById(R.id.image_profile);
    }
    public void setProfile(){

    }

}
