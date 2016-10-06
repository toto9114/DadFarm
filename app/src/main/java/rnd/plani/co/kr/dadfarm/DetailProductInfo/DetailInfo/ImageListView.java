package rnd.plani.co.kr.dadfarm.DetailProductInfo.DetailInfo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-28.
 */

public class ImageListView extends RecyclerView.ViewHolder {
    ListView listView;
    ImageListAdapter mAdapter;
    public ImageListView(View itemView) {
        super(itemView);
        listView = (ListView) itemView.findViewById(R.id.listView);
        mAdapter = new ImageListAdapter();
        listView.setAdapter(mAdapter);
    }

    public void setImageList(){

    }

    public class ImageListAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ProductImageView view = null;
            if(convertView == null){
                view = new ProductImageView(parent.getContext());
            }else{
                view = (ProductImageView) convertView;
            }
            view.setImage();
            return view;
        }
    }
}
