package rnd.plani.co.kr.dadfarm.OrderList;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import rnd.plani.co.kr.dadfarm.CustomToolbar.BlackThemeShareToolbar;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnLeftMenuClickListener;
import rnd.plani.co.kr.dadfarm.R;
import rnd.plani.co.kr.dadfarm.Utils;

public class DetailOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        BlackThemeShareToolbar toolbar = (BlackThemeShareToolbar) findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setPadding(0, Utils.getStatusBarHeight(), 0, 0);
        }

        toolbar.setToolbar("닫기", "주문내역", false);
        toolbar.setOnLeftMenuClickListener(new OnLeftMenuClickListener() {
            @Override
            public void OnLeftMenuClick() {
                finish();
            }
        });
    }
}
