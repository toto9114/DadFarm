package rnd.plani.co.kr.dadfarm;

import android.app.Application;
import android.content.Context;

/**
 * Created by RND on 2016-09-12.
 */
public class MyApplication extends Application{
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
    public static Context getContext(){
        return context;
    }
}
