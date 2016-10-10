package rnd.plani.co.kr.dadfarm.Setting;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Request;
import rnd.plani.co.kr.dadfarm.Data.AuthData;
import rnd.plani.co.kr.dadfarm.Data.MyPersonalData;
import rnd.plani.co.kr.dadfarm.Manager.NetworkManager;
import rnd.plani.co.kr.dadfarm.Manager.PropertyManager;
import rnd.plani.co.kr.dadfarm.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {


    public SettingFragment() {
        // Required empty public constructor
    }

    MyPersonalData personalData;
    ImageView profileView;
    ImageView syncView;
    TextView syncDateView, phoneView;
    EditText firstNameView, lastNameView;
    private static final int REQUEST_GALLERY = 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        firstNameView = (EditText) view.findViewById(R.id.edit_first_name);
        lastNameView = (EditText) view.findViewById(R.id.edit_last_name);
        profileView = (ImageView) view.findViewById(R.id.image_profile);
        syncDateView = (TextView) view.findViewById(R.id.text_sync_date);
        phoneView = (TextView) view.findViewById(R.id.text_phone);
        syncView = (ImageView) view.findViewById(R.id.image_sync);

        syncView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameView.getText().toString();
                String lastName = lastNameView.getText().toString();
                NetworkManager.getInstance().updateUserInfo(getContext(), personalData.id, firstName, lastName, new NetworkManager.OnResultListener<MyPersonalData>() {
                    @Override
                    public void onSuccess(Request request, MyPersonalData result) {
                        Toast.makeText(getContext(), result.first_name + result.last_name, Toast.LENGTH_SHORT).show();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        syncDateView.setText(sdf.format(new Date()) + "동기화 됨");
                    }

                    @Override
                    public void onFailure(Request request, int code, Throwable cause) {

                    }
                });
            }
        });

        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_GALLERY);
            }
        });

        Button btn = (Button) view.findViewById(R.id.btn_logout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkManager.getInstance().revokeToken(getContext(), PropertyManager.getInstance().getPafarmToken(), new NetworkManager.OnResultListener<AuthData>() {
                    @Override
                    public void onSuccess(Request request, AuthData result) {
                        if (result != null) {

                        }
                    }

                    @Override
                    public void onFailure(Request request, int code, Throwable cause) {

                    }
                });
            }
        });

        initData();
        return view;
    }


    private void initData() {
        NetworkManager.getInstance().getUserInfo(getContext(), new NetworkManager.OnResultListener<MyPersonalData>() {
            @Override
            public void onSuccess(Request request, MyPersonalData result) {
                if (result != null) {
                    personalData = result;
                    firstNameView.setText(result.first_name);
                    lastNameView.setText(result.last_name);
                    phoneView.setText(result.profile.phone_number);
                }
            }

            @Override
            public void onFailure(Request request, int code, Throwable cause) {

            }
        });
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        syncDateView.setText(PropertyManager.getInstance().getSyncDate() + "동기화 됨");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GALLERY:
                if (resultCode != Activity.RESULT_CANCELED) {
                    Uri selectedImageUri = data.getData();
                    Cursor c = getActivity().getContentResolver().query(selectedImageUri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                    if (!c.moveToNext()) {
                        c.close();
                        return;
                    }
                    String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                    c.close();
                    Glide.with(this).load(selectedImageUri).into(profileView);
                }
                break;
        }
    }
}
