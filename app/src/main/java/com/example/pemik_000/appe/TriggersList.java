package com.example.pemik_000.appe;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class TriggersList extends ActionBarActivity implements AdapterView.OnItemClickListener{

    ListView listTriggers;
    ArrayAdapter<String> adapterTrg;
    AddNewTask addNewTask;
    TaskService taskService;

    DialogFragment dlgFrg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triggers_list);
        taskService = new TaskService();

        String[] triggersArr = getResources().getStringArray(R.array.triggers);

        listTriggers = (ListView) findViewById(R.id.listTriggers);

        adapterTrg = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, triggersArr);

        listTriggers.setAdapter(adapterTrg);

        listTriggers.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_triggers_list, menu);
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
        //addNewTask = new AddNewTask();
        Intent intent;
        //addNewTask.triggerPos.add(position);
        switch (position) {
            case 0:
                // Location
                intent = new Intent(this, Location.class);
                startActivityForResult(intent,0);
                //finish();
                break;
            case 1:
                // Time frame
                Toast.makeText(this, "position: " + position + " Time frame", Toast.LENGTH_LONG).show();
                finish();
                break;
            case 2:
                // Buttery level
                intent = new Intent(this, BatteryLevel.class);
                startActivity(intent);
                finish();
                break;
            case 3:
                // Charging
                dlgFrg = new ChargingDialog();
                dlgFrg.show(getFragmentManager(), "dlgFrgCharging");
                break;
            case 4:
                //Wi-Fi
                dlgFrg = new WiFiConnectionDialog();
                dlgFrg.show(getFragmentManager(), "dlgFrgWiFiConnection");
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
                    //request Location
                    Intent intent = new Intent();
                    intent.putExtra("latitude",data.getDoubleExtra("latitude", 0));
                    intent.putExtra("longitude",data.getDoubleExtra("longitude", 0));
                    intent.putExtra("positionT", 0);
                    setResult(RESULT_OK, intent);

                    finish();
                    break;
                case 1:
                    //request Time frame
                    break;
                case 2:
                    //request Buttery level
                    break;
                case 3:
                    //request Charging
                    break;
                case 4:
                    //request Wi-Fi
                    break;
            }
        }
    }

}
