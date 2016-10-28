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
import okhttp3.Request;
import rnd.plani.co.kr.dadfarm.Data.OrderListResultData;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Order.OrderCompleteActivity;
import rnd.plani.co.kr.dadfarm.Manager.NetworkManager;
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
    FrameLayout containerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        titleLabelView = (FrameLayout) view.findViewById(R.id.title_label);
        containerView = (FrameLayout) view.findViewById(R.id.container);
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
                refresh();
            }
        });
        recyclerView.setOnItemClickListener(new FamiliarRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
                Intent i = new Intent(getContext(), OrderCompleteActivity.class);
                i.putExtra(OrderCompleteActivity.EXTRA_ORDER_PRODUCT_DATA, mAdapter.getItem(position));
                startActivity(i);
            }
        });

        initData();
        return view;
    }

    private void initData() {
        NetworkManager.getInstance().getOrderList(getContext(), 1, new NetworkManager.OnResultListener<OrderListResultData>() {
            @Override
            public void onSuccess(Request request, OrderListResultData result) {
                mAdapter.clear();
                if (result != null) {
                    mAdapter.addAll(result.results);
                }
                if (mAdapter.getItemCount() != 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                titleView.setText(String.format(getString(R.string.order_list_title), mAdapter.getItemCount()));
                containerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
    }

    private void refresh() {
        NetworkManager.getInstance().getOrderList(getContext(), 1, new NetworkManager.OnResultListener<OrderListResultData>() {
            @Override
            public void onSuccess(Request request, OrderListResultData result) {
                mAdapter.clear();
                if (result != null) {
                    mAdapter.addAll(result.results);
                }
                if (mAdapter.getItemCount() != 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                titleView.setText(String.format(getString(R.string.order_list_title), mAdapter.getItemCount()));
                containerView.setVisibility(View.VISIBLE);
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
            }
        });

    }
}
