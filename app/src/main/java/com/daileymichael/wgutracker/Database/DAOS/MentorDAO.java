package com.daileymichael.wgutracker.Database.DAOS;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import com.daileymichael.wgutracker.Database.DbContentProvider;
import com.daileymichael.wgutracker.Models.Mentor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MentorDAO extends DbContentProvider implements MentorSchema, MentorDAOInterface {
    private Cursor cursor;
    private ContentValues initialValues;

    public MentorDAO(SQLiteDatabase db) {
        super(db);
    }

    /**
     * @param mentor
     * @return
     */
    public boolean addMentor(Mentor mentor) {
        setContentValue(mentor);
        try {
            return super.insert(TABLE_MENTORS, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            return false;
        }
    }

    /**
     * @param courseId
     * @return
     */
    public List<Mentor> getMentorsByCourse(int courseId) {
        final String selectionArgs[] = { String.valueOf(courseId) };
        final String selection = MENTOR_COURSE_ID + " = ?";

        List<Mentor> mentorList = new ArrayList<Mentor>();

        cursor = super.query(TABLE_MENTORS, MENTOR_COLUMNS, selection,
                selectionArgs, MENTOR_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Mentor mentor = cursorToEntity(cursor);
                mentorList.add(mentor);

                cursor.moveToNext();
            }
            cursor.close();
        }
        return mentorList;
    }

    /**
     * @param mentorId
     * @return
     */
    public Mentor getMentorById(int mentorId) {
        final String selectionArgs[] = { String.valueOf(mentorId) };
        final String selection = MENTOR_ID + " = ?";

        Mentor mentor = null;

        cursor = super.query(TABLE_MENTORS, MENTOR_COLUMNS, selection,
                selectionArgs, MENTOR_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                mentor = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return mentor;
    }

    /**
     * @return
     */
    public int getMentorCount() {
        List<Mentor> mentorList = getMentors();

        return mentorList.size();
    }

    /**
     * @return
     */
    public List<Mentor> getMentors() {
        List<Mentor> mentorList = new ArrayList<>();

        cursor = super.query(TABLE_MENTORS, MENTOR_COLUMNS, null,
                null, MENTOR_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Mentor mentor = cursorToEntity(cursor);
                mentorList.add(mentor);

                cursor.moveToNext();
            }
            cursor.close();
        }
        return mentorList;
    }

    /**
     * @param mentor
     * @return
     */
    public boolean removeMentor(Mentor mentor) {
        final String selectionArgs[] = { String.valueOf(mentor.getId()) };
        final String selection = MENTOR_ID + " = ?";
        return super.delete(TABLE_MENTORS, selection, selectionArgs) > 0;
    }

    /**
     * @param mentor
     * @return
     */
    public boolean updateMentor(Mentor mentor) {
        final String selectionArgs[] = { String.valueOf(mentor.getId()) };
        final String selection = MENTOR_ID + " = ?";

        setContentValue(mentor);
        try {
            return super.update(TABLE_MENTORS, getContentValue(), selection, selectionArgs) > 0;
        } catch (SQLiteConstraintException ex){
            return false;
        }
    }

    /**
     * @param cursor
     * @return
     */
    protected Mentor cursorToEntity(Cursor cursor) {
        int mentorIdIdx;
        int mentorNameIdx;
        int mentorEmailIdx;
        int mentorPhoneIdx;
        int mentorCourseIdIdx;
        int mentorId = -1;
        String mentorName = "";
        String mentorPhone = "";
        String mentorEmail = "";
        int mentorCourseId = -1;

        if (cursor != null) {
            if (cursor.getColumnIndex(MENTOR_ID) != -1) {
                mentorIdIdx = cursor.getColumnIndexOrThrow(MENTOR_ID);
                mentorId = cursor.getInt(mentorIdIdx);
            }
            if (cursor.getColumnIndex(MENTOR_NAME) != -1) {
                mentorNameIdx = cursor.getColumnIndexOrThrow(MENTOR_NAME);
                mentorName = cursor.getString(mentorNameIdx);
            }
            if (cursor.getColumnIndex(MENTOR_PHONE) != -1) {
                mentorPhoneIdx = cursor.getColumnIndexOrThrow(MENTOR_PHONE);
                mentorPhone = cursor.getString(mentorPhoneIdx);
            }
            if (cursor.getColumnIndex(MENTOR_EMAIL) != -1) {
                mentorEmailIdx = cursor.getColumnIndexOrThrow(MENTOR_EMAIL);
                mentorEmail = cursor.getString(mentorEmailIdx);
            }
            if (cursor.getColumnIndex(MENTOR_COURSE_ID) != -1) {
                mentorCourseIdIdx = cursor.getColumnIndexOrThrow(MENTOR_COURSE_ID);
                mentorCourseId = cursor.getInt(mentorCourseIdIdx);
            }
        }
        return new Mentor(mentorId, mentorName, mentorPhone, mentorEmail, mentorCourseId);
    }

    /**
     * @param mentor
     */
    private void setContentValue(Mentor mentor) {
        initialValues = new ContentValues();
        initialValues.put(MENTOR_ID, mentor.getId());
        initialValues.put(MENTOR_NAME, mentor.getName());
        initialValues.put(MENTOR_PHONE, mentor.getPhone());
        initialValues.put(MENTOR_EMAIL, mentor.getEmail());
        initialValues.put(MENTOR_COURSE_ID, mentor.getCourseId());
    }

    /**
     * @return
     */
    private ContentValues getContentValue() {
        return initialValues;
    }
}
