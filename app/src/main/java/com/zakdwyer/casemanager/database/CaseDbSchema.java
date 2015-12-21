package com.zakdwyer.casemanager.database;

/**
 * Schema for the entire database - CaseTable, CaseTodoTable, and CaseContactTable
 */
public class CaseDbSchema {

    /**
     * Table filled with case names.
     */
    public static final class CaseTable {

        /**
         * Identifier for the "cases" table.
         */
        public static final String NAME = "cases";

        /**
         * Identifiers for the "cases" table's columns.
         */
        public static final class Cols {

            /**
             * Identifier for the case's ID column - the primary key.
             */
            public static final String ID = "_id";

            /**
             * Identifier for the case's first_name column.
             */
            public static final String NAME = "name";
        }
    }

    /**
     * Table filled with todos
     */
    public static final class CaseTodoTable {

        /**
         * Name of the todo table ("todo")
         */
        public static final String NAME = "todo";

        /**
         * Column names for the todo table
         */
        public static final class Cols {

            /**
             * Column name for the ID of the TO-DO table - the primary key.
             */
            public static final String ID = "_id";

            /**
             * Column name for the foreign key field for this to-do's associated case.
             */
            public static final String FK_CASEID = "FK_caseID";

            /**
             * Column name for the description of the to do
             */
            public static final String DESCRIPTION = "description";

            /**
             * Column name for the data that indicates whether this todo is complete or not.
             */
            public static final String IS_COMPLETE = "is_complete";
        }
    }

    /**
     * Table filled with case contact instances
     */
    public static final class CaseContactTable {

        /**
         * Name of the contact table ("contact")
         */
        public static final String NAME = "contact";

        /**
         * Column names for the contact table
         */
        public static final class Cols {

            /**
             * Column name for the ID of the Contact - the primary key
             */
            public static final String ID = "_id";

            /**
             * Column name: foriegn key referencing the associated case's ID
             */
            public static final String FK_CASEID = "FK_caseID";

            /**
             * Column name for the title of the contact.
             */
            public static final String TITLE = "title";

            /**
             * Column name for the description of the contact
             */
            public static final String DESCRIPTION = "description";

            /**
             * Column name: Date and time of when this contact occurred
             */
            public static final String DATE_AND_TIME_OCCURRED = "date_and_time_occurred";

        }
    }
}
