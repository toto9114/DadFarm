package rnd.plani.co.kr.dadfarm.OrderList;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-23.
 */
public class OrderItemView extends FrameLayout {

    TextView titleView, productNameView, dateView;
    public OrderItemView(Context context) {
        super(context);
        inflate(getContext(), R.layout.view_order_item,this);
        titleView = (TextView) findViewById(R.id.text_title);
        productNameView = (TextView) findViewById(R.id.text_product);
        dateView = (TextView) findViewById(R.id.text_date);
    }
}
