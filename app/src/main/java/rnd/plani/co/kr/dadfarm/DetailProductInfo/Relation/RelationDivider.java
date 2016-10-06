package rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-28.
 */

public class RelationDivider extends RecyclerView.ViewHolder {
    TextView relationView;
    public RelationDivider(View itemView) {
        super(itemView);
        relationView = (TextView) itemView.findViewById(R.id.text_relation);
    }

    public void  setRelation(String relation){
        relationView.setText(relation);
    }
}
