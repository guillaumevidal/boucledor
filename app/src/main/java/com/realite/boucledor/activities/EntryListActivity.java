package com.realite.boucledor.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.SwipeDismissRecyclerViewTouchListener;
import com.realite.boucledor.R;
import com.realite.boucledor.adapter.EntryListAdapter;
import com.realite.boucledor.business.InputEntry;
import com.realite.boucledor.db.MoneyDBHelper;

import org.joda.time.LocalDate;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Set<Integer> toRemove = new HashSet<>();
        for (int position : reverseSortedPositions) {
            toRemove.add(entryListAdapter.removeFromSwipe(position));
        }
        for (Integer id : toRemove) {
            dbHelper.deleteEntry(id);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.calendar_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_action:
                Intent calIntent = new Intent(this, MainActivity.class);
                startActivity(calIntent);
                return true;
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
