package rnd.plani.co.kr.dadfarm.OrderList;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        titleView.setText(data.product_title);
        productUnitView.setText(data.product_name +" x "+ data.quantity);
        try {
            dateView.setText(sdf.format(sdf.parse(data.updated_time)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(!data.images.isEmpty()) {
            Glide.with(itemView.getContext()).load(data.images.get(0)).into(pictureView);
        }
    }
    public void setProductItem(ProductData data){
        NumberFormat nf = NumberFormat.getInstance();
        titleView.setText(data.title);
        productUnitView.setText(data.name);
        dateView.setText(nf.format(Integer.parseInt(data.price))+"원");
        if(!data.images.isEmpty()) {
            Glide.with(itemView.getContext()).load(data.images.get(0)).into(pictureView);
        }
    }
}
