package rnd.plani.co.kr.dadfarm.DetailProductInfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import rnd.plani.co.kr.dadfarm.CustomToolbar.BlackThemeToolbar;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnLeftMenuClickListener;
import rnd.plani.co.kr.dadfarm.R;

public class ReviewProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_product);
        BlackThemeToolbar toolbar = (BlackThemeToolbar) findViewById(R.id.toolbar);
        toolbar.setToolbar("뒤로", "구매후기",false);
        toolbar.setOnLeftMenuClickListener(new OnLeftMenuClickListener() {
            @Override
            public void OnLeftMenuClick() {
                finish();
            }
        });
    }
}
