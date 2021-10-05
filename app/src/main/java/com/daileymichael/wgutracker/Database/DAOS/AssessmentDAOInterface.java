package com.daileymichael.wgutracker.Database.DAOS;

import com.daileymichael.wgutracker.Models.Assessment;

import java.util.List;

/**
 *
 */
public interface AssessmentDAOInterface {
    boolean addAssessment(Assessment assessment);

    Assessment getAssessmentById(int assessmentId);

    List<Assessment> getAssessmentsByCourse(int courseId);

    int getAssessmentCount();

    List<Assessment> getAssessments();

    boolean removeAssessment(Assessment assessment);
    boolean updateAssessment(Assessment assessment);
}
