package rnd.plani.co.kr.dadfarm.RegistProduct;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rnd.plani.co.kr.dadfarm.Data.ProductData;
import rnd.plani.co.kr.dadfarm.OrderList.SimpleProductView;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by toto9114 on 2016-10-12.
 */

public class AddedProductAdapter extends RecyclerView.Adapter implements OnHeaderViewClickListener{
    List<ProductData> items = new ArrayList<>();

    public static final int HEADER_SIZE = 1;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_PRODUCT = 1;

    public void add(ProductData data) {
        items.add(data);
        notifyDataSetChanged();
    }
    public void addAll(List<ProductData> list){
        items.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public ProductData getItem(int position){
        return items.get(position-HEADER_SIZE);
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_PRODUCT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case TYPE_HEADER:
                View headerView = inflater.inflate(R.layout.view_add_product_header, parent, false);
                return new AddProductHeaderView(headerView);
            case TYPE_PRODUCT:
                View productView = inflater.inflate(R.layout.view_simple_product_item, parent, false);
                return new SimpleProductView(productView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TYPE_HEADER:
                ((AddProductHeaderView)holder).setOnHeaderViewClickListener(this);
                break;
            case TYPE_PRODUCT:
                ((SimpleProductView) holder).setProductItem(items.get(position-HEADER_SIZE));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size()+HEADER_SIZE;
    }

    private OnHeaderViewClickListener headerViewClickListener;
    public void setOnHeaderViewClickListener(OnHeaderViewClickListener listener){
        headerViewClickListener = listener;
    }
    @Override
    public void OnHeaderViewClick() {
        if(headerViewClickListener != null){
            headerViewClickListener.OnHeaderViewClick();
        }
    }
}
