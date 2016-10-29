package com.cineflick.developer.harry.ui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.cineflick.developer.harry.R;

/**
 * Created by harry on 10/5/16.
 */
public class WiFiConnectionAlertDialog extends DialogFragment {

    public static WiFiConnectionAlertDialog newInstance() {
        WiFiConnectionAlertDialog wiFiConnectionAlertDialog = new WiFiConnectionAlertDialog();
        return wiFiConnectionAlertDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(R.string.wifi_not_avail)
                .setMessage(R.string.dialog_wifi_message)
                .setPositiveButton(R.string.dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((MainActivity) getActivity()).doPositiveClick();
                            }
                        }
                )
                .setNegativeButton(R.string.dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((MainActivity) getActivity()).doNegativeClick();
                            }
                        }
                )
                .create();
    }
}
