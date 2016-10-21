package rnd.plani.co.kr.dadfarm.DetailProductInfo.Review;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import rnd.plani.co.kr.dadfarm.Data.ReviewData;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Order.OnRelationClickListener;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-27.
 */

public class ReviewItemView extends RecyclerView.ViewHolder {
    private OnRelationClickListener relationClickListener;
    public void setOnRelationClickListener(OnRelationClickListener listener){
        relationClickListener = listener;
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
                if(relationClickListener != null){
                    relationClickListener.OnRelationClick(v,getAdapterPosition());
                }
            }
        });
        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(relationClickListener!=null){
                    relationClickListener.OnRelationClick(v,getAdapterPosition());
                }
            }
        });
    }

    public void setReview(ReviewData data){
        nameView.setText(data.name);
        relationView.setText(data.relation);
        dateView.setText(data.date);
        contentView.setText(data.content);
        productView.setText(data.product_name);
    }
}
