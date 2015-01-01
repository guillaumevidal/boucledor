package com.realite.boucledor.listener;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import com.realite.boucledor.R;
import com.realite.boucledor.business.InputEntry;
import com.realite.boucledor.db.MoneyDBHelper;
import com.realite.boucledor.ui.DisplayHandler;

import java.util.Date;

public class AddInputListener implements View.OnClickListener {
    private static final String DOMICILE = "Domicile";
    private static final double DOMICILE_RETRO_PERCENT = 20;
    private static final String CABINET = "Cabinet";
    private static final double CABINET_RETRO_PERCENT = 25;

    private static final double HUNDRED = 100;
    private final double RETRO_PERCENTAGE;
    private static final double TAX_PERCENTAGE = getTaxPercent() / HUNDRED;
    private final RadioGroup typeRdGroup;

    private static double getTaxPercent() {
        return 50;
    }

    private final EditText inputEdit;
    private final DisplayHandler dHandler;
    private final MoneyDBHelper dbHelper;

    public AddInputListener(MoneyDBHelper dbHelper, DisplayHandler dHandler, EditText inputEdit, RadioGroup typeRdGroup) {
        this.dHandler = dHandler;
        this.inputEdit = inputEdit;
        this.dbHelper = dbHelper;
        this.typeRdGroup = typeRdGroup;
        RETRO_PERCENTAGE = getRetroPercent() /HUNDRED;
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
                    .taxAmt(taxAmt).taxPercent(getTaxPercent())
                    .type(getType());
            dbHelper.addInput(entryBuilder.build());
            dHandler.addAllAndRefresh(dbHelper);
            inputEdit.setText("");
        } catch (NumberFormatException e) {
            //nothing yet
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
