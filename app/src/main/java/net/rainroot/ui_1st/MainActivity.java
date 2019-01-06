package net.rainroot.ui_1st;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private FrameLayout mF_alarm_home;
    private FrameLayout mF_alarm_onetime;
    private FrameLayout mF_alarm_list;
    private FrameLayout mF_alarm_log;
    private FrameLayout mF_app_info;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Animation animation = new AlphaAnimation(0,1);
            animation.setDuration(1000);
            switch (item.getItemId()) {
                case R.id.navigation_alarm_home:
                    mF_alarm_home.setVisibility(View.VISIBLE);
                    mF_alarm_home.setAnimation(animation);
                    mF_alarm_list.setVisibility(View.INVISIBLE);
                    mF_alarm_onetime.setVisibility(View.INVISIBLE);
                    mF_alarm_log.setVisibility(View.INVISIBLE);
                    mF_app_info.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_alarm_onetime:
                    mF_alarm_home.setVisibility(View.INVISIBLE);
                    mF_alarm_list.setVisibility(View.VISIBLE);
                    mF_alarm_onetime.setVisibility(View.INVISIBLE);
                    mF_alarm_log.setVisibility(View.INVISIBLE);
                    mF_app_info.setVisibility(View.INVISIBLE);
                    mF_alarm_list.setAnimation(animation);
                    return true;
                case R.id.navigation_alarm_list:
                    mF_alarm_home.setVisibility(View.INVISIBLE);
                    mF_alarm_list.setVisibility(View.INVISIBLE);
                    mF_alarm_onetime.setVisibility(View.VISIBLE);
                    mF_alarm_log.setVisibility(View.INVISIBLE);
                    mF_app_info.setVisibility(View.INVISIBLE);
                    mF_alarm_onetime.setAnimation(animation);
                    return true;
                case R.id.navigation_alarm_log:
                    mF_alarm_home.setVisibility(View.INVISIBLE);
                    mF_alarm_list.setVisibility(View.INVISIBLE);
                    mF_alarm_onetime.setVisibility(View.INVISIBLE);
                    mF_alarm_log.setVisibility(View.VISIBLE);
                    mF_alarm_log.setAnimation(animation);
                    mF_app_info.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_app_info:
                    mF_alarm_home.setVisibility(View.INVISIBLE);
                    mF_alarm_list.setVisibility(View.INVISIBLE);
                    mF_alarm_onetime.setVisibility(View.INVISIBLE);
                    mF_alarm_log.setVisibility(View.INVISIBLE);
                    mF_app_info.setVisibility(View.VISIBLE);
                    mF_app_info.setAnimation(animation);
                    return true;
                    /*
                case R.id.navigation_app_debug:
                    mTextMessage.setText(R.string.app_debug);
                    return true;
*/
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mF_alarm_home = (FrameLayout) findViewById(R.id.F_alarm_home);
        mF_alarm_list = (FrameLayout) findViewById(R.id.F_alarm_list);
        mF_alarm_onetime = (FrameLayout) findViewById(R.id.F_alarm_onetime);
        mF_alarm_log = (FrameLayout) findViewById(R.id.F_alarm_log);
        mF_app_info = (FrameLayout) findViewById(R.id.F_app_info);



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_alarm_list);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}


