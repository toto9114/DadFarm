package rnd.plani.co.kr.dadfarm.Notify;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import rnd.plani.co.kr.dadfarm.Data.NotifyData;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-27.
 */

public class NotifyItemView extends RecyclerView.ViewHolder {
    ImageView profileView;
    TextView nameView, dateView, contentView;

    public NotifyItemView(View itemView) {
        super(itemView);
        nameView = (TextView) itemView.findViewById(R.id.text_name);
        dateView = (TextView) itemView.findViewById(R.id.text_date);
        contentView = (TextView) itemView.findViewById(R.id.text_content);
    }

    public void setNotify(NotifyData data) {
        SimpleDateFormat yearSdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hourSdf = new SimpleDateFormat("h:mm a");
        Calendar c = Calendar.getInstance();
        Calendar tempCalender = Calendar.getInstance();
        try {
            Date created_time = yearSdf.parse(data.created_time);
            tempCalender.setTime(created_time);
            c.setTime(yearSdf.parse(yearSdf.format(new Date())));

            if (tempCalender.get(Calendar.DAY_OF_MONTH) == c.get(Calendar.DAY_OF_MONTH) && tempCalender.get(Calendar.MONTH) == c.get(Calendar.MONTH)
                    && tempCalender.get(Calendar.YEAR) == c.get(Calendar.YEAR)) {  //오늘인지 판단
                dateView.setText(hourSdf.format(created_time));
            }else{
                dateView.setText(yearSdf.format(created_time));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        nameView.setText(data.sender.last_name + data.sender.first_name);
        contentView.setText(data.content);
    }
}
