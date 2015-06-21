package com.example.pemik_000.appe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Location extends ActionBarActivity implements LocationListener,
        GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener,
        GoogleMap.OnCameraChangeListener {

    MapFragment mapFragment;
    LocationManager locationManager;
    GoogleMap map;
    android.location.Location location;
    Marker marker;
    String idMarker = "";
    String nameTrg;
    AddNewTask addNewTask;

    private double latitude = 0;
    private double longitude = 0;



    TaskManager taskManager = new TaskManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        map = mapFragment.getMap();

        // устанавливаем тип карты
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //включаем определение текущего местоположения
        map.setMyLocationEnabled(true);
        //для
        //кнопки текущего местоположения
        map.getUiSettings().setMyLocationButtonEnabled(true);

        //все жесты: вращения, пролистывания карты, смена угла обзора, жесты зума
        map.getUiSettings().setAllGesturesEnabled(true);

        //компас (слева сверху)
        map.getUiSettings().setCompassEnabled(true);

        //кнопки зума
        map.getUiSettings().setZoomControlsEnabled(true);

        map.setOnMapClickListener(this);

        Intent intent = getIntent();
        nameTrg = intent.getStringExtra("nameTrg");
    }

    public void showInfoDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Location.this);
        builder.setTitle("Как это работает ?!")
                .setMessage("Укажите место на карте, выставив маркер в нужном Вам месте. " +
                        "Подтвердите ваш выбор нажав на кнопку Done")
                //.setIcon(R.drawable.)
                .setCancelable(false)
                .setNegativeButton("Окей",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    //Button "My Location"
    public void onClickTest(View v){
        showLocation(location);
    }

    private void showLocation(android.location.Location location) {
        if (location == null) {
            Toast.makeText(this, "No information on the location", Toast.LENGTH_LONG).show();
            return;
        } else {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    // Это точка, на которую смотрит камера. Задали текущие координаты,
                    // которые определил locationManager
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                    .zoom(23) // текущий уровень зума
                    .bearing(0) // угол поворота камеры от севера по часовой
                    .tilt(45) // угол наклона камеры
                    .build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            map.animateCamera(cameraUpdate);
            Toast.makeText(this, "UserLatitude: " + location.getLatitude() +
                    "UserLongitude: " + location.getLongitude(), Toast.LENGTH_LONG).show();
        }
    }

    // Методы LocationListener: onLocationChanged,onStatusChanged,onProviderEnabled,onProviderDisabled
//-------------------------------------------------------------------------------------
    // новые данные о местоположении
    @Override
    public void onLocationChanged(android.location.Location location) {
        this.location = location;
        // showLocation(location);
    }

    // изменился статус указанного провайдера
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    // указанный провайдер был включен юзером
    @Override
    public void onProviderEnabled(String provider) {
        // запрашиваем последнее доступное местоположение
        // от включенного провайдера и отображаем его.
        // Оно может быть вполне актуальным, если вы до этого
        // использовали какое-либо приложение с определением местоположения.
        checkEnabled();
        location = locationManager.getLastKnownLocation(provider);

    }

    // указанный провайдер был отключен юзером
    @Override
    public void onProviderDisabled(String provider) {
        checkEnabled();
    }

    private void checkEnabled() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) == false &&
                locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER) == false) {
            Toast.makeText(this, "No GPS. No Network", Toast.LENGTH_LONG).show();
        }
    }

//-----------------------------------------------------------------------------


    // устанавливаем тип карты
    public void clickMapType(View v) {
        switch (v.getId()) {
            case R.id.btnNormal:
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.btnSatellite:
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.btnTerrain:
                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.btnHybrid:
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
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

    //сработает при смене позиции камеры (карта)
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    //сработает при клике на карту
    @Override
    public void onMapClick(LatLng latLng) {
        if (idMarker != "") {
            marker.remove();
        }
        marker = map.addMarker(new MarkerOptions()
                .position(latLng)
                .title("UserLatitude: " + latLng.latitude + "UserLongitude: " + latLng.longitude)
                .draggable(true)); // возможность премещать
        idMarker = marker.getId();

        latitude = latLng.latitude;
        longitude = latLng.longitude;

        // ОКРУГЛЕНИЕ ДО 3х ЗНАКОВ ПОСЛЕ ЗАПЯТОЙ !!!!!!
        //double a = latLng.latitude;
        //double newDouble = new BigDecimal(a).setScale(3, RoundingMode.UP).doubleValue();
        //Toast.makeText(this,"ОКРУГЛЕНИЕ ДО 3х ЗНАКОВ ПОСЛЕ ЗАПЯТОЙ: " + newDouble, Toast.LENGTH_LONG).show();
    }

    //сработает при длительном нажатии на карту
    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    //Закончили работу с картой
    public void clickMapDone(View view) {

        if (latitude!=0 || longitude!=0) {
            Intent intent = new Intent();
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            setResult(RESULT_OK, intent);

            finish();
        } else Toast.makeText(this, "Set Marker!", Toast.LENGTH_LONG).show();

    }


}
