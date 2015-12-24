package com.zakdwyer.casemanager.ui.CaseInfoPager;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.zakdwyer.casemanager.R;
import com.zakdwyer.casemanager.data.Case;
import com.zakdwyer.casemanager.data.Caseload;
import com.zakdwyer.casemanager.ui.CaseContactList.CaseContactListActivity;
import com.zakdwyer.casemanager.ui.CaseContactList.CaseContactListFragment;
import com.zakdwyer.casemanager.ui.CaseInfo.CaseInfoFragment;
import com.zakdwyer.casemanager.ui.TodoList.TodoListFragment;

public class CaseInfoPagerActivity extends AppCompatActivity {

    // Constant key; used to retrieve
    private static final String EXTRA_CASE_ID_KEY = CaseInfoPagerActivity.class.getSimpleName() + "case_id";

    // Case that this tabbed Activity is dealing with. It will be used to dole out info to its child Fragments.
    private Case mCase;

    // ViewPager that hosts the section's contents.
    private ViewPager mViewPager;

    // Provides fragments for the ViewPager. FragmentPagerAdapter keeps every fragment in memory.
    private SectionsPagerAdapter mSectionsPagerAdapter;

    public static Intent newIntent(Context context, int caseID) {
        Intent intent = new Intent(context, CaseInfoPagerActivity.class);       // Intent to start this class.
        intent.putExtra(EXTRA_CASE_ID_KEY, caseID);                             // Put the extra in the intent.
        return intent;                                                          // Return the intent.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_info_pager);                          // Inflate layout and give the resulting View to this Activity.

        // Get the case ID out of the Intent, use that ID to get a case from the Caseload, and finally set the result to mCase.
        mCase = Caseload.getCaseload(this).getCase(getIntent().getIntExtra(EXTRA_CASE_ID_KEY, -5));         // Should never resolved to -5.

        // Create the Toolbar from the layout and make it act as the ActionBar for this Activity.
        Toolbar toolbar = (Toolbar) findViewById(R.id.case_info_pager_toolbar);
        setSupportActionBar(toolbar);

        // Create the ViewPager from the layout.
        mViewPager = (ViewPager) findViewById(R.id.case_info_pager_fragment_container);

        // Create the Adapter for the ViewPager and give it to the ViewPager.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Create the Tabs from the layout and give the resulting TabLayout to the ViewPager.
        TabLayout tabLayout = (TabLayout) findViewById(R.id.case_info_pager_tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final int TOTAL_PAGES = 3;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // This is where the Adapter actually returns a Fragment.
        @Override
        public Fragment getItem(int position) {

            // Check to see what it wants.
            switch(position) {

                // Get a CaseInfo fragment.
                case 0:
                    return CaseInfoFragment.newInstance(mCase.getID());
                case 1:
                    return CaseContactListFragment.newInstance(mCase.getID());
                case 2:
                    return TodoListFragment.newInstance(mCase.getID());
                default:
                    throw new RuntimeException("Position out of bounds.");
            }
        }

        // How many pages are there?
        @Override
        public int getCount() {
            return TOTAL_PAGES;
        }

        // What is the title of the fragment? Used for building tab names I guess?
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Info";
                case 1:
                    return "Contacts";
                case 2:
                    return "To-do";
                default:
                    throw new RuntimeException("Position out of bounds.");
            }
        }
    }
}
