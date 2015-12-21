package com.zakdwyer.casemanager.ui.AddCaseContact;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.zakdwyer.casemanager.ui.EditCaseContact.EditCaseContactFragment;
import com.zakdwyer.casemanager.ui.ZZMisc.SingleFragmentActivity;

public class AddCaseContactActivity extends SingleFragmentActivity {

    private static final String EXTRA_CASE_ID = AddCaseContactActivity.class.getSimpleName()
            + ".case_id";

    public static Intent newIntent(Context context, int caseID) {
        Intent intent = new Intent(context, AddCaseContactActivity.class);
        intent.putExtra(EXTRA_CASE_ID, caseID);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        int caseID = getIntent().getIntExtra(EXTRA_CASE_ID, -5);
        return AddCaseContactFragment.newInstance(caseID);
    }

}
