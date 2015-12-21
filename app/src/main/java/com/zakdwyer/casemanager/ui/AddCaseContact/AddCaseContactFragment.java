package com.zakdwyer.casemanager.ui.AddCaseContact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.zakdwyer.casemanager.R;
import com.zakdwyer.casemanager.data.Case;
import com.zakdwyer.casemanager.data.CaseContact;
import com.zakdwyer.casemanager.data.Caseload;

public class AddCaseContactFragment extends Fragment {
    private Case mCase;
    private CaseContact mNewCaseContact;

    private static final String ARG_CASE_ID = "case_id";

    private EditText mTitleField;
    private EditText mDescriptionField;

    public static AddCaseContactFragment newInstance(int caseID) {
        Bundle args = new Bundle();                 // Create bundle to store fragment argument
        args.putInt(ARG_CASE_ID, caseID);           // Store fragment argument in bundle (the case ID)

        AddCaseContactFragment fragment = new AddCaseContactFragment();
        fragment.setArguments(args);                // Store bundle in Fragment
        return fragment;                            // Return fragment
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int caseID = getArguments().getInt(ARG_CASE_ID);
        mCase = Caseload.getCaseload(getContext()).getCase(caseID);
        mNewCaseContact = new CaseContact(0, mCase.getID(), "", "");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Add Case Contact");

        View view = inflater.inflate(R.layout.fragment_add_case_contact, container, false);

        mTitleField = (EditText) view.findViewById(R.id.add_case_contact_title_field);
        mDescriptionField = (EditText) view.findViewById(R.id.add_case_contact_description_field);

        if(mTitleField == null) {
            throw new RuntimeException("Title field is null");
        }

        if(mDescriptionField == null) {
            throw new RuntimeException("Desc. field null.");
        }

        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNewCaseContact.setTitle(mTitleField.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // nothing
            }
        });

        mDescriptionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNewCaseContact.setDescription(mDescriptionField.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // nothing
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        mCase.addCaseContact(mNewCaseContact);
    }
}
