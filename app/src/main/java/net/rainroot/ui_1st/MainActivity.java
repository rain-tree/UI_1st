package net.rainroot.ui_1st;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import com.beardedhen.androidbootstrap.BootstrapAlert;
import com.kyleduo.switchbutton.SwitchButton;

import android.os.Vibrator;

import net.rainroot.service.Event;
import net.rainroot.service.EventCall;
import net.rainroot.service.musicPlayer;
import net.rainroot.service.playerEvents;
import net.rainroot.service.servicesBinder;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private RelativeLayout mF_alarm_home;
    //private ListView mF_alarm_home;
    private FrameLayout mF_alarm_onetime;
    private FrameLayout mF_alarm_list;
    private ListView mL_alarm_list;
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
            mp.start();
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

        mF_alarm_home = (RelativeLayout) findViewById(R.id.F_alarm_home);
        //mF_alarm_home = (ListView)findViewById(R.id.F_alarm_home);
        mF_alarm_list = (FrameLayout) findViewById(R.id.F_alarm_list);
        mF_alarm_onetime = (FrameLayout) findViewById(R.id.F_alarm_onetime);
        mF_alarm_log = (FrameLayout) findViewById(R.id.F_alarm_log);
        mF_app_info = (FrameLayout) findViewById(R.id.F_app_info);

        mVibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        //vibrate_test();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_alarm_list);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //FrameLayout framelayout = (FrameLayout)findViewById(R.id.F_alarm_home);
        ListView listView = (ListView)findViewById(R.id.L_alarm_list);
        ArrayList<ListAlarmitem> data = new ArrayList<>();
        ListAlarmitem st_1 = new ListAlarmitem(R.drawable.ic_3d_rotation_black_24dp,"알람 1 ");
        ListAlarmitem st_2 = new ListAlarmitem(R.drawable.ic_access_alarms_black_24dp,"알람 2");
        ListAlarmitem st_3 = new ListAlarmitem(R.drawable.ic_access_alarms_black_24dp,"알람 3");
        ListAlarmitem st_4 = new ListAlarmitem(R.drawable.ic_access_alarms_black_24dp,"알람 3");
        ListAlarmitem st_5 = new ListAlarmitem(R.drawable.ic_access_alarms_black_24dp,"알람 3");

        data.add(st_1);
        data.add(st_2);
        data.add(st_3);
        data.add(st_4);
        data.add(st_5);
        ListAlarmAdapter adapter = new ListAlarmAdapter(this,R.layout.item_alarm,data);
        //framelayout.setAdapter
        //framelayout.setAdapter(adapter);
        listView.setAdapter((ListAdapter) adapter);

        mp = MediaPlayer.create(getBaseContext(), R.raw.tick); // '틱' 효과음..
        checkPermission();
    }
    MediaPlayer mp;
    public static MainActivity  ef;

    Intent servInt;
    public musicPlayer MusicPlayer;

    public Event playerEvent;


    public int Event_onPause = 101;
    public int Event_onResume = 102;
    public int Event_onDestroy = 103;
    public int Event_onBind = 104;


    public void clickPlay(){
        mp.start();
    }

    EventCall playerCall = new EventCall(null) {
        @Override
        public void onCall(final int arg) {
            if(arg == playerEvents.PLAYER_EXIT){
                MainActivity.this.finish();
            }else{
                MainActivity.ef.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        playerEvent.trigger(arg);
                    }
                });
            }
        }
    };
    ServiceConnection Sc = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            servicesBinder mLocalBinder = (servicesBinder)service;
            MusicPlayer = mLocalBinder.getServices();
            MusicPlayer.handler.mEvent.addEvent(playerCall);
//            playerEvent.trigger(Event_onBind);
            //MH.removeView(hider);
            //Log.i("My", "Massage bined..!");
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        checkPermission();
    }

    void checkPermission(){

        //if (ContextCompat.checkSelfPermission(MainActivity.ef, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        //    ActivityCompat.requestPermissions( MainActivity.ef, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        //} else{
            servInt = new Intent(getBaseContext(),musicPlayer.class);
            startService(servInt);
            bindService(servInt,Sc,BIND_ADJUST_WITH_ACTIVITY);
            //init();
        //}
    }
}


