package rnd.plani.co.kr.dadfarm.Home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rnd.plani.co.kr.dadfarm.Data.PersonalData;
import rnd.plani.co.kr.dadfarm.Data.ProductData;
import rnd.plani.co.kr.dadfarm.OnProfileClickListener;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-19.
 */
public class ProductAdapter extends RecyclerView.Adapter implements OnItemClickListener, OnProfileClickListener {
    List<ProductData> items = new ArrayList<>();

    public void add(ProductData data) {
        items.add(data);
        notifyDataSetChanged();
    }

    public ProductData getItem(int position) {
        return items.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_home_item, parent, false);

        return new HomeItemView(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((HomeItemView) holder).setData(items.get(position));
        ((HomeItemView) holder).setOnProfileClickListener(this);
        ((HomeItemView) holder).setOnItemClickListener(this);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    @Override
    public void OnItemClick(View view, int position) {
        if (itemClickListener != null) {
            itemClickListener.OnItemClick(view, position);
        }
    }

    private OnProfileClickListener profileClickListener;

    public void setOnProfileClickListener(OnProfileClickListener listener) {
        profileClickListener = listener;
    }

    @Override
    public void OnProfileClick(PersonalData personalData) {
        if(profileClickListener != null){
            profileClickListener.OnProfileClick(personalData);
        }
    }
}
