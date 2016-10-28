package rnd.plani.co.kr.dadfarm.Notify;


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
import rnd.plani.co.kr.dadfarm.Data.NotifyResultData;
import rnd.plani.co.kr.dadfarm.Manager.NetworkManager;
import rnd.plani.co.kr.dadfarm.R;
import rnd.plani.co.kr.dadfarm.Utils;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {


    public NotificationFragment() {
        // Required empty public constructor
    }

    FamiliarRecyclerView recyclerView;
    NotifyAdapter mAdapter;
    SwipeRefreshLayout refreshLayout;
    FrameLayout titleLabelView;
    FrameLayout containerView;
    TextView emptyView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        containerView = (FrameLayout) view.findViewById(R.id.container);
        recyclerView = (FamiliarRecyclerView) view.findViewById(R.id.recycler);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        titleLabelView = (FrameLayout) view.findViewById(R.id.title_label);
        emptyView = (TextView) view.findViewById(R.id.text_empty_message);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            titleLabelView.setPadding(0, Utils.getStatusBarHeight(), 0, 0);
        }
        mAdapter = new NotifyAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        initData();

        return view;
    }

    private void initData() {
        NetworkManager.getInstance().getNotification(getContext(), 1, new NetworkManager.OnResultListener<NotifyResultData>() {
            @Override
            public void onSuccess(Request request, NotifyResultData result) {
                if (result != null) {
                    mAdapter.clear();
                    mAdapter.addAll(result.results);
                }
                if (mAdapter.getItemCount() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                }
                containerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
    }

    private void refresh() {
        NetworkManager.getInstance().getNotification(getContext(), 1, new NetworkManager.OnResultListener<NotifyResultData>() {
            @Override
            public void onSuccess(Request request, NotifyResultData result) {
                if (result != null) {
                    mAdapter.clear();
                    mAdapter.addAll(result.results);
                }
                if (mAdapter.getItemCount() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                }
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
