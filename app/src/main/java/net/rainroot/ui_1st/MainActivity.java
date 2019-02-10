package net.rainroot.ui_1st;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.MimeTypeMap;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import com.beardedhen.androidbootstrap.BootstrapAlert;
import com.kyleduo.switchbutton.SwitchButton;

import android.os.Vibrator;
import android.widget.TimePicker;
import android.widget.Toast;

import net.rainroot.service.Event;
import net.rainroot.service.EventCall;
import net.rainroot.service.musicPlayer;
import net.rainroot.service.playerEvents;
import net.rainroot.service.servicesBinder;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "net.rainroot.ui_1st.MESSAGE";

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
    private FloatingActionButton mfab;

    private Context mContext;
    /*
    public void sendMessage(View view) {
        Intent intent = new Intent(this, add_alarm.class);
        EditText editText = (EditText) findViewById(R.id.textView);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    */
    /* test 2019-02-10 s */
    private TimePicker timePicker;
    private SwitchButton mListenerSb;
    /* test 2019-02-10 e */

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
        ;;
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

        mfab = (FloatingActionButton)findViewById(R.id.add_alarm_btn);
        mfab.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mp.start();
                                        mVibrator.vibrate(100);
                                        Intent intent = new Intent(getApplicationContext(), add_alarm.class);
                                        //intent.putExtra(EXTRA_MESSAGE, message);
                                        startActivity(intent);
                                    }
                                });

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

        //getMusicData();
        //getAllAudioFromDevice(this);
        checkPermission();

        /* test 2019-02-10 s */
        //mListenerSb = (SwitchButton) findViewById(R.id.sb_use_listener);
        //mListenerFinish = (TextView) findViewById(R.id.listener_finish);
        //mListenerFinish.setVisibility(mListenerSb.isChecked() ? View.VISIBLE : View.INVISIBLE);
        /*
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        findViewById(R.id.alram_timetick_btn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar calendar = Calendar.getInstance();
                if (android.os.Build.VERSION.SDK_INT >= 23) {
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),timePicker.getHour(), timePicker.getMinute(), 0);
                } else {
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                }
                setAlarm(calendar.getTimeInMillis());
            }
        });
        mListenerSb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //mListenerFinish.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
            }
        });
        */
        /* test 2019-02-10 e */

    }

    /* test 2019-02-10 s */
    private void setAlarm(long time) {
        //getting the alarm manager
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //creating a new intent specifying the broadcast receiver
        Intent i = new Intent(this, MyAlarm.class);
        //creating a pending intent using the intent
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        //setting the repeating alarm that will be fired every day
        am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();
    }
    /* test 2019-02-10 e */

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

    private static final int SERVICE_PERMISSION_GROUP = 9130;
    private static final int SERVICE_PERMISSION_ALLOW = 9131;
    private static final int SERVICE_PERMISSION_DNEY = 9132;
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        int ret = SERVICE_PERMISSION_ALLOW;
        int i=0;
        for(i = 0 ; i < permissions.length;i++) {
            Log.d("5. find_music","--------------------"+i);
            System.out.println(" find_music requestcode " + SERVICE_PERMISSION_GROUP + " grantResult[0] " + grantResults[i] + " perm string[] " + permissions[i]);
            if(grantResults[i] < 0){
                ret = SERVICE_PERMISSION_DNEY;
                break;
            }
        }
        //setResult(ret);
        //finish();
        //test
    }
    void checkPermission(){
        String[] str_permissions = new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INSTALL_PACKAGES,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WAKE_LOCK,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.WRITE_SECURE_SETTINGS,
                Manifest.permission.READ_CONTACTS
        };

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("1. find_music","--------------------");
            ArrayList<String> notGrantPermissions = new ArrayList<>();
            Log.d("2. find_music","--------------------");
            for (String perm : str_permissions) {
                Log.d("3. find_music","--------------------");
                if (this.checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED) {
                    Log.d("4. find_music","--------------------");
                    notGrantPermissions.add(perm);

                }
            }
            if(notGrantPermissions.isEmpty() == false) {
                requestPermissions(notGrantPermissions.toArray(new String[]{}), SERVICE_PERMISSION_GROUP);
            }else{
                //setResult(SERVICE_PERMISSION_ALLOW);
                //finish();
            }
        }else{
            //setResult(SERVICE_PERMISSION_ALLOW);
            //finish();
        }

