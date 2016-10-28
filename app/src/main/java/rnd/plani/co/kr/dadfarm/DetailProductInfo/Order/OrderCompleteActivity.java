package rnd.plani.co.kr.dadfarm.DetailProductInfo.Order;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import okhttp3.Request;
import rnd.plani.co.kr.dadfarm.CustomToolbar.BlackThemeShareToolbar;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnLeftMenuClickListener;
import rnd.plani.co.kr.dadfarm.Data.OrderResultData;
import rnd.plani.co.kr.dadfarm.Data.PersonalData;
import rnd.plani.co.kr.dadfarm.Data.RelationshipsData;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.OnPhoneCallClickListener;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.OnSmsClickListener;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.RelationAdapter;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.RelationInfoActivity;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Review.WriteReviewActivity;
import rnd.plani.co.kr.dadfarm.Manager.NetworkManager;
import rnd.plani.co.kr.dadfarm.Manager.PropertyManager;
import rnd.plani.co.kr.dadfarm.OnProfileClickListener;
import rnd.plani.co.kr.dadfarm.R;
import rnd.plani.co.kr.dadfarm.Utils;

public class OrderCompleteActivity extends AppCompatActivity {

    public static final String EXTRA_ORDER_PRODUCT_DATA = "order_product";

    ListView listView;
    RelationAdapter mAdapter;
    ViewSwitcher btnSwitcher;
    TextView titleView, productNameView, shopperNameView, shopperContactView, addressView, recieverNameView, recieverContactView,
            orderNumberView, orderDateView, quantityView, priceView, accountInfoView;
    private OrderResultData orderData;

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

        orderData = (OrderResultData) getIntent().getSerializableExtra(EXTRA_ORDER_PRODUCT_DATA);
        btnSwitcher = (ViewSwitcher) findViewById(R.id.switcher);
        titleView = (TextView) findViewById(R.id.text_product_title);
        productNameView = (TextView) findViewById(R.id.text_product_name);
        shopperNameView = (TextView) findViewById(R.id.text_shopper_name);
        shopperContactView = (TextView) findViewById(R.id.text_shopper_contact);
        addressView = (TextView) findViewById(R.id.text_address);
        recieverNameView = (TextView) findViewById(R.id.text_shipping_reciever);
        recieverContactView = (TextView) findViewById(R.id.text_shipping_contact);
        orderNumberView = (TextView) findViewById(R.id.text_order_number);
        orderDateView = (TextView) findViewById(R.id.text_order_date);
        quantityView = (TextView) findViewById(R.id.text_quantity);
        priceView = (TextView) findViewById(R.id.text_price);
        accountInfoView = (TextView) findViewById(R.id.text_account_info);

