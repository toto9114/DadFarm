package rnd.plani.co.kr.dadfarm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsOAuthSigning;
import com.digits.sdk.android.DigitsSession;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import okhttp3.Request;
import rnd.plani.co.kr.dadfarm.Certification.FindFriendsActivity;
import rnd.plani.co.kr.dadfarm.Data.AuthData;
import rnd.plani.co.kr.dadfarm.Main.MainActivity;
import rnd.plani.co.kr.dadfarm.Manager.NetworkManager;
import rnd.plani.co.kr.dadfarm.Manager.PropertyManager;

public class SplashActivity extends AppCompatActivity {

    private static final int CONTACT_UPLOAD_REQUEST = 100;
    private static final int PERMISSION_REQUEST_READ_CONTACT = 200;
    LinearLayout loginView;
    DigitsAuthButton digitsButton;
    Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.splash_color));
        }

        loginView = (LinearLayout) findViewById(R.id.linear_login);

        if (!TextUtils.isEmpty(PropertyManager.getInstance().getPafarmToken())) {
            loginView.setVisibility(View.GONE);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            },1000);
        } else {
            loginView.setVisibility(View.VISIBLE);
        }
        loginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(PropertyManager.getInstance().getPafarmToken())) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } else {
                    digitsButton.performClick();
                }
            }
        });
        digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                // TODO: associate the session userID with your user model
                Toast.makeText(getApplicationContext(), "Authentication successful for "
                        + phoneNumber, Toast.LENGTH_LONG).show();
                TwitterAuthConfig authConfig = TwitterCore.getInstance().getAuthConfig();
                TwitterAuthToken authToken = session.getAuthToken();

                if (authToken.isExpired()) {
                    //refresh Token
                    NetworkManager.getInstance().refreshToken(SplashActivity.this, authToken.token, new NetworkManager.OnResultListener<AuthData>() {
                        @Override
                        public void onSuccess(Request request, AuthData result) {
                            if (result != null) {
                                String pafarmToken = result.access_token;
                                PropertyManager.getInstance().setPafarmToken(pafarmToken);
                            }
                        }

                        @Override
                        public void onFailure(Request request, int code, Throwable cause) {

                        }
                    });
                } else {
                    DigitsOAuthSigning oAuthSigning = new DigitsOAuthSigning(authConfig, authToken);
                    Map<String, String> authHeaders = oAuthSigning.getOAuthEchoHeadersForVerifyCredentials();
                    try {
                        URL url = new URL("http://restapi-stage.pafarm.kr/api/verify_credentials.json");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        for (Map.Entry<String, String> entry : authHeaders.entrySet()) {
                            connection.setRequestProperty(entry.getKey(), entry.getValue());
                        }
                        String service_provider = authHeaders.get("X-Auth-Service-Provider");
                        String credentials = authHeaders.get("X-Verify-Credentials-Authorization");
                        String token = authToken.token;
                        String userId = String.valueOf(session.getId());

                        NetworkManager.getInstance().convertToken(SplashActivity.this, service_provider, credentials, token, userId, new NetworkManager.OnResultListener<AuthData>() {
                            @Override
                            public void onSuccess(Request request, AuthData result) {
                                if (result != null) {
                                    String pafarmToken = result.access_token;
                                    PropertyManager.getInstance().setPafarmToken(pafarmToken);
                                    if (!TextUtils.isEmpty(pafarmToken)) {
                                        if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                                            if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, Manifest.permission.READ_CONTACTS)) {

                                            } else {
                                                ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.READ_CONTACTS},
                                                        PERMISSION_REQUEST_READ_CONTACT);
                                            }
                                        } else {
                                            Digits.uploadContacts(CONTACT_UPLOAD_REQUEST);
                                        }
                                    }
                                } else {
                                    Toast.makeText(SplashActivity.this, "fail", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Request request, int code, Throwable cause) {

                            }
                        });
                        connection.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // perform
                }
            }

            @Override
            public void failure(DigitsException exception) {
                Log.d("Digits", "Sign in with Digits failure", exception);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CONTACT_UPLOAD_REQUEST && requestCode != RESULT_CANCELED) {
            startActivity(new Intent(SplashActivity.this, FindFriendsActivity.class));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_READ_CONTACT) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Digits.uploadContacts(CONTACT_UPLOAD_REQUEST);
            } else {

            }
        }
    }
}
