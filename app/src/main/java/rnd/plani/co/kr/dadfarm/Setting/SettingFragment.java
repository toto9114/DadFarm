package rnd.plani.co.kr.dadfarm.Setting;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.digits.sdk.android.Digits;
import com.tangxiaolv.telegramgallery.GalleryActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.Request;
import rnd.plani.co.kr.dadfarm.Data.PersonalData;
import rnd.plani.co.kr.dadfarm.Data.ProfileData;
import rnd.plani.co.kr.dadfarm.Manager.NetworkManager;
import rnd.plani.co.kr.dadfarm.Manager.PropertyManager;
import rnd.plani.co.kr.dadfarm.R;
import rnd.plani.co.kr.dadfarm.SplashActivity;
import rnd.plani.co.kr.dadfarm.Utils;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {


    public SettingFragment() {
        // Required empty public constructor
    }

    PersonalData personalData;
    ImageView profileView;
    ImageView syncView;
    TextView syncDateView, phoneView;
    EditText firstNameView, lastNameView;
    private static final int REQUEST_PICK_PICTURE = 100;
    private static final int REQUEST_IMAGE_CROP = 200;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setPadding(0, Utils.getStatusBarHeight(), 0, 0);
        }
        firstNameView = (EditText) view.findViewById(R.id.edit_first_name);
        lastNameView = (EditText) view.findViewById(R.id.edit_last_name);
        profileView = (ImageView) view.findViewById(R.id.image_profile);
        syncDateView = (TextView) view.findViewById(R.id.text_sync_date);
        phoneView = (TextView) view.findViewById(R.id.text_phone);
        syncView = (ImageView) view.findViewById(R.id.image_sync);

        syncView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_rotate);
                syncView.startAnimation(anim);
                syncDateView.setText("동기화 중..");
                String firstName = firstNameView.getText().toString();
                String lastName = lastNameView.getText().toString();
                NetworkManager.getInstance().updateUserInfo(getContext(), personalData.id, firstName, lastName, new NetworkManager.OnResultListener<PersonalData>() {
                    @Override
                    public void onSuccess(Request request, PersonalData result) {
                        Toast.makeText(getContext(), result.first_name + result.last_name, Toast.LENGTH_SHORT).show();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        syncDateView.setText(String.format(getString(R.string.sync_complete_message), sdf.format(new Date())));
                        syncView.clearAnimation();
                    }

                    @Override
                    public void onFailure(Request request, int code, Throwable cause) {
                        syncDateView.setText("업데이트 실패");
                        syncView.clearAnimation();
                    }
                });
            }
        });

        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryActivity.openActivity(getActivity(), true, REQUEST_PICK_PICTURE);
            }
        });

        Button btn = (Button) view.findViewById(R.id.btn_logout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                AlertDialog dialog = builder.setTitle(R.string.logout_title)
                        .setMessage(R.string.logout_message)
                        .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton(R.string.logout_btn, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                NetworkManager.getInstance().revokeToken(getContext(), PropertyManager.getInstance().getPafarmToken(), new NetworkManager.OnResultListener<Boolean>() {
                                    @Override
                                    public void onSuccess(Request request, Boolean result) {
                                        if (result != null) {
                                            if (result) {
                                                Digits.clearActiveSession();
                                                PropertyManager.getInstance().setPafarmToken("");
                                                startActivity(new Intent(getContext(), SplashActivity.class));
                                                getActivity().finish();
                                            } else {

                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Request request, int code, Throwable cause) {

                                    }
                                });
                            }
                        }).setCancelable(false).create();
                dialog.show();
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(getContext(), R.color.blue_gray));
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(getContext(), R.color.red_gray));

            }
        });

        btn = (Button) view.findViewById(R.id.btn_delete_account);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                AlertDialog dialog = builder.setTitle(R.string.delete_account_title)
                        .setMessage(R.string.delete_account_message)
                        .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton(R.string.delete_account_btn, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                NetworkManager.getInstance().deleteUserInfo(getContext(), new NetworkManager.OnResultListener<Boolean>() {
                                    @Override
                                    public void onSuccess(Request request, Boolean result) {
                                        if (result) {
                                            Log.e("SettingFragment", "success");
                                            Digits.clearActiveSession();
                                            PropertyManager.getInstance().setPafarmToken("");
                                            startActivity(new Intent(getContext(), SplashActivity.class));
                                            getActivity().finish();
                                        } else {
                                            Log.e("SettingFragment", "fail");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Request request, int code, Throwable cause) {

                                    }
                                });
                            }
                        }).setCancelable(false).create();
                dialog.show();
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(getContext(), R.color.blue_gray));
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(getContext(), R.color.red_gray));
            }
        });
        btn = (Button) view.findViewById(R.id.btn_terms);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), TermsOrPrivacyActivity.class);
                i.putExtra(TermsOrPrivacyActivity.CONTENT_TYPE, TermsOrPrivacyActivity.TYPE_TERMS);
                startActivity(i);
            }
        });

        btn = (Button) view.findViewById(R.id.btn_policy);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), TermsOrPrivacyActivity.class);
                i.putExtra(TermsOrPrivacyActivity.CONTENT_TYPE, TermsOrPrivacyActivity.TYPE_PRIVACY);
                startActivity(i);
            }
        });
        initData();
        return view;
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

    long uid;

    private void initData() {
        NetworkManager.getInstance().getUserInfo(getContext(), new NetworkManager.OnResultListener<PersonalData>() {
            @Override
            public void onSuccess(Request request, PersonalData result) {
                if (result != null) {
                    personalData = result;
                    uid = result.id;
                    firstNameView.setText(result.first_name);
                    lastNameView.setText(result.last_name);
                    phoneView.setText(result.profile.phone_number);
                    Glide.with(getContext()).load(result.profile.image_url).into(profileView);
                }
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        syncDateView.setText(String.format(getString(R.string.sync_complete_message), PropertyManager.getInstance().getSyncDate()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_PICK_PICTURE:
                if (resultCode != Activity.RESULT_CANCELED) {
                    List<String> photos = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);
                    String path = photos.get(0);
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    Bitmap bmp = resizeBitmapImage(BitmapFactory.decodeFile(path, bmOptions), 256);
                    File file = bmpToFile(bmp);
                    NetworkManager.getInstance().setMyProfileImage(getContext(), file,
                            uid, new NetworkManager.OnResultListener<ProfileData>() {
                                @Override
                                public void onSuccess(Request request, ProfileData result) {
                                    if (result != null) {
                                        Log.e("SettingFragment", result.image_url);
                                        Glide.with(getContext()).load(result.image_url).into(profileView);
                                    }
                                }

                                @Override
                                public void onFailure(Request request, int code, Throwable cause) {

                                }
                            });
//                    Glide.with(this).load(file).into(profileView);

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
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
}
