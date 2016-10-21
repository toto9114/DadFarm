package rnd.plani.co.kr.dadfarm.Home;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;

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

    PullRefreshLayout refreshLayout;
    FamiliarRecyclerView recyclerView;
    ProductAdapter mAdapter;
    LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (FamiliarRecyclerView) view.findViewById(R.id.recycler);
        refreshLayout = (PullRefreshLayout) view.findViewById(R.id.refresh);
        layoutManager = new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false);
        mAdapter = new ProductAdapter();
//        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.black));
        refreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);

        refreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Log.e("HomeFragment", "" + position + " : " + mAdapter.getItem(position).title);
                Intent i = new Intent(getActivity(), DetailProductActivity.class);
                i.putExtra(DetailProductActivity.EXTRA_PRODUCT_DATA, mAdapter.getItem(position));
                startActivity(i);
            }
        });
        mAdapter.setOnProfileClickListener(new OnProfileClickListener() {
            @Override
            public void OnProfileClick(PersonalData personalData) {
                Intent i = new Intent(getContext(), RelationInfoActivity.class);
                i.putExtra(RelationInfoActivity.EXTRA_PERSONAL_DATA,personalData);
                startActivity(i);
            }
        });
        initData();
        return view;
    }

    private void initData() {
        NetworkManager.getInstance().getProductList(getContext(), new NetworkManager.OnResultListener<ProductListResultData>() {
            @Override
            public void onSuccess(Request request, ProductListResultData result) {
                if(result != null){
                    for(ProductData data : result.results) {
                        mAdapter.add(data);
                    }
                }
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
    }

}
