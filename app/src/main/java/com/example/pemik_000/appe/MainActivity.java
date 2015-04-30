package com.example.pemik_000.appe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ActionBarActivity {

    ImageView imgAddNewTask;
    TextView textAddNewTask;

    ListView listTask;
    SimpleAdapter adapter;
    HashMap<String, Object> hm;
    private ArrayList<HashMap<String, Object>> itemList;
    private static final String TITLE = "Task name"; // Верхний текст
    private static final String STATUS = "description"; // ниже главного

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgAddNewTask = (ImageView) findViewById(R.id.imgAddNewTask);
        textAddNewTask = (TextView) findViewById(R.id.textAddNewTask);

        listTask = (ListView) findViewById(R.id.listTask);

        // создаем массив списков
        itemList = new ArrayList<HashMap<String, Object>>();

        //adapter, что бы привязть массив к ListView
        adapter = new SimpleAdapter(this, itemList,
                R.layout.item_list, new String[]{TITLE, STATUS},
                new int[]{R.id.textView, R.id.textView2});

        listTask.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(itemList.isEmpty()){
            listTask.setVisibility(View.GONE);
            imgAddNewTask.setVisibility(View.VISIBLE);
            textAddNewTask.setVisibility(View.VISIBLE);
        } else {
            listTask.setVisibility(View.VISIBLE);
            imgAddNewTask.setVisibility(View.GONE);
            textAddNewTask.setVisibility(View.GONE);
        }
    }

    private void addNewTask(String nameTask) {

        hm = new HashMap<>();
        hm.put(TITLE, nameTask); // Название
        hm.put(STATUS, "Ready"); // Описание
        itemList.add(hm);

        adapter.notifyDataSetChanged();
    }

    public void onclickplus(View v) {
        switch (v.getId()) {
            case R.id.imgAddNewTask:
            case R.id.textAddNewTask:
                startActivityAddNewTask();
                break;
            default:
                break;
        }
    }

    public void startActivityAddNewTask() {
        Intent intent = new Intent(this, AddNewTask.class);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (data == null)
            return;
        if(resultCode==RESULT_OK){
            String nameTask = data.getStringExtra("TITlE");
            addNewTask(nameTask);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()){
            case R.id.addTask:
                startActivityAddNewTask();
                break;
            case R.id.map:
                Intent intent = new Intent(this, Map.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}