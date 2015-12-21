package com.zakdwyer.casemanager.ui.CaseContactList;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.zakdwyer.casemanager.ui.ZZMisc.SingleFragmentActivity;

public class CaseContactListActivity extends SingleFragmentActivity {

    private static final String EXTRA_CASE_ID = "CaseContactListActivity.case_id";

    public static Intent newIntent(Context packageContext, int caseID) {
        Intent intent = new Intent(packageContext, CaseContactListActivity.class);
        intent.putExtra(EXTRA_CASE_ID, caseID);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        int caseID = getIntent().getIntExtra(EXTRA_CASE_ID, -5);
        return CaseContactListFragment.newInstance(caseID);
    }
}
