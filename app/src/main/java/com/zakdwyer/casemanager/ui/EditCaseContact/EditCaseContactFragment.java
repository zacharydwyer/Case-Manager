package com.zakdwyer.casemanager.ui.EditCaseContact;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zakdwyer.casemanager.R;
import com.zakdwyer.casemanager.data.Case;
import com.zakdwyer.casemanager.data.CaseContact;
import com.zakdwyer.casemanager.data.Caseload;

public class EditCaseContactFragment extends Fragment {
    private Case mCase;
    private CaseContact mCaseContact;

    private static final String ARG_CASE_ID = "case_id";
    private static final String ARG_CASE_CONTACT_ID = "case_contact_id";

    private EditText mTitleField;
    private EditText mDescriptionField;
    private Button mSubmitButton;

    public static EditCaseContactFragment newInstance(int caseID, int caseContactID) {
        Bundle args = new Bundle();                 // Create bundle to store fragment argument
        args.putInt(ARG_CASE_ID, caseID);           // Store fragment argument in bundle (the case ID)
        args.putInt(ARG_CASE_CONTACT_ID, caseContactID);

        EditCaseContactFragment fragment = new EditCaseContactFragment();
        fragment.setArguments(args);                // Store bundle in Fragment
        return fragment;                            // Return fragment
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int caseID = getArguments().getInt(ARG_CASE_ID);
        int caseContactID = getArguments().getInt(ARG_CASE_CONTACT_ID);

        mCase = Caseload.getCaseload(getContext()).getCase(caseID);
        mCaseContact = mCase.getCaseContact(caseContactID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Edit Case Contact");

        View view = inflater.inflate(R.layout.fragment_edit_case_contact, container, false);

        mTitleField = (EditText) view.findViewById(R.id.edit_case_contact_title_field);
        mDescriptionField = (EditText) view.findViewById(R.id.edit_case_contact_description_field);
        mSubmitButton = (Button) view.findViewById(R.id.edit_case_contact_submit_button);

        // Change properties accordingly
        mTitleField.setText(mCaseContact.getTitle());
        mDescriptionField.setText(mCaseContact.getDescription());

        // Handle submit button
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Set properties of Case Contact from the properties of the widgets.
                mCaseContact.setTitle(mTitleField.getText().toString());
                mCaseContact.setDescription(mDescriptionField.getText().toString());

                // Update the newly formed case contact.
                mCase.updateCaseContact(mCaseContact);

                // Finish activity
                getActivity().finish();
            }
        });

        return view;
    }
}
