package rnd.plani.co.kr.dadfarm.DetailProductInfo.DetailInfo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import rnd.plani.co.kr.dadfarm.Data.PersonalData;
import rnd.plani.co.kr.dadfarm.Data.ProductData;
import rnd.plani.co.kr.dadfarm.Data.ProfileData;
import rnd.plani.co.kr.dadfarm.Data.RelationshipsData;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.OnPhoneCallClickListener;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.OnSmsClickListener;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.RelationAdapter;
import rnd.plani.co.kr.dadfarm.Manager.PropertyManager;
import rnd.plani.co.kr.dadfarm.OnProfileClickListener;
import rnd.plani.co.kr.dadfarm.R;
import rnd.plani.co.kr.dadfarm.Utils;

/**
 * Created by RND on 2016-09-28.
 */

public class RelationListView extends RecyclerView.ViewHolder {

    private OnSmsClickListener smsClickListener;

    public void setOnSmsClickListener(OnSmsClickListener listener) {
        smsClickListener = listener;
    }

    private OnPhoneCallClickListener phoneCallClickListener;

    public void setOnPhoneCallClickListener(OnPhoneCallClickListener listener) {
        phoneCallClickListener = listener;
    }

    private OnProfileClickListener profileClickListener;

    public void setOnProfileClickListener(OnProfileClickListener listener) {
        profileClickListener = listener;
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
        mAdapter.setOnSmsClickListener(new OnSmsClickListener() {
            @Override
            public void OnSmsClick(String phoneNum) {
                if (smsClickListener != null) {
                    smsClickListener.OnSmsClick(phoneNum);
                }
            }
        });
        mAdapter.setOnPhoneCallClickListener(new OnPhoneCallClickListener() {
            @Override
            public void OnPhoneCallClick(String phoneNum) {
                if (phoneCallClickListener != null) {
                    phoneCallClickListener.OnPhoneCallClick(phoneNum);
                }
            }
        });
        mAdapter.setOnProfileClickListener(new OnProfileClickListener() {
            @Override
            public void OnProfileClick(PersonalData personalData) {
                if (profileClickListener != null) {
                    profileClickListener.OnProfileClick(personalData);
                }
            }
        });
    }

    public void setRelationList(final ProductData data) {
        mAdapter.clear();
        PersonalData me = new PersonalData();
        me.first_name = PropertyManager.getInstance().getFirstName();
        me.last_name = PropertyManager.getInstance().getLastName();
        me.id = PropertyManager.getInstance().getUserId();
        me.profile = new ProfileData();
        me.profile.image_url = PropertyManager.getInstance().getProfileUrl();
        me.profile.phone_number = PropertyManager.getInstance().getPhoneNum();


        if (data.friend != null) {
            String meFriendRelation = data.relationships.get(data.friend.id + "-" + me.id);
            String friendManagerRelation = data.relationships.get(data.manager.id + "-" + data.friend.id);
            String managerSellerRelation = data.relationships.get(data.seller.id + "-" + data.manager.id);
            RelationshipsData relationshipsData = new RelationshipsData();
            mAdapter.add(data.seller);
            relationshipsData.relationName = managerSellerRelation;
            mAdapter.add(relationshipsData);

            mAdapter.add(data.manager);
            relationshipsData = new RelationshipsData();
            relationshipsData.relationName = friendManagerRelation;
            mAdapter.add(relationshipsData);

            mAdapter.add(data.friend);
            relationshipsData = new RelationshipsData();
            relationshipsData.relationName = meFriendRelation;
            mAdapter.add(relationshipsData);

            mAdapter.add(me);
            //four
        } else {
            if (data.manager.id == me.id) {
                if (data.seller.id == me.id) {
                    mAdapter.add(me);
                    //one
                } else {
                    RelationshipsData relationshipsData = new RelationshipsData();
                    String relation = data.relationships.get(data.seller.id + "-" + me.id);
                    mAdapter.add(data.seller);
                    relationshipsData.relationName = relation;
                    mAdapter.add(relationshipsData);

                    mAdapter.add(me);
                    //two
                }
            } else {
                String meManagerRelation = data.relationships.get(data.manager.id + "-" + me.id);
                String managerSellerRelation = data.relationships.get(data.seller.id + "-" + data.manager.id);
                RelationshipsData relationshipsData = new RelationshipsData();
                mAdapter.add(data.seller);
                relationshipsData.relationName = managerSellerRelation;
                mAdapter.add(relationshipsData);

                mAdapter.add(data.manager);
                relationshipsData = new RelationshipsData();
                relationshipsData.relationName = meManagerRelation;
                mAdapter.add(relationshipsData);

                mAdapter.add(me);
                //three
            }
        }
        Utils.setListViewHeightBasedOnChildren(listView);


    }
}
