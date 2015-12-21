package com.zakdwyer.casemanager.ui.CaseList;

import android.support.v4.app.Fragment;

import com.zakdwyer.casemanager.R;
import com.zakdwyer.casemanager.ui.ZZMisc.SingleFragmentActivity;

// Activity for the list of cases.
public class CaseListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CaseListFragment();
    }
}
