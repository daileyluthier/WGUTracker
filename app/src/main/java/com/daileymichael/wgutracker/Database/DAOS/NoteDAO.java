package com.daileymichael.wgutracker.Database.DAOS;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;


import com.daileymichael.wgutracker.Database.DbContentProvider;
import com.daileymichael.wgutracker.Models.Note;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class NoteDAO extends DbContentProvider implements NoteSchema, NoteDAOInterface {

    private Cursor cursor;
    private ContentValues initialValues;

    public NoteDAO(SQLiteDatabase db) {
        super(db);
    }

    /**
     * @param note
     * @return
     */
    public boolean addNote(Note note) {
        setContentValue(note);
        try {
            return super.insert(TABLE_NOTES, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * @param courseId
     * @return
     */
    public List<Note> getNotesByCourse(int courseId) {
        final String selectionArgs[] = { String.valueOf(courseId) };
        final String selection = NOTE_COURSE_ID + " = ?";

        List<Note> noteList = new ArrayList<Note>();

        cursor = super.query(TABLE_NOTES, NOTES_COLUMNS, selection,
                selectionArgs, NOTE_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Note note = cursorToEntity(cursor);
                noteList.add(note);

                cursor.moveToNext();
            }
            cursor.close();
        }
        return noteList;
    }

    /**
     * @param noteId
     * @return
     */
    public Note getNoteById(int noteId) {
        final String selectionArgs[] = { String.valueOf(noteId) };
        final String selection = NOTE_ID + " = ?";

        Note note = null;

        cursor = super.query(TABLE_NOTES, NOTES_COLUMNS, selection,
                selectionArgs, NOTE_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                note = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return note;
    }

    /**
     * @return
     */
    public int getNoteCount() {
        List<Note> noteList = getNotes();
        return noteList.size();
    }

    /**
     * @return
     */
    public List<Note> getNotes() {
        List<Note> noteList = new ArrayList<Note>();

        cursor = super.query(TABLE_NOTES, NOTES_COLUMNS, null,
                null, NOTE_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Note note = cursorToEntity(cursor);
                noteList.add(note);

                cursor.moveToNext();
            }
            cursor.close();
        }
        System.out.println(noteList);
        return noteList;
    }

    /**
     * @param note
     * @return
     */
    public boolean removeNote(Note note) {
        final String selectionArgs[] = { String.valueOf(note.getId()) };
        final String selection = NOTE_ID + " = ?";
        return super.delete(TABLE_NOTES, selection, selectionArgs) > 0;
    }

    /**
     * @param note
     * @return
     */
    public boolean updateNote(Note note) {
        final String selectionArgs[] = { String.valueOf(note.getId()) };
        final String selection = NOTE_ID + " = ?";

        setContentValue(note);
        try {
            return super.update(TABLE_NOTES, getContentValue(), selection, selectionArgs) > 0;
        } catch (SQLiteConstraintException ex){
            return false;
        }
    }

    /**
     * @param cursor
     * @return
     */
    protected Note cursorToEntity(Cursor cursor) {
        int noteIdIdx;
        int noteTitleIdx;
        int noteTextIdx;
        int noteCourseIdIdx;
        int noteId = -1;
        String noteTitle = "";
        String noteText = "";
        int noteCourseId = -1;

        if (cursor != null) {
            if (cursor.getColumnIndex(NOTE_ID) != -1) {
                noteIdIdx = cursor.getColumnIndexOrThrow(NOTE_ID);
                noteId = cursor.getInt(noteIdIdx);
            }
            if (cursor.getColumnIndex(NOTE_TITLE) != -1) {
                noteTitleIdx = cursor.getColumnIndexOrThrow(NOTE_TITLE);
                noteTitle = cursor.getString(noteTitleIdx);
            }
            if (cursor.getColumnIndex(NOTE_TEXT) != -1) {
                noteTextIdx = cursor.getColumnIndexOrThrow(NOTE_TEXT);
                noteText = cursor.getString(noteTextIdx);
            }
            if (cursor.getColumnIndex(NOTE_COURSE_ID) != -1) {
                noteCourseIdIdx = cursor.getColumnIndexOrThrow(NOTE_COURSE_ID);
                noteCourseId = cursor.getInt(noteCourseIdIdx);
            }
        }
        return new Note(noteId, noteTitle, noteText, noteCourseId);
    }

    /**
     * @param note
     */
    private void setContentValue(Note note) {
        initialValues = new ContentValues();
        initialValues.put(NOTE_ID, note.getId());
        initialValues.put(NOTE_TITLE, note.getTitle());
        initialValues.put(NOTE_TEXT, note.getText());
        initialValues.put(NOTE_COURSE_ID, note.getCourseId());
    }

    /**
     * @return
     */
    private ContentValues getContentValue() {
        return initialValues;
    }
}
