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


public class TriggersList extends ActionBarActivity implements AdapterView.OnItemClickListener{

    ListView listTriggers;
    ArrayAdapter<String> adapterTrg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triggers_list);

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
        Intent intent = new Intent(this, AddNewTask.class);
        intent.putExtra("nameTrg", ((TextView) view).getText());
        setResult(RESULT_OK, intent);
        finish();
    }
}
