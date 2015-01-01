package com.realite.boucledor.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import com.realite.boucledor.R;
import com.realite.boucledor.adapter.EntryListAdapter;
import com.realite.boucledor.business.InputEntry;
import com.realite.boucledor.db.MoneyDBHelper;
import com.realite.boucledor.listener.DeleteGestureListener;
import com.realite.boucledor.listener.ListEntryTouchListener;
import org.joda.time.LocalDate;

import java.util.Date;
import java.util.List;

public class EntryListActivity extends ListActivity{

    private MoneyDBHelper dbHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new MoneyDBHelper(getApplicationContext());
        ListView entryList = (ListView) findViewById(R.id.entryList);
        //entryList.setOnTouchListener(new ListEntryTouchListener(getApplicationContext(), entryList));
        EntryListAdapter listAdapter = new EntryListAdapter(getApplicationContext());
        setListAdapter(listAdapter);
        List<InputEntry> defaultEntries = getDefaultInput(dbHelper);
        listAdapter.updateEntries(defaultEntries);
    }

    private List<InputEntry> getDefaultInput(MoneyDBHelper dbHelper) {
        Date today = new LocalDate().toDate();
        Date backOneMonth = new LocalDate().minusMonths(1).toDate();
        return getInputBetweenDates(dbHelper, backOneMonth, today);
    }

    private List<InputEntry> getInputBetweenDates(MoneyDBHelper dbHelper, Date first, Date second) {
       return dbHelper.getInputBetweenDates(first, second);
    }

    @Override
    public ListView getListView() {
        return (ListView) findViewById(R.id.entryList);
    }
}
