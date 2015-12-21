package com.zakdwyer.casemanager.ui.CaseAdd;

import android.support.v4.app.Fragment;

import com.zakdwyer.casemanager.ui.ZZMisc.SingleFragmentActivity;

public class CaseAddActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CaseAddFragment();
    }
}
