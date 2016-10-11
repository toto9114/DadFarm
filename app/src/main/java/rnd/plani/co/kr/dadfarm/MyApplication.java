package rnd.plani.co.kr.dadfarm;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.digits.sdk.android.Digits;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;

/**
 * Created by RND on 2016-09-12.
 */
public class MyApplication extends Application{
    private static Context context;
    private static final String TWITTER_KEY = "xnl5tlhPiHklyCPCsWhvwev80";
    private static final String TWITTER_SECRET = "rULlIkzTgW0gsSCf860evzp6dI6wHd1jstc8pRKZsjk4xGTEov";
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Digits.Builder digitBuilder = new Digits.Builder().withTheme(R.style.CustomDigitsTheme);
        Fabric.with(this, new TwitterCore(authConfig), digitBuilder.build(), new Crashlytics());
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
    public static Context getContext(){
        return context;
    }
}
