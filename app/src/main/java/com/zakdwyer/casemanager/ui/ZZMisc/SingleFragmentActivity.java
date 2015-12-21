package com.zakdwyer.casemanager.ui.ZZMisc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.zakdwyer.casemanager.R;

// Single fragment
public abstract class SingleFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set view to the single fragment view
        setContentView(R.layout.activity_single_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        // If the fragment isn't there
        if (fragment == null) {
            fragment = createFragment();                        // Make one. Implemented by child classes.
            fm.beginTransaction()                               // Begin the transaction.
                    .add(R.id.fragment_container, fragment)     // Add the fragment to the transaction.
                    .commit();                                  // Execute.
        }
    }

    //Define a abstract method that must be overridden in child classes to return a fragment to use.
    protected abstract Fragment createFragment();

}
