package com.gnuftw.bestandworst;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

public class CalendarFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    /*public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
        // Need to pull the best & worst entry for the selected date and display it in the
        // text views below the calendar. If the selected date has no entry, the user should
        // be notified.
    }*/
}
