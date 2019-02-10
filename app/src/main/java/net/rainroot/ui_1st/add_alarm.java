package net.rainroot.ui_1st;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;
import com.mcsoft.timerangepickerdialog.RangeTimePickerDialog;

import java.util.Calendar;

public class add_alarm extends AppCompatActivity implements RangeTimePickerDialog.ISelectedTime {
    /* test 2019-02-10 s */
    private TimePicker timePicker;
    private SwitchButton mListenerSb;
    private TimePickerDialog.OnTimeSetListener listener;
    /* test 2019-02-10 e */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        Intent intent = getIntent();


        /* test 2019-02-10 s */
        listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
// 설정버튼 눌렀을 때
                Toast.makeText(getApplicationContext(), hourOfDay + "시 " + minute + "분", Toast.LENGTH_SHORT).show();
            }
        };

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

    public void alarm_name_btn(View view){
        String[] multiChoiceItems = getResources().getStringArray(R.array.dialog_multi_choice_array);
        final boolean[] checkedItems = {false, false, false, false};
        new AlertDialog.Builder(this)
                .setTitle("Select your favourite movies")
                .setMultiChoiceItems(multiChoiceItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int index, boolean isChecked) {
                        Log.d("MainActivity", "clicked item index is " + index);
                    }
                })
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void alarm_time_range_btn(View view){
        RangeTimePickerDialog dialog = new RangeTimePickerDialog();
        dialog.newInstance();
        dialog.setIs24HourView(true);
        dialog.setRadiusDialog(20);
        dialog.setTextTabStart("Start");
        dialog.setTextTabEnd("End");
        dialog.setTextBtnPositive("Accept");
        dialog.setTextBtnNegative("Close");
        dialog.setValidateRange(false);
        dialog.setColorBackgroundHeader(R.color.colorPrimary);
        dialog.setColorBackgroundTimePickerHeader(R.color.colorPrimary);
        dialog.setColorTextButton(R.color.colorPrimaryDark);
        dialog.enableMinutes(true);
        dialog.setStartTabIcon(R.drawable.ic_access_time_black_24dp);
        dialog.setEndTabIcon(R.drawable.ic_timelapse_black_24dp);
        FragmentManager fragmentManager = getFragmentManager();
        //dialog.setTargetFragment(R.id.F_alarm_home, 1234);
        dialog.show(fragmentManager, ".......................");
    }
    public  void alarm_time_step_btn(View view){
        Log.d("rainroot","alarm_time_step_btn");
        TimePickerDialog dialog = new TimePickerDialog(this,android.R.style.Theme_Holo_Light_DialogWhenLarge
                , listener, 15, 24, true);
        dialog.show();
    }

    @Override
    public void onSelectedTime(int hourStart, int minuteStart, int hourEnd, int minuteEnd) {
        Toast.makeText(this, "Start: "+hourStart+":"+minuteStart+"\nEnd: "+hourEnd+":"+minuteEnd, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                if (data.getExtras().containsKey(RangeTimePickerDialog.HOUR_START))
                {
                    int hourStart = data.getExtras().getInt(RangeTimePickerDialog.HOUR_START);
                    int hourEnd = data.getExtras().getInt(RangeTimePickerDialog.HOUR_END);
                    int minuteStart = data.getExtras().getInt(RangeTimePickerDialog.MINUTE_START);
                    int minuteEnd = data.getExtras().getInt(RangeTimePickerDialog.MINUTE_END);
                    // Use the returned value
                    //Toast.makeText(getActivity(), "Time start:"+hourStart+":"+minuteStart+"\nUntil: "+hourEnd+":"+minuteEnd, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    /* test 2019-02-10 e */
}
