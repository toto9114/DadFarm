package rnd.plani.co.kr.dadfarm.Certification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import okhttp3.Request;
import rnd.plani.co.kr.dadfarm.CustomToolbar.IntroToolbar;
import rnd.plani.co.kr.dadfarm.Data.PersonalData;
import rnd.plani.co.kr.dadfarm.Manager.NetworkManager;
import rnd.plani.co.kr.dadfarm.Manager.PropertyManager;
import rnd.plani.co.kr.dadfarm.R;

public class InsertNameActivity extends AppCompatActivity {

    EditText firstNameView, lastNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_name);
        IntroToolbar toolbar = (IntroToolbar) findViewById(R.id.toolbar);
        toolbar.setToolbar("", getString(R.string.insert_name_title));

        firstNameView = (EditText) findViewById(R.id.edit_first_name);
        lastNameView = (EditText) findViewById(R.id.edit_last_name);


        Button btn = (Button) findViewById(R.id.btn_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameView.getText().toString();
                String lastName = lastNameView.getText().toString();
                if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)) {
                    if (uid != -1) {
                        NetworkManager.getInstance().updateUserInfo(InsertNameActivity.this, uid, firstName, lastName, new NetworkManager.OnResultListener<PersonalData>() {
                            @Override
                            public void onSuccess(Request request, PersonalData result) {
                                if(result != null){
                                    startActivity(new Intent(InsertNameActivity.this, FindFriendsActivity.class));
                                }
                            }

                            @Override
                            public void onFailure(Request request, int code, Throwable cause) {

                            }
                        });
                    }
                }
            }
        });
    }

    long uid = -1;

    @Override
    protected void onStart() {
        super.onStart();
        NetworkManager.getInstance().getUserInfo(this, new NetworkManager.OnResultListener<PersonalData>() {
            @Override
            public void onSuccess(Request request, PersonalData result) {
                if (result != null) {
                    uid = result.id;
                    PropertyManager.getInstance().setUserId(uid);
                }
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
    }
}
