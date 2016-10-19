package rnd.plani.co.kr.dadfarm.DetailProductInfo.DetailInfo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rnd.plani.co.kr.dadfarm.Data.ProductData;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.OnPhoneCallClickListener;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.OnSmsClickListener;
import rnd.plani.co.kr.dadfarm.OnProfileClickListener;
import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-28.
 */

public class DetailInfoAdapter extends RecyclerView.Adapter implements OnReviewBtnClickListener, OnOrderBtnClickListener, OnSmsClickListener, OnPhoneCallClickListener, OnProfileClickListener {
    private static final int VIEW_TYPE_COUNT = 5;
    private static final int VIEW_TYPE_TITLE = 0;
    private static final int VIEW_TYPE_RELATION = 1;
    private static final int VIEW_TYPE_IMAGE = 2;
    private static final int VIEW_TYPE_CONTENT = 3;
    private static final int VIEW_TYPE_EMPTY = 4;

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return VIEW_TYPE_TITLE;
            case 1:
                return VIEW_TYPE_RELATION;
            case 2:
                return VIEW_TYPE_IMAGE;
            case 3:
                return VIEW_TYPE_CONTENT;
            case 4:
                return VIEW_TYPE_EMPTY;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        switch (viewType) {
            case VIEW_TYPE_TITLE:
                view = inflater.inflate(R.layout.view_detail_info_title, parent, false);
                return new TitleInfoView(view);
            case VIEW_TYPE_RELATION:
                view = inflater.inflate(R.layout.view_relation_list, parent, false);
                return new RelationListView(view);
            case VIEW_TYPE_IMAGE:
                view = inflater.inflate(R.layout.view_image_list, parent, false);
                return new ImageListView(view);
            case VIEW_TYPE_CONTENT:
                view = inflater.inflate(R.layout.view_content_info, parent, false);
                return new ContentInfoView(view);
            case VIEW_TYPE_EMPTY:
                view = inflater.inflate(R.layout.view_bottom_empty, parent, false);
                return new BottomEmptyView(view);
        }
        return null;
    }

    ProductData data = null;

    public void setProduct(ProductData data) {
        this.data = data;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_TITLE:
                ((TitleInfoView) holder).setTitleView(data);
                break;
            case VIEW_TYPE_RELATION:
                ((RelationListView) holder).setOnSmsClickListener(this);
                ((RelationListView) holder).setOnPhoneCallClickListener(this);
                ((RelationListView) holder).setOnProfileClickListener(this);
                break;
            case VIEW_TYPE_IMAGE:
                ((ImageListView) holder).setImageList();
                break;
            case VIEW_TYPE_CONTENT:
                ((ContentInfoView) holder).setContentView(data);
                break;
            case VIEW_TYPE_EMPTY:

                break;
        }
    }

    @Override
    public int getItemCount() {
        return VIEW_TYPE_COUNT;
    }

    private OnReviewBtnClickListener reviewBtnClickListener;

    public void setOnReviewBtnClickListener(OnReviewBtnClickListener listener) {
        reviewBtnClickListener = listener;
    }

    private OnOrderBtnClickListener orderBtnClickListener;

    public void setOnOrderBtnClickListener(OnOrderBtnClickListener listener) {
        orderBtnClickListener = listener;
    }

    @Override
    public void OnOrderBtnClick() {
        if (orderBtnClickListener != null) {
            orderBtnClickListener.OnOrderBtnClick();
        }
    }

    @Override
    public void OnReviewBtnClick() {
        if (reviewBtnClickListener != null) {
            reviewBtnClickListener.OnReviewBtnClick();
        }
    }

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
    public void OnSmsClick(String phoneNum) {
        if (smsClickListener != null) {
            smsClickListener.OnSmsClick(phoneNum);
        }
    }

    @Override
    public void OnPhoneCallClick(String phoneNum) {
        if (phoneCallClickListener != null) {
            phoneCallClickListener.OnPhoneCallClick(phoneNum);
        }
    }

    @Override
    public void OnProfileClick() {
        if (profileClickListener != null) {
            profileClickListener.OnProfileClick();
        }
    }
}
