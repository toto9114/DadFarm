package rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-28.
 */

public class RelationDivider extends FrameLayout {
    TextView relationView;

    public RelationDivider(Context context) {
        super(context);
        inflate(getContext(),R.layout.view_vertical_relation,this);
        relationView = (TextView) findViewById(R.id.text_relation);
    }

    public void  setRelation(String relation){
        relationView.setText(relation);
    }
}
