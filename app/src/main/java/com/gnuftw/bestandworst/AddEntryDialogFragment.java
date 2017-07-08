package com.gnuftw.bestandworst;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

public class AddEntryDialogFragment extends DialogFragment {
    SQLiteDatabase db;
    DbOpenHelper dbOpenHelper;
    View textEntryView;

    public AddEntryDialogFragment () { }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dbOpenHelper = new DbOpenHelper(getActivity().getApplicationContext());
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        textEntryView = layoutInflater.inflate(R.layout.fragment_add_entry_dialog, null);
        alertDialogBuilder.setView(textEntryView)
                .setPositiveButton(R.string.title_save_entry_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int id) {
                        String todaysDate = getTodaysDate();
                        db = openDatabase();

                        if (isThereAnEntryForTodayAlready(todaysDate)) {
                            // Destroy current dialog & create new dialog notifying user & don't save the entered info.
                            Log.d("AddEntryDialogFragment", "The database already has an entry for today");
                            return;
                        }

                        Log.d("AddEntryDialogFragment", "The dialog positive button has been clicked.");
                        String bestEntry = getBestEntryFromDialog();
                        String worstEntry = getWorstEntryFromDialog();
                        Log.d("AddEntryDialogFragment", "Best entry: " + bestEntry);
                        Log.d("AddEntryDialogFragment", "Worst entry: " + worstEntry);

                        // Add new entry to database.
                        if (addEntryToDatabase(todaysDate, bestEntry, worstEntry) == -1) {
                            Log.d("AddEntryDialogFragment", "An error occurred when inserting content values in the table.");
                        }
                        else {
                            Log.d("AddEntryDialogFragment", "The database has been added to.");
                        }
                    }
                })
                .setNegativeButton(R.string.title_cancel_entry_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int id) {
                        // User canceled dialog, discard entry here.
                        Log.d("AddEntryDialogFragment", "User decided to cancel entry");
                    }
                });

        return alertDialogBuilder.create();
    }

    @Override
    public void onDestroy() {
        dbOpenHelper.close();
        super.onDestroy();
    }

    private String getTodaysDate() {
        Calendar calendar = Calendar.getInstance();
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy.MM.dd");
        String formattedDate = simpleDateFormat.format(calendar.getTime());

        return formattedDate;
    }

    private SQLiteDatabase openDatabase() {
        try {
            // TODO: getWritableDatabase() should be called in bg thread using AsyncTask or IntentService.
            db = dbOpenHelper.getWritableDatabase();
            Log.d("AddEntryDialogFragment", "getWritableDatabase() executed with success, database should be created.");
        } catch (SQLiteException sqliteException) {
            Log.e("AddEntryDialogFragment", "SQLiteException", sqliteException);
        }

        return db;
    }

    private String getBestEntryFromDialog() {
        EditText bestEditText = (EditText) textEntryView.findViewById(R.id.bestEntry);
        if (bestEditText == null) {
            Log.d("AddEntryDialogFragment", "The best entry EditText object is null.");
        }

        return bestEditText.getText().toString();
    }

    private String getWorstEntryFromDialog() {
        EditText worstEditText = (EditText) textEntryView.findViewById(R.id.worstEntry);
        if (worstEditText == null) {
            Log.d("AddEntryDialogFragment", "The worst entry EditText object is null.");
        }

        return worstEditText.getText().toString();
    }

    private long addEntryToDatabase(String date, String best, String worst) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.DbEntry.COLUMN_DATE, date);
        contentValues.put(DbContract.DbEntry.COLUMN_BEST, best);
        contentValues.put(DbContract.DbEntry.COLUMN_WORST, worst);

        return db.insert(DbContract.DbEntry.TABLE_NAME, null, contentValues);
    }

    private boolean isThereAnEntryForTodayAlready(String date) {
        SQLiteDatabase db = null;
        try {
            db = dbOpenHelper.getReadableDatabase();
        } catch (SQLiteException ex) {
            Log.d("AddEntryDialogFragment", "getReadableDatabase() failed in isThereAnEntryForTodayAlready()");
        }

        String columns[] = { DbContract.DbEntry.COLUMN_DATE };
        String selection = DbContract.DbEntry._ID + "= ?";
        String selectionArgs[] = { "1" };
        String sortOrder = DbContract.DbEntry.COLUMN_DATE + " DESC";
        String limit = "1";

        Cursor cursor = db.query(
                DbContract.DbEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder,
                limit
        );

        // Since cursor starts at position -1, place the read position on the first entry in the results.
        if (cursor.moveToNext() == false) {
            Log.d("AddEntryDialogFragment", "moveToNext() failed in isThereAnEntryForTodayAlready()");
        }

        String mostRecentDateInDb = null;
        try {
            mostRecentDateInDb = cursor.getString(cursor.getColumnIndexOrThrow(DbContract.DbEntry.COLUMN_DATE));
            Log.d("AddEntryDialogFragment", "The most recent date in the database: " + mostRecentDateInDb);
        }
        catch (IllegalArgumentException ex) {
            Log.d("AddEntryDialogFragment", "The column requested does not exist.");
            cursor.close();
        }

        cursor.close();

        Log.d("AddEntryDialogFragment", "Todays date is: " + date);
        if (mostRecentDateInDb.equals(date)) {
            return true;
        }

        return false;
    }
}
