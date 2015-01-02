package com.realite.boucledor.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.realite.boucledor.R;
import com.realite.boucledor.adapter.EntryListAdapter;
import com.realite.boucledor.db.MoneyDBHelper;
import com.realite.boucledor.listener.AddInputListener;
import com.realite.boucledor.ui.DisplayHandler;

public class MainActivity extends Activity {

	private MoneyDBHelper dbHelper;
	private DisplayHandler displayHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		final TextView currentAmt = (TextView) findViewById(R.id.currentAmtText);
		dbHelper = new MoneyDBHelper(getApplicationContext());
		dbHelper.wipe();
		displayHandler = new DisplayHandler(currentAmt);
		displayHandler.addAllAndRefresh(dbHelper);
		addListeners();
	}

	private void addListeners() {
        final Button encaisserBtn = (Button) findViewById(R.id.encaisserBtn);
		encaisserBtn.setOnClickListener(new AddInputListener(this, dbHelper, displayHandler));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

    @Override
    protected void onResume() {
        super.onResume();
        displayHandler.addAllAndRefresh(dbHelper);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_calendar:
                Intent intent = new Intent(this, EntryListActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
