package com.zakdwyer.casemanager.ui.CaseInfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zakdwyer.casemanager.R;
import com.zakdwyer.casemanager.data.Case;
import com.zakdwyer.casemanager.data.Caseload;

public class CaseInfoFragment extends Fragment {

    private Case mCase;

    private static final String ARG_CASE_ID = "case_id";

    private EditText mNameField;
    private Button mSubmitButton;

    // CONSTRUCTOR //
    public static CaseInfoFragment newInstance(int caseID) {
        Bundle args = new Bundle();                             // Create a Bundle to store an argument that will be stored in the Fragment returned by this method.
        args.putInt(ARG_CASE_ID, caseID);                       // Store the caseID received in the Bundle.
        CaseInfoFragment fragment = new CaseInfoFragment();     // Create a new Fragment.
        fragment.setArguments(args);                            // Store the Bundle inside of the Fragment.
        return fragment;                                        // Return the Fragment.
    }

    // INITIALIZE DATA //
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve case ID from Bundle.
        int caseID = getArguments().getInt(ARG_CASE_ID);        // Get sent Case ID

        // Get a Case with the given ID.
        mCase = Caseload.getCaseload(getContext()).getCase(caseID);
    }

    // INITIALIZE WIDGETS //
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Create view from layout.
        View view = inflater.inflate(R.layout.fragment_case_info, container, false);

        // Retrieve widgets from inflated View.
        mNameField = (EditText) view.findViewById(R.id.case_info_name_field);
        mSubmitButton = (Button) view.findViewById(R.id.case_info_submit_button);

        // Set name field's text to the case's name
        mNameField.setText(mCase.getName());

        // Whenever something happens to this EditText...
        mNameField.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            // Boolean indicates whether you consumed the action...?
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                // If the action was "action done" meaning they clicked "Done" on their keyboard
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    mNameField.setFocusable(false);
                    mNameField.setFocusableInTouchMode(true);

                    // Click the submit button.
                    mSubmitButton.performClick();
                    return true;
                }
                return false;
            }
        });

        // Handle button click. Can be "clicked" by the enter key being submitted in
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // If the name field is valid...
                if(isNameFieldValid(mNameField)) {

                    // Update our copy of the case.
                    mCase.setName(mNameField.getText().toString());

                    // Update the database.
                    Caseload.getCaseload(getContext()).updateCase(mCase);

                    // Show toast to say it was updated.
                    Toast.makeText(getContext(), R.string.case_info_toast_success, Toast.LENGTH_SHORT).show();
                } else {

                    // Don't do anything but show an error
                    Toast.makeText(getContext(), R.string.case_info_toast_failure, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private boolean isNameFieldValid(EditText nameField) {
        if(mNameField.getText().toString().trim().equals("")) {
            mNameField.setError("Cannot be empty");
            return false;
        } else {
            mNameField.setError(null);
            return true;
        }
    }
}
