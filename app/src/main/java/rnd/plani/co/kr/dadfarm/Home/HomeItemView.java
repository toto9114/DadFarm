package rnd.plani.co.kr.dadfarm.Home;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wefika.flowlayout.FlowLayout;

import rnd.plani.co.kr.dadfarm.Data.ProductData;
import rnd.plani.co.kr.dadfarm.Data.RelationData;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-19.
 */
public class HomeItemView extends RecyclerView.ViewHolder {

    private static final String[] TAG_CONTENT = {"아로니아즙 1박스(30팩)", "35,000원","충남 서산시 운산면 상성리 124-2번지 하늘농원","주문수 4","업데이트 2016-09-15"};
    private OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        itemClickListener = listener;
    }

    TextView titleView;
    ImageView profileView;
    FlowLayout mFlowlayout;
    RecyclerView recyclerView;
    HorizontalRelationAdapter mAdapter;
    LinearLayoutManager layoutManager;
    Context context;
    public HomeItemView(View itemView) {
        super(itemView);
        context = itemView.getContext();
        profileView = (ImageView) itemView.findViewById(R.id.image_profile);
        titleView = (TextView) itemView.findViewById(R.id.text_title);
        mFlowlayout = (FlowLayout) itemView.findViewById(R.id.flowlayout);
        recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler);
        mAdapter = new HorizontalRelationAdapter();
        layoutManager = new LinearLayoutManager(itemView.getContext(), OrientationHelper.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener != null){
                    itemClickListener.OnItemClick(v,getAdapterPosition());
                }
            }
        });
    }

    public void setData(ProductData data){
        titleView.setText(data.title);
        for(int i = 0 ; i < TAG_CONTENT.length ; i++) {
            TagBoxView tagBoxView = new TagBoxView(context);
            tagBoxView.setContent(TAG_CONTENT[i]);
            mFlowlayout.addView(tagBoxView,FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);
        }
        for(int i = 0 ; i < 4 ; i++){
            RelationData relationData= new RelationData();
            relationData.name = "테스터" + i;
            relationData.role = "역할" + i;
            mAdapter.add(relationData);
            if(i<3){
                RelationData relation = new RelationData();
                relation.relation = "관계"+i;
                mAdapter.add(relation);
            }
        }
    }
}
