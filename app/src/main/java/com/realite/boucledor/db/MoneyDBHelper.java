package com.realite.boucledor.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.realite.boucledor.business.InputEntry;
import com.realite.boucledor.util.DateUtil;
import org.joda.time.LocalDate;

public class MoneyDBHelper extends SQLiteOpenHelper {

	private static final String LOG_TAG = "DB_HELPER";

	private static final int DATABASE_VERSION = 11;
	private static final String DB_NAME = "EvaMoneyDB";
	private static final String INPUT = "input";
	private static final String INPUT_ID_COL = "INPUT_ID";
	private static final String DATE_COL = "INPUT_DATE";
	private static final String RAW_AMT_COL = "RAW_AMT";
	private static final String RETRO_AMT_COL = "RETRO_AMT";
	private static final String RETRO_PERCENT_COL = "RETRO_PERCENT_AMT";
	private static final String AFTER_RETRO_AMT_COL = "AFTER_RETRO_AMT";
	private static final String TAX_AMT_COL = "TAX_AMT";
	private static final String TAX_PERCENT_COL = "TAX_PERCENT_AMT";
	private static final String NET_AMT_COL = "NET_AMT";
	private static final String TYPE_COL = "TYPE";
	private static final String NB_SESSIONS = "NB_SESSIONS";

	private static final String INPUT_CREATE_QUERY =
			"CREATE TABLE " + INPUT + " (" +
					INPUT_ID_COL + " INTEGER PRIMARY KEY, " +
					DATE_COL + " DATETIME, " +
					RAW_AMT_COL + " NUMERIC, " +
					RETRO_AMT_COL + " NUMERIC, " +
					RETRO_PERCENT_COL + " NUMERIC, " +
					AFTER_RETRO_AMT_COL + " NUMERIC, " +
					TAX_AMT_COL + " NUMERIC, " +
					TAX_PERCENT_COL + " NUMERIC, " +
					NET_AMT_COL + " NUMERIC, " +
					TYPE_COL + " NUMERIC, " +
                    NB_SESSIONS + " NUMERIC);";

	private static final String INPUT_DELETE_QUERY = "DROP TABLE " + INPUT;
	private static final String SELECT_QUERY = "SELECT "
			+ INPUT_ID_COL + ", "
			+ DATE_COL + ", "
			+ RAW_AMT_COL + ", "
			+ RETRO_AMT_COL + ", "
			+ RETRO_PERCENT_COL + ", "
			+ AFTER_RETRO_AMT_COL + ", "
			+ TAX_AMT_COL + ", "
			+ TAX_PERCENT_COL + ", "
			+ NET_AMT_COL + ", "
			+ NB_SESSIONS + ", "
			+ TYPE_COL + " FROM " + INPUT;

	private static final String WHERE_DATE_RANGE = " WHERE "+ DATE_COL + " BETWEEN ? AND ?";
	private static final String DELETE = " DELETE FROM "+ INPUT + " ";
    private static final String DELETE_ID_CONDITION = " WHERE "+ INPUT_ID_COL + " == ?";


	private final Context context;

	public MoneyDBHelper(Context context) {
		super(context, DB_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(INPUT_CREATE_QUERY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(INPUT_DELETE_QUERY);
		db.execSQL(INPUT_CREATE_QUERY);
	}

	public List<InputEntry> getAllInput() {
		Date now = new LocalDate().toDate();
		Date longAgo = new LocalDate().minusYears(100).toDate();
		return getInputBetweenDates(longAgo, now);
	}

	public List<InputEntry> getInputBetweenDates(Date firstDate, Date secondDate) {
		List<InputEntry> result = new ArrayList<InputEntry>();
		String query = SELECT_QUERY + WHERE_DATE_RANGE;
		String[] args = new String[2];
		args[0] = DateUtil.formatDateTimeForDb(context, firstDate.toString());
		args[1] = DateUtil.formatDateTimeForDb(context, secondDate.toString());
		Cursor cursor = getReadableDatabase().rawQuery(query, args);
		while(cursor.moveToNext()) {
			result.add(getEntryFromCursor(cursor));
		}
		return result;
	}

	public void addInput(InputEntry entry) {
		try {
			ContentValues values = new ContentValues();
			values.put(DATE_COL,  DateUtil.formatDateTimeForDb(context, entry.getInputDate().toString()));
			values.put(RAW_AMT_COL, entry.getRawAmt());
			values.put(RETRO_AMT_COL, entry.getRetroAmt());
			values.put(RETRO_PERCENT_COL, entry.getRetroPercent());
			values.put(AFTER_RETRO_AMT_COL, entry.getAfterRetroAmt());
			values.put(TAX_AMT_COL, entry.getTaxAmt());
			values.put(TAX_PERCENT_COL, entry.getTaxPercent());
			values.put(NET_AMT_COL, entry.getNetAmt());
			values.put(TYPE_COL, entry.getType());
			values.put(NB_SESSIONS, entry.getSessions());
			getWritableDatabase().insert(INPUT, null, values);
		} catch (Exception e) {
            Log.e(LOG_TAG, "Error inserting entry " + entry, e);
		}
	}

	public void wipe() {
		getWritableDatabase().execSQL("DELETE FROM INPUT");
	}

	private InputEntry getEntryFromCursor(Cursor cursor) {
		Date date = new Date(cursor.getInt(cursor.getColumnIndex(DATE_COL)));
		Double rawAmt = cursor.getDouble(cursor.getColumnIndex(RAW_AMT_COL));
		return new InputEntry.Builder(date, rawAmt)
                .id(cursor.getInt(cursor.getColumnIndex(INPUT_ID_COL)))
				.afterRetroAmt(cursor.getDouble(cursor.getColumnIndex(AFTER_RETRO_AMT_COL)))
				.retroAmt(cursor.getDouble(cursor.getColumnIndex(RETRO_AMT_COL)))
				.retroPercent(cursor.getDouble(cursor.getColumnIndex(RETRO_PERCENT_COL)))
				.taxAmt(cursor.getDouble(cursor.getColumnIndex(TAX_AMT_COL)))
				.taxPercent(cursor.getDouble(cursor.getColumnIndex(TAX_PERCENT_COL)))
				.netAmt(cursor.getDouble(cursor.getColumnIndex(NET_AMT_COL)))
				.type(cursor.getString(cursor.getColumnIndex(TYPE_COL)))
                .sessions(cursor.getInt(cursor.getColumnIndex(NB_SESSIONS)))
                .build();
	}

    public void deleteEntry(int id){
        Integer[] args = new Integer[1];
        args[0] = id;
        getWritableDatabase().execSQL(DELETE + DELETE_ID_CONDITION, args);
    }

}
