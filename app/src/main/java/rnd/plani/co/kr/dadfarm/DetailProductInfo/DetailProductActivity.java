package rnd.plani.co.kr.dadfarm.DetailProductInfo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import rnd.plani.co.kr.dadfarm.CustomDialog.CustomShareDialog;
import rnd.plani.co.kr.dadfarm.CustomToolbar.BlackThemeShareToolbar;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnLeftMenuClickListener;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnRightMenuClickListener;
import rnd.plani.co.kr.dadfarm.Data.PersonalData;
import rnd.plani.co.kr.dadfarm.Data.ProductData;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.DetailInfo.DetailInfoAdapter;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.DetailInfo.OnOrderBtnClickListener;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.DetailInfo.OnReviewBtnClickListener;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Order.OrderProductActivity;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.OnPhoneCallClickListener;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.OnSmsClickListener;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.RelationInfoActivity;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Review.ReviewProductActivity;
import rnd.plani.co.kr.dadfarm.Manager.PropertyManager;
import rnd.plani.co.kr.dadfarm.OnProfileClickListener;
import rnd.plani.co.kr.dadfarm.R;
import rnd.plani.co.kr.dadfarm.Utils;

public class DetailProductActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_PHONE_CALL = 100;
    private static final int PERMISSION_REQUEST_SEND_SMS = 200;
    public static final String EXTRA_PRODUCT_DATA = "product";
    ProductData product;
    TextView reviewView, orderView;
    FamiliarRecyclerView recyclerView;
    DetailInfoAdapter mAdapter;
    LinearLayoutManager layoutManager;
    LinearLayout btnLayout;
    float alpha = 0.0f;
    float testDy = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        BlackThemeShareToolbar toolbar = (BlackThemeShareToolbar) findViewById(R.id.toolbar);
        toolbar.setToolbar("뒤로", "상세정보", true);

        product = (ProductData) getIntent().getSerializableExtra(EXTRA_PRODUCT_DATA);
        btnLayout = (LinearLayout) findViewById(R.id.linear_btn);
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
                intent.setData(Uri.parse("smsto:" + phoneNum));
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
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
                    startActivity(intent);
                }
            }
        });
        mAdapter.setOnProfileClickListener(new OnProfileClickListener() {
            @Override
            public void OnProfileClick(PersonalData personalData) {
                Intent i = new Intent(DetailProductActivity.this, RelationInfoActivity.class);
                i.putExtra(RelationInfoActivity.EXTRA_PERSONAL_DATA, personalData);
                startActivity(i);
            }
        });
        reviewView = (TextView) findViewById(R.id.btn_review);
        orderView = (TextView) findViewById(R.id.btn_order);


        reviewView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailProductActivity.this, ReviewProductActivity.class);
                i.putExtra(ReviewProductActivity.EXTRA_PRODUCT_DATA, product);
                startActivity(i);
            }
        });

        orderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.manager.id == PropertyManager.getInstance().getUserId()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailProductActivity.this);
                    AlertDialog dialog = builder.setTitle(R.string.alert_order_my_product_title)
                            .setMessage(R.string.alert_order_my_product_message)
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setCancelable(false).create();
                    dialog.show();
                    dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(DetailProductActivity.this, R.color.blue_gray));
                    dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(DetailProductActivity.this, R.color.red_gray));
                } else {
                    Intent i = new Intent(DetailProductActivity.this, OrderProductActivity.class);
                    i.putExtra(OrderProductActivity.EXTRA_PRODUCT_DATA, product);
                    startActivity(i);
                }
            }
        });

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
                CustomShareDialog dialog = new CustomShareDialog();
                Bundle args = new Bundle();
//                args.putString();
                dialog.show(getSupportFragmentManager(), "dialog");

            }
        });

//        btnLayout.setAlpha(0.2f);
        final float mActivityHeight = Utils.getScreenHeight(this) - Utils.getStatusBarHeight();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (layoutManager.findLastVisibleItemPosition() >= 3) {

                    alpha += ((float) dy / (float) layoutManager.findViewByPosition(3).getHeight()) * 1.0f;

                    testDy += dy;
                    Log.e("DetailProduct", "dy, height" + testDy + "," + layoutManager.findViewByPosition(3).getHeight());
                    if (dy > 0) {
                        btnLayout.setAlpha(alpha);
                        btnLayout.setVisibility(View.VISIBLE);
                        if (layoutManager.findLastCompletelyVisibleItemPosition() >= 3) {
                            alpha = 1.0f;
                        }
                    } else {
                        btnLayout.setAlpha(alpha);
                    }
                } else {
                    if (layoutManager.findLastVisibleItemPosition() < 3) {
                        alpha = 0.0f;
                        btnLayout.setVisibility(View.GONE);
                    }
                }
                Log.e("DetailProduct", "" + alpha);
            }
        });
        initData();
    }

    private void initData() {
//        NetworkManager.getInstance().getMyUserInfo(this, new NetworkManager.OnResultListener<PersonalData>() {
//            @Override
//            public void onSuccess(Request request, PersonalData result) {
//                if(result != null){
//                    mAdapter.setMyInfo(result);
//                }
//            }
//
//            @Override
//            public void onFailure(Request request, int code, Throwable cause) {
//
//            }
//        });
        mAdapter.setProduct(product);
        reviewView.setText(String.format(getString(R.string.review), product.review_count));
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
