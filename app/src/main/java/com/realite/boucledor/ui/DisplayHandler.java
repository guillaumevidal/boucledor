package com.realite.boucledor.ui;

import android.widget.TextView;
import com.realite.boucledor.business.InputEntry;
import com.realite.boucledor.db.MoneyDBHelper;

public class DisplayHandler {

    private final TextView amtView;

    public DisplayHandler(TextView amtView) {
        this.amtView = amtView;
    }

    public void addAllAndRefresh(MoneyDBHelper dbHelper) {
        double amt = 0d;
        for (InputEntry entry : dbHelper.getAllInput()) {
            amt += entry.getNetAmt() != null ? entry.getNetAmt() : 0;
        }
        amtView.setText(amt + "€");
    }
}
