package rnd.plani.co.kr.dadfarm.DetailProductInfo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.widget.Button;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import rnd.plani.co.kr.dadfarm.CustomToolbar.BlackThemeShareToolbar;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnLeftMenuClickListener;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnRightMenuClickListener;
import rnd.plani.co.kr.dadfarm.Data.ProductData;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.DetailInfo.DetailInfoAdapter;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.DetailInfo.OnOrderBtnClickListener;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.DetailInfo.OnReviewBtnClickListener;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Order.OrderProductActivity;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.OnPhoneCallClickListener;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.OnSmsClickListener;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Review.ReviewProductActivity;
import rnd.plani.co.kr.dadfarm.R;
import rnd.plani.co.kr.dadfarm.ShareDialog;

public class DetailProductActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_PHONE_CALL = 100;
    private static final int PERMISSION_REQUEST_SEND_SMS = 200;
    public static final String EXTRA_PRODUCT_DATA = "product";
    ProductData product;
    Button reviewView, orderView;
    FamiliarRecyclerView recyclerView;
    DetailInfoAdapter mAdapter;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        BlackThemeShareToolbar toolbar = (BlackThemeShareToolbar) findViewById(R.id.toolbar);
        toolbar.setToolbar("뒤로", "상세정보", true);
        product = (ProductData) getIntent().getSerializableExtra(EXTRA_PRODUCT_DATA);
        recyclerView = (FamiliarRecyclerView) findViewById(R.id.recycler);
        mAdapter = new DetailInfoAdapter();
        layoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(layoutManager);

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
                if (ActivityCompat.checkSelfPermission(DetailProductActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(DetailProductActivity.this, Manifest.permission.CALL_PHONE)) {

                    } else {
                        ActivityCompat.requestPermissions(DetailProductActivity.this, new String[]{Manifest.permission.CALL_PHONE},
                                PERMISSION_REQUEST_PHONE_CALL);
                    }
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1234-1234"));
                    startActivity(intent);
                }
            }
        });
//        reviewView = (Button) findViewById(R.id.btn_review);
//        orderView = (Button) findViewById(R.id.btn_order);
//
//
//        reviewView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(DetailProductActivity.this, ReviewProductActivity.class));
//            }
//        });
//
//        orderView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(DetailProductActivity.this, OrderProductActivity.class));
//            }
//        });

        mAdapter.setOnReviewBtnClickListener(new OnReviewBtnClickListener() {
            @Override
            public void OnReviewBtnClick() {
                startActivity(new Intent(DetailProductActivity.this, ReviewProductActivity.class));
            }
        });

        mAdapter.setOnOrderBtnClickListener(new OnOrderBtnClickListener() {
            @Override
            public void OnOrderBtnClick() {
                startActivity(new Intent(DetailProductActivity.this, OrderProductActivity.class));
            }
        });
        toolbar.setOnLeftMenuClickListener(new OnLeftMenuClickListener() {
            @Override
            public void OnLeftMenuClick() {
                finish();
            }
        });

        toolbar.setOnRightMenuClickListener(new OnRightMenuClickListener() {
            @Override
            public void OnRightMenuClick() {
                //공유하기
                ShareDialog dialog = new ShareDialog();
                dialog.show(getSupportFragmentManager(), "dialog");
            }
        });
        initData();
    }

    private void initData() {
        mAdapter.setProduct(product);
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
