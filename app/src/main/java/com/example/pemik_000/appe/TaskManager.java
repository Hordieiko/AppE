package com.example.pemik_000.appe;

/**
 * Created by pemik_000 on 25.05.2015.
 */
public class TaskManager {

    // Send SMS
    private String textSMS;
    private String telNumber;

    // Notification
    private String titleNotification;
    private String textNotification;

//------------------------------------------

    // Location
    private double UserLatitude;
    private double UserLongitude;
    private final double radius = 0.00005;

    // Battery level
    private int batteryLevel;

    // Charging
    private int itemCharging;

    // Wi-Fi Connection
    private int itemWiFiConnection;


    public void setTextNotification(String textNotification) {
        this.textNotification = textNotification;
    }

    public void setTitleNotification(String titleNotification) {
        this.titleNotification = titleNotification;
    }

    public void setItemWiFiConnection(int itemWiFiConnection) {
        this.itemWiFiConnection = itemWiFiConnection;
    }

    public void setItemCharging(int itemCharging) {
        this.itemCharging = itemCharging;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public void setTextSMS(String textSMS) {
        this.textSMS = textSMS;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public void setUserLatitude(double Latitude) {
        this.UserLatitude = Latitude;
    }

    public void setUserLongitude(double Longitude) {
        this.UserLongitude = Longitude;
    }

    public double getRadius() {
        return radius;
    }

    public double getUserLatitude() {
        return UserLatitude;
    }

    public double getUserLongitude() {
        return UserLongitude;
    }

    public String getTextSMS() {
        return textSMS;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public int getItemCharging() {
        return itemCharging;
    }

    public int getItemWiFiConnection() {
        return itemWiFiConnection;
    }

    public String getTitleNotification() {
        return titleNotification;
    }

    public String getTextNotification() {
        return textNotification;
    }
}
