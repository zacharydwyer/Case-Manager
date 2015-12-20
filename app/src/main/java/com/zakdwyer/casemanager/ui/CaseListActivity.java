package com.zakdwyer.casemanager.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zakdwyer.casemanager.R;

/**
 * Activity for the LIST of cases.
 */
public class CaseListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_list);
    }
}
