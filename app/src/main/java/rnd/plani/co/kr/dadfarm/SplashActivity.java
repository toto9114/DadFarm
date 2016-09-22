package rnd.plani.co.kr.dadfarm;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import rnd.plani.co.kr.dadfarm.Certification.RecieveCertificationActivity;

public class SplashActivity extends AppCompatActivity {

    Handler mHandler = new Handler(Looper.getMainLooper());

    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loginBtn = (Button) findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, RecieveCertificationActivity.class));
            }
        });
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //버튼보이기
                loginBtn.setVisibility(View.VISIBLE);
            }
        }, 1500);
    }
}
