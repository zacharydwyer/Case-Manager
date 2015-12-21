package com.zakdwyer.casemanager.ui.EditCaseContact;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.zakdwyer.casemanager.ui.ZZMisc.SingleFragmentActivity;

public class EditCaseContactActivity extends SingleFragmentActivity {
    private static final String EXTRA_CASE_ID = EditCaseContactActivity.class.getSimpleName() + ".case_id";
    private static final String EXTRA_CASE_CONTACT_ID = EditCaseContactActivity.class.getSimpleName() + ".case_contact_id";

    public static final String TAG = "Case Contact Activity";

    public static Intent newIntent(Context packageContext, int caseID, int caseContactID) {
        Intent intent = new Intent(packageContext, EditCaseContactActivity.class);
        intent.putExtra(EXTRA_CASE_ID, caseID);
        intent.putExtra(EXTRA_CASE_CONTACT_ID, caseContactID);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        int caseID = getIntent().getIntExtra(EXTRA_CASE_ID, -5);
        Log.i(TAG, "Case ID retrieved from Intent. Returned: " + caseID + ". Should not be -5!");
        int caseContactID = getIntent().getIntExtra(EXTRA_CASE_CONTACT_ID, -5);
        Log.i(TAG, "Case Contact ID retrieved from Intent. Returned: " + caseContactID + ". Should not be -5!");

        return EditCaseContactFragment.newInstance(caseID, caseContactID);
    }
}
