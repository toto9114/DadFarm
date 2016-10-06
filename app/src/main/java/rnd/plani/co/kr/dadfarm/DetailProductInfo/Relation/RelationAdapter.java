package rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rnd.plani.co.kr.dadfarm.Data.RelationData;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.PersonHorizontalRelationView;
import rnd.plani.co.kr.dadfarm.R;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.RelationDivider;

/**
 * Created by RND on 2016-09-28.
 */

public class RelationAdapter extends RecyclerView.Adapter {
    List<RelationData> items = new ArrayList<>();

    private static final int VIEW_TYPE_PERSON = 0;
    private static final int VIEW_TYPE_RELATION = 1;

    public void add(RelationData data){
        items.add(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(position%2 == 0){
            return VIEW_TYPE_PERSON;
        }else{
            return  VIEW_TYPE_RELATION;
        }
    }

    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case VIEW_TYPE_PERSON:
                View personView = inflater.inflate(R.layout.view_horizontal_person_relation,parent,false);
                return new PersonHorizontalRelationView(personView);
            case VIEW_TYPE_RELATION:
                View relationView = inflater.inflate(R.layout.view_relation,parent,false);
                return new RelationDivider(relationView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case VIEW_TYPE_PERSON:
                ((PersonHorizontalRelationView)holder).setPersonView(items.get(position));
                break;
            case VIEW_TYPE_RELATION:
                ((RelationDivider)holder).setRelation(items.get(position).relation);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
