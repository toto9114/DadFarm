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

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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

    OneRelationView oneRelationView;
    TwoRelationView twoRelationView;
    ThreeRelationView threeRelationView;
    FourRelationView fourRelationView;
    TagBoxView productNameTag, priceTag, addressTag, orderCountTag, updateDateTag;

    public HomeItemView(View itemView) {
        super(itemView);
        context = itemView.getContext();
        profileView = (ImageView) itemView.findViewById(R.id.image_profile);
        pictureView = (ImageView) itemView.findViewById(R.id.image_picture);
        titleView = (TextView) itemView.findViewById(R.id.text_title);
        sellerNameView = (TextView) itemView.findViewById(R.id.text_seller_name);
//        mFlowlayout = (FlowLayout) itemView.findViewById(R.id.flowlayout);

        oneRelationView = (OneRelationView) itemView.findViewById(R.id.one_relation);
        twoRelationView = (TwoRelationView) itemView.findViewById(R.id.two_relation);
        threeRelationView = (ThreeRelationView) itemView.findViewById(R.id.three_relation);
        fourRelationView = (FourRelationView) itemView.findViewById(R.id.four_relation);

        productNameTag = (TagBoxView) itemView.findViewById(R.id.tag_product_name);
        priceTag = (TagBoxView) itemView.findViewById(R.id.tag_price);
        addressTag = (TagBoxView) itemView.findViewById(R.id.tag_address);
        orderCountTag = (TagBoxView) itemView.findViewById(R.id.tag_order_count);
        updateDateTag = (TagBoxView) itemView.findViewById(R.id.tag_update_date);

        layoutManager = new LinearLayoutManager(itemView.getContext(), OrientationHelper.HORIZONTAL, false);

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
                    profileClickListener.OnProfileClick(productData.seller);
                }
            }
        });

        oneRelationView.setOnProfileClickListener(new OnProfileClickListener() {
            @Override
            public void OnProfileClick(PersonalData personalData) {
                if (profileClickListener != null) {
                    profileClickListener.OnProfileClick(personalData);
                }
            }
        });

        twoRelationView.setOnProfileClickListener(new OnProfileClickListener() {
            @Override
            public void OnProfileClick(PersonalData personalData) {
                if (profileClickListener != null) {
                    profileClickListener.OnProfileClick(personalData);
                }
            }
        });
        threeRelationView.setOnProfileClickListener(new OnProfileClickListener() {
            @Override
            public void OnProfileClick(PersonalData personalData) {
                if (profileClickListener != null) {
                    profileClickListener.OnProfileClick(personalData);
                }
            }
        });

        fourRelationView.setOnProfileClickListener(new OnProfileClickListener() {
            @Override
            public void OnProfileClick(PersonalData personalData) {
                if (profileClickListener != null) {
                    profileClickListener.OnProfileClick(personalData);
                }
            }
        });
        pictureView.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.image_opacity));
    }

    ProductData productData = null;

    public void setData(final ProductData data) {
        productData = data;
        titleView.setText(data.title);
        NumberFormat nf = NumberFormat.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        productNameTag.setContent(data.name);
        priceTag.setContent(nf.format(Integer.parseInt(data.price)) + "원");
        addressTag.setContent(data.address);
        orderCountTag.setContent("주문수 " + data.order_count);
        try {
            updateDateTag.setContent("업데이트 " + sdf.format(sdf.parse(data.updated_time)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!data.images.isEmpty()) {
            Glide.with(itemView.getContext()).load(data.images.get(0)).into(pictureView);
        }
        if (data.seller.profile.image_url != null) {
            Glide.with(itemView.getContext()).load(data.seller.profile.image_url).into(profileView);
        }
        NetworkManager.getInstance().getMyUserInfo(itemView.getContext(), new NetworkManager.OnResultListener<PersonalData>() {
            @Override
            public void onSuccess(Request request, PersonalData me) {
                List<String> key = new ArrayList<String>();
                List<String> val = new ArrayList<String>();
                key.addAll(data.relationships.keySet());    //key값 넣기
                for (int i = 0; i < key.size(); i++) {
                    val.add(data.relationships.get(key.get(i)));    //value 값 넣기
                }
                if (data.friend != null) {
                    setRelationVisible(TYPE_FOUR_RELATION);
                    String meFriendRelation = data.relationships.get(me.id + "-" + data.friend.id);
                    String friendManagerRelation = data.relationships.get(data.friend.id + "-" + data.manager.id);
                    String managerSellerRelation = data.relationships.get(data.manager.id + "-" + data.seller.id);
                    sellerNameView.setText(data.friend.last_name + data.friend.first_name + "의 " + friendManagerRelation + "의 " + managerSellerRelation);
                    fourRelationView.setFourRelation(me, data, meFriendRelation, friendManagerRelation, managerSellerRelation);
                    //four
                } else {
                    if (data.manager.id == me.id) {
                        if (data.seller.id == me.id) {
                            setRelationVisible(TYPE_ONE_RELATION);
                            oneRelationView.setOneRelation(me);
                            //one
                        } else {
                            setRelationVisible(TYPE_TWO_RELATION);
                            String relation = data.relationships.get(me.id + "-" + data.seller.id);
                            sellerNameView.setText(me.last_name + me.first_name + "의 " + relation);
                            twoRelationView.setTwoRelation(me, data, relation);
                            //two
                        }
                    } else {
                        setRelationVisible(TYPE_THREE_RELATION);
                        String meManagerrelation = data.relationships.get(me.id + "-" + data.manager.id);
                        String managerSellerRelation = data.relationships.get(data.manager.id + "-" + data.seller.id);
                        sellerNameView.setText(data.manager.last_name + data.manager.first_name + "의 " + managerSellerRelation);
                        threeRelationView.setThreeRelation(me, data, meManagerrelation, managerSellerRelation);
                        //three
                    }
                }

            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });

    }

    private static final int TYPE_ONE_RELATION = 1;
    private static final int TYPE_TWO_RELATION = 2;
    private static final int TYPE_THREE_RELATION = 3;
    private static final int TYPE_FOUR_RELATION = 4;

    private void setRelationVisible(int type) {
        switch (type) {
            case TYPE_ONE_RELATION:
                oneRelationView.setVisibility(View.VISIBLE);
                twoRelationView.setVisibility(View.GONE);
                threeRelationView.setVisibility(View.GONE);
                fourRelationView.setVisibility(View.GONE);
                break;
            case TYPE_TWO_RELATION:
                twoRelationView.setVisibility(View.VISIBLE);
                oneRelationView.setVisibility(View.GONE);
                threeRelationView.setVisibility(View.GONE);
                fourRelationView.setVisibility(View.GONE);
                break;
            case TYPE_THREE_RELATION:
                threeRelationView.setVisibility(View.VISIBLE);
                twoRelationView.setVisibility(View.GONE);
                oneRelationView.setVisibility(View.GONE);
                fourRelationView.setVisibility(View.GONE);
                break;
            case TYPE_FOUR_RELATION:
                fourRelationView.setVisibility(View.VISIBLE);
                twoRelationView.setVisibility(View.GONE);
                threeRelationView.setVisibility(View.GONE);
                oneRelationView.setVisibility(View.GONE);
                break;
        }
    }

}
