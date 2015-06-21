package com.example.pemik_000.appe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Notification extends ActionBarActivity implements View.OnClickListener {

    EditText etTitleNotification;
    EditText etTextNotification;
    Button btnDoneNotification;

    TaskManager taskManager = new TaskManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        etTextNotification = (EditText) findViewById(R.id.etTextNotification);
        etTitleNotification = (EditText) findViewById(R.id.etTitleNotification);
        btnDoneNotification = (Button) findViewById(R.id.btnDoneNotification);

        btnDoneNotification.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification, menu);
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

    @Override
    public void onClick(View v) {

        if (etTitleNotification.length() != 0 || etTextNotification.length() != 0) {
            Intent intent = new Intent();
            intent.putExtra("TitleNotification", etTitleNotification.getText().toString());
            intent.putExtra("TextNotification", etTextNotification.getText().toString());
            setResult(RESULT_OK, intent);

            finish();
        } else Toast.makeText(this, "Fill all fields please!", Toast.LENGTH_LONG).show();

    }
}
