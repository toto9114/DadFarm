package rnd.plani.co.kr.dadfarm.RegistProduct;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by toto9114 on 2016-10-12.
 */

public class AddProductHeaderView extends RecyclerView.ViewHolder {
    private OnHeaderViewClickListener headerViewClickListener;
    public void setOnHeaderViewClickListener(OnHeaderViewClickListener listener){
        headerViewClickListener = listener;
    }
    public AddProductHeaderView(View itemView) {
        super(itemView);
        FrameLayout btn = (FrameLayout) itemView.findViewById(R.id.btn_add_product);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(headerViewClickListener != null){
                    headerViewClickListener.OnHeaderViewClick();
                }
            }
        });
    }
}
