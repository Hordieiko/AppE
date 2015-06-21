package com.example.pemik_000.appe;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

public class BatteryLevel extends ActionBarActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    SeekBar seekBar;
    TextView tvSetSB, tvSeekBar;
    CheckBox checkBox50, checkBox35, checkBox25, checkBox10;
    Button btnDoneBL;

    TaskManager taskManager = new TaskManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_level);

        btnDoneBL = (Button) findViewById(R.id.btnDoneBL);
        btnDoneBL.setOnClickListener(this);

        checkBox10 = (CheckBox) findViewById(R.id.checkBox10);
        checkBox25 = (CheckBox) findViewById(R.id.checkBox25);
        checkBox35 = (CheckBox) findViewById(R.id.checkBox35);
        checkBox50 = (CheckBox) findViewById(R.id.checkBox50);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);

        tvSetSB = (TextView) findViewById(R.id.tvSetSB);
        tvSeekBar = (TextView) findViewById(R.id.tvSeekBar);

    }

    public void onStart() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();
    }


    // save USER value
    public void setBatteryLevel() {
        if (checkBox10.isChecked()) {
            taskManager.setBatteryLevel(10);
        } else if (checkBox25.isChecked()) {
            taskManager.setBatteryLevel(25);
        } else if (checkBox35.isChecked()) {
            taskManager.setBatteryLevel(35);
        } else if (checkBox50.isChecked()) {
            taskManager.setBatteryLevel(50);
        } else {
            taskManager.setBatteryLevel(Integer.parseInt(tvSetSB.getText().toString()));
        }
    }


    // обработчики SeekBar
    //---------------------------------------------------------------------------------
    //уведомляет об изменении положения ползунка
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        tvSetSB.setText(String.valueOf(seekBar.getProgress()) + "%");
    }

    //уведомляет о том, что пользователь начал перемещать ползунок
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        tvSeekBar.setText("Battery level  <  ");
        tvSetSB.setText(String.valueOf(seekBar.getProgress()) + "%");
    }

    //уведомляет о том, что пользователь закончил перемещать ползунок
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        tvSetSB.setText(String.valueOf(seekBar.getProgress()) + "%");
        if (seekBar.getProgress() == 0) {
            tvSeekBar.setText("");
            tvSetSB.setText("");
        }
    }
    //---------------------------------------------------------------------------------


    // Click DONE
    @Override
    public void onClick(View v) {
        setBatteryLevel();
        finish();
    }

}
