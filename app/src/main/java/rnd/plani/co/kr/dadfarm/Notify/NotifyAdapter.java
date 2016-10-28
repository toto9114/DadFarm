package rnd.plani.co.kr.dadfarm.Notify;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rnd.plani.co.kr.dadfarm.Data.NotifyData;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-27.
 */

public class NotifyAdapter extends RecyclerView.Adapter {
    List<NotifyData> items = new ArrayList<>();

    public void addAll(List<NotifyData> list) {
        items.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_notify_item, parent, false);

        return new NotifyItemView(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((NotifyItemView) holder).setNotify(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
