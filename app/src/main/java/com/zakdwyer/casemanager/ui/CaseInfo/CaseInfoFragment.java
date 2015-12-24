package com.zakdwyer.casemanager.ui.CaseInfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.zakdwyer.casemanager.R;
import com.zakdwyer.casemanager.data.Case;
import com.zakdwyer.casemanager.data.Caseload;

public class CaseInfoFragment extends Fragment {

    private Case mCase;

    private static final String ARG_CASE_ID = "case_id";

    private EditText mNameField;

    public static CaseInfoFragment newInstance(int caseID) {
        Bundle args = new Bundle();                 // Create bundle to store fragment argument
        args.putInt(ARG_CASE_ID, caseID);           // Store fragment argument in bundle (the case ID)
        CaseInfoFragment fragment = new CaseInfoFragment();
        fragment.setArguments(args);                // Store bundle in Fragment
        return fragment;                            // Return fragment
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve case ID from args
        int caseID = getArguments().getInt(ARG_CASE_ID);        // Get sent Case ID

        // Get case by its ID.
        mCase = Caseload.getCaseload(getContext()).getCase(caseID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Create view from layout.
        View view = inflater.inflate(R.layout.fragment_case_info, container, false);

        // Get name field
        mNameField = (EditText) view.findViewById(R.id.case_info_name_field);

        // Set name field's text to the case's name
        mNameField.setText(mCase.getName());

        // Handle text changing
        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // Get reference to main list of cases
                Caseload caseload = Caseload.getCaseload(getContext());

                // Edit our current case's name to the text in the name field
                mCase.setName(mNameField.getText().toString());

                // Update the case
                caseload.updateCase(mCase);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Nothing
            }
        });


        return view;
    }
}
