package com.example.pemik_000.appe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ActionsList extends ActionBarActivity implements AdapterView.OnItemClickListener {

    ListView listActions;
    ArrayAdapter<String> adapterAct;
    AddNewTask addNewTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions_list);

        String[] actionsArr = getResources().getStringArray(R.array.actions);

        listActions = (ListView) findViewById(R.id.listActions);

        adapterAct = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, actionsArr);

        listActions.setAdapter(adapterAct);

        listActions.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actions_list, menu);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        addNewTask = new AddNewTask();
        Intent intent;
        //addNewTask.actionPos.add(position);
        switch (position) {
            case 0: //Send SMS
                intent = new Intent(this, SendMessage.class);
                startActivity(intent);
                break;
            case 1: //Send Notification
                intent = new Intent(this, Notification.class);
                startActivityForResult(intent, 1);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                break;
        }
    }

    //ПАРАМЕТРЫ
    //с какого Activity с каким результатом и что
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        } else if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    break;
                case 1: //request Send Notification
                    Intent intent = new Intent();
                    intent.putExtra("TitleNotification", data.getStringExtra("TitleNotification"));
                    intent.putExtra("TextNotification", data.getStringExtra("TextNotification"));
                    intent.putExtra("positionA", 1);
                    setResult(RESULT_OK, intent);

                    finish();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
                    break;
            }
        }

    }
}
