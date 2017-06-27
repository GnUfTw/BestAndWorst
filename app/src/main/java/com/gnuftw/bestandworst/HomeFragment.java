package com.gnuftw.bestandworst;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button addButton = (Button) view.findViewById(R.id.addButton);
        addButton.setOnClickListener(this);

        return view;
    }

    // When the add button is selected, an entry dialog should be created.
    @Override
    public void onClick(View view) {
        AddEntryDialogFragment dialogFragment = new AddEntryDialogFragment();
        dialogFragment.show(getActivity().getSupportFragmentManager(), "AddEntryDialogFragment");
    }
}
