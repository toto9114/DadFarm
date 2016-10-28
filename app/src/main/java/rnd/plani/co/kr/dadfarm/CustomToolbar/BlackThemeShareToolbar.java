package rnd.plani.co.kr.dadfarm.CustomToolbar;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import rnd.plani.co.kr.dadfarm.R;
import rnd.plani.co.kr.dadfarm.Utils;

/**
 * Created by RND on 2016-09-23.
 */
public class BlackThemeShareToolbar extends FrameLayout {
    private OnLeftMenuClickListener leftMenuClickListener;

    public void setOnLeftMenuClickListener(OnLeftMenuClickListener listener) {
        leftMenuClickListener = listener;
    }

    private OnRightMenuClickListener rightMenuClickListener;

    public void setOnRightMenuClickListener(OnRightMenuClickListener listener) {
        rightMenuClickListener = listener;
    }

    TextView leftMenuView, titleView;
    RelativeLayout toolbar;
    ImageView shareView;

    public BlackThemeShareToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.view_black_theme_toolbar, this);
        toolbar = (RelativeLayout) findViewById(R.id.toolbar_layout);
        leftMenuView = (TextView) findViewById(R.id.text_left_menu);
        titleView = (TextView) findViewById(R.id.text_title);
        shareView = (ImageView) findViewById(R.id.image_right_menu);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setPadding(0, Utils.getStatusBarHeight(), 0, 0);
        }
        leftMenuView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftMenuClickListener != null) {
                    leftMenuClickListener.OnLeftMenuClick();
                }
            }
        });

        shareView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightMenuClickListener != null) {
                    rightMenuClickListener.OnRightMenuClick();
                }
            }
        });
    }

    public void setToolbar(String leftMenu, String title, boolean isSharetMenu) {
        leftMenuView.setText(leftMenu);
        titleView.setText(title);
        if (isSharetMenu) {
            shareView.setVisibility(VISIBLE);
        } else {
            shareView.setVisibility(GONE);
        }
    }
}
