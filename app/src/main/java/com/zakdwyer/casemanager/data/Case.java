package com.zakdwyer.casemanager.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zakdwyer.casemanager.database.wrapper.CaseContactCursorWrapper;
import com.zakdwyer.casemanager.database.wrapper.CaseTodoCursorWrapper;

import java.util.ArrayList;
import java.util.List;

import static com.zakdwyer.casemanager.database.CaseDbSchema.*;

/**
 * Represents a person on the caseload. The most top-level data object.
 */
public class Case {

    private int mID;
    private String mName;
    private SQLiteDatabase mDatabase;

    // Constructor
    public Case(int id, String name) {

        // Assign member variables.
        mID = id;
        mName = name;

        // Every time the Case is created, it gets the associated database.
        // This is so it can create todos and contacts.
        mDatabase = Caseload.getCaseload().getDatabase();
    }

    // Getters and Setters
    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    /////////////////////////////
    // CASE CONTACT MANAGEMENT //
    /////////////////////////////

    // ADD CASE CONTACT //
    public void addCaseContact(CaseContact caseContact) {
        ContentValues values = getContentValues(caseContact);       // Retrieve values for case contact
        mDatabase.insert(CaseContactTable.NAME, null, values);      // Insert values into case contact table
    }

    // UPDATE CASE CONTACT //
    public void updateCaseContact(CaseContact caseContact) {
        String idString = Integer.toString(caseContact.getID());    // Parse out ID of case contact
        ContentValues values = getContentValues(caseContact);       // Get content values out of case contact

        // Update the Case Contact row with the specified ID using the ContentValues we just got.
        mDatabase.update(CaseTodoTable.NAME, values, CaseTable.Cols.ID + " = ?", new String[]{idString});
    }

    // GET SINGLE CASE CONTACT //
    public CaseContact getCaseContact(int id) {

        // Get the case contact with the specified ID
        CaseContactCursorWrapper cursorWrapper = queryCaseContactTable(CaseContactTable.Cols.ID + " = ?", new String[]{Integer.toString(id)});

        try {

            // If there is not a case contact with that ID...
            if (cursorWrapper.getCount() == 0) {
                throw new RuntimeException("Tried to get a case contact with id: " + id + " but it didn't exist!");
            } else if (cursorWrapper.getCount() > 1) {
                throw new RuntimeException("There are more than one case contacts with that ID! This shouldn't happen!");
            }

            cursorWrapper.moveToFirst();            // Go to the first row
            return cursorWrapper.getCaseContact();  // Return the case contact in that row
        } finally {
            cursorWrapper.close();                  // Close the cursor
        }
    }

    // GET MULTIPLE CASE CONTACTS //
    public List<CaseContact> getCaseContacts() {
        List<CaseContact> caseContacts = new ArrayList<>();

        // Get the ID of this case
        String idOfThisCase = Integer.toString(this.getID());

        // Get a table full of case contacts that match the ID of this case.
        CaseContactCursorWrapper cursorWrapper = queryCaseContactTable(CaseContactTable.Cols.FK_CASEID + " = ?", new String[]{idOfThisCase});

        try {
            cursorWrapper.moveToFirst();                                // First row...
            while (!cursorWrapper.isAfterLast()) {                      // While the cursor isn't pointing to a row after the last one...
                caseContacts.add(cursorWrapper.getCaseContact());           // Add the case contact at the current row
                cursorWrapper.moveToNext();                                 // Increment cursor
            }
        } finally {
            cursorWrapper.close();
        }

        return caseContacts;
    }

