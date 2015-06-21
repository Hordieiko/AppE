package com.example.pemik_000.appe;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TaskService extends Service implements LocationListener {

    ArrayList<Integer> triggerPos;// = new ArrayList<>();
    ArrayList<Integer> actionPos;// = new ArrayList<>();

    Intent myIntent;

    TaskManager taskManager = new TaskManager();
    String nameTask;

    WifiManager wifiManager;

    BroadcastReceiver receiver;
    private int statusCharging;

    private int chargedPct;

    Location location;
    LocationManager locationManager;

    private final double radius = 0.0001;

    // ИКС
    //Latitude 46.45948
    //Longitude 30.75205

    final String LOG_TAG = "myLogs";

    private Timer mTimer = new Timer();
    private TimerTask timerTask;
    final Handler uiHandler = new Handler();

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");

        wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }


    public int onStartCommand(Intent intent, int flags, int startId) {


        myIntent = intent;

        triggerPos = intent.getIntegerArrayListExtra("triggerPos");
        actionPos = intent.getIntegerArrayListExtra("actionPos");

        Log.d("Task", "startTrigger1");

        timerTask = new TimerTask() {

            @Override
            public void run() {
                Log.d("Task", "startTrigger2");
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Task", "startTrigger3");
                        startTrigger(triggerPos);
                    }
                });
            }
        }; // delay 0ms, repeat in 3000ms
        mTimer.schedule(timerTask,3000,30000);


        //startTrigger(triggerPos);



        registerReceiver(receiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        Log.d(LOG_TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");

        locationManager.removeUpdates(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.asd
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void startTrigger(ArrayList<Integer> triggerPos) {
        Log.d("Task", "startTriggerImp");
        for (Integer item : triggerPos) {
            switch (item) {
                case 0: //Location
                    double UserLatitude = myIntent.getDoubleExtra("latitude", 0);
                    double UserLongitude = myIntent.getDoubleExtra("longitude", 0);
                    checkLocation(UserLatitude, UserLongitude);
                    break;
                case 1: //Time frame
                    break;
                case 2: //Battery level
                    break;
                default:
                    break;
            }
        }
    }


    // Выбоор действия--------------------------------------
    private void sortActions(ArrayList<Integer> actionPos) {
        for (Integer item : actionPos) {
            switch (item) {
                case 0:
                    //Send SMS
                    break;
                case 1: //Notification
                    Log.d("Task", "sortActions");
                    String titleNotification = myIntent.getStringExtra("titleNotification");
                    String textNotification = myIntent.getStringExtra("textNotification");
                    startNotification(titleNotification, textNotification);
                    break;
                case 2:
                    //Ringer Volume
                    break;
                case 3:
                    //Brightness
                    break;
                case 4:
                    //GPS
                    break;
                case 5:
                    //Wi-Fi
                    break;
                case 6:
                    //Bluetooth
                    break;
                case 7:
                    //Airplane Mode
                    break;
                case 8:
                    //Launch Website
                    break;
                default:
                    break;
            }
        }
    }

    //-----------------------Notification----------------------------------------------------

    private void startNotification(String titleNotification, String textNotification) {
        // Идентификатор уведомления
        int NOTIFY_ID = 101;

        Log.d("Task", "startNotification");

        Intent notificationIntent = new Intent(); // можно задать куда переходить при нажатии на уведомление
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentIntent(contentIntent)
                 // автоудаление после нажатия на него
                .setTicker("New Notification!") // текст в строке состояния
                .setSmallIcon(android.R.drawable.ic_menu_myplaces) // Иконка маленькая
                .setContentTitle(titleNotification) // Заголовок уведомления
                .setContentText(textNotification)// Текст уведомленимя
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFY_ID, builder.build());
    }

    //----------------------------------------------------------------------------


    // Проверка LOCATION. Заданого пользователем с настоящим
    public void checkLocation(double Latitude, double Longitude) {
        Log.d("Task", "inCheckLocation");
        // 1) каждую минуту сверяем свое местоположение с заданым

        if (location == null) {
            Toast.makeText(this, "Location Off", Toast.LENGTH_LONG).show();
            return;
        } else if (Latitude > location.getLatitude() - radius &&
                    Latitude <= location.getLatitude() + radius &&
                       Longitude > location.getLongitude() - radius &&
                           Longitude <= location.getLongitude() + radius) {
            // 2) если в радиусе, запускаем метод "что делать дальше" -
            // смотрим какие были заданы Actions - действия, для этого задания
            Log.d("Task", "inCheckLocationYES");
            sortActions(actionPos);
        }

    }


    // LOCATION----------------------------------------------------------------------
    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        location = locationManager.getLastKnownLocation(provider);
    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    //--------------------------------------------------------------------------------


    // Send SMS
    public void sendSMS(String telNumber, String textMassage) {
        SmsManager.getDefault().sendTextMessage(telNumber, null, textMassage, null, null);
    }

    //------------------------------------------------------------------------------


    // Charging -----------------------------------------------------------

    //item: 0 - Charging; 1 - Discharging. Что выбрал пользователь.
    public void checkCharging(int item) {
        InitReceiverStatusCharging();

        switch (item) {
            case 0:
                //Charging
                if (getStatusCharging() == BatteryManager.BATTERY_STATUS_CHARGING) {
                    sortActions(actionPos);
                }
                break;
            case 1:
                //Discharging
                if (getStatusCharging() == BatteryManager.BATTERY_STATUS_DISCHARGING) {
                    sortActions(actionPos);
                }
                break;
            default:
                break;
        }
    }

    private void InitReceiverStatusCharging() {
        receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                setStatusCharging(status);
            }
        };
    }

    private void setStatusCharging(int statusCharging) {
        this.statusCharging = statusCharging;
    }

    private int getStatusCharging() {
        return statusCharging;
    }
    //----------------------------------------------------------------------------------


    // Battery level-------------------------------------------------------------------

    public void checkBatteryLevel(int batteryLevel) {
        InitReceiverBatteryLevel();

        if (getChargedPct() < batteryLevel)
            sortActions(actionPos);
    }

    private void InitReceiverBatteryLevel() {
        receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                int level = intent.getIntExtra(
                        BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(
                        BatteryManager.EXTRA_SCALE, -1);

                setChargedPct((level * 100) / scale);
            }
        };
    }

    private void setChargedPct(int chargedPct) {
        this.chargedPct = chargedPct;
    }

    public int getChargedPct() {
        return chargedPct;
    }
    //--------------------------------------------------------------------------------


    // Wi-Fi Connection ---------------------------------------------------------------

    // Возвращает состояние:
    //0 - WIFI_STATE_DISABLING в настоящее время отключается
    //1 - WIFI_STATE_DISABLED отключен
    //2 - WIFI_STATE_ENABLING в настоящие время включается
    //3 - WIFI_STATE_ENABLED включен
    //4 - WIFI_STATE_UNKNOWN хз какой State
    public int getWifiState() {
        return wifiManager.getWifiState();
    }

    // Wi-Fi ON
    public void wifiEnabled() {
        wifiManager.setWifiEnabled(true);
    }

    // Wi-Fi OFF
    public void wifiDisable() {
        wifiManager.setWifiEnabled(false);
    }

    public void checkWiFiConnection(int item) {
        switch (item) {
            case 0:
                //Wi-Fi ENABLED
                if (getWifiState() == 3) {
                    sortActions(actionPos);
                }
                break;
            case 1:
                //Wi-Fi DISABLED
                if (getWifiState() == 1) {
                    sortActions(actionPos);
                }
                break;
            default:
                break;
        }
    }
    //---------------------------------------------------------------
}
