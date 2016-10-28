package rnd.plani.co.kr.dadfarm.Setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import rnd.plani.co.kr.dadfarm.CustomToolbar.BlackThemeTextToolbar;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnLeftMenuClickListener;
import rnd.plani.co.kr.dadfarm.R;

public class OpenSourceLicenseActivity extends AppCompatActivity {

    BlackThemeTextToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_source_license);
        toolbar = (BlackThemeTextToolbar) findViewById(R.id.toolbar);
        toolbar.setToolbar("닫기","오픈소스 라이센스","");
        toolbar.setOnLeftMenuClickListener(new OnLeftMenuClickListener() {
            @Override
            public void OnLeftMenuClick() {
                finish();
            }
        });
    }
}
