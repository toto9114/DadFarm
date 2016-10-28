package rnd.plani.co.kr.dadfarm.DetailProductInfo.Review;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;

import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import okhttp3.Request;
import rnd.plani.co.kr.dadfarm.CustomToolbar.BlackThemeShareToolbar;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnLeftMenuClickListener;
import rnd.plani.co.kr.dadfarm.Data.PersonalData;
import rnd.plani.co.kr.dadfarm.Data.ProductData;
import rnd.plani.co.kr.dadfarm.Data.ReviewListResultData;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.Relation.RelationInfoActivity;
import rnd.plani.co.kr.dadfarm.Manager.NetworkManager;
import rnd.plani.co.kr.dadfarm.OnProfileClickListener;
import rnd.plani.co.kr.dadfarm.R;

public class ReviewProductActivity extends AppCompatActivity {

    public static final String EXTRA_PRODUCT_DATA = "product";

    FamiliarRecyclerView recyclerView;
    ReviewAdapter mAdaper;
    LinearLayoutManager layoutManager;
    ReviewHeaderView headerView;
    ProductData productData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_product);
        BlackThemeShareToolbar toolbar = (BlackThemeShareToolbar) findViewById(R.id.toolbar);
        toolbar.setToolbar("뒤로", "구매후기", false);
        toolbar.setOnLeftMenuClickListener(new OnLeftMenuClickListener() {
            @Override
            public void OnLeftMenuClick() {
                finish();
            }
        });

        productData = (ProductData) getIntent().getSerializableExtra(EXTRA_PRODUCT_DATA);

        headerView = new ReviewHeaderView(this);
        headerView.setReviewHeader(productData.seller);

        recyclerView = (FamiliarRecyclerView) findViewById(R.id.recycler);
        mAdaper = new ReviewAdapter();
        layoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addHeaderView(headerView);
        recyclerView.setAdapter(mAdaper);
        mAdaper.setOnProfileClickListener(new OnProfileClickListener() {
            @Override
            public void OnProfileClick(PersonalData personalData) {
                Intent i = new Intent(ReviewProductActivity.this, RelationInfoActivity.class);
                i.putExtra(RelationInfoActivity.EXTRA_PERSONAL_DATA, personalData);
                startActivity(i);
            }
        });
        initData();
    }

    private void initData() {
        NetworkManager.getInstance().getReviewList(ReviewProductActivity.this, productData.id, new NetworkManager.OnResultListener<ReviewListResultData>() {
            @Override
            public void onSuccess(Request request, ReviewListResultData result) {
                mAdaper.clear();
                if(result != null) {
                    mAdaper.addAll(result.results);
                }
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });

    }
}
