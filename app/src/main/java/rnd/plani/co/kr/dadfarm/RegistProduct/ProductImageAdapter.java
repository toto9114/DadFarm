package rnd.plani.co.kr.dadfarm.RegistProduct;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toto9114 on 2016-10-11.
 */

public class ProductImageAdapter extends BaseAdapter implements OnCloseClickListener{
    List<String> items = new ArrayList<>();

    public void addAll(List<String> photos){
        items.addAll(photos);
        notifyDataSetChanged();
    }
    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }

    public void remove(String imageUri){
        items.remove(imageUri);
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
        RegistProductImageView view;
        if(convertView == null){
            view = new RegistProductImageView(parent.getContext());
        }else{
            view = (RegistProductImageView) convertView;
        }
        view.setImageFromUri(items.get(position));
        view.setOnCloseClickListener(this);
        return view;
    }

    private OnCloseClickListener closeClickListener;
    public void setOnCloseClickListener(OnCloseClickListener listener){
        closeClickListener = listener;
    }
    @Override
    public void onCloseClick(String imageUri) {
        if(closeClickListener != null){
            closeClickListener.onCloseClick(imageUri);
        }
    }
}
