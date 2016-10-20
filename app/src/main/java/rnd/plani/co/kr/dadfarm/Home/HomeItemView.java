package rnd.plani.co.kr.dadfarm.Home;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wefika.flowlayout.FlowLayout;

import java.util.Set;

import okhttp3.Request;
import rnd.plani.co.kr.dadfarm.Data.PersonalData;
import rnd.plani.co.kr.dadfarm.Data.ProductData;
import rnd.plani.co.kr.dadfarm.Manager.NetworkManager;
import rnd.plani.co.kr.dadfarm.OnProfileClickListener;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-19.
 */
public class HomeItemView extends RecyclerView.ViewHolder {

    private static final String[] TAG_CONTENT = {"아로니아즙 1박스(30팩)", "35,000원", "충남 서산시 운산면 상성리 124-2번지 하늘농원", "주문수 4", "업데이트 2016-09-15"};
    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    private OnProfileClickListener profileClickListener;

    public void setOnProfileClickListener(OnProfileClickListener listener) {
        profileClickListener = listener;
    }

    TextView titleView, sellerNameView;
    ImageView profileView, pictureView;
    FlowLayout mFlowlayout;
    RecyclerView recyclerView;
    HorizontalRelationAdapter mAdapter;
    LinearLayoutManager layoutManager;
    Context context;

    public HomeItemView(View itemView) {
        super(itemView);
        context = itemView.getContext();
        profileView = (ImageView) itemView.findViewById(R.id.image_profile);
        pictureView = (ImageView) itemView.findViewById(R.id.image_picture);
        titleView = (TextView) itemView.findViewById(R.id.text_title);
        sellerNameView = (TextView) itemView.findViewById(R.id.text_seller_name);
        mFlowlayout = (FlowLayout) itemView.findViewById(R.id.flowlayout);
        recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler);
        mAdapter = new HorizontalRelationAdapter();
        layoutManager = new LinearLayoutManager(itemView.getContext(), OrientationHelper.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.OnItemClick(v, getAdapterPosition());
                }
            }
        });
        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileClickListener != null) {
                    profileClickListener.OnProfileClick();
                }
            }
        });
        mAdapter.setOnProfileClickListener(new OnProfileClickListener() {
            @Override
            public void OnProfileClick() {
                if (profileClickListener != null) {
                    profileClickListener.OnProfileClick();
                }
            }
        });


        pictureView.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.image_opacity));
    }


    public void setData(final ProductData data) {
        titleView.setText(data.title);
        for (int i = 0; i < 5; i++) {
            TagBoxView tagBoxView = new TagBoxView(context);
            switch (i) {
                case 0:
                    tagBoxView.setContent(data.name);
                    break;
                case 1:
                    tagBoxView.setContent(data.price);
                    break;
                case 2:
                    tagBoxView.setContent(data.address);
                    break;
                case 3:
                    tagBoxView.setContent("" + data.order_count);
                    break;
                case 4:
                    tagBoxView.setContent(data.updated_time);
                    break;
            }
            mFlowlayout.addView(tagBoxView, FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);
        }
        sellerNameView.setText(data.seller.last_name + data.seller.first_name);
        Glide.with(itemView.getContext()).load(data.images.get(0)).into(pictureView);
        if (data.seller.profile.image_url != null) {
            Glide.with(itemView.getContext()).load(data.seller.profile.image_url);
        }
        NetworkManager.getInstance().getUserInfo(itemView.getContext(), new NetworkManager.OnResultListener<PersonalData>() {
            @Override
            public void onSuccess(Request request, PersonalData result) {
                long subject_id = result.profile.id;
                long object_id = data.manager.profile.id;
                String temp = subject_id + "-" + object_id;
                mAdapter.add(result);
                data.relationships.containsKey(temp);
                mAdapter.add(data.seller);
                Set<String> relation = data.relationships.keySet();
                String[] key = relation.toArray(new String[relation.size()]);
                for (int i = 0; i < key.length; i++) {

//                    if(i <relation.size()-1){
//                        mAdapter.add(null);
//                    }
                }

            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });

    }

}
