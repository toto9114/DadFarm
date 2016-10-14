package rnd.plani.co.kr.dadfarm.OrderList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rnd.plani.co.kr.dadfarm.Data.OrderResultData;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by toto9114 on 2016-10-12.
 */

public class OrderListAdapter extends RecyclerView.Adapter {
    List<OrderResultData> items = new ArrayList<>();

    public void add(OrderResultData data){
        items.add(data);
        notifyDataSetChanged();
    }
    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_simple_product_item,parent,false);
        return new SimpleProductView(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SimpleProductView)holder).setOrderItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
