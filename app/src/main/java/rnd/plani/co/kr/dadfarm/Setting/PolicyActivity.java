package rnd.plani.co.kr.dadfarm.Setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import okhttp3.Request;
import rnd.plani.co.kr.dadfarm.Data.TermsOrPrivacy;
import rnd.plani.co.kr.dadfarm.Manager.NetworkManager;
import rnd.plani.co.kr.dadfarm.R;

public class PolicyActivity extends AppCompatActivity {

    public static final String CONTENT_TYPE = "type";
    public static final String TYPE_TERMS = "terms";
    public static final String TYPE_PRIVACY = "privacy";

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);
        webView = (WebView) findViewById(R.id.webView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        switch (getIntent().getStringExtra(CONTENT_TYPE)){
            case TYPE_TERMS:
                NetworkManager.getInstance().getTerms(this, new NetworkManager.OnResultListener<TermsOrPrivacy>() {
                    @Override
                    public void onSuccess(Request request, TermsOrPrivacy result) {
                        if(result!=null){
                            webView.loadDataWithBaseURL(null,result.content,"text/html","utf-8",null);
                        }
                    }

                    @Override
                    public void onFailure(Request request, int code, Throwable cause) {

                    }
                });
                break;
            case TYPE_PRIVACY:
                NetworkManager.getInstance().getPrivacy(this, new NetworkManager.OnResultListener<TermsOrPrivacy>() {
                    @Override
                    public void onSuccess(Request request, TermsOrPrivacy result) {
                        if(result!=null){
                            webView.loadDataWithBaseURL(null,result.content,"text/html","utf-8",null);
                        }
                    }

                    @Override
                    public void onFailure(Request request, int code, Throwable cause) {

                    }
                });
                break;
        }

    }
}
