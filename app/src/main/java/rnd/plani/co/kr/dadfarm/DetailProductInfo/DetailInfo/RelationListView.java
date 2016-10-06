package rnd.plani.co.kr.dadfarm.DetailProductInfo.DetailInfo;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import rnd.plani.co.kr.dadfarm.Data.RelationData;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.RelationAdapter;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-28.
 */

public class RelationListView extends RecyclerView.ViewHolder {
    FamiliarRecyclerView recyclerView;
    RelationAdapter mAdapter;
    LinearLayoutManager layoutManager;
    public RelationListView(View itemView) {
        super(itemView);
        recyclerView = (FamiliarRecyclerView) itemView.findViewById(R.id.recycler);
        mAdapter = new RelationAdapter();
        layoutManager = new LinearLayoutManager(itemView.getContext(), OrientationHelper.VERTICAL,false);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(layoutManager);
        initData();
    }

    private void initData(){
        for(int i = 0 ; i < 3 ; i++){
            RelationData data= new RelationData();
            data.name = "테스터" + i;
            data.role = "역할" + i;
            mAdapter.add(data);
            if(i<2){
                RelationData relation = new RelationData();
                relation.relation = "관계"+i;
                mAdapter.add(relation);
            }
        }
    }
}
