package rnd.plani.co.kr.dadfarm.Main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import rnd.plani.co.kr.dadfarm.Home.HomeFragment;
import rnd.plani.co.kr.dadfarm.R;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setText("홈").setIcon(R.drawable.test));
        tabLayout.addTab(tabLayout.newTab().setText("주문목록"));
        tabLayout.addTab(tabLayout.newTab().setText("상품등록"));
        tabLayout.addTab(tabLayout.newTab().setText("알림"));
        tabLayout.addTab(tabLayout.newTab().setText("설정"));

        tabLayout.setTabTextColors(ContextCompat.getColor(this,R.color.unselect_tab),ContextCompat.getColor(this,R.color.select_tab));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,new HomeFragment())
                    .commit();
        }
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
