package rnd.plani.co.kr.dadfarm.Setting;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rnd.plani.co.kr.dadfarm.CustomToolbar.BlackThemeTextToolbar;
import rnd.plani.co.kr.dadfarm.Data.TermsOrPrivacy;
import rnd.plani.co.kr.dadfarm.Manager.NetworkManager;
import rnd.plani.co.kr.dadfarm.Manager.PropertyManager;
import rnd.plani.co.kr.dadfarm.R;

public class TermsOrPrivacyActivity extends AppCompatActivity {

    public static final String CONTENT_TYPE = "type";
    public static final String TYPE_TERMS = "terms";
    public static final String TYPE_PRIVACY = "privacy";

    WebView webView;
    BlackThemeTextToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);
        toolbar = (BlackThemeTextToolbar) findViewById(R.id.toolbar);
        webView = (WebView) findViewById(R.id.webView);

    }

    Retrofit retrofit;
    NetworkService service;

    @Override
    protected void onStart() {
        super.onStart();
        switch (getIntent().getStringExtra(CONTENT_TYPE)) {
            case TYPE_TERMS:
//                NetworkManager.getInstance().getTerms(this, new NetworkManager.OnResultListener<TermsOrPrivacy>() {
//                    @Override
//                    public void onSuccess(Request request, TermsOrPrivacy result) {
//                        if(result!=null){
//                            webView.loadDataWithBaseURL(null,result.content,"text/html","utf-8",null);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Request request, int code, Throwable cause) {
//
//                    }
//                });
                retrofit = new Retrofit.Builder()
                        .baseUrl("http://restapi-stage.pafarm.kr:9100/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                service = retrofit.create(NetworkService.class);
                Call<TermsOrPrivacy> terms = service.getTerms(PropertyManager.getInstance().getPafarmToken());

                terms.enqueue(new Callback<TermsOrPrivacy>() {
                    @Override
                    public void onResponse(Call<TermsOrPrivacy> call, Response<TermsOrPrivacy> response) {
                        TermsOrPrivacy termsOrPrivacy = response.body();
                        webView.loadDataWithBaseURL(null, termsOrPrivacy.content, "text/html", "utf-8", null);
                    }

                    @Override
                    public void onFailure(Call<TermsOrPrivacy> call, Throwable t) {

                    }
                });

                toolbar.setToolbar("닫기","서비스 이용약관","");
                break;
            case TYPE_PRIVACY:
                NetworkManager.getInstance().getPrivacy(this, new NetworkManager.OnResultListener<TermsOrPrivacy>() {
                    @Override
                    public void onSuccess(Request request, TermsOrPrivacy result) {
                        if (result != null) {
                            webView.loadDataWithBaseURL(null, result.content, "text/html", "utf-8", null);
                        }
                    }

                    @Override
                    public void onFailure(Request request, int code, Throwable cause) {

                    }
                });
                toolbar.setToolbar("닫기","개인정보 보호정책","");
                break;
        }

    }

    class MyTask<T> extends AsyncTask<Call<T>, Integer, T> {
        @Override
        protected T doInBackground(Call<T>... params) {
            Call<T> call = params[0];
            try {
                Response<T> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(T t) {
            super.onPostExecute(t);
            if (t != null) {
                Toast.makeText(TermsOrPrivacyActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
