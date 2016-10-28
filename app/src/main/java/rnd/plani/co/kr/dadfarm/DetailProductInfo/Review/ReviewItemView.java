package rnd.plani.co.kr.dadfarm.DetailProductInfo.Review;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import rnd.plani.co.kr.dadfarm.Data.PersonalData;
import rnd.plani.co.kr.dadfarm.Data.ReviewData;
import rnd.plani.co.kr.dadfarm.OnProfileClickListener;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-27.
 */

public class ReviewItemView extends RecyclerView.ViewHolder {
    private OnProfileClickListener profileClickListener;

    public void setOnProfileClickListener(OnProfileClickListener listener) {
        profileClickListener = listener;
    }

    ImageView profileView;
    TextView nameView, relationView, dateView, contentView, productView;

    public ReviewItemView(View itemView) {
        super(itemView);
        profileView = (ImageView) itemView.findViewById(R.id.image_profile);
        nameView = (TextView) itemView.findViewById(R.id.text_name);
        relationView = (TextView) itemView.findViewById(R.id.text_relation);
        dateView = (TextView) itemView.findViewById(R.id.text_date);
        contentView = (TextView) itemView.findViewById(R.id.text_content);
        productView = (TextView) itemView.findViewById(R.id.text_product);
        nameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileClickListener != null) {
                    profileClickListener.OnProfileClick(shopper);
                }
            }
        });
        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileClickListener != null) {
                    profileClickListener.OnProfileClick(shopper);
                }
            }
        });
    }

    PersonalData shopper;
    public void setReview(ReviewData data) {
        shopper = data.order.shopper;
        SimpleDateFormat yearSdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hourSdf = new SimpleDateFormat("h:mm aa");
        nameView.setText(data.order.shopper.last_name + data.order.shopper.first_name);
//        relationView.setText(data.relation);
        Calendar c = Calendar.getInstance();
        Calendar tempCalender = Calendar.getInstance();
        try {
            Date updated_time = yearSdf.parse(data.updated_time);
            tempCalender.setTime(updated_time);
            c.setTime(yearSdf.parse(yearSdf.format(new Date())));

            if (tempCalender.get(Calendar.DAY_OF_MONTH) == c.get(Calendar.DAY_OF_MONTH) && tempCalender.get(Calendar.MONTH) == c.get(Calendar.MONTH)
                    && tempCalender.get(Calendar.YEAR) == c.get(Calendar.YEAR)) {  //오늘인지 판단
                dateView.setText(hourSdf.format(updated_time));
            }else{
                dateView.setText(yearSdf.format(updated_time));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        contentView.setText(data.content);
        productView.setText(data.order.product_name + " x " + data.order.quantity);
        Glide.with(itemView.getContext()).load(data.order.shopper.profile.image_url).into(profileView);
    }
}
