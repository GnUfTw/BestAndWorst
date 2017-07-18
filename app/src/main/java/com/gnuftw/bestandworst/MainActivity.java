package com.gnuftw.bestandworst;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    // onCreate() contains basic application startup logic that happens 1x in entire life of activity.
    // It should be noted that the Bundle argument needs to be checked for null before reading it. Or implement a onRestoreInstanceState() function.
    // If it is null, then the system is creating a new activity instance otherwise it was restored.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add toolbar to activity.
        Toolbar actionToolbar = (Toolbar) findViewById(R.id.bw_toolbar);
        setSupportActionBar(actionToolbar);

        // Create new HomeFragment to be placed in the activity layout.
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, homeFragment).commit();
    }

    // onStart() contains code for maintaining the UI.
    @Override
    protected void onStart() {
        super.onStart();
    }

    // onResume() must initialize components that activity only uses when it has user focus which are released in onPause().
    @Override
    protected void onResume() {
        super.onResume();
    }

    // ?????
    @Override
    protected  void onRestart() {
        super.onRestart();
    }

    // onPause() must release resources that may affect battery life while the activity is paused & user doesn't need them.
    @Override
    protected void onPause() {
        super.onPause();
    }

    // onStop() release anything the user can't see & any resources that may leak memory.
    @Override
    protected void onStop() {
        super.onStop();
    }

    // onSaveInstanceState() needs to save user's current entry state/data
    // doesn't get called when user hits back button, can override onBackPressed() to implement custom behavior.
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    // onRestoreInstanceState() recreates instance state after configuration changes such as portrait to landscape orientation.
    // Configuration changes can be handled here or t
    // he system can restore the activity & recreate it with new dimensions.
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    // Needs to be over-ridden for to retrieve fragments from the back stack. If this isn't necessary,
    // pressing back will cause app to return to activity or close the app.
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // Specifies the option menu for the activity.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }

    // This method is called whenever an item is selected from the options menu.
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            /*case R.id.action_bar_calendar:
                CalendarFragment calendarFragment = new CalendarFragment();
                replaceFragment(calendarFragment);
                return true;*/
            case R.id.action_bar_home:
                HomeFragment homeFragment = new HomeFragment();
                replaceFragment(homeFragment);
                return true;
            case R.id.action_bar_history:
                HistoryFragment historyFragment = new HistoryFragment();
                replaceFragment(historyFragment);
                return true;
            /*case R.id.options_settings:
                SettingsFragment settingsFragment = new SettingsFragment();
                replaceFragment(settingsFragment);
                return true;*/
            case R.id.options_about:
                AboutFragment aboutFragment = new AboutFragment();
                replaceFragment(aboutFragment);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment); // Replace whatever is in fragment_container with a settings fragment.
        fragmentTransaction.addToBackStack(null);                       // Add transaction to back stack for backwards user navigation.
        fragmentTransaction.commit();                                   // Commit transaction.
    }
}
