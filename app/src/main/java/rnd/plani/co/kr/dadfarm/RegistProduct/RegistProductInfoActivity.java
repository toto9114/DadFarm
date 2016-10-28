package rnd.plani.co.kr.dadfarm.RegistProduct;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.tangxiaolv.telegramgallery.GalleryActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.Request;
import rnd.plani.co.kr.dadfarm.CustomToolbar.BlackThemeTextToolbar;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnLeftMenuClickListener;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnRightMenuClickListener;
import rnd.plani.co.kr.dadfarm.Data.PersonalData;
import rnd.plani.co.kr.dadfarm.Data.ProductData;
import rnd.plani.co.kr.dadfarm.Data.UserListResultData;
import rnd.plani.co.kr.dadfarm.Manager.NetworkManager;
import rnd.plani.co.kr.dadfarm.R;
import rnd.plani.co.kr.dadfarm.Utils;

public class RegistProductInfoActivity extends AppCompatActivity {

    public static final String EDIT_TYPE = "type";
    public static final String EXTRA_PRODUCT_DATA = "product";
    public static final int TYPE_ADD_PRODUCT = 0;
    public static final int TYPE_EDIT_PRODUCT = 1;
    private static final int REQUEST_PICK_PICTURE = 100;
    private static final int REQUEST_PICK_CONTACT = 200;

    FrameLayout ImagePickerView;
    LinearLayout imageList, contactPickerView;
    ListView listView;
    ProductImageAdapter mAdapter;
    EditText firstNameView, lastNameView, phoneNumView, hasRelationView, isRelationView, titleView, contentView, addressView, productNameView, priceView, bankNameView, bankAccountView, bankAccountHolderView;
    Button deleteBtn;
    int editType;
    ProductData productData = null;
    InputMethodManager imm;

