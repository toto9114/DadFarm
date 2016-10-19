package rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import rnd.plani.co.kr.dadfarm.Data.RelationData;
import rnd.plani.co.kr.dadfarm.OnProfileClickListener;

/**
 * Created by RND on 2016-09-28.
 */

public class RelationAdapter extends BaseAdapter implements OnSmsClickListener, OnPhoneCallClickListener, OnProfileClickListener {
    List<RelationData> items = new ArrayList<>();

    private static final int VIEW_TYPE_PERSON = 0;
    private static final int VIEW_TYPE_RELATION = 1;

    public void add(RelationData data) {
        items.add(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return VIEW_TYPE_PERSON;
        } else {
            return VIEW_TYPE_RELATION;
        }
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_PERSON:
                PersonVerticalRelationView personView;
                if (convertView == null) {
                    personView = new PersonVerticalRelationView(parent.getContext());
                } else {
                    personView = (PersonVerticalRelationView) convertView;
                }
                personView.setPersonView(items.get(position));
                personView.setOnPhoneCallClickListener(this);
                personView.setOnSmsClickListener(this);
                personView.setOnProfileClickListener(this);
                return personView;
            case VIEW_TYPE_RELATION:
                RelationDivider divider;
                if (convertView == null) {
                    divider = new RelationDivider(parent.getContext());
                } else {
                    divider = (RelationDivider) convertView;
                }
                divider.setRelation(items.get(position).relation);
                return divider;
        }
        return null;
    }

//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        switch (viewType) {
//            case VIEW_TYPE_PERSON:
//                View personView = inflater.inflate(R.layout.view_vertical_person_relation, parent, false);
//                return new PersonVerticalRelationView(personView);
//            case VIEW_TYPE_RELATION:
//                View relationView = inflater.inflate(R.layout.view_vertical_relation, parent, false);
//                return new RelationDivider(relationView);
//        }
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        switch (getItemViewType(position)) {
//            case VIEW_TYPE_PERSON:
//                ((PersonVerticalRelationView) holder).setPersonView(items.get(position));
//                ((PersonVerticalRelationView)holder).setOnPhoneCallClickListener(this);
//                ((PersonVerticalRelationView)holder).setOnSmsClickListener(this);
//                break;
//            case VIEW_TYPE_RELATION:
//                ((RelationDivider) holder).setRelation(items.get(position).relation);
//                break;
//        }
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return items.size();
//    }

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

    @Override
    public void OnPhoneCallClick(String phoneNum) {
        if (phoneCallClickListener != null) {
            phoneCallClickListener.OnPhoneCallClick(phoneNum);
        }
    }

    @Override
    public void OnSmsClick(String phoneNum) {
        if (smsClickListener != null) {
            smsClickListener.OnSmsClick(phoneNum);
        }
    }

    @Override
    public void OnProfileClick() {
        if (profileClickListener != null) {
            profileClickListener.OnProfileClick();
        }
    }
}
