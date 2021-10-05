package com.daileymichael.wgutracker.UI.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.daileymichael.wgutracker.Database.Database;
import com.daileymichael.wgutracker.Models.Assessment;
import com.daileymichael.wgutracker.Models.Course;
import com.daileymichael.wgutracker.R;
import com.daileymichael.wgutracker.UI.Fragments.AssessmentDetailFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 */
public class AssessmentEditorActivity extends AppCompatActivity implements View.OnClickListener{
    private Assessment modifiedAssessment;
    private Spinner courseSpinner;
    private Spinner typeSpinner;
    public Database db;
    Button btnDatePicker;
    private int sYear, sMonth, sDay;
    EditText editDate;

    /**
     * This method sets the contentView and toolbar & spinner action items
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_editor);
        db = new Database(this);
        db.open();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courseSpinner = findViewById(R.id.term_spinner);
        List<Course> courseList = db.courseDAO.getCourses();
        ArrayAdapter<Course> courseDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, courseList);
        courseDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(courseDataAdapter);

        typeSpinner = findViewById(R.id.type_spinner);
        List<String> statusList = new ArrayList<>();
        statusList.add("Objective Assessment");
        statusList.add("Performance Assessment");
        ArrayAdapter<String> statusDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, statusList);
        statusDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(statusDataAdapter);

        Intent intent = getIntent();

        int modifiedAssessmentId = intent.getIntExtra(AssessmentDetailFragment.ARG_ASSESSMENT_ID, -1);
        editDate = findViewById(R.id.assessment_date);
        btnDatePicker = findViewById(R.id.assessment_date_button);
        btnDatePicker.setOnClickListener(this);

        if (modifiedAssessmentId != -1) {
            modifiedAssessment = db.assessmentDAO.getAssessmentById(modifiedAssessmentId);

            String aTitle = modifiedAssessment.getName();
            String aDate = modifiedAssessment.getDate();
            EditText editTitle = findViewById(R.id.assessment_title);
            editDate = findViewById(R.id.assessment_date);
            editTitle.setText(aTitle);
            editDate.setText(aDate);
            findViewById(R.id.delete_course).setVisibility(View.VISIBLE);
            btnDatePicker.setOnClickListener(this);
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
     * This method removes the selected assessment when called
     * @param view
     */
    public void onDelete(View view) {
        boolean removed = db.assessmentDAO.removeAssessment(modifiedAssessment);
        if (removed) {
            Intent intent = new Intent(this, AssessmentListActivity.class);
            startActivity(intent);
        }
    }

    /**
     * This method saves the entered and selected information when called
     * @param view
     */
    public void onSave(View view) {
        EditText editTitle = findViewById(R.id.assessment_title);
        EditText editDate = findViewById(R.id.assessment_date);
        String aTitle = editTitle.getText().toString();
        String aDate = editDate.getText().toString();
        String aType = String.valueOf(typeSpinner.getSelectedItem());
        Course aCourse = (Course) courseSpinner.getSelectedItem();

        int aId = db.assessmentDAO.getAssessmentCount();
        int modifiedAssessmentId = -1;

        if (modifiedAssessment != null) {
            modifiedAssessmentId = modifiedAssessment.getId();
            aId = modifiedAssessmentId;
        }
        Assessment newAssessment = new Assessment(aId, aTitle, aType, aDate, aCourse.getId());

        boolean soSave = false;
        boolean isValid = newAssessment.isValid();

        if (isValid && modifiedAssessmentId == -1) {
            soSave = db.assessmentDAO.addAssessment(newAssessment);
        } else if (isValid) {
            soSave = db.assessmentDAO.updateAssessment(newAssessment);
        }
        if (soSave) {
            Intent intent = new Intent(this, AssessmentListActivity.class);
            startActivity(intent);
        }
        finish();
    }

    /**
     * This method enables for the date picker to display and to set in text field
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == btnDatePicker) {
            final Calendar c = Calendar.getInstance();
            sYear = c.get(Calendar.YEAR);
            sMonth = c.get(Calendar.MONTH);
            sDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year, monthOfYear, dayOfMonth) -> editDate.setText((monthOfYear + 1) + "-" + dayOfMonth + "-" + year), sYear, sMonth, sDay);
            datePickerDialog.show();
        }
    }
}
