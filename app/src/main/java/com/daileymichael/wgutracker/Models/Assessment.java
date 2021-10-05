package com.daileymichael.wgutracker.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 */
public class Assessment {
    private int id;
    private String name;
    private String type;
    private String date;
    private int courseId;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

    /**
     * This method builds an Assessment instance
     * @param id
     * @param name
     * @param type
     * @param date
     * @param courseId
     */
    public Assessment(int id, String name, String type, String date, int courseId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.date = date;
        this.courseId = courseId;
    }

    /**
     * This method gets the assessment ID
     * @return id
     */
    public int getId() {
        return this.id;
    }

    /**
     * This method gets the assessment name
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method gets an assessment type, either "OA" (not The OA)
     * @return type
     */
    public String getType() {
        return this.type;
    }

    /**
     * This method get an assessment's date
     * @return date
     */
    public String getDate() {
        return this.date;
    }

    /**
     * This method get the related course ID.
     * @return associated course ID
     */
    public int getCourseId() {
        return this.courseId;
    }

    /**
     * This method creates a string representation of an assessment
     * @return string repr
     */
    @Override
    public String toString() {
        return this.name + " (" + this.type + ")";
    }

    /**
     * This is the validation method
     * @return is it valid?
     */
    public boolean isValid() {
        if (name.isEmpty() || type.isEmpty() || date.isEmpty()) {
            return false;
        }
        try {
            //Are the dates in the correct format?
            dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