/* 서비스 구동..
        //servInt = new Intent(getBaseContext(),musicPlayer.class);
        //startService(servInt);
        //bindService(servInt,Sc,BIND_ADJUST_WITH_ACTIVITY);
*/
        //find_music();
        //queryFiles(this);
    }

    void find_music(){
        Log.d("1. find_music","--------------------");
        String path="";
        Cursor music = getContentResolver().query(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{ "_id", "_data" },
                "is_music != 0",
                null, null);

        while ( music.moveToNext() )
        {
            path = music.getString(1).toLowerCase();
            Log.d("2. find_music "," PATH: "+path);
            //if ( path.contains(query) ) {
                //adapter.add(music.getString(0) + ": " + music.getString(1));
            //}
        }
    }

    public static void queryFiles(Context context) {

        // External 스토리지의 URI 획득
        final Uri uri = MediaStore.Files.getContentUri("external");
        //ID, 파일명, mimeType, 파일크기 을 가져오도록 설정
        final String[] projection = new String[]
                {
                    MediaStore.Files.FileColumns._ID,
                    MediaStore.Files.FileColumns.DISPLAY_NAME,
                    MediaStore.Files.FileColumns.MIME_TYPE,
                    MediaStore.Files.FileColumns.SIZE
                };
        //확장자가 txt인 mimeType을 쿼리
        final String selection = MediaStore.Files.FileColumns.MIME_TYPE + "=?";
        final String[] selectionArgs = new String[] {
                    MimeTypeMap.getSingleton().getMimeTypeFromExtension("mp3")
                };

        // 쿼리 수행 후, 컬럼명, 값 출력
        Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
        while (cursor != null && cursor.moveToNext()) {
            int columnCount = cursor.getColumnCount();
            for (int i = 0 ; i < columnCount ; i++ ) {
                Log.d("queryFiles",cursor.getColumnName(i) + " : " + cursor.getString(i));
            }
            Log.d("queryFiles","----------------------------");
        }
    }

    /*
    public void asset_load(int resourceId) {
        mResourceId = resourceId;
        AssetFileDescriptor assetFileDescriptor =
                mContext.getResources().openRawResourceFd(mResourceId);
        try {
            logToUI("load() {1. setDataSource}");
            mMediaPlayer.setDataSource(assetFileDescriptor);
        } catch (Exception e) {
            logToUI(e.toString());
        }

        try {
            logToUI("load() {2. prepare}");
            mMediaPlayer.prepare();
        } catch (Exception e) {
            logToUI(e.toString());
        }
        initSeekbar();
    }
    */

    public List<AudioModel> getAllAudioFromDevice(final Context context) {
        final List<AudioModel> tempAudioList = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AudioColumns.DATA,MediaStore.Audio.AudioColumns.TITLE ,MediaStore.Audio.AudioColumns.ALBUM, MediaStore.Audio.ArtistColumns.ARTIST,};
        Cursor c = context.getContentResolver().query(uri, projection, MediaStore.Audio.Media.DATA + " like ? ", new String[]{"%utm%"}, null);

        if (c != null) {
            while (c.moveToNext()) {
                // Create a model object.
                AudioModel audioModel = new AudioModel();

                String path = c.getString(0);   // Retrieve path.
                String name = c.getString(1);   // Retrieve name.
                String album = c.getString(2);  // Retrieve album name.
                String artist = c.getString(3); // Retrieve artist name.

                // Set data to the model object.
                audioModel.setaName(name);
                audioModel.setaAlbum(album);
                audioModel.setaArtist(artist);
                audioModel.setaPath(path);

                Log.e("Name :" + name, " Album :" + album);
                Log.e("Path :" + path, " Artist :" + artist);

                // Add the model object to the list .
                tempAudioList.add(audioModel);
            }
            c.close();
        }

        // Return the list.
        return tempAudioList;
    }

    private void getMusicData(){
        String[] projection = {
                MediaStore.Audio.Media.IS_MUSIC,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media._ID
        };

        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor = contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Audio.Media.TITLE+"ASC"
        );

        if(cursor != null){
            try{
                if(cursor.getInt(0) != 0){
                    Uri sAtrworkUri = Uri.parse("content://media/external/audio/albumart");
                    Uri uri = ContentUris.withAppendedId(sAtrworkUri,Integer.valueOf(cursor.getString(1)));

                    Log.d("LIST-rain"," Title: "+cursor.getString(2)+" Singer "+cursor.getString(3)+" AlumId "+cursor.getString(1)+" MusicId "+cursor.getString(4));
                    //data.setMusicImg(uri);
                    //data.setMusicTitle(cursor.getString(2));
                    //data.setSinger(cursor.getString(3));
                    //data.setAlbumId(cursor.getString(1));
                    //data.setMusicId(cursor.getString(4));

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}


