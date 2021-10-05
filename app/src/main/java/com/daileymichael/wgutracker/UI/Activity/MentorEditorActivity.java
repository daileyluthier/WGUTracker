package com.daileymichael.wgutracker.UI.Activity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.daileymichael.wgutracker.Database.Database;
import com.daileymichael.wgutracker.Models.Course;
import com.daileymichael.wgutracker.Models.Mentor;

import com.daileymichael.wgutracker.R;
import com.daileymichael.wgutracker.UI.Fragments.CourseDetailFragment;

import java.util.List;

/**
 *
 */
public class MentorEditorActivity extends AppCompatActivity {
    private Mentor modifiedMentor;
    private Spinner courseSpinner;
    private int courseId;
    public Database db;

    /**
     * This method sets the contentView and toolbar action items
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_editor);
        db = new Database(this);
        db.open();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courseSpinner = findViewById(R.id.course_spinner);
        List<Course> courseList = db.courseDAO.getCourses();
        ArrayAdapter<Course> courseDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, courseList);
        courseDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(courseDataAdapter);

        Intent intent = getIntent();
        int modifiedMentorId = intent.getIntExtra(CourseDetailFragment.ARG_MENTOR_ID, -1);
        courseId = intent.getIntExtra(CourseDetailFragment.ARG_COURSE_ID, -1);

        if (modifiedMentorId != -1) {
            modifiedMentor = db.mentorDAO.getMentorById(modifiedMentorId);

            String mentorName = modifiedMentor.getName();
            String mentorEmail = modifiedMentor.getEmail();
            String mentorPhone = modifiedMentor.getPhone();
            EditText editName = findViewById(R.id.mentor_name);
            EditText editEmail = findViewById(R.id.mentor_email);
            EditText editPhone = findViewById(R.id.mentor_phone);

            editName.setText(mentorName);
            editEmail.setText(mentorEmail);
            editPhone.setText(mentorPhone);
            findViewById(R.id.delete_mentor).setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method closes the database when called
     */
    @Override
    public void onDestroy() {
        db.close();
        super.onDestroy();
    }

    /**
     * This method removes the selected mentor when called
     * @param view
     */
    public void onDelete(View view) {
        int mentorCourse = modifiedMentor.getCourseId();
        boolean removed = db.mentorDAO.removeMentor(modifiedMentor);

        if (removed) {
            Intent intent = new Intent(this, CourseDetailActivity.class);
            intent.putExtra(CourseDetailFragment.ARG_COURSE_ID, mentorCourse);
            startActivity(intent);
        }
    }

    /**
     * This method saves the entered and selected information when called
     * @param view
     */
    public void onSave(View view) {
        EditText editName = findViewById(R.id.mentor_name);
        EditText editEmail = findViewById(R.id.mentor_email);
        EditText editPhone = findViewById(R.id.mentor_phone);
        String mentorName = editName.getText().toString();
        String mentorEmail = editEmail.getText().toString();
        String mentorPhone = editPhone.getText().toString();
        Course mCourse = (Course) courseSpinner.getSelectedItem();
        int mentorId = db.mentorDAO.getMentorCount();
        int modifiedMentorId = -1;

        if (modifiedMentor != null) {
            modifiedMentorId = modifiedMentor.getId();
            mentorId = modifiedMentorId;
        }

        Mentor newMentor = new Mentor(mentorId, mentorName, mentorPhone, mentorEmail, mCourse.getId());
        boolean soSave = false;
        boolean isValid = newMentor.isValid();

        if (isValid && modifiedMentorId == -1) {
            soSave = db.mentorDAO.addMentor(newMentor);
        } else if (isValid) {
            soSave = db.mentorDAO.updateMentor(newMentor);
        }
        if (soSave) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
