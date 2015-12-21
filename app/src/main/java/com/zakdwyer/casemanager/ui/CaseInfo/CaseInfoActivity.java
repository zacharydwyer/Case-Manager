package com.zakdwyer.casemanager.ui.CaseInfo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.zakdwyer.casemanager.ui.ZZMisc.SingleFragmentActivity;

public class CaseInfoActivity extends SingleFragmentActivity {
    private static final String EXTRA_CASE_ID = CaseInfoActivity.class.getSimpleName() + ".case_id";
    public static final String TAG = "Case Info Activity";

    // In order to start the Case Info activity, it needs the ID of the case it's showing info for.
    public static Intent newIntent(Context packageContext, int caseID) {
        Intent intent = new Intent(packageContext, CaseInfoActivity.class);
        intent.putExtra(EXTRA_CASE_ID, caseID);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        int caseID = getIntent().getIntExtra(EXTRA_CASE_ID, -5);
        Log.i(TAG, "Case ID retrieved from Intent. Returned: " + caseID + ". Should not be -5!");
        return CaseInfoFragment.newInstance(caseID);
    }
}


