package rnd.plani.co.kr.dadfarm.CustomToolbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-20.
 */
public class IntroToolbar extends FrameLayout {

    private OnLeftMenuClickListener leftMenuClickListener;
    public void setOnLeftMenuClickListener(OnLeftMenuClickListener listener){
        leftMenuClickListener = listener;
    }

    TextView leftMenuView, rightMenuVIew, titleView;
    public IntroToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.view_intro_toolbar,this);
        titleView = (TextView) findViewById(R.id.text_title);
        leftMenuView = (TextView) findViewById(R.id.text_left_menu);
        rightMenuVIew = (TextView) findViewById(R.id.text_right_menu);

        leftMenuView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(leftMenuClickListener != null){
                    leftMenuClickListener.OnLeftMenuClick();
                }
            }
        });
    }

    public void setToolbar(String leftMenu, String title){
        leftMenuView.setText(leftMenu);
        titleView.setText(title);
    }
}
