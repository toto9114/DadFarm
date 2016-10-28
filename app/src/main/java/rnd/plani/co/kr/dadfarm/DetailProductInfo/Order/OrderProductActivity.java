package rnd.plani.co.kr.dadfarm.DetailProductInfo.Order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import okhttp3.Request;
import rnd.plani.co.kr.dadfarm.CustomToolbar.BlackThemeShareToolbar;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnLeftMenuClickListener;
import rnd.plani.co.kr.dadfarm.Data.OrderResultData;
import rnd.plani.co.kr.dadfarm.Data.ProductData;
import rnd.plani.co.kr.dadfarm.Manager.NetworkManager;
import rnd.plani.co.kr.dadfarm.Manager.PropertyManager;
import rnd.plani.co.kr.dadfarm.R;

public class OrderProductActivity extends AppCompatActivity {

    public static final String EXTRA_PRODUCT_DATA = "product_data";

    EditText unitView, addressView, recieverView, contactView;
    Button orderBtn;

    TextView productNameView, priceView, accountView;
    ProductData productData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_product);
        BlackThemeShareToolbar toolbar = (BlackThemeShareToolbar) findViewById(R.id.toolbar);
        toolbar.setToolbar("뒤로", "주문하기", false);
        toolbar.setOnLeftMenuClickListener(new OnLeftMenuClickListener() {
            @Override
            public void OnLeftMenuClick() {
                finish();
            }
        });
        productData = (ProductData) getIntent().getSerializableExtra(EXTRA_PRODUCT_DATA);
        unitView = (EditText) findViewById(R.id.edit_unit);
        addressView = (EditText) findViewById(R.id.edit_address);
        recieverView = (EditText) findViewById(R.id.edit_reciever);
        contactView = (EditText) findViewById(R.id.edit_contact);

        productNameView = (TextView) findViewById(R.id.text_product_name);
        priceView = (TextView) findViewById(R.id.text_price);
        accountView = (TextView) findViewById(R.id.text_account_info);
        unitView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isEmptyEditText()) {
                    orderBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addressView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isEmptyEditText()) {
                    orderBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        recieverView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isEmptyEditText()) {
                    orderBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        contactView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isEmptyEditText()) {
                    orderBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        orderBtn = (Button) findViewById(R.id.btn_order);
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productData != null) {
                    int quantity = Integer.parseInt(unitView.getText().toString());
                    String shipping_address = addressView.getText().toString();
                    String phone_number = contactView.getText().toString();
                    String name = recieverView.getText().toString();

                    NetworkManager.getInstance().orderProduct(OrderProductActivity.this, productData, quantity, shipping_address, name, phone_number, new NetworkManager.OnResultListener<OrderResultData>() {
                        @Override
                        public void onSuccess(Request request, OrderResultData result) {
                            if (result != null) {
                                Intent i = new Intent(OrderProductActivity.this, OrderCompleteActivity.class);
                                i.putExtra(OrderCompleteActivity.EXTRA_ORDER_PRODUCT_DATA, result);
                                startActivity(i);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Request request, int code, Throwable cause) {

                        }
                    });

                }
            }
        });

        initData();
    }

    private void initData() {
        productNameView.setText(productData.name);
        priceView.setText(productData.price);
        recieverView.setText(PropertyManager.getInstance().getLastName() + PropertyManager.getInstance().getFirstName());
        contactView.setText(PropertyManager.getInstance().getPhoneNum());
        accountView.setText(productData.bank_name + " " + productData.bank_account + " (" + productData.bank_account_holder +")");
    }

    private boolean isEmptyEditText() {
        if (TextUtils.isEmpty(unitView.getText().toString()) || TextUtils.isEmpty(addressView.getText().toString())
                || TextUtils.isEmpty(recieverView.getText().toString()) || TextUtils.isEmpty(contactView.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }
}