    boolean isSaveClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_product_info);
        editType = getIntent().getIntExtra(EDIT_TYPE, 0);
        productData = (ProductData) getIntent().getSerializableExtra(EXTRA_PRODUCT_DATA);
        BlackThemeTextToolbar toolbar = (BlackThemeTextToolbar) findViewById(R.id.toolbar);
        toolbar.setToolbar("취소", "상품등록", "완료");
        if (editType == TYPE_EDIT_PRODUCT) {
            toolbar.setTitle("상품수정");
        }
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        toolbar.setOnLeftMenuClickListener(new OnLeftMenuClickListener() {
            @Override
            public void OnLeftMenuClick() {
                imm.hideSoftInputFromWindow(contentView.getWindowToken(), 0);
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
                isSaveClick = true;
                imm.hideSoftInputFromWindow(contentView.getWindowToken(), 0);
                getPafarmUser();
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
        hasRelationView = (EditText) findViewById(R.id.edit_has_relation);
        isRelationView = (EditText) findViewById(R.id.edit_is_relation);
        titleView = (EditText) findViewById(R.id.edit_title);
        contentView = (EditText) findViewById(R.id.edit_content);
        addressView = (EditText) findViewById(R.id.edit_address);
        productNameView = (EditText) findViewById(R.id.edit_product_name);
        priceView = (EditText) findViewById(R.id.edit_price);
        bankNameView = (EditText) findViewById(R.id.edit_bank_name);
        bankAccountView = (EditText) findViewById(R.id.edit_bank_account);
        bankAccountHolderView = (EditText) findViewById(R.id.edit_bank_account_holder);
        deleteBtn = (Button) findViewById(R.id.btn_delete);
        ImagePickerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryActivity.openActivity(RegistProductInfoActivity.this, false, 10, REQUEST_PICK_PICTURE);
            }
        });

        contactPickerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, REQUEST_PICK_CONTACT);
            }
        });
        mAdapter.setOnCloseClickListener(new OnCloseClickListener() {
            @Override
            public void onCloseClick(final String imageUri) {
                if (editType == TYPE_ADD_PRODUCT) {
                    Log.e("RegistProductInfo", "Add Product: delete picture success");
                    mAdapter.remove(imageUri);
                    refreshImageList();
                } else {
                    NetworkManager.getInstance().deleteProductImage(RegistProductInfoActivity.this, imageUri, new NetworkManager.OnResultListener<Boolean>() {
                        @Override
                        public void onSuccess(Request request, Boolean isSuccess) {
                            if (isSuccess) {
                                mAdapter.remove(imageUri);
                                refreshImageList();
                                Log.e("RegistProductInfo", "delete picture success");
                            } else {
                                Log.e("RegistProductInfo", "delete picture fail");
                                //요청실패
                            }
                        }

                        @Override
                        public void onFailure(Request request, int code, Throwable cause) {

                        }
                    });

                }

            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct();
            }
        });
        initData();
    }

    private void initData() {
        if (editType != TYPE_ADD_PRODUCT) {
            deleteBtn.setVisibility(View.VISIBLE);
            long manager_id = productData.manager.id;
            long seller_id = productData.seller.id;
            lastNameView.setText(productData.seller.last_name);
            firstNameView.setText(productData.seller.first_name);
            phoneNumView.setText(productData.seller.profile.phone_number);
            hasRelationView.setText(productData.relationships.get(manager_id + "-" + seller_id));
            isRelationView.setText(productData.relationships.get(seller_id + "-" + manager_id));
            titleView.setText(productData.title);
            mAdapter.addAll(productData.images);
            refreshImageList();
            contentView.setText(productData.description);
            addressView.setText(productData.address);
            productNameView.setText(productData.name);
            priceView.setText(productData.price);
            bankNameView.setText(productData.bank_name);
            bankAccountView.setText(productData.bank_account);
            bankAccountHolderView.setText(productData.bank_account_holder);
        } else {
            deleteBtn.setVisibility(View.GONE);
        }
    }

    private void deleteProduct() {
        NetworkManager.getInstance().deleteProduct(this, productData.id, new NetworkManager.OnResultListener<Boolean>() {
            @Override
            public void onSuccess(Request request, Boolean result) {
                if (result) {
                    finish();
                } else {
                    Toast.makeText(RegistProductInfoActivity.this, "fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
    }

    private void uploadProduct(long sellerId) {
        ProductData productData = new ProductData();
        productData.title = titleView.getText().toString();
        productData.description = contentView.getText().toString();
        productData.name = productNameView.getText().toString();
        productData.price = priceView.getText().toString();
        productData.address = addressView.getText().toString();
        productData.seller_id = sellerId;
        productData.bank_name = bankNameView.getText().toString();
        productData.bank_account = bankAccountView.getText().toString();
        productData.bank_account_holder = bankAccountHolderView.getText().toString();

        if (editType == TYPE_ADD_PRODUCT) {
            NetworkManager.getInstance().uploadProduct(RegistProductInfoActivity.this, productData, new NetworkManager.OnResultListener<ProductData>() {
                @Override
                public void onSuccess(Request request, ProductData result) {
                    if (result != null) {
                        for (int i = 0; i < mAdapter.getCount(); i++) {
                            String imagePath = (String) mAdapter.getItem(i);
                            Bitmap bmp = resizeBitmapImage(BitmapFactory.decodeFile(imagePath), 512);
                            File file = bmpToFile(bmp);
                            NetworkManager.getInstance().uploadProductImage(RegistProductInfoActivity.this, result, file, new NetworkManager.OnResultListener<ProductData>() {
                                @Override
                                public void onSuccess(Request request, ProductData result) {
                                    if (result != null) {
                                        finish();
                                    }
                                    isSaveClick = false;
                                }

                                @Override
                                public void onFailure(Request request, int code, Throwable cause) {
                                    isSaveClick = false;
                                }
                            });
                        }
                    }

                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {
                    isSaveClick = false;
                }
            });
        } else {
            //상품수정하기
            NetworkManager.getInstance().updateProduct(RegistProductInfoActivity.this, this.productData.id, productData, new NetworkManager.OnResultListener<ProductData>() {
                @Override
                public void onSuccess(Request request, ProductData result) {
                    if (result != null) {
                        for (int i = 0; i < mAdapter.getCount(); i++) {
                            String imagePath = (String) mAdapter.getItem(i);
                            if (!imagePath.startsWith("http")) {
                                Bitmap bmp = resizeBitmapImage(BitmapFactory.decodeFile(imagePath), 512);
                                File file = bmpToFile(bmp);
                                NetworkManager.getInstance().uploadProductImage(RegistProductInfoActivity.this, result, file, new NetworkManager.OnResultListener<ProductData>() {
                                    @Override
                                    public void onSuccess(Request request, ProductData result) {
                                        if (result != null) {
                                            finish();
                                        }
                                        isSaveClick = false;
                                    }

                                    @Override
                                    public void onFailure(Request request, int code, Throwable cause) {
                                        isSaveClick = false;
                                    }
                                });
                            }
                        }
                    }
                    finish();
                }

                @Override
                public void onFailure(Request request, int code, Throwable cause) {
                    isSaveClick = false;
                }
            });
        }
    }


    int page = 0;
    boolean isPafarmUser = false;

    private void getPafarmUser() {
        page++;
        final String firstName = firstNameView.getText().toString();
        final String lastName = lastNameView.getText().toString();
        final String phoneNum = phoneNumView.getText().toString();
        NetworkManager.getInstance().getUserInfo(RegistProductInfoActivity.this, page, firstName, lastName, phoneNum, new NetworkManager.OnResultListener<UserListResultData>() {
            @Override
            public void onSuccess(Request request, UserListResultData result) {
                if (result != null) {
                    if (result.results != null) {
                        if (result.results.size() == 0) {
                            if (result.next != null) {
                                Log.e("RegistProductInfo", "현재페이지에는 찾는 유저가 없지만 다음페이지가 있다.");
                                getPafarmUser();//현재페이지에는 찾는 유저가 없지만 다음페이지가 있다.
                            } else {
                                isPafarmUser = false;
                                page = 0;
                                //찾는 유저도 없고 더이상 page도 없다.
                                String hasRelation = hasRelationView.getText().toString();
                                String isRelation = isRelationView.getText().toString();
                                NetworkManager.getInstance().createUser(RegistProductInfoActivity.this, firstName, lastName, phoneNum, hasRelation, isRelation, new NetworkManager.OnResultListener<PersonalData>() {
                                    @Override
                                    public void onSuccess(Request request, PersonalData result) {
                                        if (result != null) {
                                            Log.e("RegistProductInfo", "create User");
                                            uploadProduct(result.id);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Request request, int code, Throwable cause) {

                                    }
                                });
                                Log.e("RegistProductInfo", "찾는 유저도 없고 더이상 page도 없다.");
                                return;
                            }
                        } else {
                            isPafarmUser = true;
                            page = 0;
                            Log.e("RegistProductInfo", "유저가 있다.");
                            uploadProduct(result.results.get(0).id);
                            //유저가 있다.
                        }
                    }
                }
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {
                page = 0;
            }
        });
        Log.e("RegistProductInfo", "page : " + page);
    }


    public Bitmap resizeBitmapImage(
            Bitmap bmpSource, int maxResolution) {
        int iWidth = bmpSource.getWidth();      //비트맵이미지의 넓이
        int iHeight = bmpSource.getHeight();     //비트맵이미지의 높이
        int newWidth = iWidth;
        int newHeight = iHeight;
        float rate = 0.0f;

        //이미지의 가로 세로 비율에 맞게 조절
        if (iWidth > iHeight) {
            if (maxResolution < iWidth) {
                rate = maxResolution / (float) iWidth;
                newHeight = (int) (iHeight * rate);
                newWidth = maxResolution;
            }
        } else {
            if (maxResolution < iHeight) {
                rate = maxResolution / (float) iHeight;
                newWidth = (int) (iWidth * rate);
                newHeight = maxResolution;
            }
        }

        return Bitmap.createScaledBitmap(
                bmpSource, newWidth, newHeight, true);
    }

    private static final String FILE_NAME = "image.png";

    private File bmpToFile(Bitmap bmp) {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "pafarm");
        File saveFile = new File(dir, FILE_NAME);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            FileOutputStream fos = new FileOutputStream(saveFile);
            if (bmp != null) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                bmp.recycle();
            }
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return saveFile;
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
