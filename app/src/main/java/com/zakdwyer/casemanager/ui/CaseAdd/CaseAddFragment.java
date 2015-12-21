package com.zakdwyer.casemanager.ui.CaseAdd;

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
import com.zakdwyer.casemanager.data.Caseload;


public class CaseAddFragment extends Fragment {
    private EditText mNameField;
    private Button mSubmitButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("New Case...");

        // Inflate layout.
        View view = inflater.inflate(R.layout.fragment_case_add, container, false);

        // Obtain text view
        mNameField = (EditText) view.findViewById(R.id.case_add_edit_name_field);

        // Obtain button
        mSubmitButton = (Button) view.findViewById(R.id.case_add_submit_button);

        // Handle button
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get case info out of Name field. It's okay if it's blank - we don't care.
                String name = mNameField.getText().toString();

                // Add the case. Use 0 for the ID. It won't matter.
                Caseload.getCaseload(getContext()).addCase(new Case(getContext(), 0, name));

                // Finish the activity
                getActivity().finish();
            }
        });

        return view;
    }
}
