package rnd.plani.co.kr.dadfarm.Notify;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    public void setNotify(NotifyData data){
        nameView.setText(data.name);
        dateView.setText(data.date);
        contentView.setText(data.content);
    }
}
