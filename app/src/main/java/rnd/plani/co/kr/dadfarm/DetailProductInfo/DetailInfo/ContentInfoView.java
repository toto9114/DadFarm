package rnd.plani.co.kr.dadfarm.DetailProductInfo.DetailInfo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import rnd.plani.co.kr.dadfarm.Data.ProductData;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-28.
 */

public class ContentInfoView extends RecyclerView.ViewHolder {
    ImageView profileView;
    TextView nameView, roleView, dateView;
    TextView contentView;

    public ContentInfoView(View itemView) {
        super(itemView);
        nameView = (TextView) itemView.findViewById(R.id.text_name);
        contentView = (TextView) itemView.findViewById(R.id.text_content);
    }

    public void setContentView(ProductData data){
        nameView.setText(data.sellerName);
        contentView.setText(data.content);
    }
}
