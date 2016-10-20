package rnd.plani.co.kr.dadfarm.DetailProductInfo.DetailInfo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
        dateView = (TextView) itemView.findViewById(R.id.text_date);
        contentView = (TextView) itemView.findViewById(R.id.text_content);

    }

    public void setContentView(ProductData data) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        nameView.setText(data.manager.last_name + data.manager.first_name);
        try {
            dateView.setText(sdf.format(sdf.parse(data.updated_time)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        contentView.setText(data.description);
    }
}
