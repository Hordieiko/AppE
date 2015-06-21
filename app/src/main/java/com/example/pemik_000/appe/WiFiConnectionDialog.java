package com.example.pemik_000.appe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by pemik_000 on 07.06.2015.
 */
public class WiFiConnectionDialog extends DialogFragment {

    Context context;
    private int itemWiFiConnection;

    TaskManager taskManager = new TaskManager();


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String[] mCharging = { "Wi-Fi ENABLED", "Wi-Fi DISABLED"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Charging")
                .setCancelable(false)
                .setNeutralButton("Назад",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setNeutralButton("Окей",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                taskManager.setItemWiFiConnection(itemWiFiConnection);
                                dialog.cancel();
                            }
                        })
                .setSingleChoiceItems(mCharging, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                Toast.makeText(context, "item: " + item, Toast.LENGTH_LONG).show();
                                setItemWiFiConnection(item);
                            }
                        });

        return builder.create();
    }

    private void setItemWiFiConnection(int itemWiFiConnection) {
        this.itemWiFiConnection = itemWiFiConnection;
    }
}
