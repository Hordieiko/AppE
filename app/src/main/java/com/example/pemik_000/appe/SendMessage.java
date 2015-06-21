package com.example.pemik_000.appe;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SendMessage extends ActionBarActivity implements View.OnClickListener {

    EditText editTextNumber, editTextMassage;
    AddNewTask addNewTask = new AddNewTask();
    Button btnDone;
    TaskManager taskManager = new TaskManager();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_massage);

        btnDone = (Button) findViewById(R.id.btnDone);
        btnDone.setOnClickListener(this);

        editTextMassage = (EditText) findViewById(R.id.editTextMassage);
        editTextNumber = (EditText) findViewById(R.id.editTextNumber);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDone:
                //запоминаем текст сообщения.
                taskManager.setTextSMS(editTextMassage.getText().toString());
                //запоминаем номер куда отправлять соощение
                taskManager.
                        setTelNumber(editTextNumber.getText().toString());
                finish();
                break;
            default:
                break;
        }
    }
}
