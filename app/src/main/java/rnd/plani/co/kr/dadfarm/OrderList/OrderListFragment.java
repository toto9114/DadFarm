package rnd.plani.co.kr.dadfarm.OrderList;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import rnd.plani.co.kr.dadfarm.Data.OrderResultData;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Order.OrderCompleteActivity;
import rnd.plani.co.kr.dadfarm.R;
import rnd.plani.co.kr.dadfarm.Utils;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderListFragment extends Fragment {


    public OrderListFragment() {
        // Required empty public constructor
    }

    SwipeRefreshLayout refreshLayout;
    FamiliarRecyclerView recyclerView;
    OrderListAdapter mAdapter;
    LinearLayoutManager layoutManager;
    TextView titleView;
    TextView emptyView;
    FrameLayout titleLabelView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        titleLabelView = (FrameLayout) view.findViewById(R.id.title_label);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            titleLabelView.setPadding(0, Utils.getStatusBarHeight(), 0, 0);
        }
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        titleView = (TextView) view.findViewById(R.id.text_title);
        emptyView = (TextView) view.findViewById(R.id.text_empty_message);
        recyclerView = (FamiliarRecyclerView) view.findViewById(R.id.recycler);
        mAdapter = new OrderListAdapter();
        layoutManager = new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
            }
        });
        recyclerView.setOnItemClickListener(new FamiliarRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
                Intent i = new Intent(getContext(), OrderCompleteActivity.class);
                startActivity(i);
            }
        });

        initData();
        return view;
    }

    private void initData() {
        OrderResultData data = new OrderResultData();
        data.productName = "신선한 유기농 청경채";
        data.orderCount = "유기농 청경채 500g";
        data.orderDate = "2016-10-08";
        mAdapter.add(data);
        if (mAdapter.getItemCount() != 0) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        titleView.setText(String.format(getString(R.string.order_list_title), mAdapter.getItemCount()));
    }
}
