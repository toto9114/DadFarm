package rnd.plani.co.kr.dadfarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.digits.sdk.android.ContactsUploadService;
import com.digits.sdk.android.models.ContactsUploadResult;

public class MyResultReceiver extends BroadcastReceiver {
    public MyResultReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        if (ContactsUploadService.UPLOAD_COMPLETE.equals(intent.getAction())) {
            ContactsUploadResult result = intent
                    .getParcelableExtra(ContactsUploadService.UPLOAD_COMPLETE_EXTRA);
            int count = result.totalCount;
            // Post success notification
        } else {
            // Post failure notification
        }
    }
}
