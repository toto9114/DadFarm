package rnd.plani.co.kr.dadfarm.DetailProductInfo.Review;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rnd.plani.co.kr.dadfarm.Data.ReviewData;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Order.OnRelationClickListener;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-27.
 */

public class ReviewAdapter extends RecyclerView.Adapter implements OnRelationClickListener {
    List<ReviewData> items = new ArrayList<>();
    public void add(ReviewData data){
        items.add(data);
        notifyDataSetChanged();
    }
    public void addAll(List<ReviewData> list){
        items.addAll(list);

    }
    public void clear(){
        items.clear();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_review_item,parent,false);

        return new ReviewItemView(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ReviewItemView)holder).setReview(items.get(position));
        ((ReviewItemView)holder).setOnRelationClickListener(this);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private OnRelationClickListener relationClickListener;
    public void setOnRelationClickListenr(OnRelationClickListener listener){
        relationClickListener = listener;
    }
    @Override
    public void OnRelationClick(View view, int position) {
        if(relationClickListener != null){
            relationClickListener.OnRelationClick(view,position);
        }
    }
}
