package com.daileymichael.wgutracker.Database.DAOS;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;


import com.daileymichael.wgutracker.Database.DbContentProvider;
import com.daileymichael.wgutracker.Models.Course;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class CourseDAO extends DbContentProvider implements CourseSchema, CourseDAOInterface {
    private Cursor cursor;
    private ContentValues initialValues;

    public CourseDAO(SQLiteDatabase db) {
        super(db);
    }

    /**
     * This method adds to the DB the course
     * @param course
     * @return
     */
    public boolean addCourse(Course course) {
        setContentValue(course);
        try {
            return super.insert(TABLE_COURSES, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            return false;
        }
    }

    /**
     *
     * @param courseId
     * @return
     */
    public Course getCourseById(int courseId) {
        final String selectionArgs[] = { String.valueOf(courseId) };
        final String selection = COURSE_ID + " = ?";

        Course course = null;

        cursor = super.query(TABLE_COURSES, COURSES_COLUMNS, selection,
                selectionArgs, COURSE_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                course = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return course;
    }

    /**
     * This method gets all the term's associated course
     * @param termId
     * @return
     */
    public List<Course> getCoursesByTerm(int termId) {
        final String selectionArgs[] = { String.valueOf(termId) };
        final String selection = COURSE_TERM_ID + " = ?";

        List<Course> courseList = new ArrayList<>();

        cursor = super.query(TABLE_COURSES, COURSES_COLUMNS, selection,
                selectionArgs, COURSE_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Course course = cursorToEntity(cursor);
                courseList.add(course);

                cursor.moveToNext();
            }
            cursor.close();
        }
        return courseList;
    }

    /**
     *
     * @return
     */
    public int getCourseCount() {
        List<Course> courseList = getCourses();
        return courseList.size();
    }

    /**
     * yea
     * @return
     */
    public List<Course> getCourses() {
        List<Course> courseList = new ArrayList<>();

        cursor = super.query(TABLE_COURSES, COURSES_COLUMNS, null,
                null, COURSE_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Course course = cursorToEntity(cursor);
                courseList.add(course);

                cursor.moveToNext();
            }
            cursor.close();
        }
        return courseList;
    }

    /**
     *
     * @param course
     * @return
     */
    public boolean removeCourse(Course course) {
        final String selectionArgs[] = { String.valueOf(course.getId()) };
        final String selection = COURSE_ID + " = ?";
        return super.delete(TABLE_COURSES, selection, selectionArgs) > 0;
    }

    /**
     * This method updates the course
     *
     * @param course
     * @return
     */
    public boolean updateCourse(Course course) {
        final String selectionArgs[] = { String.valueOf(course.getId()) };
        final String selection = COURSE_ID + " = ?";

        setContentValue(course);
        try {
            return super.update(TABLE_COURSES, getContentValue(), selection, selectionArgs) > 0;
        } catch (SQLiteConstraintException ex){
            return false;
        }
    }

    /**
     *
     * @param cursor
     * @return
     */
    protected Course cursorToEntity(Cursor cursor) {
        int courseIdIdx;
        int courseTitleIdx;
        int courseStartIdx;
        int courseEndIdx;
        int courseStatusIdx;
        int courseTermIdIdx;
        int courseId = -1;
        String courseTitle = "";
        String courseStart = "";
        String courseEnd = "";
        String courseStatus = "";
        int courseTermId = -1;

        if (cursor != null) {
            if (cursor.getColumnIndex(COURSE_ID) != -1) {
                courseIdIdx = cursor.getColumnIndexOrThrow(COURSE_ID);
                courseId = cursor.getInt(courseIdIdx);
            }
            if (cursor.getColumnIndex(COURSE_TITLE) != -1) {
                courseTitleIdx = cursor.getColumnIndexOrThrow(COURSE_TITLE);
                courseTitle = cursor.getString(courseTitleIdx);
            }
            if (cursor.getColumnIndex(COURSE_START_DATE) != -1) {
                courseStartIdx = cursor.getColumnIndexOrThrow(COURSE_START_DATE);
                courseStart = cursor.getString(courseStartIdx);
            }
            if (cursor.getColumnIndex(COURSE_END_DATE) != -1) {
                courseEndIdx = cursor.getColumnIndexOrThrow(COURSE_END_DATE);
                courseEnd = cursor.getString(courseEndIdx);
            }
            if (cursor.getColumnIndex(COURSE_STATUS) != -1) {
                courseStatusIdx = cursor.getColumnIndexOrThrow(COURSE_STATUS);
                courseStatus = cursor.getString(courseStatusIdx);
            }
            if (cursor.getColumnIndex(COURSE_TERM_ID) != -1) {
                courseTermIdIdx = cursor.getColumnIndexOrThrow(COURSE_TERM_ID);
                courseTermId = cursor.getInt(courseTermIdIdx);
            }
        }
        return new Course(courseId, courseTitle, courseStart, courseEnd, courseStatus, courseTermId);
    }

    /**
     *
     * @param course
     */
    private void setContentValue(Course course) {
        initialValues = new ContentValues();
        initialValues.put(COURSE_ID, course.getId());
        initialValues.put(COURSE_TITLE, course.getTitle());
        initialValues.put(COURSE_START_DATE, course.getStartDate());
        initialValues.put(COURSE_END_DATE, course.getEndDate());
        initialValues.put(COURSE_STATUS, course.getStatus());
        initialValues.put(COURSE_TERM_ID, course.getTermId());
    }

    /**
     *
     * @return
     */
    private ContentValues getContentValue() {
        return initialValues;
    }
}
