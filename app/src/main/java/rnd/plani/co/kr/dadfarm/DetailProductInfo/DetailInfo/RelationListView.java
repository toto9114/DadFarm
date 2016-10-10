package rnd.plani.co.kr.dadfarm.DetailProductInfo.DetailInfo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import rnd.plani.co.kr.dadfarm.Data.RelationData;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.OnPhoneCallClickListener;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.OnSmsClickListener;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.RelationAdapter;
import rnd.plani.co.kr.dadfarm.R;
import rnd.plani.co.kr.dadfarm.Utils;

/**
 * Created by RND on 2016-09-28.
 */

public class RelationListView extends RecyclerView.ViewHolder {

    private OnSmsClickListener smsClickListener;
    public void setOnSmsClickListener(OnSmsClickListener listener){
        smsClickListener = listener;
    }

    private OnPhoneCallClickListener phoneCallClickListener;
    public void setOnPhoneCallClickListener(OnPhoneCallClickListener listener){
        phoneCallClickListener = listener;
    }
    ListView listView;
    RelationAdapter mAdapter;
    public RelationListView(View itemView) {
        super(itemView);
        listView = (ListView) itemView.findViewById(R.id.listview);
        mAdapter = new RelationAdapter();
//        layoutManager = new LinearLayoutManager(itemView.getContext(), OrientationHelper.VERTICAL,false);
        listView.setAdapter(mAdapter);
//        recyclerView.setLayoutManager(layoutManager);
        initData();
        mAdapter.setOnSmsClickListener(new OnSmsClickListener() {
            @Override
            public void OnSmsClick(String phoneNum) {
                if(smsClickListener != null){
                    smsClickListener.OnSmsClick(phoneNum);
                }
            }
        });
        mAdapter.setOnPhoneCallClickListener(new OnPhoneCallClickListener() {
            @Override
            public void OnPhoneCallClick(String phoneNum) {
                if(phoneCallClickListener != null){
                    phoneCallClickListener.OnPhoneCallClick(phoneNum);
                }
            }
        });
    }

    private void initData(){
        for(int i = 0 ; i < 3 ; i++){
            RelationData data= new RelationData();
            data.name = "테스터" + i;
            data.role = "역할" + i;
            data.phone = "010-2672-4411";
            mAdapter.add(data);
            if(i<2){
                RelationData relation = new RelationData();
                relation.relation = "관계"+i;
                mAdapter.add(relation);
            }
        }
        Utils.setListViewHeightBasedOnChildren(listView);
    }
}
