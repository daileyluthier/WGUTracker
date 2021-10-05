package com.daileymichael.wgutracker.Database.DAOS;

public interface TermSchema {

    String TABLE_TERMS = "terms";
    String TERM_ID = "id";
    String TERM_TITLE = "term_title";
    String TERM_START_DATE = "term_start_date";
    String TERM_END_DATE = "term_end_date";

    String[] TERMS_COLUMNS = {TERM_ID, TERM_TITLE, TERM_START_DATE, TERM_END_DATE};

    String TERMS_CREATE =
            "CREATE TABLE " + TABLE_TERMS + " (" +
                    TERM_ID + " INTEGER PRIMARY KEY, " +
                    TERM_TITLE + " TEXT, " +
                    TERM_START_DATE + " TEXT, " +
                    TERM_END_DATE + " TEXT" +
                    ")";
}
