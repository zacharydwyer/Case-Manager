package com.zakdwyer.casemanager.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.zakdwyer.casemanager.R;
import com.zakdwyer.casemanager.ui.fragment.CaseListFragment;

// Activity for the list of cases.
public class CaseListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CaseListFragment();
    }
}
