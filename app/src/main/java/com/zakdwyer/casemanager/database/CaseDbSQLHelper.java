package com.zakdwyer.casemanager.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.zakdwyer.casemanager.database.CaseDbSchema.*;

/**
 * Opens (or creates if it doesn't exist) the Case database.
 */
public class CaseDbSQLHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "caseDatabase.db";

    // Create a "helper object" that has a handle to the database.
    public CaseDbSQLHelper(Context context) {
        // context: used to open or create the database.
        // DATABASE_NAME: name of the database file
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createCaseTable(db);
        createTodoTable(db);
        createContactTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not going to handle upgrading the database right now.
    }

    ///////////////////////////////////////
    // EXTRACTED METHODS FROM ONCREATE() //
    ///////////////////////////////////////

    private void createCaseTable(SQLiteDatabase db) {
        // Create the CASES table
        db.execSQL("CREATE TABLE " + CaseTable.NAME + "(" +                                         // Create table
                        CaseTable.Cols.ID +  " integer PRIMARY KEY AUTOINCREMENT, " +               // ID is primary key, automatically increments each time another row in this table is created.
                        CaseTable.Cols.NAME + " text"                                               // Name of case
        );
    }

    private void createTodoTable(SQLiteDatabase db) {
        // Create the TO-DO table
        db.execSQL("CREATE TABLE " + CaseTodoTable.NAME + "(" +
                CaseTodoTable.Cols.ID + " integer PRIMARY KEY AUTOINCREMENT, " +
                "FOREIGN KEY(" + CaseTodoTable.Cols.FK_CASEID + ") REFERENCES " + CaseTable.NAME + "(" + CaseTable.Cols.ID + ") NOT NULL, " +
                CaseTodoTable.Cols.DESCRIPTION + "text NOT NULL, " +
                CaseTodoTable.Cols.IS_COMPLETE + "integer NOT NULL");
    }

    private void createContactTable(SQLiteDatabase db) {
        // Create the CONTACT table. This time, use a stringbuilder to make the sql statement, just to try it out.
        StringBuilder createString = new StringBuilder();

        // Name of table
        createString.append("CREATE TABLE " + CaseContactTable.NAME + "(");

        // Create columns
        createString.append(CaseContactTable.Cols.ID + " integer PRIMARY KEY AUTOINCREMENT, ");
        createString.append("FOREIGN KEY(" + CaseContactTable.Cols.FK_CASEID + ") REFERENCES " + CaseTable.NAME + "(" + CaseTable.Cols.ID + ") NOT NULL, ");
        createString.append(CaseContactTable.Cols.TITLE + " text NOT NULL, ");
        createString.append(CaseContactTable.Cols.DESCRIPTION + " text NOT NULL, ");
        createString.append(CaseContactTable.Cols.DATE_AND_TIME_OCCURRED + " integer NOT NULL");

        // Execute SQL
        db.execSQL(createString.toString());
    }
}
