package com.zakdwyer.casemanager.ui.TodoListAdd;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.zakdwyer.casemanager.ui.ZZMisc.SingleFragmentActivity;


public class TodoListAddActivity extends SingleFragmentActivity {

    public static final String TAG = "Todo List Add Activity";
    private static final String EXTRA_CASE_ID = TodoListAddActivity.class.getSimpleName() + ".case_id";

    protected Fragment createFragment() {

        // Retrieve intent extras.
        int caseID = getIntent().getIntExtra(EXTRA_CASE_ID, -5);
        Log.i(TAG, "Case ID retrieved from Intent. Returned: " + caseID + ". Should not be -5!");

        return TodoListAddFragment.newInstance(caseID);
    }

    public static Intent newIntent(Context packageContext, int caseID) {
        Intent intent = new Intent(packageContext, TodoListAddActivity.class);
        intent.putExtra(EXTRA_CASE_ID, caseID);
        return intent;
    }
}
