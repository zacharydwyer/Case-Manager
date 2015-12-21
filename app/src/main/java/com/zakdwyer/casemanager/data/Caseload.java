package com.zakdwyer.casemanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zakdwyer.casemanager.database.wrapper.CaseCursorWrapper;
import com.zakdwyer.casemanager.database.CaseDbSQLHelper;

import java.util.ArrayList;
import java.util.List;

import static com.zakdwyer.casemanager.database.CaseDbSchema.*;

// Caseload singleton
public class Caseload {

    // Reference to database that holds the caseload.
    private SQLiteDatabase mDatabase;

    // Context
    private static Context sContext;

    // Singleton instance of this class.
    private static Caseload sCaseload;

    // Private constructor
    private Caseload(Context context) {

        // Needs access to database.
        mDatabase = new CaseDbSQLHelper(context).getWritableDatabase();
    }

    // Returns static single instance of Caseload.
    public static Caseload getCaseload(Context context) {

        // Assign context
        sContext = context;

        if(sCaseload == null) {
            sCaseload = new Caseload(sContext);
        }
        return sCaseload;
    }

    // Returns the database
    public SQLiteDatabase getDatabase() {
        return mDatabase;
    }

    /* per-case CRUD functions */

    /**
     * Add a case to the database. Case's ID can be whatever you want - it won't be used.
     * @param c
     */
    public void addCase(Case c) {

        // Get content values for case (last name, first name)
        ContentValues valuesForCase = getContentValues(c);

        // Insert the values into the appropriate table.
        mDatabase.insert(CaseTable.NAME, null, valuesForCase);
    }

    public Case getCase(int id) {

        // Convert the id to a string
        String idString = Integer.toString(id);

        // Get a table that has the case with the given id
        CaseCursorWrapper cursor = queryCaseTable(CaseTable.Cols.ID + " = ?", new String[]{idString});

        try {

            // If the amount of rows is 0
            if (cursor.getCount() == 0) {

                // Throw an exception - this program should only be trying to get cases with a specified ID!
                throw new RuntimeException("At Caseload.java - tried to get a case with an ID that didn't return anything!");
            }

            // Cursor returned more than 1.

            // Move the cursor to the first row in the table.
            cursor.moveToFirst();

            // Get the case out of the cursor's current position
            return cursor.getCase();

        } finally {
            cursor.close();
        }
    }

    public void updateCase(Case c) {

        // Get ID of case in the form of a string, so it can be used in the instruction.
        String id = Integer.toString(c.getID());

        // Get column data for the case.
        ContentValues values = getContentValues(c);

        // Update the table "cases" with the new values at the row with the specified ID.
        mDatabase.update(CaseTable.NAME, values, CaseTable.Cols.ID + " = ?", new String[]{id});
    }

    public void removeCase(Case c) {

        // Get ID of case in the form of a string, so it can be used in the instruction.
        String id = Integer.toString(c.getID());

        // Remove the case with the specified ID.
        mDatabase.delete(CaseTable.NAME, CaseTable.Cols.ID + " = ?", new String[]{id});
    }

    public List<Case> getCases() {

        // List to return
        List<Case> cases = new ArrayList<>();

        // Get ALL of the cases from the Case Table.
        CaseCursorWrapper cursor = queryCaseTable(null, null);

        try {
            // Move the pointer to the first record.
            cursor.moveToFirst();

            // While we are not at the end of the table (past the end of the table, actually)
            while(! cursor.isAfterLast()) {

                // Get the case that the pointer is pointing to
                cases.add(cursor.getCase());

                // Move the pointer up one
                cursor.moveToNext();
            }
        } finally {
            // Close the cursor
            cursor.close();
        }

        // Return cases. Will be null if there were no cases.
        return cases;
    }

    /* utility methods */

    // Get content values that can be used to insert/update a case.
    private static ContentValues getContentValues(Case c) {

        // Used by ContentResolver.
        ContentValues values = new ContentValues();

        // It only needs the name information - the ID is created automatically.
        values.put(CaseTable.Cols.NAME, c.getName());

        return values;
    }

    // Get a CaseCursorWrapper (with a cursor in it) to get a list or a single case
    private CaseCursorWrapper queryCaseTable(String whereClause, String[] whereArgs) {

        // Get the cursor
        Cursor caseTableCursor = mDatabase.query(CaseTable.NAME, null, whereClause, whereArgs, null, null, null);

        // Return the special wrapper for the cursor that has the ability to get the case.
        return new CaseCursorWrapper(sContext, caseTableCursor);
    }

}
