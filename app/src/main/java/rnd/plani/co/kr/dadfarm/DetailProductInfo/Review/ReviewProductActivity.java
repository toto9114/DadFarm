package rnd.plani.co.kr.dadfarm.DetailProductInfo.Review;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import rnd.plani.co.kr.dadfarm.CustomToolbar.BlackThemeShareToolbar;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnLeftMenuClickListener;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Order.OnRelationClickListener;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.RelationInfoActivity;
import rnd.plani.co.kr.dadfarm.R;

public class ReviewProductActivity extends AppCompatActivity {

    FamiliarRecyclerView recyclerView;
    ReviewAdapter mAdaper;
    LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_product);
        BlackThemeShareToolbar toolbar = (BlackThemeShareToolbar) findViewById(R.id.toolbar);
        toolbar.setToolbar("뒤로", "구매후기",false);
        toolbar.setOnLeftMenuClickListener(new OnLeftMenuClickListener() {
            @Override
            public void OnLeftMenuClick() {
                finish();
            }
        });
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));
        }
        recyclerView = (FamiliarRecyclerView) findViewById(R.id.recycler);
        mAdaper = new ReviewAdapter();
        layoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addHeaderView(new ReviewHeaderView(this));
        recyclerView.setAdapter(mAdaper);
        mAdaper.setOnRelationClickListenr(new OnRelationClickListener() {
            @Override
            public void OnRelationClick(View view, int position) {
                Intent i = new Intent(ReviewProductActivity.this, RelationInfoActivity.class);
                startActivity(i);
            }
        });
        initData();
    }

    private void initData(){
        mAdaper.clear();
        ReviewData data = new ReviewData();
        data.name = "이지훈";
        data.date = "2016-09-17";
        data.content = "마실때마다 건강해지는 기분이에요.";
        data.relation = "판매자의 조카의 친구";
        data.product = "아로니아즙 1박스";
        mAdaper.add(data);
    }
}
