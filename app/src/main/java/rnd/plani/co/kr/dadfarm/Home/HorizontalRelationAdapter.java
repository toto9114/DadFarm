package rnd.plani.co.kr.dadfarm.Home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rnd.plani.co.kr.dadfarm.Data.PersonalData;
import rnd.plani.co.kr.dadfarm.Data.Relationships;
import rnd.plani.co.kr.dadfarm.Data.RelationshipsData;
import rnd.plani.co.kr.dadfarm.OnProfileClickListener;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by toto9114 on 2016-10-06.
 */

public class HorizontalRelationAdapter extends RecyclerView.Adapter implements OnProfileClickListener{
    List<Relationships> items = new ArrayList<>();

    private static final int VIEW_TYPE_PERSON = 0;
    private static final int VIEW_TYPE_RELATION = 1;

    public void add(Relationships data){
        items.add(data);
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        if(items.get(position) instanceof PersonalData){
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
                ((PersonHorizontalView)holder).setProfile((PersonalData) items.get(position));
                ((PersonHorizontalView)holder).setOnProfileClickListener(this);
                break;
            case VIEW_TYPE_RELATION:
                ((HorizontalRelationDivider)holder).setRelation((RelationshipsData)items.get(position));
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
    public void OnProfileClick(PersonalData personalData) {
        if(profileClickListener != null){
            profileClickListener.OnProfileClick(personalData);
        }
    }
}
