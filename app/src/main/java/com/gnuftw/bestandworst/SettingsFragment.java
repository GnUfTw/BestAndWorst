package com.gnuftw.bestandworst;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

public class SettingsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //addListenerButton();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    /*public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
        hr = hourOfDay;
        min = minute;
    }*/

    @Override
    public void onStart() {
        super.onStart();
        Button changeNotificationTimeButton = (Button) getActivity().findViewById(R.id.updateTimeButton);
        changeNotificationTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePicker timePicker = (TimePicker) getActivity().findViewById(R.id.timePicker);
                int hr = timePicker.getCurrentHour();
                int min = timePicker.getCurrentMinute();
                SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(getString(R.string.data_hour), hr);
                editor.putInt(getString(R.string.data_minute), min);
                editor.apply();
            }
        });
    }
}
