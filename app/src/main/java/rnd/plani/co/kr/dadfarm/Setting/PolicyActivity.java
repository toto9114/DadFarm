package rnd.plani.co.kr.dadfarm.Setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import rnd.plani.co.kr.dadfarm.R;

public class PolicyActivity extends AppCompatActivity {

    TextView contentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);
        contentView = (TextView) findViewById(R.id.text_content);
    }
}
