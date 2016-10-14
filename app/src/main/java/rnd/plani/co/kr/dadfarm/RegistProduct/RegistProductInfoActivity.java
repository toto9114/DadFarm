package rnd.plani.co.kr.dadfarm.RegistProduct;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tangxiaolv.telegramgallery.GalleryActivity;

import java.util.List;

import rnd.plani.co.kr.dadfarm.CustomToolbar.BlackThemeTextToolbar;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnLeftMenuClickListener;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnRightMenuClickListener;
import rnd.plani.co.kr.dadfarm.R;
import rnd.plani.co.kr.dadfarm.Utils;

public class RegistProductInfoActivity extends AppCompatActivity {

    public static final String EDIT_TYPE = "type";
    public static final int TYPE_ADD_PRODUCT = 0;
    public static final int TYPE_EDIT_PRODUCT = 1;
    private static final int REQUEST_PICK_PICTURE = 100;
    private static final int REQUEST_PICK_CONTACT = 200;

    FrameLayout ImagePickerView;
    LinearLayout imageList, contactPickerView;
    ListView listView;
    ProductImageAdapter mAdapter;
    EditText firstNameView, lastNameView, phoneNumView;
    int editType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_product_info);
        editType = getIntent().getIntExtra(EDIT_TYPE, 0);
        BlackThemeTextToolbar toolbar = (BlackThemeTextToolbar) findViewById(R.id.toolbar);
        toolbar.setToolbar("취소", "상품등록", "완료");
        if (editType == TYPE_EDIT_PRODUCT) {
            toolbar.setTitle("상품");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
        toolbar.setOnLeftMenuClickListener(new OnLeftMenuClickListener() {
            @Override
            public void OnLeftMenuClick() {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistProductInfoActivity.this);
                AlertDialog dialog = builder.setTitle(R.string.cancel_edit_product_title)
                        .setMessage(R.string.cancel_edit_product_message)
                        .setPositiveButton(R.string.no_btn, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).setCancelable(false).create();
                dialog.show();
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(RegistProductInfoActivity.this, R.color.blue_gray));
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(RegistProductInfoActivity.this, R.color.red_gray));
            }
        });

        toolbar.setOnRightMenuClickListener(new OnRightMenuClickListener() {
            @Override
            public void OnRightMenuClick() {
                finish();
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        mAdapter = new ProductImageAdapter();
        listView.setAdapter(mAdapter);
        ImagePickerView = (FrameLayout) findViewById(R.id.btn_pick);
        contactPickerView = (LinearLayout) findViewById(R.id.contact_picker);
        imageList = (LinearLayout) findViewById(R.id.linear_image_list);
        firstNameView = (EditText) findViewById(R.id.edit_first_name);
        lastNameView = (EditText) findViewById(R.id.edit_last_name);
        phoneNumView = (EditText) findViewById(R.id.edit_phone_number);
        ImagePickerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent, REQUEST_PICK_PICTURE);
                GalleryActivity.openActivity(RegistProductInfoActivity.this, false, 10, REQUEST_PICK_PICTURE);
            }
        });

        contactPickerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, REQUEST_PICK_CONTACT);
            }
        });
        mAdapter.setOnCloseClickListener(new OnCloseClickListener() {
            @Override
            public void onCloseClick(String imageUri) {
                mAdapter.remove(imageUri);
                refreshImageList();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK_PICTURE && resultCode != RESULT_CANCELED) {
            if (data != null) {
                List<String> photos = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);
                mAdapter.addAll(photos);
                refreshImageList();
            }
        }
        if (requestCode == REQUEST_PICK_CONTACT && resultCode != RESULT_CANCELED) {
            Uri contactData = data.getData();
            Cursor c = getContentResolver().query(contactData, null, null, null, null);
            if (c.moveToFirst()) {
                String name = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNum = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Log.e("RegistProduct", "name : " + name + ", num : " + phoneNum);
                firstNameView.setText(name);
                phoneNumView.setText(phoneNum);
            }
        }
    }

    private void refreshImageList() {
        Utils.setListViewHeightBasedOnChildren(listView);
    }
}
