package rnd.plani.co.kr.dadfarm.DetailProductInfo.Review;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rnd.plani.co.kr.dadfarm.Data.PersonalData;
import rnd.plani.co.kr.dadfarm.Data.ReviewData;
import rnd.plani.co.kr.dadfarm.OnProfileClickListener;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-27.
 */

public class ReviewAdapter extends RecyclerView.Adapter implements OnProfileClickListener {
    List<ReviewData> items = new ArrayList<>();
    public void add(ReviewData data){
        items.add(data);
        notifyDataSetChanged();
    }
    public void addAll(List<ReviewData> list){
        items.addAll(list);
        notifyDataSetChanged();
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
        ((ReviewItemView)holder).setOnProfileClickListener(this);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private OnProfileClickListener profileClickListener;

    public void setOnProfileClickListener(OnProfileClickListener listener) {
        profileClickListener = listener;
    }

    @Override
    public void OnProfileClick(PersonalData personalData) {
        if (profileClickListener != null) {
            profileClickListener.OnProfileClick(personalData);
        }
    }
}
