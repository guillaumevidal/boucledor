package com.realite.boucledor.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.SwipeDismissRecyclerViewTouchListener;
import com.realite.boucledor.R;
import com.realite.boucledor.adapter.EntryListAdapter;
import com.realite.boucledor.business.InputEntry;
import com.realite.boucledor.db.MoneyDBHelper;

import org.joda.time.LocalDate;

import java.util.Date;
import java.util.List;

public class EntryListActivity extends Activity implements SwipeDismissRecyclerViewTouchListener.DismissCallbacks {

    private MoneyDBHelper dbHelper;
    EntryListAdapter entryListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new MoneyDBHelper(getApplicationContext());
        setContentView(R.layout.history);
        SuperRecyclerView entryList = (SuperRecyclerView) findViewById(R.id.recyclerList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        entryList.setLayoutManager(layoutManager);
        List<InputEntry> defaultEntries = getDefaultInput(dbHelper);
        entryListAdapter = new EntryListAdapter(getApplicationContext(), defaultEntries);
        entryList.setAdapter(entryListAdapter);
        entryList.setupSwipeToDismiss(this);
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
    public boolean canDismiss(int position) {
        return true;
    }

    @Override
    public void onDismiss(RecyclerView recyclerView, int[] reverseSortedPositions) {
        for (int position : reverseSortedPositions) {
            entryListAdapter.removeFromSwipe(position);
        }
    }
}
