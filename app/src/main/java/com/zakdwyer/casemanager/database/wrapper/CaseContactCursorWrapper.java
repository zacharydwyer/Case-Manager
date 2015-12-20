package com.zakdwyer.casemanager.database.wrapper;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.zakdwyer.casemanager.data.CaseContact;

import static com.zakdwyer.casemanager.database.CaseDbSchema.*;

/**
 * Cursor wrapper specifically for a Contact
 */
public class CaseContactCursorWrapper extends CursorWrapper {

    public CaseContactCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    // Gets a case contact out of the row of data that this cursor is currently pointing to.
    public CaseContact getCaseContact() {

        // Get values out of the row the cursor is currently pointing to
        int id = getInt(getColumnIndex(CaseContactTable.Cols.ID));              // Id of Case Contact
        int caseID = getInt(getColumnIndex(CaseContactTable.Cols.FK_CASEID));   // Associated Case ID
        String title = getString(getColumnIndex(CaseContactTable.Cols.TITLE));  // Title of Case Contact
        String description = getString(getColumnIndex(CaseContactTable.Cols.DESCRIPTION));  // Description
        long dateAndTimeOfContact = getLong(getColumnIndex(CaseContactTable.Cols.DATE_AND_TIME_OCCURRED));

        // Create and return a created Case Contact
        return new CaseContact(id, caseID, title, description, dateAndTimeOfContact);
    }
}
