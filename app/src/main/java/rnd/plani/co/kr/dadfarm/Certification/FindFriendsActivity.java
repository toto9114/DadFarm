package rnd.plani.co.kr.dadfarm.Certification;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.digits.sdk.android.ContactsCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.models.Contacts;
import com.digits.sdk.android.models.DigitsUser;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

import java.util.List;

import rnd.plani.co.kr.dadfarm.CustomToolbar.IntroToolbar;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnLeftMenuClickListener;
import rnd.plani.co.kr.dadfarm.Main.MainActivity;
import rnd.plani.co.kr.dadfarm.R;

public class FindFriendsActivity extends AppCompatActivity {

    TextView statusView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);
        IntroToolbar toolbar = (IntroToolbar) findViewById(R.id.toolbar);
        statusView = (TextView) findViewById(R.id.text_status);
        toolbar.setToolbar("닫기", "친구찾기");
        toolbar.setOnLeftMenuClickListener(new OnLeftMenuClickListener() {
            @Override
            public void OnLeftMenuClick() {
                finish();
            }
        });
        Button btn = (Button) findViewById(R.id.btn_find_friends);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusView.setText("동기화중..");

                Digits.findFriends(new ContactsCallback<Contacts>() {
                    @Override
                    public void success(Result<Contacts> result) {
                        List<DigitsUser> users = result.data.users;
                        statusView.setText(getContactCount()+"개의 연락처에서\n"+users.size()
                        +"명의 아빠농장 친구를 찾았습니다.");
                        Intent i = new Intent(FindFriendsActivity.this, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void failure(TwitterException exception) {

                    }
                });
            }
        });
    }
    String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
    String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " NOT NULL AND " +
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " != ''";
    String[] selectionArgs = null;
    String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

    private int getContactCount() {
        ContentResolver resolver = getContentResolver();

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        Cursor c = resolver.query(uri, projection, selection, selectionArgs, sortOrder);

        int count = 0;
        Log.e("FindFriends", ""+count);
        if (c.moveToFirst()) {
            while (c.moveToNext()) {
                String name = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Log.e("GetContact","name: " + name + ","+ "phone: "+phoneNumber);
                count++;
            }
        }
        return count;
    }
}
