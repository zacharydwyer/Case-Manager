package com.zakdwyer.casemanager.ui.activity;

import android.support.v4.app.Fragment;

import com.zakdwyer.casemanager.ui.fragment.CaseAddFragment;

public class CaseAddActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CaseAddFragment();
    }
}
