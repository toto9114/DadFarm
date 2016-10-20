package rnd.plani.co.kr.dadfarm.Certification;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.digits.sdk.android.ContactsCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.models.Contacts;
import com.digits.sdk.android.models.DigitsUser;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rnd.plani.co.kr.dadfarm.CustomToolbar.IntroToolbar;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnLeftMenuClickListener;
import rnd.plani.co.kr.dadfarm.Main.MainActivity;
import rnd.plani.co.kr.dadfarm.Manager.PropertyManager;
import rnd.plani.co.kr.dadfarm.R;
import rnd.plani.co.kr.dadfarm.Setting.TermsOrPrivacyService;

public class FindFriendsActivity extends AppCompatActivity {

    TextView statusView;
    Handler mHandler = new Handler(Looper.getMainLooper());

    Retrofit retrofit;
    TermsOrPrivacyService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);
        IntroToolbar toolbar = (IntroToolbar) findViewById(R.id.toolbar);
        statusView = (TextView) findViewById(R.id.text_status);
        toolbar.setToolbar("닫기", "친구찾기");
        toolbar.setOnLeftMenuClickListener(new OnLeftMenuClickListener() {
            @Override
            public void OnLeftMenuClick() {
                finish();
            }
        });
        Button btn = (Button) findViewById(R.id.btn_find_friends);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusView.setText("동기화중..");

                Digits.findFriends(new ContactsCallback<Contacts>() {
                    @Override
                    public void success(Result<Contacts> result) {
                        final List<DigitsUser> users = result.data.users;
//                        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//                        httpClient.addInterceptor(new Interceptor() {
//                            @Override
//                            public okhttp3.Response intercept(Chain chain) throws IOException {
//                                Request original = chain.request();
//
//                                Request request = original.newBuilder()
//                                        .addHeader("Content-Type", "application/json")
//                                        .addHeader("Authorization", "bearer " + PropertyManager.getInstance().getPafarmToken())
//                                        .method(original.method(), original.body())
//                                        .build();
//
//                                return chain.proceed(request);
//                            }
//                        });
//
//                        OkHttpClient client =httpClient.build();

                        retrofit = new Retrofit.Builder()
                                .baseUrl("http://restapi-stage.pafarm.kr:9100/api/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        service = retrofit.create(TermsOrPrivacyService.class);
                        ArrayList<String> friends = new ArrayList<String>();
                        JSONObject jsonObject = new JSONObject();
                        JSONArray jsonArray = new JSONArray();
                        FriendsData data = new FriendsData();
                        List<String> list  = new ArrayList<String>();;
                        for (int i = 0; i < users.size(); i++) {
//                            friends.add(users.get(i).idStr);
//                            jsonArray.put(users.get(i).idStr);
                            list.add(users.get(i).idStr);
                        }
                        data.friends = list;
                        try {
                            jsonObject.put("friends", jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Call<ResponseBody> isSuccess = service.uploadFriends("Bearer " + PropertyManager.getInstance().getPafarmToken(), data);

                        isSuccess.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                int code = response.code();
                                Log.e("FindFriends", "" + code);
                                if(code == 200){
                                    statusView.setText(getContactCount() + "개의 연락처에서\n" + users.size()
                                            + "명의 아빠농장 친구를 찾았습니다.");
                                    SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                    PropertyManager.getInstance().setSyncDate(sdf.format(new Date()));
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent i = new Intent(FindFriendsActivity.this, MainActivity.class);
                                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(i);
                                            finish();
                                        }
                                    }, 1000);
                                }else{
                                    Log.e("FindFriends","fail");
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });

//                        NetworkManager.getInstance().uploadFriends(FindFriendsActivity.this, users, new NetworkManager.OnResultListener<Boolean>() {
//                            @Override
//                            public void onSuccess(Request request, Boolean result) {
//                                if(result){
//                                    Log.e("FindFriends","success");
//                                    statusView.setText(getContactCount() + "개의 연락처에서\n" + users.size()
//                                            + "명의 아빠농장 친구를 찾았습니다.");
//                                    SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                                    PropertyManager.getInstance().setSyncDate(sdf.format(new Date()));
//                                    mHandler.postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            Intent i = new Intent(FindFriendsActivity.this, MainActivity.class);
//                                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                            startActivity(i);
//                                            finish();
//                                        }
//                                    }, 1000);
//                                }else{
//                                    Log.e("FindFriends","fail");
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Request request, int code, Throwable cause) {
//
//                            }
//                        });
                    }

                    @Override
                    public void failure(TwitterException exception) {

                    }
                });
            }
        });
    }

    String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
    String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " NOT NULL AND " +
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " != ''";
    String[] selectionArgs = null;
    String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

    private int getContactCount() {
        ContentResolver resolver = getContentResolver();

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        Cursor c = resolver.query(uri, projection, selection, selectionArgs, sortOrder);

        int count = 0;
        Log.e("FindFriends", "" + count);
        if (c.moveToFirst()) {
            while (c.moveToNext()) {
                String name = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Log.e("GetContact", "name: " + name + "," + "phone: " + phoneNumber);
                count++;
            }
        }
        return count;
    }
}
