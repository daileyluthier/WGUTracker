package com.daileymichael.wgutracker.Models;

/**
 *
 */
public class Note {
    private int id;
    private String title;
    private String text;
    private int courseId;

    /**
     * This method builds a note instance
     * @param id
     * @param title
     * @param text
     * @param courseId
     */
    public Note(int id, String title, String text, int courseId) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.courseId = courseId;
    }

    /**
     * This method gets your note via the ID
     * @return id
     */
    public int getId() { return this.id; }

    /**
     * This method gets your note's title
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * This method gets your note's text
     * @return text
     */
    public String getText() {
        return this.text;
    }

    /**
     * This method gets your note's related course ID.
     * @return associated course ID
     */
    public int getCourseId() {
        return this.courseId;
    }

    /**
     * This method creates your note as a string representation
     * @return string representation
     */
    @Override
    public String toString() {
        return this.title;
    }

    /**
     * This method returns your note's validation
     * @return is it valid?
     */
    public boolean isValid() {
        if (title.isEmpty() || text.isEmpty()) {
            return false;
        }
        return true;
    }
}
