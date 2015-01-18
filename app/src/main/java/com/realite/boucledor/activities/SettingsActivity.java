package com.realite.boucledor.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.realite.boucledor.R;
import com.realite.boucledor.adapter.EntryListAdapter;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_action:
                Intent calIntent = new Intent(this, MainActivity.class);
                startActivity(calIntent);
                return true;
            case R.id.action_calendar:
                Intent settingsIntent = new Intent(this, EntryListActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
