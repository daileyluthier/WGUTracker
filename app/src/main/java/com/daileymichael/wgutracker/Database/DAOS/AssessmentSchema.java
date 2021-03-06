package com.daileymichael.wgutracker.Database.DAOS;

/**
 *
 */
public interface AssessmentSchema {

    String TABLE_ASSESSMENTS = "assessments";
    String ASSESSMENT_ID = "id";
    String ASSESSMENT_NAME = "assessment_name";
    String ASSESSMENT_DATE = "assessment_date";
    String ASSESSMENT_TYPE = "assessment_type";
    String ASSESSMENT_COURSE_ID = "assessment_course_id";

    String[] ASSESSMENTS_COLUMNS = {ASSESSMENT_ID, ASSESSMENT_NAME, ASSESSMENT_DATE, ASSESSMENT_TYPE, ASSESSMENT_COURSE_ID};

    String ASSESSMENTS_CREATE =
            "CREATE TABLE " + TABLE_ASSESSMENTS + " (" +
                    ASSESSMENT_ID + " INTEGER PRIMARY KEY, " +
                    ASSESSMENT_NAME + " TEXT, " +
                    ASSESSMENT_DATE + " TEXT, " +
                    ASSESSMENT_TYPE + " TEXT, " +
                    ASSESSMENT_COURSE_ID + " INTEGER " +
                    ")";
}
