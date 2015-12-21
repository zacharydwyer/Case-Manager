package com.zakdwyer.casemanager.ui.CaseList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zakdwyer.casemanager.R;
import com.zakdwyer.casemanager.data.Case;
import com.zakdwyer.casemanager.data.Caseload;
import com.zakdwyer.casemanager.ui.CaseAdd.CaseAddActivity;
import com.zakdwyer.casemanager.ui.CaseContactList.CaseContactListActivity;
import com.zakdwyer.casemanager.ui.CaseInfo.CaseInfoActivity;
import com.zakdwyer.casemanager.ui.TodoList.TodoListActivity;

import java.util.List;

public class CaseListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private FloatingActionButton mAddCaseActionButton;
    private CaseListAdapter mCaseListAdapter;

    // onCreateView - Widgets are wired up, handles add case button, populates Adapter
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Create a View out of the associated layout. Don't add it to its container yet.
        View view = inflater.inflate(R.layout.fragment_case_list, container, false);

        // Obtain recycler view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.case_list_recycler_view);

        // Obtain FAB that adds a case
        mAddCaseActionButton = (FloatingActionButton) view.findViewById(R.id.case_list_add_case_fab);

        // Give RecyclerView its Layout Manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // ADD CASE BUTTON ONCLICK
        mAddCaseActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToStartAddCaseActivity = new Intent(getActivity(), CaseAddActivity.class);
                startActivity(intentToStartAddCaseActivity);
            }
        });

        // Make adapter populate itself
        updateAdapter();

        return view;
    }

    // onResume - Update the adapter. A case's name could have been edited, or the case deleted altogether!
    @Override
    public void onResume() {
        super.onResume();
        updateAdapter();        // Update the adapter
    }

    // updateAdapter - creates a new adapter if there already isn't one, or notifies the adapter that the data set changed if there already is one.
    private void updateAdapter() {

        // Get list of cases from Database.
        List<Case> cases = Caseload.getCaseload(getContext()).getCases();

        // Create adapter
        mCaseListAdapter = new CaseListAdapter(cases);

        // Assign RecyclerView its Adapter
        mRecyclerView.setAdapter(mCaseListAdapter);
    }

    ///////////////////////////
    // VIEWHOLDER FOR A CASE //
    ///////////////////////////
    private class CaseViewHolder extends RecyclerView.ViewHolder {

        private TextView mCaseName_TextView;
        private ImageButton mDeleteCase_ImageButton;
        private ImageButton mViewCaseContacts_ImageButton;
        private ImageButton mViewTodos_ImageButton;

        // Case this viewholder is working with.
        private Case mCase;

        // Called by the Adapter to create a new ViewHolder - wires up widgets and handles button events.
        public CaseViewHolder(View itemView) {
            super(itemView);

            // Wire up widgets
            mCaseName_TextView = (TextView) itemView.findViewById(R.id.case_list_item_case_name_field);
            mDeleteCase_ImageButton = (ImageButton) itemView.findViewById(R.id.list_item_delete_case_button);
            mViewCaseContacts_ImageButton = (ImageButton) itemView.findViewById(R.id.case_list_item_view_case_contacts_button);
            mViewTodos_ImageButton = (ImageButton) itemView.findViewById(R.id.case_list_item_view_todos_button);

            mViewTodos_ImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentToStartCaseTodoList = TodoListActivity.newIntent(getActivity(), mCase.getID());
                    startActivity(intentToStartCaseTodoList);
                }
            });

            mViewCaseContacts_ImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = CaseContactListActivity.newIntent(getActivity(), mCase.getID());
                    startActivity(intent);
                }
            });

              // NOT IMPLEMENTED - CAUSED BUGS
//            // Name field edited
//            mCaseName_EditText.addTextChangedListener(new TextWatcher() {
//                String text;
//
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                    // Set text from edit text.
//                    text = mCaseName_EditText.getText().toString();
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//
//                    // Hide the cursor
//                    mCaseName_EditText.setCursorVisible(false);
//
//                    // Set this case's name to the one retrieved.
//                    mCase.setName(text);
//
//                    // Update the case.
//                    Caseload handleToCaseload = Caseload.getCaseload(getContext());
//                    handleToCaseload.updateCase(mCase);
//
//                    // Update the adapter.
//                    updateAdapter();
//                }
//            });

            // DELETE CASE BUTTON
            mDeleteCase_ImageButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    // Show an alert dialog
                    new AlertDialog.Builder(getContext())
                        .setTitle("Delete case")
                        .setMessage("Are you sure you want to delete this case?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // Remove the case.
                                Caseload.getCaseload(getContext()).removeCase(mCase);

                                // Update the adapter (the dataset changed)
                                updateAdapter();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing.
                            }
                        })
                        .show();
                }
            });

            // ENTIRE ITEMVIEW CLICKED - Start case info activity
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intentToStartCaseInfo = CaseInfoActivity.newIntent(getActivity(), mCase.getID());
                    startActivity(intentToStartCaseInfo);
                    return true;
                }
            });
        }

        // Called by the Adapter to bind a Case to this ViewHolder.
        public void bindCase(Case aCase) {

            // This is now the case that we are working with. We can use this to start the CaseInfo, TodoList, and CaseContactList activities.
            mCase = aCase;

            // Assign name from case.
            mCaseName_TextView.setText(mCase.getName());
        }
    }

    ////////////////////////
    // ADAPTER FOR A CASE //
    ////////////////////////
    private class CaseListAdapter extends RecyclerView.Adapter<CaseViewHolder> {

        // Case list this adapter is working with.
        private List<Case> mCases;

        public CaseListAdapter(List<Case> cases) {
            this.mCases = cases;
        }

        // Called by RecyclerView to get a ViewHolder. Only happens a few times.
        @Override
        public CaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());                         // Get layout inflater
            View view = layoutInflater.inflate(R.layout.viewholder_case_list_item, parent, false);      // Create view from layout
            return new CaseViewHolder(view);                                                            // Create a new CaseViewHolder from the view and return it.
        }

        // Called by RecyclerView to update a ViewHolder's data.
        @Override
        public void onBindViewHolder(CaseViewHolder holder, int position) {
            Case aCase = mCases.get(position);               // Get the case at the specified position, corresponding to where we are at in the RecyclerView.
            holder.bindCase(aCase);
        }

        @Override
        public int getItemCount() {
            return mCases.size();
        }
    }
}
