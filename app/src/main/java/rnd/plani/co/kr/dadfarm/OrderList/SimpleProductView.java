package rnd.plani.co.kr.dadfarm.OrderList;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;

import rnd.plani.co.kr.dadfarm.Data.OrderResultData;
import rnd.plani.co.kr.dadfarm.Data.ProductData;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-23.
 */
public class SimpleProductView extends RecyclerView.ViewHolder {

    TextView titleView, productUnitView, dateView;
    ImageView pictureView;
    public SimpleProductView(View itemView) {
        super(itemView);
        titleView = (TextView) itemView.findViewById(R.id.text_title);
        productUnitView = (TextView) itemView.findViewById(R.id.text_product_unit);
        dateView = (TextView) itemView.findViewById(R.id.text_date);
        pictureView = (ImageView) itemView.findViewById(R.id.image_product);

        pictureView.setColorFilter(ContextCompat.getColor(itemView.getContext(),R.color.image_opacity));
    }

    public void setOrderItem(OrderResultData data){
        titleView.setText(data.productName);
        productUnitView.setText(data.orderCount);
        dateView.setText(data.orderDate);
    }
    public void setProductItem(ProductData data){
        NumberFormat nf = NumberFormat.getInstance();
        titleView.setText(data.title);
        productUnitView.setText(data.productName);
        dateView.setText(data.price);
    }
}
