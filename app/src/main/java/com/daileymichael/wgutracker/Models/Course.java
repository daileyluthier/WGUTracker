package com.daileymichael.wgutracker.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class Course {
    private final int id;
    private final String title;
    private final String startDate;
    private final String endDate;
    private final String status;
    private final int termId;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

    /**
     * This method builds a course instance
     * @param id
     * @param title
     * @param startDate
     * @param endDate
     * @param status
     * @param termId
     */
    public Course(int id, String title, String startDate, String endDate,
                  String status, int termId) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.termId = termId;
    }

    /**
     * This method gets the course's ID
     * @return id
     */
    public int getId() {
        return this.id;
    }

    /**
     * This method gets the course's title
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * This method gets the course start date
     * @return starDate
     */
    public String getStartDate() {
        return this.startDate;
    }

    /**
     * This method gets the course end date
     * @return endDate
     */
    public String getEndDate() {
        return this.endDate;
    }

    /**
     * This method gets the course status
     * @return status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * This method creates a string representation of the term duration
     * @return
     */
    public String getDates() {
        return startDate + " to " + endDate;
    }

    /**
     * This method gets the course related term ID.
     * @return termId
     */
    public int getTermId() {
        return this.termId;
    }

    /**
     * This method creates a string representation
     * @return
     */
    @Override
    public String toString() {
        return title + " (" + getDates() + ")";
    }

    /**
     * This method validates the course dates
     * @return
     */
    public boolean isValid() {
        if (title.isEmpty() || startDate.isEmpty() || endDate.isEmpty() || status.isEmpty()) {
            return false;
        }
        try {
            //Are the dates in the correct format?
            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);
            //Is the start date before the end date?
            if (!start.before(end)) {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
