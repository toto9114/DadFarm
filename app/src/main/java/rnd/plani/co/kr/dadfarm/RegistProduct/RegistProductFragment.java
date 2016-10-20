package rnd.plani.co.kr.dadfarm.RegistProduct;


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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import rnd.plani.co.kr.dadfarm.Data.ProductData;
import rnd.plani.co.kr.dadfarm.R;
import rnd.plani.co.kr.dadfarm.Utils;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistProductFragment extends Fragment {


    public RegistProductFragment() {
        // Required empty public constructor
    }

    TextView titleView;
    ImageView addProductBtn;
    FrameLayout titleLabelView;
    LinearLayout emptyView;
    FamiliarRecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    AddedProductAdapter mAdapter;
    LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_regist_product, container, false);
        titleLabelView = (FrameLayout) view.findViewById(R.id.title_label);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            titleLabelView.setPadding(0, Utils.getStatusBarHeight(), 0, 0);
        }
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        titleView = (TextView) view.findViewById(R.id.text_title);
        emptyView = (LinearLayout) view.findViewById(R.id.linear_empty);
        addProductBtn = (ImageView) view.findViewById(R.id.btn_regist);
        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), RegistProductInfoActivity.class));
            }
        });
        recyclerView = (FamiliarRecyclerView) view.findViewById(R.id.recycler);
        mAdapter = new AddedProductAdapter();
        layoutManager = new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(layoutManager);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
                }
            }
        });
        recyclerView.setOnItemClickListener(new FamiliarRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
                Intent i = new Intent(getContext(),RegistProductInfoActivity.class);
                i.putExtra(RegistProductInfoActivity.EDIT_TYPE,RegistProductInfoActivity.TYPE_EDIT_PRODUCT);
                startActivity(i);
            }
        });

        mAdapter.setOnHeaderViewClickListener(new OnHeaderViewClickListener() {
            @Override
            public void OnHeaderViewClick() {
                Intent i = new Intent(getContext(), RegistProductInfoActivity.class);
                i.putExtra(RegistProductInfoActivity.EDIT_TYPE,RegistProductInfoActivity.TYPE_ADD_PRODUCT);
                startActivity(i);
            }
        });
        initData();
        return view;
    }

    private void initData() {
        ProductData data = new ProductData();
        data.title = "맛있는 물회";
        data.name = "물회 한그릇";
        data.price = "10,000원";
        mAdapter.add(data);
        mAdapter.add(data);
        mAdapter.add(data);
        mAdapter.add(data);
        mAdapter.add(data);
        if (mAdapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        titleView.setText(mAdapter.getItemCount() - AddedProductAdapter.HEADER_SIZE + "개의 상품을 중계하고 있습니다");
    }
}
