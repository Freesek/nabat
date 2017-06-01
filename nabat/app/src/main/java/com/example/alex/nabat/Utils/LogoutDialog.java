package com.example.alex.nabat.Utils;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.alex.nabat.FragmentLogin;
import com.example.alex.nabat.FragmentStepOne;
import com.example.alex.nabat.R;
import com.example.alex.nabat.data.ChangeHeader;
import com.example.alex.nabat.data.MySettings;
import com.facebook.login.LoginManager;

/**
 * Created by alexey on 19.04.17.
 */

public class LogoutDialog extends DialogFragment {
    FragmentTransaction fTrans;
    ChangeHeader header;

    public LogoutDialog() {
        super();
    }

    public void setHeader(ChangeHeader header) {
        this.header = header;
    }

    public void nextFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Выход")
                .setMessage("Вы вошли как: " + MySettings.getMySettings().getName())
                .setPositiveButton("Выход", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        header.changeHeaderNavDrawer(false);
                        LoginManager.getInstance().logOut();
                        MySettings.getMySettings().setUserInactive();
                        FragmentTransaction fTrans = getActivity().getFragmentManager().beginTransaction();
                        fTrans.replace(R.id.container, new FragmentLogin());
                        fTrans.commit();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        return builder.create();
    }
}
