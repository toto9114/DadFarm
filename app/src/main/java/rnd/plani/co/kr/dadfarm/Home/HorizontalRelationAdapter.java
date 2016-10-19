package rnd.plani.co.kr.dadfarm.Home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rnd.plani.co.kr.dadfarm.Data.RelationData;
import rnd.plani.co.kr.dadfarm.OnProfileClickListener;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by toto9114 on 2016-10-06.
 */

public class HorizontalRelationAdapter extends RecyclerView.Adapter implements OnProfileClickListener{
    List<RelationData> items = new ArrayList<>();

    private static final int VIEW_TYPE_PERSON = 0;
    private static final int VIEW_TYPE_RELATION = 1;

    public void add(RelationData data){
        items.add(data);
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        if(position%2 ==0){
            return VIEW_TYPE_PERSON;
        }else{
            return VIEW_TYPE_RELATION;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case VIEW_TYPE_PERSON:
                View personView = inflater.inflate(R.layout.view_horizontal_person_relation, parent, false);
                return new PersonHorizontalView(personView);
            case VIEW_TYPE_RELATION:
                View divider = inflater.inflate(R.layout.view_horizontal_relation,parent,false);
                return new HorizontalRelationDivider(divider);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case VIEW_TYPE_PERSON:
                ((PersonHorizontalView)holder).setProfile();
                ((PersonHorizontalView)holder).setOnProfileClickListener(this);
                break;
            case VIEW_TYPE_RELATION:
                ((HorizontalRelationDivider)holder).setRelation(items.get(position).relation);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private OnProfileClickListener profileClickListener;
    public void setOnProfileClickListener(OnProfileClickListener listener){
        profileClickListener = listener;
    }
    @Override
    public void OnProfileClick() {
        if(profileClickListener != null){
            profileClickListener.OnProfileClick();
        }
    }
}
