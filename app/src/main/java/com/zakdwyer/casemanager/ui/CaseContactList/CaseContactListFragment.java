package com.zakdwyer.casemanager.ui.CaseContactList;

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
import com.zakdwyer.casemanager.data.CaseContact;
import com.zakdwyer.casemanager.data.Caseload;
import com.zakdwyer.casemanager.ui.AddCaseContact.AddCaseContactActivity;
import com.zakdwyer.casemanager.ui.CaseAdd.CaseAddActivity;
import com.zakdwyer.casemanager.ui.EditCaseContact.EditCaseContactActivity;

import java.util.List;

public class CaseContactListFragment extends Fragment {

    private Case mCase;     // Case this Fragment has access to.
    private static final String ARG_CASE_ID = "case_id";

    private RecyclerView mRecyclerView;
    private FloatingActionButton mAddCaseContactActionButton;
    private CaseContactListAdapter mCaseContactListAdapter;

    public static CaseContactListFragment newInstance(int caseID) {
        Bundle args = new Bundle();                 // Create bundle to store fragment argument
        args.putInt(ARG_CASE_ID, caseID);           // Store fragment argument in bundle
        CaseContactListFragment fragment = new CaseContactListFragment();
        fragment.setArguments(args);                // Store bundle in Fragment
        return fragment;                            // Return fragment
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get case ID from arguments.
        int caseID = getArguments().getInt(ARG_CASE_ID);

        // Assign case.
        mCase = Caseload.getCaseload(getContext()).getCase(caseID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Create a View out of the associated layout. Don't add it to its container yet.
        View view = inflater.inflate(R.layout.fragment_case_contact_list, container, false);

        // Obtain recycler view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.case_contact_list_recycler_view);

        // Obtain FAB that adds a case
        mAddCaseContactActionButton = (FloatingActionButton) view.findViewById(R.id.case_contact_list_add_case_fab);

        // Give RecyclerView its Layout Manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getActivity().setTitle(mCase.getName() + "'s Case Contacts");

        mAddCaseContactActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddCaseContactActivity.newIntent(getContext(), mCase.getID());
                startActivity(intent);
            }
        });

        // Make adapter populate itself
        updateAdapter();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAdapter();
    }

    private void updateAdapter() {

        // Get list of case contacts from the case.
        List<CaseContact> caseContacts = mCase.getCaseContacts();

        // Create adapter
        mCaseContactListAdapter = new CaseContactListAdapter(caseContacts);

        // Assign RecyclerView its Adapter
        mRecyclerView.setAdapter(mCaseContactListAdapter);
    }

    ///////////////////////////////////
    // VIEWHOLDER FOR A CASE CONTACT //
    ///////////////////////////////////
    private class CaseContactViewHolder extends RecyclerView.ViewHolder {

        // Widgets
        private TextView mCaseContactTitle;
        private TextView mCaseContactDescription;
        private ImageButton mDeleteCaseContact;

        // Case Contact this viewholder is working with
        private CaseContact mCaseContact;

        public CaseContactViewHolder(View itemView) {
            super(itemView);

            // Wire widgets up
            mCaseContactTitle = (TextView) itemView.findViewById(R.id.case_contact_list_title);
            mCaseContactDescription = (TextView) itemView.findViewById(R.id.case_contact_list_description);
            mDeleteCaseContact = (ImageButton) itemView.findViewById(R.id.case_contact_list_delete_case_contact);

            // DELETE Case Contact
            mDeleteCaseContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Show an alert dialog
                    new AlertDialog.Builder(getContext())
                            .setTitle("Delete case contact")
                            .setMessage("Are you sure you want to delete this case contact?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    // Remove case contact
                                    mCase.removeCaseContact(mCaseContact);

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

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intent = EditCaseContactActivity.newIntent(getActivity(), mCase.getID(), mCaseContact.getID());
                    startActivity(intent);
                    return true;
                }
            });
        }

        public void bindCaseContact(CaseContact caseContact) {
            mCaseContact = caseContact;

            // Set attributes of widgets.
            mCaseContactTitle.setText(mCaseContact.getTitle());
            mCaseContactDescription.setText(mCaseContact.getDescription());
        }
    }

    ////////////////////////////////
    // ADAPTER FOR A CASE CONTACT //
    ////////////////////////////////
    private class CaseContactListAdapter extends RecyclerView.Adapter<CaseContactViewHolder> {
        private List<CaseContact> mCaseContactList;

        public CaseContactListAdapter(List<CaseContact> caseContactList) {
            this.mCaseContactList = caseContactList;
        }

        @Override
        public CaseContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.viewholder_case_contact_list_item, parent, false);
            return new CaseContactViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CaseContactViewHolder holder, int position) {
            CaseContact contact = mCaseContactList.get(position);
            holder.bindCaseContact(contact);
        }

        @Override
        public int getItemCount() {
            return mCaseContactList.size();
        }
    }
}
