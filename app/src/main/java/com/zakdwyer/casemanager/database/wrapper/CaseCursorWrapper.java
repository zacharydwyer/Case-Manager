package com.zakdwyer.casemanager.database.wrapper;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.zakdwyer.casemanager.data.Case;

import static com.zakdwyer.casemanager.database.CaseDbSchema.*;

public class CaseCursorWrapper extends CursorWrapper {
    public CaseCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Case getCase() {

        /* Get values from the cursor that was sent to us */
        int id = getInt(getColumnIndex(CaseTable.Cols.ID));                         // Get id of case
        String name = getString(getColumnIndex(CaseTable.Cols.NAME));    // Get name

        /* Create a new case from these values and return it */
        return new Case(id, name);
    }
}
