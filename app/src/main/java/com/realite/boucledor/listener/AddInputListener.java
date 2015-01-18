package com.realite.boucledor.listener;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import com.realite.boucledor.R;
import com.realite.boucledor.business.InputEntry;
import com.realite.boucledor.db.MoneyDBHelper;
import com.realite.boucledor.ui.DisplayHandler;
import com.realite.boucledor.util.PreferenceHandler;

import java.util.Date;

public class AddInputListener implements View.OnClickListener {

    private static final String LOG_TAG = "Add_Input";
    private static final String DOMICILE = "Domicile";
    private double domicileRetroPercent;
    private static final String CABINET = "Cabinet";
    private double cabinetRetroPercent;
    private double taxPercent;

    private static final double HUNDRED = 100;
    private double retroPercentage;
    private double taxPercentage;
    private RadioGroup typeRdGroup;

    private  EditText inputEdit;
    private DisplayHandler dHandler;
    private MoneyDBHelper dbHelper;

    public AddInputListener(Activity context, MoneyDBHelper dbHelper, DisplayHandler dHandler) {
        this.inputEdit = (EditText) context.findViewById(R.id.encaisserAmtInput);
        this.typeRdGroup = (RadioGroup) context.findViewById(R.id.typeRdGroup);
        this.dHandler = dHandler;
        this.dbHelper = dbHelper;
        recoverPreferences(context);
        retroPercentage = getRetroPercent() / HUNDRED;
        taxPercentage = taxPercent / HUNDRED;

    }

    private void recoverPreferences(Activity context) {
        PreferenceHandler preferenceHandler = new PreferenceHandler(context);
        domicileRetroPercent = preferenceHandler.getDomRetro();
        cabinetRetroPercent = preferenceHandler.getCabRetro();
        taxPercent = preferenceHandler.getTax();
    }

    @Override
    public void onClick(View v) {
        try {
            Double amt = Double.parseDouble(inputEdit.getText().toString());
            Double afterRetro;
            Double retroAmt = amt * retroPercentage;
            afterRetro = amt - retroAmt;
            Double taxAmt = afterRetro * taxPercentage;
            Double afterTax = afterRetro - taxAmt;
            InputEntry.Builder entryBuilder = new InputEntry.Builder(new Date(), amt)
                    .afterRetroAmt(afterRetro).netAmt(afterTax)
                    .retroAmt(retroAmt).retroPercent(getRetroPercent())
                    .taxAmt(taxAmt).taxPercent(taxPercent)
                    .type(getType());
            dbHelper.addInput(entryBuilder.build());
            dHandler.addAllAndRefresh(dbHelper);
            inputEdit.setText("");
        } catch (NumberFormatException e) {
            Log.e(LOG_TAG, "Montant incorrect",  e);
        }
    }

    private String getType() {
        if (R.id.cabRd == typeRdGroup.getCheckedRadioButtonId()) {
            return CABINET;
        }
        if (R.id.domRd == typeRdGroup.getCheckedRadioButtonId()) {
            return DOMICILE;
        }
        return null;
    }

    private double getRetroPercent() {
        if (R.id.cabRd == typeRdGroup.getCheckedRadioButtonId()) {
            return cabinetRetroPercent;
        }
        if (R.id.domRd == typeRdGroup.getCheckedRadioButtonId()) {
            return domicileRetroPercent;
        }
        return 0;
    }
}
