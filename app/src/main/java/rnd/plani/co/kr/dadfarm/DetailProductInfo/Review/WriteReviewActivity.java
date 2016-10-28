package rnd.plani.co.kr.dadfarm.DetailProductInfo.Review;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;

import okhttp3.Request;
import rnd.plani.co.kr.dadfarm.CustomToolbar.BlackThemeTextToolbar;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnLeftMenuClickListener;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnRightMenuClickListener;
import rnd.plani.co.kr.dadfarm.Data.OrderResultData;
import rnd.plani.co.kr.dadfarm.Data.ReviewData;
import rnd.plani.co.kr.dadfarm.Manager.NetworkManager;
import rnd.plani.co.kr.dadfarm.R;

public class WriteReviewActivity extends AppCompatActivity {

    public static final String EXTRA_ORDER_PRODUCT = "order_product";
    private OrderResultData orderData;
    EditText contentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);
        BlackThemeTextToolbar toolbar = (BlackThemeTextToolbar) findViewById(R.id.toolbar);
        toolbar.setToolbar("취소","구매후기","완료");

        orderData = (OrderResultData)getIntent().getSerializableExtra(EXTRA_ORDER_PRODUCT);
        contentView = (EditText) findViewById(R.id.edit_content);
        toolbar.setOnLeftMenuClickListener(new OnLeftMenuClickListener() {
            @Override
            public void OnLeftMenuClick() {
                finish();
            }
        });

        toolbar.setOnRightMenuClickListener(new OnRightMenuClickListener() {
            @Override
            public void OnRightMenuClick() {
                String content = contentView.getText().toString();
                if(!TextUtils.isEmpty(content)) {
                    NetworkManager.getInstance().uploadReview(WriteReviewActivity.this, orderData, content, new NetworkManager.OnResultListener<ReviewData>() {
                        @Override
                        public void onSuccess(Request request, ReviewData result) {
                            if(result != null) {
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
    }
}
