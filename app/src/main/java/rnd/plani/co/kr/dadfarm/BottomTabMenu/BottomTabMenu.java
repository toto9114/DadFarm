package rnd.plani.co.kr.dadfarm.BottomTabMenu;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import rnd.plani.co.kr.dadfarm.R;

/**
 * Created by toto9114 on 2016-10-11.
 */

public class BottomTabMenu extends FrameLayout{

    public static final int MENU_SIZE = 5;
    public static final int HOME_MENU = 0;
    public static final int ORDER_LIST_MENU = 1;
    public static final int ADD_PRODUCT_MENU = 2;
    public static final int NOTIFICATION_MENU = 3;
    public static final int SETTING_MENU = 4;

    TextView titleView;
    ImageView iconView;
    public BottomTabMenu(Context context) {
        super(context);
        inflate(getContext(), R.layout.view_bottom_tab_menu,this);
        titleView = (TextView) findViewById(R.id.text_title);
        iconView = (ImageView) findViewById(R.id.image_icon);

    }

    public void setTabMenu(int menuType){
        int selectColor = ContextCompat.getColor(getContext(),R.color.select_tab);
        int unselectColor = ContextCompat.getColor(getContext(),R.color.unselect_tab);
        titleView.setTextColor(unselectColor);
        iconView.setColorFilter(unselectColor, PorterDuff.Mode.SRC_IN);
        switch (menuType){
            case HOME_MENU:
                titleView.setText(R.string.home_menu);
                titleView.setTextColor(selectColor);
                iconView.setImageResource(R.drawable.ic_home_black);
                iconView.setColorFilter(selectColor, PorterDuff.Mode.SRC_IN);
                break;
            case ORDER_LIST_MENU:
                titleView.setText(R.string.order_list_menu);
                iconView.setImageResource(R.drawable.ic_shopping_basket_black);
                break;
            case ADD_PRODUCT_MENU:
                titleView.setText(R.string.regist_product_menu);
                iconView.setImageResource(R.drawable.ic_add_circle_outline_black);
                break;
            case NOTIFICATION_MENU:
                titleView.setText(R.string.notification_menu);
                iconView.setImageResource(R.drawable.ic_notifications_none_black);
                break;
            case SETTING_MENU:
                titleView.setText(R.string.settings);
                iconView.setImageResource(R.drawable.ic_settings_black);
                break;
        }
    }
}
