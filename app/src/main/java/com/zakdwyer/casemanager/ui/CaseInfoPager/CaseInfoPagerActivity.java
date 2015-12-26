package com.zakdwyer.casemanager.ui.CaseInfoPager;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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
import com.zakdwyer.casemanager.ui.AddCaseContact.AddCaseContactActivity;
import com.zakdwyer.casemanager.ui.CaseContactList.CaseContactListActivity;
import com.zakdwyer.casemanager.ui.CaseContactList.CaseContactListFragment;
import com.zakdwyer.casemanager.ui.CaseInfo.CaseInfoFragment;
import com.zakdwyer.casemanager.ui.TodoList.TodoListFragment;
import com.zakdwyer.casemanager.ui.TodoListAdd.TodoListAddActivity;

public class CaseInfoPagerActivity extends AppCompatActivity {

    private static final int CASE_INFO_INDEX = 0;
    private static final int CASE_CONTACT_LIST_INDEX = 1;
    private static final int TODO_LIST_INDEX = 2;
    private static final int TOTAL_PAGES = 3;

    // Constant key; used to retrieve
    private static final String EXTRA_CASE_ID_KEY = CaseInfoPagerActivity.class.getSimpleName() + "case_id";

    // Case that this tabbed Activity is dealing with. It will be used to dole out info to its child Fragments.
    private Case mCase;

    // Fragment that this viewPager is working with
    private Fragment mCurrentFragment;

    // ViewPager that hosts the section's contents.
    private ViewPager mViewPager;
    private FloatingActionButton mFab;

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

        // Get floating action button from the layout
        mFab = (FloatingActionButton) findViewById(R.id.case_info_pager_fab);

        // Hide the fab at first - it lands on a tab that doesn't have an adding ability.
        mFab.hide();

        // Create the Adapter for the ViewPager and give it to the ViewPager.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Create the Tabs from the layout and give the resulting TabLayout to the ViewPager.
        TabLayout tabLayout = (TabLayout) findViewById(R.id.case_info_pager_tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // LISTENERS //
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            // Handle hiding the
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case CASE_INFO_INDEX:
                        // Info
                        if(mFab.isShown()) {
                            mFab.hide();
                        }
                        break;
                    case CASE_CONTACT_LIST_INDEX:
                        // Contacts
                        if(!mFab.isShown()) {
                            mFab.show();
                        }
                        break;
                    case TODO_LIST_INDEX:
                        // To-do
                        if(!mFab.isShown()) {
                            mFab.show();
                        }
                        break;
                    default:
                        throw new RuntimeException("Position out of bounds. Position given: " + position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Look at what page we're on.
                switch(mViewPager.getCurrentItem()) {
                    case CASE_INFO_INDEX:

                        // Case info tab. This shouldn't happen.
                        throw new RuntimeException("User hit the + FAB on a page that shouldn't have it!");
                    case CASE_CONTACT_LIST_INDEX:

                        // Case contact list tab.
                        handleAddingACaseContact();
                        break;
                    case TODO_LIST_INDEX:

                        // To-do list
                        handleAddingATodo();
                        break;
                    default:

                        // This shouldn't happen.
                        throw new RuntimeException("View pager gave a page number that's out of bounds! That really shouldn't happen!");
                }
            }
        });
    }


    private void handleAddingATodo() {
        Intent intentToStartTodoListAdd = TodoListAddActivity.newIntent(this, mCase.getID());
        startActivity(intentToStartTodoListAdd);
    }

    private void handleAddingACaseContact() {
        Intent intent = AddCaseContactActivity.newIntent(this, mCase.getID());
        startActivity(intent);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // This is where the Adapter actually returns a Fragment.
        @Override
        public Fragment getItem(int position) {

            // Check to see what it wants.
            switch(position) {

                // Get a CaseInfo fragment.
                case CASE_INFO_INDEX:
                    return CaseInfoFragment.newInstance(mCase.getID());
                case CASE_CONTACT_LIST_INDEX:
                    return CaseContactListFragment.newInstance(mCase.getID());
                case TODO_LIST_INDEX:
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
                case CASE_INFO_INDEX:
                    return "Info";
                case CASE_CONTACT_LIST_INDEX:
                    return "Contacts";
                case TODO_LIST_INDEX:
                    return "To-do";
                default:
                    throw new RuntimeException("Position out of bounds.");
            }
        }
    }
}