        listView = (ListView) findViewById(R.id.listview);
        mAdapter = new RelationAdapter();
        listView.setAdapter(mAdapter);
        Button btn = (Button) findViewById(R.id.btn_write_review);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderCompleteActivity.this, WriteReviewActivity.class);
                i.putExtra(WriteReviewActivity.EXTRA_ORDER_PRODUCT, orderData);
                startActivity(i);
            }
        });
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
                if (ActivityCompat.checkSelfPermission(OrderCompleteActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(OrderCompleteActivity.this, Manifest.permission.CALL_PHONE)) {

                    } else {
                        ActivityCompat.requestPermissions(OrderCompleteActivity.this, new String[]{Manifest.permission.CALL_PHONE},
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
                Intent i = new Intent(OrderCompleteActivity.this, RelationInfoActivity.class);
                i.putExtra(RelationInfoActivity.EXTRA_PERSONAL_DATA,personalData);
                startActivity(i);
            }
        });

        if (orderData != null) {
            initData();
        }
    }

    private void initData() {
        mAdapter.clear();
        NetworkManager.getInstance().getMyUserInfo(this, new NetworkManager.OnResultListener<PersonalData>() {
            @Override
            public void onSuccess(Request request, PersonalData me) {
                if (me != null) {
                    if (orderData.friend != null) {
                        String friendMeRelation = orderData.relationships.get(orderData.friend.id + "-" + me.id);
                        String managerFriendRelation = orderData.relationships.get(orderData.manager.id + "-" + orderData.friend.id);
                        String friendManagerRelation = orderData.relationships.get(orderData.friend.id + "-" + orderData.manager.id);
                        String sellerManagerRelation = orderData.relationships.get(orderData.seller.id + "-" + orderData.manager.id);
                        String managerSellerRelation = orderData.relationships.get(orderData.manager.id + "-" + orderData.seller.id);
                        RelationshipsData relationshipsData = new RelationshipsData();
                        mAdapter.add(orderData.seller);
                        relationshipsData.relationName = sellerManagerRelation;
                        mAdapter.add(relationshipsData);

                        mAdapter.add(orderData.manager);
                        relationshipsData = new RelationshipsData();
                        relationshipsData.relationName = managerFriendRelation;
                        mAdapter.add(relationshipsData);

                        mAdapter.add(orderData.friend);
                        relationshipsData = new RelationshipsData();
                        relationshipsData.relationName = friendMeRelation;
                        mAdapter.add(relationshipsData);

                        mAdapter.add(me);

                        titleView.setText(orderData.friend.last_name + orderData.friend.first_name + "의 " + friendManagerRelation + "의 " + managerSellerRelation);
                        //four
                    } else {
                        if (orderData.manager.id == me.id) {
                            if (orderData.seller.id == me.id) {
                                mAdapter.add(me);
                                //one
                            } else {
                                RelationshipsData relationshipsData = new RelationshipsData();
                                String sellerMeRelation = orderData.relationships.get(orderData.seller.id + "-" + me.id);
                                String meSellerRelation = orderData.relationships.get(me.id + "-" + orderData.seller.id);
                                mAdapter.add(orderData.seller);
                                relationshipsData.relationName = sellerMeRelation;
                                mAdapter.add(relationshipsData);

                                mAdapter.add(me);

                                titleView.setText(me.last_name + me.first_name + "의 " + meSellerRelation);
                                //two
                            }
                        } else {
                            String managerMeRelation = orderData.relationships.get(orderData.manager.id + "-" + me.id);
                            String sellerManagerRelation = orderData.relationships.get(orderData.seller.id + "-" + orderData.manager.id);
                            String managerSellerRelation = orderData.relationships.get(orderData.manager.id + "-" + orderData.seller.id);
                            RelationshipsData relationshipsData = new RelationshipsData();
                            mAdapter.add(orderData.seller);
                            relationshipsData.relationName = sellerManagerRelation;
                            mAdapter.add(relationshipsData);

                            mAdapter.add(orderData.manager);
                            relationshipsData = new RelationshipsData();
                            relationshipsData.relationName = managerMeRelation;
                            mAdapter.add(relationshipsData);

                            mAdapter.add(me);

                            titleView.setText(orderData.manager.last_name + orderData.manager.first_name + "의 " + managerSellerRelation);
                            //three
                        }
                    }
                    Utils.setListViewHeightBasedOnChildren(listView);
                }
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
        listView.setFocusable(false);
        if (orderData.reviewed) {
            btnSwitcher.showNext();
        }
        String relation = PropertyManager.getInstance().getUserId() + "-" + orderData.seller.id;
        if (!TextUtils.isEmpty(orderData.relationships.get(relation))) {
            titleView.setText(orderData.relationships.get(relation) + "농장");
        }
        productNameView.setText(orderData.product_name);
        shopperNameView.setText(orderData.shopper.last_name + orderData.shopper.first_name);
        shopperContactView.setText(orderData.shopper.profile.phone_number);
        addressView.setText(orderData.shipping_address);
        recieverNameView.setText(orderData.shipping_recipient_name);
        recieverContactView.setText(orderData.shipping_phone_number);
        orderNumberView.setText("" + orderData.order_number);
        orderDateView.setText(orderData.created_time);
        quantityView.setText("" + orderData.quantity);
        priceView.setText("" + orderData.product_price * orderData.quantity);
        accountInfoView.setText(orderData.bank_name + " " + orderData.bank_account + " (" + orderData.bank_account_holder + ")");
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
