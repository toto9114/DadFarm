package rnd.plani.co.kr.dadfarm.Home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wefika.flowlayout.FlowLayout;

import rnd.plani.co.kr.dadfarm.Data.ProductData;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-19.
 */
public class HomeItemView extends RecyclerView.ViewHolder {

    private OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        itemClickListener = listener;
    }

    TextView titleView;
    ImageView profileView;
    FlowLayout mFlowlayout;
    Context context;
    public HomeItemView(View itemView) {
        super(itemView);
        context = itemView.getContext();
        titleView = (TextView) itemView.findViewById(R.id.text_title);
        mFlowlayout = (FlowLayout) itemView.findViewById(R.id.flowlayout);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener != null){
                    itemClickListener.OnItemClick(v,getAdapterPosition());
                }
            }
        });
    }

    public void setData(ProductData data){
        titleView.setText(data.title);
        TagBoxView tagBoxView = new TagBoxView(context);
        for(int i = 0 ; i < 6 ; i++) {
            tagBoxView.setContent("테스트");
        }
        mFlowlayout.addView(tagBoxView);
    }
}
