package rnd.plani.co.kr.dadfarm.Home;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rnd.plani.co.kr.dadfarm.Data.ProductData;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.DetailProductActivity;
import rnd.plani.co.kr.dadfarm.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    ProductAdapter mAdapter;
    LinearLayoutManager layoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL,false);
        mAdapter = new ProductAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Log.e("HomeFragment",""+position +" : " + mAdapter.getItem(position).title);
                Intent i = new Intent(getActivity(),DetailProductActivity.class);
                i.putExtra(DetailProductActivity.EXTRA_PRODUCT_DATA ,mAdapter.getItem(position));
                startActivity(i);
            }
        });
        initData();
        return view;
    }

    private void initData(){
        ProductData data = new ProductData();
        data.title = "피로회복에 좋은 유기농 아로니아";
        data.content ="내용";
        mAdapter.add(data);
    }

}