    // Used to query the table to get case contact(s)
    private CaseContactCursorWrapper queryCaseContactTable(String whereClause, String[] whereArgs) {

        // Execute query and get cursor over result set
        Cursor cursor = mDatabase.query(
                CaseContactTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new CaseContactCursorWrapper(cursor);
    }

    // Used to insert/update a case contact
    private ContentValues getContentValues(CaseContact caseContact) {
        ContentValues values = new ContentValues();

        // Use this case's ID as the foreign key.
        values.put(CaseContactTable.Cols.FK_CASEID, this.getID());

        values.put(CaseContactTable.Cols.TITLE, caseContact.getTitle());
        values.put(CaseContactTable.Cols.DESCRIPTION, caseContact.getDescription());
        values.put(CaseContactTable.Cols.DATE_AND_TIME_OCCURRED, caseContact.getDateAndTimeOfContact());

        return values;
    }

    //////////////////////
    // TO-DO MANAGEMENT //
    //////////////////////

    // ADD //
    public void addCaseTodo(CaseTodo todo) {
        ContentValues valuesForTodo = getContentValues(todo);

        // Add values to database
        mDatabase.insert(CaseTodoTable.NAME, null, valuesForTodo);
    }

    // UPDATE //
    public void updateCaseTodo(CaseTodo todo) {
        String idString = Integer.toString(todo.getID());       // Parse out the ID to a string
        ContentValues valuesForTodo = getContentValues(todo);   // Get content values for this to-do

        mDatabase.update(CaseTodoTable.NAME, valuesForTodo, CaseTable.Cols.ID + " = ?", new String[]{idString});
    }

    // GET ALL TODOS ASSOCIATED WITH THIS CASE //
    public List<CaseTodo> getTodos() {
        List<CaseTodo> caseTodos = new ArrayList<>();

        // Get the ID of this case
        String idOfThisCase = Integer.toString(this.getID());

        // Get a table full of to-dos that match the ID of this case.
        CaseTodoCursorWrapper cursorWrapper = queryCaseTodoTable(CaseTodoTable.Cols.FK_CASEID + " = ?", new String[]{idOfThisCase});

        try {
            cursorWrapper.moveToFirst();                // First row...
            while (!cursorWrapper.isAfterLast()) {      // While the cursor isn't pointing to a row after the last one...
                caseTodos.add(cursorWrapper.getCaseTodo());     // Add the case to-do at the current row
                cursorWrapper.moveToNext();                 // Increment cursor
            }
        } finally {
            cursorWrapper.close();
        }

        return caseTodos;
    }

    // GET ONE TO-DO BY ITS ID //
    public CaseTodo getCaseTodo(int id) {

        // Get the to-do with the specified ID.
        CaseTodoCursorWrapper cursorWrapper = queryCaseTodoTable(CaseTodoTable.Cols.ID + " = ?", new String[]{Integer.toString(id)});

        try {

            // If there is not a to-do with that ID...
            if (cursorWrapper.getCount() == 0) {
                throw new RuntimeException("Tried to get a todo with id: " + id + " but it didn't exist!");
            } else if (cursorWrapper.getCount() > 1) {
                throw new RuntimeException("There are more than one to-dos with that ID! This shouldn't happen!");
            }

            cursorWrapper.moveToFirst();        // Go to the first row
            return cursorWrapper.getCaseTodo(); // Return the to-do in that row
        } finally {
            cursorWrapper.close();              // Close the cursor
        }
    }

    // Used to insert/update a to-do
    private ContentValues getContentValues(CaseTodo todo) {

        ContentValues values = new ContentValues();

        // Use this case's ID as the foreign key.
        values.put(CaseTodoTable.Cols.FK_CASEID, this.getID());

        values.put(CaseTodoTable.Cols.DESCRIPTION, todo.getDescription());
        values.put(CaseTodoTable.Cols.IS_COMPLETE, todo.isComplete());

        return values;
    }

    // Used by getCaseTodo
    private CaseTodoCursorWrapper queryCaseTodoTable(String whereClause, String[] whereArgs) {

        // Execute query and get cursor over result set
        Cursor cursor = mDatabase.query(
                CaseTodoTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new CaseTodoCursorWrapper(cursor);
    }
}
