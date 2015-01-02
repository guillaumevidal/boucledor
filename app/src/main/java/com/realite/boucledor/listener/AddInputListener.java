package com.realite.boucledor.listener;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import com.realite.boucledor.R;
import com.realite.boucledor.business.InputEntry;
import com.realite.boucledor.db.MoneyDBHelper;
import com.realite.boucledor.ui.DisplayHandler;

import java.util.Date;

public class AddInputListener implements View.OnClickListener {

    private static final String LOG_TAG = "Add_Input";
    private static final String DOMICILE = "Domicile";
    private static double DOMICILE_RETRO_PERCENT;
    private static final String CABINET = "Cabinet";
    private static double CABINET_RETRO_PERCENT;
    private static double TAX_PERCENT;

    private static final double HUNDRED = 100;
    private static double RETRO_PERCENTAGE;
    private static double TAX_PERCENTAGE;
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
        RETRO_PERCENTAGE = getRetroPercent() / HUNDRED;
        TAX_PERCENTAGE = TAX_PERCENT / HUNDRED;

    }

    private void recoverPreferences(Activity context) {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.pref_file), Context.MODE_PRIVATE);
        if (null != preferences) {
            int defaultRetroDom = context.getResources().getInteger(R.integer.default_retro_percent_dom);
            DOMICILE_RETRO_PERCENT = preferences.getInt(context.getString(R.string.set_retro_percent_dom_prop), defaultRetroDom);
            int defaultRetroCab = context.getResources().getInteger(R.integer.default_retro_percent_cab);
            CABINET_RETRO_PERCENT = preferences.getInt(context.getString(R.string.set_retro_percent_cab_prop), defaultRetroCab);
            int defaultTax = context.getResources().getInteger(R.integer.default_tax_percent);
            TAX_PERCENT = preferences.getInt(context.getString(R.string.set_tax_percent_prop), defaultTax);
        }
    }

    @Override
    public void onClick(View v) {
        try {
            Double amt = Double.parseDouble(inputEdit.getText().toString());
            Double afterRetro;
            Double retroAmt = amt * RETRO_PERCENTAGE;
            afterRetro = amt - retroAmt;
            Double taxAmt = afterRetro * TAX_PERCENTAGE;
            Double afterTax = afterRetro - taxAmt;
            InputEntry.Builder entryBuilder = new InputEntry.Builder(new Date(), amt)
                    .afterRetroAmt(afterRetro).netAmt(afterTax)
                    .retroAmt(retroAmt).retroPercent(getRetroPercent())
                    .taxAmt(taxAmt).taxPercent(TAX_PERCENT)
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
            return CABINET_RETRO_PERCENT;
        }
        if (R.id.domRd == typeRdGroup.getCheckedRadioButtonId()) {
            return DOMICILE_RETRO_PERCENT;
        }
        return 0;
    }
}
