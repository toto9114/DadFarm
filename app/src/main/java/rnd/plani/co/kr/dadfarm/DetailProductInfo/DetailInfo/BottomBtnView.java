package rnd.plani.co.kr.dadfarm.DetailProductInfo.DetailInfo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-28.
 */

public class BottomBtnView extends RecyclerView.ViewHolder {
    private OnReviewBtnClickListener reviewBtnClickListener;
    public void setOnReviewBtnClickListener(OnReviewBtnClickListener listener){
        reviewBtnClickListener = listener;
    }

    private OnOrderBtnClickListener orderBtnClickListener;
    public void setOnOrderBtnClickListener(OnOrderBtnClickListener listener){
        orderBtnClickListener = listener;
    }
    Button reviewBtn, orderBtn;
    public BottomBtnView(View itemView) {
        super(itemView);
        reviewBtn = (Button) itemView.findViewById(R.id.btn_review);
        orderBtn = (Button) itemView.findViewById(R.id.btn_order);

        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reviewBtnClickListener != null){
                    reviewBtnClickListener.OnReviewBtnClick();
                }
            }
        });

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderBtnClickListener!=null){
                    orderBtnClickListener.OnOrderBtnClick();
                }
            }
        });
    }
}
