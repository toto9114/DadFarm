package rnd.plani.co.kr.dadfarm.RegistProduct;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.tangxiaolv.telegramgallery.GalleryActivity;

import java.util.List;

import rnd.plani.co.kr.dadfarm.CustomToolbar.BlackThemeTextToolbar;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnLeftMenuClickListener;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnRightMenuClickListener;
import rnd.plani.co.kr.dadfarm.DetailProductInfo.DetailInfo.ProductImageView;
import rnd.plani.co.kr.dadfarm.R;

public class RegistProductInfoActivity extends AppCompatActivity {

    private static final int REQUEST_PICK_PICTURE = 100;

    FrameLayout pickerView;
    LinearLayout imageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_product_info);
        BlackThemeTextToolbar toolbar = (BlackThemeTextToolbar) findViewById(R.id.toolbar);
        toolbar.setToolbar("취소","상품등록","완료");

        toolbar.setOnLeftMenuClickListener(new OnLeftMenuClickListener() {
            @Override
            public void OnLeftMenuClick() {
                finish();
            }
        });

        toolbar.setOnRightMenuClickListener(new OnRightMenuClickListener() {
            @Override
            public void OnRightMenuClick() {
                finish();
            }
        });
        pickerView = (FrameLayout) findViewById(R.id.btn_pick);
        imageList = (LinearLayout) findViewById(R.id.linear_image_list);
        pickerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent, REQUEST_PICK_PICTURE);
                GalleryActivity.openActivity(RegistProductInfoActivity.this, false, 10,REQUEST_PICK_PICTURE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<String> photos = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);
        for(String s : photos){
            ProductImageView imageView = new ProductImageView(this);
            imageView.setImageFromUri(s);
            imageList.addView(imageView);
        }
    }
}
