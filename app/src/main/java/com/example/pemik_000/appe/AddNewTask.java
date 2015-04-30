package com.example.pemik_000.appe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class AddNewTask extends ActionBarActivity {

    EditText nameTask;
    ListView listTriggersTask;
    ListView listActionsTask;

    ArrayList<String> actionsArrT;
    ArrayList<String> triggersArrT;

    ArrayAdapter<String> adapterActT;
    ArrayAdapter<String> adapterTrgT;

    final int REQUEST_ACTIONS = 1;
    final int REQUEST_TRIGGER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);

        //имя задания
        nameTask = (EditText) findViewById(R.id.nameTask);

        //массивы триггеров и действий
        actionsArrT = new ArrayList<>();
        triggersArrT = new ArrayList<>();

        //находим списки триггеров и действий
        listActionsTask = (ListView) findViewById(R.id.listActionsTask);
        listTriggersTask = (ListView) findViewById(R.id.listTriggersTask);

        //создаем аддаптеры для списков
        adapterActT = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, actionsArrT);
        adapterTrgT = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, triggersArrT);

        //присваеваем аддаптеры спискам
        listActionsTask.setAdapter(adapterActT);
        listTriggersTask.setAdapter(adapterTrgT);

    }

    //добавляем новый триггер в задание
    private void addNewTrg(String nameTrg) {
        triggersArrT.add(nameTrg);
        adapterTrgT.notifyDataSetChanged();
    }

    //добавляем новое действие в задание
    public void addNewAct(String nameAct) {
        actionsArrT.add(nameAct);
        adapterActT.notifyDataSetChanged();
    }

    //задание сформировано
    public void clickDone(View v) {
        if (nameTask.length() == 0) {
            Toast.makeText(this, "Enter task name", Toast.LENGTH_LONG).show();
        } else if (triggersArrT.isEmpty()) {
            Toast.makeText(this, "Add triggers", Toast.LENGTH_LONG).show();
        } else if (actionsArrT.isEmpty()) {
            Toast.makeText(this, "Add action", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent();
            intent.putExtra("TITlE", nameTask.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    public void clickAdd(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.addTriggers:
                intent = new Intent(this, TriggersList.class);
                startActivityForResult(intent, REQUEST_TRIGGER);
                break;
            case R.id.addActions:
                intent = new Intent(this, ActionsList.class);
                startActivityForResult(intent, REQUEST_ACTIONS);
                break;
            default:
                break;
        }
    }


    @Override // заполняем ListView выбранными Actions и Triggers
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_ACTIONS:
                    addNewAct(data.getStringExtra("nameAct"));
                    break;
                case REQUEST_TRIGGER:
                    addNewTrg(data.getStringExtra("nameTrg"));
                    break;
                default:
                    break;
            }
            // если вернулось не ОК
        } else {
            Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_task, menu);
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
}
