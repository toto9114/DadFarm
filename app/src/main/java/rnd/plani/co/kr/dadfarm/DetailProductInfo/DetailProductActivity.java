package rnd.plani.co.kr.dadfarm.DetailProductInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.widget.Button;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import rnd.plani.co.kr.dadfarm.CustomToolbar.BlackThemeShareToolbar;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnLeftMenuClickListener;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnRightMenuClickListener;
import rnd.plani.co.kr.dadfarm.Data.ProductData;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.DetailInfo.DetailInfoAdapter;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.DetailInfo.OnOrderBtnClickListener;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.DetailInfo.OnReviewBtnClickListener;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Order.OrderProductActivity;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Review.ReviewProductActivity;
import rnd.plani.co.kr.dadfarm.R;
import rnd.plani.co.kr.dadfarm.ShareDialog;

public class DetailProductActivity extends AppCompatActivity {

    public static final String EXTRA_PRODUCT_DATA = "product";
    ProductData product;
    Button reviewView, orderView;
    FamiliarRecyclerView recyclerView;
    DetailInfoAdapter mAdapter;
    LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        BlackThemeShareToolbar toolbar = (BlackThemeShareToolbar)findViewById(R.id.toolbar);
        toolbar.setToolbar("뒤로","상세정보",true);
        product = (ProductData) getIntent().getSerializableExtra(EXTRA_PRODUCT_DATA);
        recyclerView = (FamiliarRecyclerView) findViewById(R.id.recycler);
        mAdapter = new DetailInfoAdapter();
        layoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL,false);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(layoutManager);

//        reviewView = (Button) findViewById(R.id.btn_review);
//        orderView = (Button) findViewById(R.id.btn_order);
//
//
//        reviewView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(DetailProductActivity.this, ReviewProductActivity.class));
//            }
//        });
//
//        orderView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(DetailProductActivity.this, OrderProductActivity.class));
//            }
//        });

        mAdapter.setOnReviewBtnClickListener(new OnReviewBtnClickListener() {
            @Override
            public void OnReviewBtnClick() {
                startActivity(new Intent(DetailProductActivity.this, ReviewProductActivity.class));
            }
        });

        mAdapter.setOnOrderBtnClickListener(new OnOrderBtnClickListener() {
            @Override
            public void OnOrderBtnClick() {
                startActivity(new Intent(DetailProductActivity.this, OrderProductActivity.class));
            }
        });
        toolbar.setOnLeftMenuClickListener(new OnLeftMenuClickListener() {
            @Override
            public void OnLeftMenuClick() {
                finish();
            }
        });

        toolbar.setOnRightMenuClickListener(new OnRightMenuClickListener() {
            @Override
            public void OnRightMenuClick() {
                //공유하기
                ShareDialog dialog = new ShareDialog();
                dialog.show(getSupportFragmentManager(),"dialog");
            }
        });
        initData();
    }

    private void initData(){
        mAdapter.setProduct(product);
    }
}
