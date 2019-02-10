package net.rainroot.ui_1st;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;

import java.util.Calendar;

public class add_alarm extends AppCompatActivity {
    /* test 2019-02-10 s */
    private TimePicker timePicker;
    private SwitchButton mListenerSb;
    /* test 2019-02-10 e */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        Intent intent = getIntent();
        /* test 2019-02-10 s */
        //mListenerSb = (SwitchButton) findViewById(R.id.sb_use_listener);
        //mListenerFinish = (TextView) findViewById(R.id.listener_finish);
        //mListenerFinish.setVisibility(mListenerSb.isChecked() ? View.VISIBLE : View.INVISIBLE);
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
        /* test 2019-02-10 e */


        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        //TextView textView = findViewById(R.id.textView);
        //textView.setText(message);
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
}
