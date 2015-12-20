package com.zakdwyer.casemanager.data;

/**
 * A to-do inside of a case.
 */
public class CaseTodo {

    private int mID;                // ID of the to-do
    private int mCaseID;            // ID of the Case this to-do belongs to
    private String mDescription;    // Description of to-do
    private boolean mIsComplete;    // Whether the to-do is complete or not

    // Constructor
    public CaseTodo(int id, int caseID, String description) {
        this(id, caseID, description, false);
    }

    // Constructor (more specific)
    public CaseTodo(int id, int caseID, String description, boolean isComplete) {
        this.mID = id;
        this.mCaseID = caseID;
        this.mDescription = description;
        mIsComplete = isComplete;
    }

    // Getters and Setters
    public int getID() {
        return mID;
    }

    public int getCaseID() {
        return mCaseID;
    }

    public String getDescription() {

        // Needed to get text of the to-do.
        return mDescription;
    }

    public void setDescription(String description) {

        // In case the user edits the description of the to-do.
        mDescription = description;
    }

    public boolean isComplete() {

        // Needed to determine if the to-do should have a check-box or not.
        return mIsComplete;
    }

    public void setIsComplete(boolean isComplete) {

        // Used to check/uncheck a to-do.
        mIsComplete = isComplete;
    }
}
