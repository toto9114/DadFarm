package rnd.plani.co.kr.dadfarm.Main;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import rnd.plani.co.kr.dadfarm.BottomTabMenu.BottomTabMenu;
import rnd.plani.co.kr.dadfarm.Home.HomeFragment;
import rnd.plani.co.kr.dadfarm.Notify.NotificationFragment;
import rnd.plani.co.kr.dadfarm.OrderList.OrderListFragment;
import rnd.plani.co.kr.dadfarm.R;
import rnd.plani.co.kr.dadfarm.RegistProduct.RegistProductFragment;
import rnd.plani.co.kr.dadfarm.Setting.SettingFragment;

public class MainActivity extends AppCompatActivity {


    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.removeAllTabs();
        setTabMenu();
//        tabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.unselect_tab), ContextCompat.getColor(this, R.color.select_tab));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int selectColor = ContextCompat.getColor(MainActivity.this, R.color.select_tab);
                TextView titleView = (TextView) tab.getCustomView().findViewById(R.id.text_title);
                ImageView iconView = (ImageView) tab.getCustomView().findViewById(R.id.image_icon);
                titleView.setTextColor(selectColor);
                iconView.setColorFilter(selectColor, PorterDuff.Mode.SRC_IN);

                switch (tab.getPosition()) {
                    case 0:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new HomeFragment())
                                .commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new OrderListFragment())
                                .commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new RegistProductFragment())
                                .commit();
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new NotificationFragment())
                                .commit();
                        break;
                    case 4:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new SettingFragment())
                                .commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                int unselectColor = ContextCompat.getColor(MainActivity.this, R.color.unselect_tab);
                TextView titleView = (TextView) tab.getCustomView().findViewById(R.id.text_title);
                ImageView iconView = (ImageView) tab.getCustomView().findViewById(R.id.image_icon);
                titleView.setTextColor(unselectColor);
                iconView.setColorFilter(unselectColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new HomeFragment())
                    .commit();
        }
    }

    private void setTabMenu() {
        for(int i = 0 ; i < BottomTabMenu.MENU_SIZE ; i++){
            BottomTabMenu v = new BottomTabMenu(this);
            v.setTabMenu(i);
            tabLayout.addTab(tabLayout.newTab().setCustomView(v));
        }
//        tabLayout.addTab(tabLayout.newTab().setCustomView(new BottomTabMenu(this)));
//        tabLayout.addTab(tabLayout.newTab().setCustomView(new BottomTabMenu(this)));
//        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.view_order_list_menu));
//        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.view_bottom_tab_menu));
//        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.view_bottom_tab_menu));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
