package rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

import okhttp3.Request;
import rnd.plani.co.kr.dadfarm.CustomToolbar.BlackThemeTextToolbar;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnLeftMenuClickListener;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnRightMenuClickListener;
import rnd.plani.co.kr.dadfarm.Data.PersonalData;
import rnd.plani.co.kr.dadfarm.Data.ProfileData;
import rnd.plani.co.kr.dadfarm.Manager.NetworkManager;
import rnd.plani.co.kr.dadfarm.Manager.PropertyManager;
import rnd.plani.co.kr.dadfarm.PersonRelationView;
import rnd.plani.co.kr.dadfarm.R;

public class RelationInfoActivity extends AppCompatActivity {

    public static final String EXTRA_PERSONAL_DATA = "personal_data";

    EditText editRelation, editMyRelation;
    PersonalData personalData;
    TextView relationView, myRelationView;
    PersonRelationView personView;
    LinearLayout editRelationLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation_info);
        BlackThemeTextToolbar toolbar = (BlackThemeTextToolbar) findViewById(R.id.toolbar);

        personalData = (PersonalData) getIntent().getSerializableExtra(EXTRA_PERSONAL_DATA);

        toolbar.setToolbar("뒤로", personalData.last_name + personalData.first_name, "저장");
        toolbar.setOnLeftMenuClickListener(new OnLeftMenuClickListener() {
            @Override
            public void OnLeftMenuClick() {
                finish();
            }
        });
        toolbar.setOnRightMenuClickListener(new OnRightMenuClickListener() {
            @Override
            public void OnRightMenuClick() {
                String has_x = editRelation.getText().toString();
                String is_x_of = editMyRelation.getText().toString();
                NetworkManager.getInstance().updateRelation(RelationInfoActivity.this, subject_id, object_id, has_x, is_x_of, new NetworkManager.OnResultListener<HashMap<String, String>>() {
                    @Override
                    public void onSuccess(Request request, HashMap<String, String> result) {
                        if (result != null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RelationInfoActivity.this);
                            builder.setTitle("관계수정")
                                    .setMessage(personalData.last_name + personalData.first_name + " 님과의 관계 정보가 저장되었습니다.")
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Request request, int code, Throwable cause) {

                    }
                });
            }
        });

        editRelation = (EditText) findViewById(R.id.edit_relation);
        editMyRelation = (EditText) findViewById(R.id.edit_my_relation);
        relationView = (TextView) findViewById(R.id.text_of_relation);
        myRelationView = (TextView) findViewById(R.id.text_my_relation);
        personView = (PersonRelationView) findViewById(R.id.relation_view);
        editRelationLayout = (LinearLayout) findViewById(R.id.linear_edit_relation);

        initData();

    }

    long subject_id;
    long object_id;
    PersonalData me;

    private void initData() {
        relationView.setText(personalData.last_name + personalData.first_name + "은(는) 나의");
        myRelationView.setText("나는 " + personalData.last_name + personalData.first_name + "의");

        me = new PersonalData();
        me.first_name = PropertyManager.getInstance().getFirstName();
        me.last_name = PropertyManager.getInstance().getLastName();
        me.id = PropertyManager.getInstance().getUserId();
        me.profile = new ProfileData();
        me.profile.id = PropertyManager.getInstance().getProfileId();
        me.profile.image_url = PropertyManager.getInstance().getProfileUrl();
        me.profile.phone_number = PropertyManager.getInstance().getPhoneNum();


        subject_id = me.profile.id;
        object_id = personalData.profile.id;
        if (subject_id == object_id) {
            personView.setRelationView(me, "나");
            editRelationLayout.setVisibility(View.GONE);
        } else {
            editRelationLayout.setVisibility(View.VISIBLE);
            NetworkManager.getInstance().getRelationship(RelationInfoActivity.this, me.profile.id, personalData.profile.id, new NetworkManager.OnResultListener<HashMap<String, String>>() {
                @Override
                public void onSuccess(Request request, HashMap<String, String> result) {
                    if (result != null) {
                        String relationId = me.profile.id + "-" + personalData.profile.id;
                        String myRelationId = personalData.profile.id + "-" + me.profile.id;
                        editRelation.setText(result.get(relationId));
                        editMyRelation.setText(result.get(myRelationId));
                        personView.setRelationView(personalData, result.get(relationId));
                    } else {
                        personView.setRelationView(personalData, "아빠농장 사용자");
                    }
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {

                }
            });
        }

    }
}
