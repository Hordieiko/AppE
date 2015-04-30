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
import android.widget.TextView;


public class ActionsList extends ActionBarActivity implements AdapterView.OnItemClickListener {

    ListView listActions;
    ArrayAdapter<String> adapterAct;

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
        Intent intent = new Intent(this, AddNewTask.class);
        intent.putExtra("nameAct", ((TextView) view).getText());
        setResult(RESULT_OK, intent);
        finish();
    }
}
