package rnd.plani.co.kr.dadfarm.Home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by toto9114 on 2016-10-06.
 */

public class HorizontalRelationDivider extends RecyclerView.ViewHolder {
    TextView relationView;
    public HorizontalRelationDivider(View itemView) {
        super(itemView);
        relationView = (TextView) itemView.findViewById(R.id.text_relation);
    }
    public void setRelation(String relation){
        relationView.setText(relation);
    }
}
