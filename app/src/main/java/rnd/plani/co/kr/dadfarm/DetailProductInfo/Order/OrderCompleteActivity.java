package rnd.plani.co.kr.dadfarm.DetailProductInfo.Order;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import rnd.plani.co.kr.dadfarm.CustomToolbar.BlackThemeShareToolbar;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnLeftMenuClickListener;
import rnd.plani.co.kr.dadfarm.Data.RelationData;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.OnPhoneCallClickListener;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.OnSmsClickListener;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.RelationAdapter;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.RelationInfoActivity;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Review.WriteReviewActivity;
import rnd.plani.co.kr.dadfarm.OnProfileClickListener;
import rnd.plani.co.kr.dadfarm.R;
import rnd.plani.co.kr.dadfarm.Utils;

public class OrderCompleteActivity extends AppCompatActivity {

    ListView listView;
    RelationAdapter mAdapter;
    private static final int PERMISSION_REQUEST_PHONE_CALL = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_complete);
        BlackThemeShareToolbar toolbar = (BlackThemeShareToolbar) findViewById(R.id.toolbar);
        toolbar.setToolbar("닫기", "주문완료", false);
        toolbar.setOnLeftMenuClickListener(new OnLeftMenuClickListener() {
            @Override
            public void OnLeftMenuClick() {
                finish();
            }
        });
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));
        }
        listView = (ListView) findViewById(R.id.listview);
        mAdapter = new RelationAdapter();
        listView.setAdapter(mAdapter);
        Button btn = (Button) findViewById(R.id.btn_write_review);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderCompleteActivity.this, WriteReviewActivity.class));
            }
        });
        mAdapter.setOnSmsClickListener(new OnSmsClickListener() {
            @Override
            public void OnSmsClick(String phoneNum) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.putExtra("sms_body", "This is a test message");
                intent.setData(Uri.parse("smsto:01012345678; 01098765432"));
                startActivity(intent);
            }
        });

        mAdapter.setOnPhoneCallClickListener(new OnPhoneCallClickListener() {
            @Override
            public void OnPhoneCallClick(String phoneNum) {
                if (ActivityCompat.checkSelfPermission(OrderCompleteActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(OrderCompleteActivity.this, Manifest.permission.CALL_PHONE)) {

                    } else {
                        ActivityCompat.requestPermissions(OrderCompleteActivity.this, new String[]{Manifest.permission.CALL_PHONE},
                                PERMISSION_REQUEST_PHONE_CALL);
                    }
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1234-1234"));
                    startActivity(intent);
                }
            }
        });
        mAdapter.setOnProfileClickListener(new OnProfileClickListener() {
            @Override
            public void OnProfileClick() {
                Intent i = new Intent(OrderCompleteActivity.this, RelationInfoActivity.class);
                startActivity(i);
            }
        });

        initData();
    }

    private void initData() {
        for(int i = 0 ; i < 3 ; i++){
            RelationData data= new RelationData();
            data.name = "테스터" + i;
            data.role = "역할" + i;
            data.phone = "010-2672-4411";
            mAdapter.add(data);
            if(i<2){
                RelationData relation = new RelationData();
                relation.relation = "관계"+i;
                mAdapter.add(relation);
            }
        }
        Utils.setListViewHeightBasedOnChildren(listView);
        listView.setFocusable(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_PHONE_CALL) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1234-1234"));
                // TODO: Consider calling
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
        }
//        if(requestCode == PERMISSION_REQUEST_SEND_SMS){
//            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//                sendIntent.putExtra("sms_body", product.title); // 보낼 문자
//                sendIntent.putExtra("address", "010-2672-4411"); // 받는사람 번호
//                sendIntent.setType("vnd.android-dir/mms-sms");
//                startActivity(sendIntent);
//            }
//        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
