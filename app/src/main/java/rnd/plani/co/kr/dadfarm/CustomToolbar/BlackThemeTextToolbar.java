package rnd.plani.co.kr.dadfarm.CustomToolbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by RND on 2016-09-26.
 */

public class BlackThemeTextToolbar extends FrameLayout {
    private OnLeftMenuClickListener leftMenuClickListener;

    public void setOnLeftMenuClickListener(OnLeftMenuClickListener listener) {
        leftMenuClickListener = listener;
    }

    private OnRightMenuClickListener rightMenuClickListener;

    public void setOnRightMenuClickListener(OnRightMenuClickListener listener) {
        rightMenuClickListener = listener;
    }

    TextView leftMenuView, titleView, rightMenuView;

    public BlackThemeTextToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.view_black_theme_text_toolbar, this);
        leftMenuView = (TextView) findViewById(R.id.text_left_menu);
        titleView = (TextView) findViewById(R.id.text_title);
        rightMenuView = (TextView) findViewById(R.id.text_right_menu);

        leftMenuView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(leftMenuClickListener!=null) {
                    leftMenuClickListener.OnLeftMenuClick();
                }
            }
        });

        rightMenuView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rightMenuClickListener != null) {
                    rightMenuClickListener.OnRightMenuClick();
                }
            }
        });

    }

    public void setToolbar(String leftMenu, String title, String rightMenu) {
        leftMenuView.setText(leftMenu);
        titleView.setText(title);
        rightMenuView.setText(rightMenu);
    }
    public void setTitle(String title){
        titleView.setText(title);
    }
}
