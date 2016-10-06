package rnd.plani.co.kr.dadfarm.DetailProductInfo.DetailInfo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import rnd.plani.co.kr.dadfarm.Data.ProductData;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-28.
 */

public class TitleInfoView extends RecyclerView.ViewHolder {
    TextView titleView, productView, priceView, addressView;
    public TitleInfoView(View itemView) {
        super(itemView);
        titleView = (TextView) itemView.findViewById(R.id.text_title);
        productView = (TextView) itemView.findViewById(R.id.text_product_name);
        priceView = (TextView) itemView.findViewById(R.id.text_price);
        addressView = (TextView) itemView.findViewById(R.id.text_address);
    }

    public void setTitleView(ProductData data){
        titleView.setText(data.title);
        productView.setText(data.productName);
        priceView.setText(data.price);
        addressView.setText(data.address);
    }
}
