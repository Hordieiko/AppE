package com.example.pemik_000.appe;

import android.telephony.SmsManager;

/**
 * Created by pemik_000 on 05.06.2015.
 */
public class StartActionsForTrigger {

    // Send SMS
    public void sendSMS(String telNumber, String textMassage) {
        SmsManager.getDefault().sendTextMessage(telNumber, null, textMassage, null, null);
    }
}
