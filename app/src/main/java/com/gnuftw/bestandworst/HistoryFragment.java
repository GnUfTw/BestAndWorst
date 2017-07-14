package com.gnuftw.bestandworst;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class HistoryFragment extends ListFragment {
    DbOpenHelper dbOpenHelper;
    SimpleCursorAdapter adapter;

    static final String[] PROJECTION = {
            DbContract.DbEntry._ID,
            DbContract.DbEntry.COLUMN_DATE,
            DbContract.DbEntry.COLUMN_BEST,
            DbContract.DbEntry.COLUMN_WORST
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        dbOpenHelper = new DbOpenHelper(getActivity().getApplicationContext());
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getReadableDatabase();
        } catch (SQLiteException ex) {
            Log.d("HistoryFragment", "getReadableDatabase() failed in onCreateView()");
        }

        // Return all rows from the table in descending order & display them in a list.
        Cursor cursor = db.query(DbContract.DbEntry.TABLE_NAME, PROJECTION, null, null, null, null, null, null);
        if (cursor == null) {
            Log.d("HistoryFragment", "Cursor is null after query().");
        }
        cursor.moveToFirst();
        String[] fromColumns = new String[] {
                DbContract.DbEntry.COLUMN_DATE,
                DbContract.DbEntry.COLUMN_BEST,
                DbContract.DbEntry.COLUMN_WORST
        };
        int[] toViews = new int[] {
                R.id.list_item_date,
                R.id.list_item_best,
                R.id.list_item_worst
        };
        adapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.list_item, cursor, fromColumns, toViews, 0);
        if (adapter == null) {
            Log.d("HistoryFragment", "Adapter is null after construction.");
        }
        ListView listView = getListView();
        listView.setAdapter(adapter);
    }
}
