package rnd.plani.co.kr.dadfarm.DetailProductInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import rnd.plani.co.kr.dadfarm.CustomToolbar.BlackThemeToolbar;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnLeftMenuClickListener;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnRightMenuClickListener;
import rnd.plani.co.kr.dadfarm.Data.ProductData;
import rnd.plani.co.kr.dadfarm.R;

public class DetailProductActivity extends AppCompatActivity {

    public static final String EXTRA_PRODUCT_DATA = "product";
    ProductData product;
    Button reviewView, orderView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        BlackThemeToolbar toolbar = (BlackThemeToolbar)findViewById(R.id.toolbar);
        toolbar.setToolbar("뒤로","상세정보",true);
        product = (ProductData) getIntent().getSerializableExtra(EXTRA_PRODUCT_DATA);

        reviewView = (Button) findViewById(R.id.btn_review);
        orderView = (Button) findViewById(R.id.btn_order);

        reviewView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailProductActivity.this, ReviewProductActivity.class));
            }
        });

        orderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
    }
}
