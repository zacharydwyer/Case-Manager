package com.zakdwyer.casemanager.data;

/**
 * Represents an instance of contact with the person on the case.
 */
public class CaseContact {

    private int mID;
    private int mCaseID;
    private String mTitle;
    private String mDescription;

    // See http://stackoverflow.com/questions/17905018/how-to-format-mysql-date-to-show-in-datepicker-in-android
    private long mDateAndTimeOfContact;

    // Constructor
    public CaseContact(int id, int caseID, String title, String description, long dateAndTimeOfContact) {
        this.mID = id;
        this.mCaseID = caseID;
        this.mTitle = title;
        this.mDescription = description;
        this.mDateAndTimeOfContact = dateAndTimeOfContact;
    }

    // Getters and Setters
    public int getID() {
        return mID;
    }

    public int getCaseID() {
        return mCaseID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public long getDateAndTimeOfContact() {
        return mDateAndTimeOfContact;
    }

    public void setDateAndTimeOfContact(long dateAndTimeOfContact) {
        mDateAndTimeOfContact = dateAndTimeOfContact;
    }
}