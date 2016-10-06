package rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import rnd.plani.co.kr.dadfarm.Data.RelationData;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-28.
 */

public class PersonHorizontalRelationView extends RecyclerView.ViewHolder {
    ImageView profileView;
    TextView nameView,roleView;
    ImageView smsView, phoneCallView;

    public PersonHorizontalRelationView(View itemView) {
        super(itemView);
        profileView = (ImageView) itemView.findViewById(R.id.image_profile);
        nameView = (TextView) itemView.findViewById(R.id.text_name);
        roleView = (TextView) itemView.findViewById(R.id.text_role);
        smsView = (ImageView) itemView.findViewById(R.id.image_sms);
        phoneCallView = (ImageView) itemView.findViewById(R.id.image_phone_call);
    }

    public void setPersonView(RelationData data){
        nameView.setText(data.name);
        roleView.setText(data.role);
    }

}
