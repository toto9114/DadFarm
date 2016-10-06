package rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import rnd.plani.co.kr.dadfarm.CustomToolbar.BlackThemeTextToolbar;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnLeftMenuClickListener;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnRightMenuClickListener;
import rnd.plani.co.kr.dadfarm.R;

public class RelationInfoActivity extends AppCompatActivity {

    EditText relationView, myRelationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation_info);
        BlackThemeTextToolbar toolbar = (BlackThemeTextToolbar) findViewById(R.id.toolbar);
        toolbar.setToolbar("뒤로","이름","저장");
        toolbar.setOnLeftMenuClickListener(new OnLeftMenuClickListener() {
            @Override
            public void OnLeftMenuClick() {
                finish();
            }
        });
        toolbar.setOnRightMenuClickListener(new OnRightMenuClickListener() {
            @Override
            public void OnRightMenuClick() {
                //저장
            }
        });

        relationView = (EditText) findViewById(R.id.edit_relation);
        myRelationView = (EditText) findViewById(R.id.edit_my_relation);

    }
}
