package com.realite.boucledor.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.realite.boucledor.R;
import com.realite.boucledor.util.PreferenceHandler;

public class SettingsActivity extends Activity {

    private EditText retroCabEdit;
    private EditText retroDomEdit;
    private EditText taxEdit;
    private PreferenceHandler preferenceHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        retroCabEdit = (EditText)findViewById(R.id.editRetroCab);
        retroDomEdit = (EditText)findViewById(R.id.editRetroDom);
        taxEdit = (EditText) findViewById(R.id.editTax);
        preferenceHandler = new PreferenceHandler(this);
        fillSettings();
        Button saveBtn = (Button)findViewById(R.id.saveSettingsBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceHandler.setNewSettings(Integer.valueOf(retroCabEdit.getText().toString()),
                Integer.valueOf(retroDomEdit.getText().toString()),
                Integer.valueOf(taxEdit.getText().toString()));
            }
        });
    }

    private void fillSettings() {
        int domicileRetroPercent = preferenceHandler.getDomRetro();
        int cabinetRetroPercent = preferenceHandler.getCabRetro();
        int taxPercent = preferenceHandler.getTax();
        retroCabEdit.setText(String.valueOf(cabinetRetroPercent));
        retroDomEdit.setText(String.valueOf(domicileRetroPercent));
        taxEdit.setText(String.valueOf(taxPercent));
    }

}
