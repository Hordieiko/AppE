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


    // Location
    public double UserLatitude = 0;
    public double UserLongitude = 0;
    private final double radius = 0.0001;

    // ИКС
    //Latitude 46.45948
    //Longitude 30.75205


    // Notification
    private String titleNotification;
    private String textNotification;



    ArrayList<Integer> triggerPos = new ArrayList<>();
    ArrayList<Integer> actionPos = new ArrayList<>();


    EditText nameTask;
    ListView listTriggersTask;
    ListView listActionsTask;

    ArrayList<String> actionsArrT;
    ArrayList<String> triggersArrT;

    ArrayAdapter<String> adapterActT;
    ArrayAdapter<String> adapterTrgT;

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

    @Override
    protected void onStart() {
        super.onStart();
    }

    //добавляем новый триггер в listView Triggers задание
    private void addNewTrgInList() {
        for (Integer itemTrg : triggerPos) {
            switch (itemTrg) {
                case 0:
                    adapterTrgT.add("Location");
                    break;
                case 1:
                    adapterTrgT.add("Time frame");
                    break;
                case 2:
                    adapterTrgT.add("Buttery level");
                    break;
                case 3:
                    adapterTrgT.add("Charging");
                    break;
                case 4:
                    adapterTrgT.add("Wi-Fi");
                    break;
                default:
                    break;
            }
        }
    }

    //добавляем новое действие в listView Actions задание
    public void addNewActInList() {
        for (Integer itemAct : actionPos) {
            switch (itemAct) {
                case 0:
                    adapterActT.add("Send SMS");
                    break;
                case 1:
                    adapterActT.add("Notification");
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

    //задание сформировано
    public void clickDone(View v) {
        if (validForm(nameTask, triggersArrT, actionsArrT)) {

            Intent intent = new Intent(this, TaskService.class);
            for (Integer item : triggerPos) {
                switch (item) {
                    case 0: //Location
                        intent.putExtra("latitude", UserLatitude);
                        intent.putExtra("longitude", UserLongitude);
                        break;
                    case 1:
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

            for (Integer item : actionPos) {
                switch (item) {
                    case 0:
                        break;
                    case 1: //Notification
                        intent.putExtra("titleNotification", titleNotification);
                        intent.putExtra("textNotification", textNotification);
                        break;
                    default:
                        break;
                }
            }
            intent.putExtra("triggerPos", triggerPos);
            intent.putExtra("actionPos", actionPos);
            startService(intent);

//-------------------------- Ответ для MainActivity ----------------------------
            Intent resIntent = new Intent();
            intent.putExtra("TITlE", nameTask.getText().toString());
            setResult(RESULT_OK, resIntent);
            finish();
        }
    }

    // проверка на пустоту формы
    private boolean validForm(EditText nameTask,
                              ArrayList<String> triggersArrT, ArrayList<String> actionsArrT) {
        if (nameTask.length() == 0) {
            Toast.makeText(this, "Enter task name", Toast.LENGTH_LONG).show();
            return false;
        } else if (triggersArrT.isEmpty()) {
            Toast.makeText(this, "Add triggers", Toast.LENGTH_LONG).show();
            return false;
        } else if (actionsArrT.isEmpty()) {
            Toast.makeText(this, "Add action", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    //добавить Триггер либо Дествие
    public void clickAdd(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.addTriggers:
                intent = new Intent(this, TriggersList.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.addActions:
                intent = new Intent(this, ActionsList.class);
                startActivityForResult(intent, 2);
                break;
            default:
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        } else if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:  //вернулись данные о триггере

                    int positionT = data.getIntExtra("positionT", 5);
                    if (positionT < 5) {

                        //Нужно обработать данные в зависимости от тригера.

                        switch (positionT) {
                            case 0: //Location
                                UserLatitude = data.getDoubleExtra("latitude", 0);
                                UserLongitude = data.getDoubleExtra("longitude", 0);

                                triggerPos.add(positionT);
                                adapterTrgT.add("Location");
                                break;
                            case 1: //Time frame

                                triggerPos.add(positionT);
                                adapterTrgT.add("Time frame");
                                break;
                            case 2: //Buttery level
                                triggerPos.add(positionT);
                                adapterTrgT.add("Buttery level");
                                break;
                            case 3: //Charging
                                triggerPos.add(positionT);
                                adapterTrgT.add("Charging");
                                break;
                            case 4: //Wi-Fi

                                adapterTrgT.add("Wi-Fi");
                                break;
                            default:
                                break;
                        }
                        triggerPos.add(positionT);
                    }
                    break;
                case 2: //вернулись данные о действие

                    int positionA = data.getIntExtra("positionA", 22);
                    if (positionA < 22) {
                        switch (positionA) {
                            case 0:

                                adapterActT.add("Send SMS");
                                break;
                            case 1: //Send Notification
                                titleNotification = data.getStringExtra("TitleNotification");
                                textNotification = data.getStringExtra("TextNotification");

                                adapterActT.add("Notification");
                                break;
                            case 2:
                                break;
                            default:
                                break;
                        }
                        actionPos.add(positionA);
                    }
                    break;
                default:
                    break;
            }
        }
        adapterTrgT.notifyDataSetChanged();
        adapterActT.notifyDataSetChanged();
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
