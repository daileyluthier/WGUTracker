package com.daileymichael.wgutracker.Database.DAOS;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;


import com.daileymichael.wgutracker.Database.DbContentProvider;
import com.daileymichael.wgutracker.Models.Term;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TermDAO extends DbContentProvider implements TermSchema, TermDAOInterface {
    private Cursor cursor;
    private ContentValues initialValues;

    public TermDAO(SQLiteDatabase db) {
        super(db);
    }

    /**
     *
     * @param term
     * @return
     */
    public boolean addTerm(Term term) {
        setContentValue(term);
        try {
            return super.insert(TABLE_TERMS, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            return false;
        }
    }

    /**
     *
     * @param termId
     * @return
     */
    public Term getTermById(int termId) {
        final String selectionArgs[] = { String.valueOf(termId) };
        final String selection = TERM_ID + " = ?";

        Term term = null;

        cursor = super.query(TABLE_TERMS, TERMS_COLUMNS, selection,
                selectionArgs, TERM_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                term = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return term;
    }

    /**
     * @return
     */
    public int getTermCount() {
        List<Term> termList = getTerms();
        return termList.size();
    }

    /**
     * @return
     */
    public List<Term> getTerms() {
        List<Term> termList = new ArrayList<>();

        cursor = super.query(TABLE_TERMS, TERMS_COLUMNS, null,
                null, TERM_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Term term = cursorToEntity(cursor);
                termList.add(term);

                cursor.moveToNext();
            }
            cursor.close();
        }
        return termList;
    }

    /**
     * @param term
     * @return
     */
    public boolean removeTerm(Term term) {
        final String selectionArgs[] = { String.valueOf(term.getId()) };
        final String selection = TERM_ID + " = ?";

        return super.delete(TABLE_TERMS, selection, selectionArgs) > 0;
    }

    /**
     * @param term
     * @return
     */
    public boolean updateTerm(Term term) {
        final String selectionArgs[] = { String.valueOf(term.getId()) };
        final String selection = TERM_ID + " = ?";

        setContentValue(term);
        try {
            return super.update(TABLE_TERMS, getContentValue(), selection, selectionArgs) > 0;
        } catch (SQLiteConstraintException ex){
            return false;
        }
    }

    /**
     * @param cursor
     * @return
     */
    protected Term cursorToEntity(Cursor cursor) {
        int termIdIdx;
        int termTitleIdx;
        int termStartIdx;
        int termEndIdx;
        int termId = -1;
        String termTitle = "";
        String termStartDate = "";
        String termEndDate = "";

        if (cursor != null) {
            if (cursor.getColumnIndex(TERM_ID) != -1) {
                termIdIdx = cursor.getColumnIndexOrThrow(TERM_ID);
                termId = cursor.getInt(termIdIdx);
            }
            if (cursor.getColumnIndex(TERM_TITLE) != -1) {
                termTitleIdx = cursor.getColumnIndexOrThrow(TERM_TITLE);
                termTitle = cursor.getString(termTitleIdx);
            }
            if (cursor.getColumnIndex(TERM_START_DATE) != -1) {
                termStartIdx = cursor.getColumnIndexOrThrow(TERM_START_DATE);
                termStartDate = cursor.getString(termStartIdx);
            }
            if (cursor.getColumnIndex(TERM_END_DATE) != -1) {
                termEndIdx = cursor.getColumnIndexOrThrow(TERM_END_DATE);
                termEndDate = cursor.getString(termEndIdx);
            }
        }
        return new Term(termId, termTitle, termStartDate, termEndDate);
    }

    /**
     * @param term
     */
    private void setContentValue(Term term) {
        initialValues = new ContentValues();
        initialValues.put(TERM_ID, term.getId());
        initialValues.put(TERM_TITLE, term.getTitle());
        initialValues.put(TERM_START_DATE,term.getStartDate());
        initialValues.put(TERM_END_DATE, term.getEndDate());
    }

    /**
     * @return
     */
    private ContentValues getContentValue() {
        return initialValues;
    }
}
