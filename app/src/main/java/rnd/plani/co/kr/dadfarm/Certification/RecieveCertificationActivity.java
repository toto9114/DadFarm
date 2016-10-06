package rnd.plani.co.kr.dadfarm.Certification;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import rnd.plani.co.kr.dadfarm.CustomToolbar.IntroToolbar;
import rnd.plani.co.kr.dadfarm.CustomToolbar.OnLeftMenuClickListener;
import rnd.plani.co.kr.dadfarm.R;

public class RecieveCertificationActivity extends AppCompatActivity {

    IntroToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve_certification);
        toolbar = (IntroToolbar) findViewById(R.id.toolbar);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,new RecieveCertFragment())
                    .commit();
        }
        toolbar.setOnLeftMenuClickListener(new OnLeftMenuClickListener() {
            @Override
            public void OnLeftMenuClick() {
                if(getSupportFragmentManager().getBackStackEntryCount()>0) {
                    getSupportFragmentManager().popBackStack();     //InsertCertFragment에서 클릭 시
                }else{
                    finish();       //RecieveCertFragment에서 클릭 시
                }
            }
        });

    }

    public void changeInsertCert(String phone){
        InsertCertFragment f= new InsertCertFragment();
        Bundle args = new Bundle();
        args.putString(InsertCertFragment.EXTRA_PHONE_NUMBER,phone);
        f.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container,f)
                .commit();
    }

    public void changeFindFriends(){
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container,new FindFriendsFragment())
                .commit();
    }

    public void changeInsertName(){
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container,new InsertNameFragment())
                .commit();
    }

    public void changeGetContact(){
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container,new GetContactFragment())
                .commit();
    }
    public void setToolbar(String leftMenu, String title){
        toolbar.setToolbar(leftMenu, title);
    }
}
