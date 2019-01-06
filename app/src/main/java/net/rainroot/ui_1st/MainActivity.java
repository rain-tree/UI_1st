package net.rainroot.ui_1st;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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

import java.io.File;

import com.beardedhen.androidbootstrap.BootstrapAlert;
import com.kyleduo.switchbutton.SwitchButton;

import android.os.Vibrator;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private FrameLayout mF_alarm_home;
    private FrameLayout mF_alarm_onetime;
    private FrameLayout mF_alarm_list;
    private FrameLayout mF_alarm_log;
    private FrameLayout mF_app_info;

    private SQLiteDatabase sqldb;

    private Vibrator mVibrator;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Animation animation = new AlphaAnimation(0,1);
            animation.setDuration(1000);
            mVibrator.vibrate(100);
            switch (item.getItemId()) {

                case R.id.navigation_alarm_home:

                    setTitle(R.string.alarm_home);
                    mF_alarm_home.setVisibility(View.VISIBLE);
                    mF_alarm_home.setAnimation(animation);
                    mF_alarm_list.setVisibility(View.INVISIBLE);
                    mF_alarm_onetime.setVisibility(View.INVISIBLE);
                    mF_alarm_log.setVisibility(View.INVISIBLE);
                    mF_app_info.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_alarm_onetime:
                    setTitle(R.string.alarm_onetime);
                    mF_alarm_home.setVisibility(View.INVISIBLE);
                    mF_alarm_list.setVisibility(View.VISIBLE);
                    mF_alarm_onetime.setVisibility(View.INVISIBLE);
                    mF_alarm_log.setVisibility(View.INVISIBLE);
                    mF_app_info.setVisibility(View.INVISIBLE);
                    mF_alarm_list.setAnimation(animation);
                    return true;
                case R.id.navigation_alarm_list:
                    setTitle(R.string.alarm_list);
                    mF_alarm_home.setVisibility(View.INVISIBLE);
                    mF_alarm_list.setVisibility(View.INVISIBLE);
                    mF_alarm_onetime.setVisibility(View.VISIBLE);
                    mF_alarm_log.setVisibility(View.INVISIBLE);
                    mF_app_info.setVisibility(View.INVISIBLE);
                    mF_alarm_onetime.setAnimation(animation);
                    return true;
                case R.id.navigation_alarm_log:
                    setTitle(R.string.alarm_log);
                    mF_alarm_home.setVisibility(View.INVISIBLE);
                    mF_alarm_list.setVisibility(View.INVISIBLE);
                    mF_alarm_onetime.setVisibility(View.INVISIBLE);
                    mF_alarm_log.setVisibility(View.VISIBLE);
                    mF_alarm_log.setAnimation(animation);
                    mF_app_info.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_app_info:
                    setTitle(R.string.app_info);
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

    private SQLiteDatabase init_database(){
        SQLiteDatabase db = null;
        File file = new File(getFilesDir(),"contact.db");

        try{
            db = SQLiteDatabase.openOrCreateDatabase(file,null);

        }catch (SQLiteException e){
            e.printStackTrace();
        }
        if(db == null){
            System.out.println("DB create failed."+file.getAbsolutePath());

        }
        return db;
    }

    private void init_table(){
        if(sqldb != null){
            String sqlCreateTbl =
                    "CREATE TABLE IF NOT EXISTS CONTACT_T (" +
                    "No"           + "INTEGER NOT NULL," +
                    "NAME "         + "TEXT," +
                    "PHONE "        + "TEXT," +
                    "OVER20 "       + "INTEGER" + ")" ;

            System.out.println(sqlCreateTbl) ;

            sqldb.execSQL(sqlCreateTbl) ;
        }
    }

    private void vibrate_test(){
        long[] pattern = {0,500,200,200,100,200,300,0,1000,30000};
        mVibrator.vibrate(pattern,01);
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mVibrator.cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqldb = init_database();
        init_table();

        mF_alarm_home = (FrameLayout) findViewById(R.id.F_alarm_home);
        mF_alarm_list = (FrameLayout) findViewById(R.id.F_alarm_list);
        mF_alarm_onetime = (FrameLayout) findViewById(R.id.F_alarm_onetime);
        mF_alarm_log = (FrameLayout) findViewById(R.id.F_alarm_log);
        mF_app_info = (FrameLayout) findViewById(R.id.F_app_info);

        mVibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        //vibrate_test();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_alarm_list);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}


