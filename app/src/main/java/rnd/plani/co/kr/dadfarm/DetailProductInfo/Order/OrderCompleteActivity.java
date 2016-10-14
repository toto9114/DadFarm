package rnd.plani.co.kr.dadfarm.DetailProductInfo.Order;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import rnd.plani.co.kr.dadfarm.CustomToolbar.BlackThemeShareToolbar;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnLeftMenuClickListener;
import rnd.plani.co.kr.dadfarm.Data.RelationData;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.RelationAdapter;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Review.WriteReviewActivity;
import rnd.plani.co.kr.dadfarm.R;
import rnd.plani.co.kr.dadfarm.Utils;

public class OrderCompleteActivity extends AppCompatActivity {

    ListView listView;
    RelationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_complete);
        BlackThemeShareToolbar toolbar = (BlackThemeShareToolbar) findViewById(R.id.toolbar);
        toolbar.setToolbar("닫기", "주문완료", false);
        toolbar.setOnLeftMenuClickListener(new OnLeftMenuClickListener() {
            @Override
            public void OnLeftMenuClick() {
                finish();
            }
        });
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));
        }
        listView = (ListView) findViewById(R.id.listview);
        mAdapter = new RelationAdapter();
        listView.setAdapter(mAdapter);
        Button btn = (Button) findViewById(R.id.btn_write_review);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderCompleteActivity.this, WriteReviewActivity.class));
            }
        });

        initData();
    }

    private void initData() {
        for(int i = 0 ; i < 3 ; i++){
            RelationData data= new RelationData();
            data.name = "테스터" + i;
            data.role = "역할" + i;
            data.phone = "010-2672-4411";
            mAdapter.add(data);
            if(i<2){
                RelationData relation = new RelationData();
                relation.relation = "관계"+i;
                mAdapter.add(relation);
            }
        }
        Utils.setListViewHeightBasedOnChildren(listView);
        listView.setFocusable(false);
//        Utils.setRecyclerViewHeightBasedOnChildren(recyclerView);
    }
}
