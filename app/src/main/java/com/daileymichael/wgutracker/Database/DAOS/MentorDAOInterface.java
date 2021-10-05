package com.daileymichael.wgutracker.Database.DAOS;

import com.daileymichael.wgutracker.Models.Mentor;

import java.util.List;

public interface MentorDAOInterface {

    boolean addMentor(Mentor mentor);

    Mentor getMentorById(int mentorId);

    List<Mentor> getMentorsByCourse(int courseId);

    int getMentorCount();

    List<Mentor> getMentors();

    boolean removeMentor(Mentor mentor);
    boolean updateMentor(Mentor mentor);
}
