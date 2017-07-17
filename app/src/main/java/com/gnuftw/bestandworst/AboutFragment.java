package com.gnuftw.bestandworst;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView versionTextView = (TextView) getActivity().findViewById(R.id.versionTextView);
        versionTextView.setText(getVersionInfo());
    }

    private String getVersionInfo() {
        String strVersion = "Version: ";
        PackageInfo packageInfo;
        try {
            packageInfo = getActivity().getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(
                            getActivity().getApplicationContext().getPackageName(),
                            0
                    );
            strVersion += packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("AboutFragment", "Unknown version number");
            strVersion += "unknown";
        }

        return strVersion;
    }
}
