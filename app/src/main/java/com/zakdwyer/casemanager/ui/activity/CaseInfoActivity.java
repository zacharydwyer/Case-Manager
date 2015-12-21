package com.zakdwyer.casemanager.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.zakdwyer.casemanager.R;

public class CaseInfoActivity extends FragmentActivity {
    public static final String TAG = CaseInfoActivity.class.getSimpleName();

    CaseListPagerAdapter mCaseListPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_list_viewpager);
    }

    /////////////
    // ADAPTER //
    /////////////

    private class CaseListPagerAdapter extends FragmentStatePagerAdapter {

        public CaseListPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }
    }
}


