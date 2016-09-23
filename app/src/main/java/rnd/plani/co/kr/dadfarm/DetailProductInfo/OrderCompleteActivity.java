package rnd.plani.co.kr.dadfarm.DetailProductInfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import rnd.plani.co.kr.dadfarm.CustomToolbar.BlackThemeToolbar;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnLeftMenuClickListener;
import rnd.plani.co.kr.dadfarm.R;

public class OrderCompleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_complete);
        BlackThemeToolbar toolbar = (BlackThemeToolbar) findViewById(R.id.toolbar);
        toolbar.setToolbar("닫기", "주문완료",false);
        toolbar.setOnLeftMenuClickListener(new OnLeftMenuClickListener() {
            @Override
            public void OnLeftMenuClick() {
                finish();
            }
        });
    }
}
