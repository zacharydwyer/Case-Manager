package com.zakdwyer.casemanager.ui.TodoList;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zakdwyer.casemanager.R;
import com.zakdwyer.casemanager.data.Case;
import com.zakdwyer.casemanager.data.CaseTodo;
import com.zakdwyer.casemanager.data.Caseload;
import com.zakdwyer.casemanager.ui.TodoListAdd.TodoListAddActivity;

import java.util.List;

public class TodoListFragment extends Fragment {
    private Case mCase;     // Case this To-doList is working with
    private static final String ARG_CASE_ID = "case_id";

    private RecyclerView mRecyclerView;
    private CaseTodoListAdapter mCaseTodoListAdapter;

    public static TodoListFragment newInstance(int caseID) {
        Bundle args = new Bundle();                 // Create bundle to store fragment argument
        args.putInt(ARG_CASE_ID, caseID);           // Store fragment argument in bundle
        TodoListFragment fragment = new TodoListFragment();
        fragment.setArguments(args);                // Store bundle in Fragment
        return fragment;                            // Return fragment
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve case ID from args
        int caseID = getArguments().getInt(ARG_CASE_ID);        // Get sent Case ID

        // Get case by its ID.
        mCase = Caseload.getCaseload(getContext()).getCase(caseID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // INFLATE VIEW
        View viewFromLayout = inflater.inflate(R.layout.fragment_case_todo_list, container, false);

        // WIRE UP WIDGETS
        mRecyclerView = (RecyclerView) viewFromLayout.findViewById(R.id.case_todo_list_recycler_view);

        // ASSIGN PROPERTIES
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // MAKE ADAPTER UPDATE
        updateAdapter();

        // RETURN VIEW
        return viewFromLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAdapter();
    }

    ///////////////////////////
    // UPDATE ADAPTER METHOD //
    ///////////////////////////
    private void updateAdapter() {

        // Create adapter out of the associated case's to-do list.
        // This actually queries the database - no stale data should result.
        mCaseTodoListAdapter = new CaseTodoListAdapter(mCase.getTodos());

        // Assign RecyclerView this brand new Adapter
        mRecyclerView.setAdapter(mCaseTodoListAdapter);
    }

    /////////////////////////////////
    // VIEWHOLDER FOR A CASE TO-DO //
    /////////////////////////////////
    private class CaseTodoViewHolder extends  RecyclerView.ViewHolder {

        // Widgets
        private CheckBox mIsComplete_CheckBox;
        private TextView mTodoDescription_TextView;
        private ImageButton mDeleteTodo_ImageButton;

        // To-do this Viewholder is working with
        private CaseTodo mCaseTodo;

        public CaseTodoViewHolder(View itemView) {
            super(itemView);

            // Wire up widgets
            mIsComplete_CheckBox = (CheckBox) itemView.findViewById(R.id.case_todo_list_item_is_complete_checkbox);
            mTodoDescription_TextView = (TextView) itemView.findViewById(R.id.case_todo_list_item_todo_name_textview);
            mDeleteTodo_ImageButton = (ImageButton) itemView.findViewById(R.id.case_todo_list_item_delete_todo_imagebutton);

            mDeleteTodo_ImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mCase.removeCaseTodo(mCaseTodo);        // Remove to-do from database.

                    updateAdapter();                        // Reload adapter.
                    // Let adapter know the to-do was removed, too.
                    // mCaseTodoListAdapter.notifyItemRemoved(getAdapterPosition());
                }
            });

            mIsComplete_CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mCaseTodo.setIsComplete(mIsComplete_CheckBox.isChecked());      // Set isComplete value to case to-do
                    mCase.updateCaseTodo(mCaseTodo);                                // Update the to-do in the case.

                    if (mIsComplete_CheckBox.isChecked()) {
                        mTodoDescription_TextView.setTextColor(Color.LTGRAY);
                    } else {
                        mTodoDescription_TextView.setTextColor(Color.BLACK);
                    }
                }
            });
        }

        public void bindCaseTodo(CaseTodo caseTodo) {
            mCaseTodo = caseTodo;       // Assign case to-do we are now working on.

            // Assign data to widgets in this ViewHolder.
            mIsComplete_CheckBox.setChecked(caseTodo.isComplete());
            mTodoDescription_TextView.setText(mCaseTodo.getDescription());

        }
    }

    //////////////////////////////
    // ADAPTER FOR A CASE TO-DO //
    //////////////////////////////
    private class CaseTodoListAdapter extends RecyclerView.Adapter<CaseTodoViewHolder> {

        // To-do list this adapter is working with. Set in Constructor.
        private List<CaseTodo> mCaseTodoList;

        // Re-created every time the data set changes.
        public CaseTodoListAdapter(List<CaseTodo> caseTodos) {
            this.mCaseTodoList = caseTodos;
        }

        // RecyclerView needs a brand new ViewHolder.
        @Override
        public CaseTodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.viewholder_case_todo_list_item, parent, false);
            return new CaseTodoViewHolder(view);
        }

        // RecyclerView needs me to update a ViewHolder with new data.

        @Override
        public void onBindViewHolder(CaseTodoViewHolder holder, int position) {
            CaseTodo todo = mCaseTodoList.get(position);    // Get the data from the list.
            holder.bindCaseTodo(todo);                      // Bind the data.
        }

        @Override
        public int getItemCount() {
            return mCaseTodoList.size();
        }
    }


}
