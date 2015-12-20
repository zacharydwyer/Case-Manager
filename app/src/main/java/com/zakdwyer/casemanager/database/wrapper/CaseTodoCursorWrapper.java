package com.zakdwyer.casemanager.database.wrapper;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.zakdwyer.casemanager.data.CaseTodo;

import static com.zakdwyer.casemanager.database.CaseDbSchema.*;

public class CaseTodoCursorWrapper  extends CursorWrapper {

    public CaseTodoCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public CaseTodo getCaseTodo() {

        // Get values out of the cursor's result set
        int id = getInt(getColumnIndex(CaseTodoTable.Cols.ID));
        int caseID = getInt(getColumnIndex(CaseTodoTable.Cols.FK_CASEID));
        String description = getString(getColumnIndex(CaseTodoTable.Cols.DESCRIPTION));
        int rawIsComplete = getInt(getColumnIndex(CaseTodoTable.Cols.IS_COMPLETE));

        // Handles converting the true/false numeric value (0 = false, 1 = true)
        boolean isComplete;

        if (rawIsComplete == 0) {
            isComplete = false;
        } else if (rawIsComplete == 1) {
            isComplete = true;
        } else {
            throw new RuntimeException("The raw \"is complete\" value was not 1 or 0. Must be 1 or 0!");
        }

        // Create and return a created to-do.
        return new CaseTodo(id, caseID, description, isComplete);
    }
}
