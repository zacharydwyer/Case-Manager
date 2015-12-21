package com.zakdwyer.casemanager.ui.TodoList;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.zakdwyer.casemanager.ui.ZZMisc.SingleFragmentActivity;

// Actvity for the list of a case's To-Dos
public class TodoListActivity extends SingleFragmentActivity {

    private static final String EXTRA_CASE_ID = TodoListActivity.class.getSimpleName() + ".case_id";
    public static final String TAG = "Case Todo List Activity";

    // In order to start the Case To-do List activity, it needs the ID of the case it's showing to-dos for
    public static Intent newIntent(Context packageContext, int caseID) {
        Intent intent = new Intent(packageContext, TodoListActivity.class);
        intent.putExtra(EXTRA_CASE_ID, caseID);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        int caseID = getIntent().getIntExtra(EXTRA_CASE_ID, -5);
        Log.i(TAG, "Case ID retrieved from Intent. Returned: " + caseID + ". Should not be -5!");
        return TodoListFragment.newInstance(caseID);
    }


}
