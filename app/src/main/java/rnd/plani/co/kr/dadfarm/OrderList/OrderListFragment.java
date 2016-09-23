package rnd.plani.co.kr.dadfarm.OrderList;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rnd.plani.co.kr.dadfarm.Home.ProductAdapter;
import rnd.plani.co.kr.dadfarm.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderListFragment extends Fragment {


    public OrderListFragment() {
        // Required empty public constructor
    }


    RecyclerView recyclerView;
    ProductAdapter mAdapter;
    LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mAdapter = new ProductAdapter();
        layoutManager = new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        initData();
        return view;
    }

    private void initData(){

    }
}
