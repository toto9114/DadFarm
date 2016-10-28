package rnd.plani.co.kr.dadfarm.Home;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import okhttp3.Request;
import rnd.plani.co.kr.dadfarm.Data.PersonalData;
import rnd.plani.co.kr.dadfarm.Data.ProductData;
import rnd.plani.co.kr.dadfarm.Data.ProductListResultData;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.DetailProductActivity;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.RelationInfoActivity;
import rnd.plani.co.kr.dadfarm.Manager.NetworkManager;
import rnd.plani.co.kr.dadfarm.OnProfileClickListener;
import rnd.plani.co.kr.dadfarm.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    SwipeRefreshLayout refreshLayout;
    FamiliarRecyclerView recyclerView;
    ProductAdapter mAdapter;
    LinearLayoutManager layoutManager;
    boolean isMoreData = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (FamiliarRecyclerView) view.findViewById(R.id.recycler);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        layoutManager = new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false);
        mAdapter = new ProductAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent i = new Intent(getActivity(), DetailProductActivity.class);
                i.putExtra(DetailProductActivity.EXTRA_PRODUCT_DATA, mAdapter.getItem(position));
                startActivity(i);
            }
        });
        mAdapter.setOnProfileClickListener(new OnProfileClickListener() {
            @Override
            public void OnProfileClick(PersonalData personalData) {
                Intent i = new Intent(getContext(), RelationInfoActivity.class);
                i.putExtra(RelationInfoActivity.EXTRA_PERSONAL_DATA, personalData);
                startActivity(i);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = layoutManager.findFirstVisibleItemPosition();
                Log.e("HomeFragment", "" + position + " itemCount : " + (mAdapter.getItemCount() - 1));
                if(dy>0) {
                    if (layoutManager.findLastVisibleItemPosition() == (mAdapter.getItemCount() - 1)) {
                        if (!isMoreData) {
                            getMoreData();
                        }
                    }
                }
            }
        });
        initData();
        return view;
    }

    private void initData() {
        NetworkManager.getInstance().getProductList(getContext(), 1, new NetworkManager.OnResultListener<ProductListResultData>() {
            @Override
            public void onSuccess(Request request, ProductListResultData result) {
                if (result != null) {
                    mAdapter.clear();
                    if (result.next != null) {
                        isMoreData = false;
                    } else {
                        isMoreData = true;
                    }
                    for (ProductData data : result.results) {
                        mAdapter.add(data);
                    }
                }
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
    }

    private void getMoreData() {
        if (isMoreData) return;
        isMoreData = true;

        int page = (mAdapter.getItemCount() / 2) + 1;

        NetworkManager.getInstance().getProductList(getContext(), page, new NetworkManager.OnResultListener<ProductListResultData>() {
            @Override
            public void onSuccess(Request request, ProductListResultData result) {
                if (result != null) {
                    if (result.next != null) {
                        isMoreData = false;
                    } else {
                        isMoreData = true;
                    }
                    mAdapter.addAll(result.results);
                }
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {
                isMoreData = false;

            }
        });
    }

    private void refresh() {
        NetworkManager.getInstance().getProductList(getContext(), 1, new NetworkManager.OnResultListener<ProductListResultData>() {
            @Override
            public void onSuccess(Request request, ProductListResultData result) {
                if (result != null) {
                    if (result.next != null) {
                        isMoreData = false;
                    } else {
                        isMoreData = true;
                    }
                    mAdapter.clear();
                    for (ProductData data : result.results) {
                        mAdapter.add(data);
                    }
                }
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
