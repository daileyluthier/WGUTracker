package com.daileymichael.wgutracker.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class Term {
    private int id;
    private String title;
    private String startDate;
    private String endDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

    /**
     * This method builds a term instance
     * @param id
     * @param title
     * @param startDate
     * @param endDate
     */
    public Term(int id, String title, String startDate, String endDate) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * This method get the term ID
     * @return id
     */
    public int getId() {
        return this.id;
    }

    /**
     * This method get the term title
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * This method get the term start date
     * @return
     */
    public String getStartDate() {
        return this.startDate;
    }

    /**
     * This method get the term end date
     * @return endDate
     */
    public String getEndDate() {
        return this.endDate;
    }

    /**
     * This method get the term as a string representin' the date range of the term
     * @return
     */
    public String getDates() {
        return startDate + " to " + endDate;
    }

    /**
     * This method get/create the term string representation
     * @return
     */
    @Override
    public String toString() {
        return title + " (" + getDates() + ")";
    }

    /**
     * his method get the term validation of the model
     * @return is it valid?
     */
    public boolean isValid() {
        if (title.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
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
