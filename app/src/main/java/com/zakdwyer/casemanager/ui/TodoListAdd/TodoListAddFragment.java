package com.zakdwyer.casemanager.ui.TodoListAdd;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.zakdwyer.casemanager.R;
import com.zakdwyer.casemanager.data.Case;
import com.zakdwyer.casemanager.data.CaseTodo;
import com.zakdwyer.casemanager.data.Caseload;

// NEEDS CASE ID AND TO-DO ID
public class TodoListAddFragment extends Fragment {

    private CaseTodo mCaseTodo;
    private Case mCase;

    private static final String ARG_CASE_ID = "case_id";

    private EditText mTodoDescriptionField;
    private Button mSubmitButton;

    public static TodoListAddFragment newInstance(int caseID) {
        Bundle args = new Bundle();                 // Create bundle to store fragment argument
        args.putInt(ARG_CASE_ID, caseID);           // Store fragment arguments
        TodoListAddFragment fragment = new TodoListAddFragment();
        fragment.setArguments(args);                // Store bundle in Fragment
        return fragment;                            // Return fragment
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve case ID from args
        int caseID = getArguments().getInt(ARG_CASE_ID);

        // Get the case we will add this to-do to.
        mCase = Caseload.getCaseload(getContext()).getCase(caseID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Set Activity title appropriately.
        getActivity().setTitle("Edit Todo for " + mCase.getName());

        // Create view.
        View view = inflater.inflate(R.layout.fragment_todo_add, container, false);

        // Wire up widgets
        mTodoDescriptionField = (EditText) view.findViewById(R.id.todo_add_name_field);
        mSubmitButton = (Button) view.findViewById(R.id.todo_add_submit_button);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descriptionOfTodo = mTodoDescriptionField.getText().toString();
                mCase.addCaseTodo(new CaseTodo(0, mCase.getID(), descriptionOfTodo));
                getActivity().finish();
            }
        });

        return view;
    }
}
