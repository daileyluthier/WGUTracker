package com.daileymichael.wgutracker.Models;

/**
 *
 */
public class Mentor {
    private int id;
    private String name;
    private String phone;
    private String email;
    private int courseId;

    /**
     * This method builds a mentor instance
     * @param id
     * @param name
     * @param phone
     * @param email
     * @param courseId
     */
    public Mentor(int id, String name, String phone, String email, int courseId) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.courseId = courseId;
    }

    /**
     * This method get your mentor's ID
     * @return id
     */
    public int getId() { return this.id; }

    /**
     * This method get your mentor's email
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * This method get your mentor's name
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method get your mentor's phone number
     * @return phone
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * This method get your mentor's related course ID.
     * @return courseId
     */
    public int getCourseId() {
        return this.courseId;
    }

    /**
     * This method creates a string representation
     * @return
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * This method get your mentor's validation
     * @return
     */
    public boolean isValid() {
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            return false;
        }
        return true;
    }
}
