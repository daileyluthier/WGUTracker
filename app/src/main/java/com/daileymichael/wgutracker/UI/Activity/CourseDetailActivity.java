package com.daileymichael.wgutracker.UI.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.daileymichael.wgutracker.Database.Database;
import com.daileymichael.wgutracker.Models.Course;

import com.daileymichael.wgutracker.R;
import com.daileymichael.wgutracker.Receivers.AlarmReceiver;
import com.daileymichael.wgutracker.UI.Fragments.CourseDetailFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 */
public class CourseDetailActivity extends AppCompatActivity {

    private Course selectedCourse;
    public Database db;

    /**
     * This method sets the contentView and toolbar & floating action items
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        db = new Database(this);
        db.open();

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        final int selectedCourseId = getIntent().getIntExtra(CourseDetailFragment.ARG_COURSE_ID, 0);
        selectedCourse = db.courseDAO.getCourseById(selectedCourseId);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(CourseDetailActivity.this, CourseEditorActivity.class);
            intent.putExtra(CourseDetailFragment.ARG_COURSE_ID, selectedCourseId);
            startActivity(intent);
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //fragments api guide: http://developer.android.com/guide/components/fragments.html ***For my reference
        if (savedInstanceState == null) {

            Bundle arguments = new Bundle();
            arguments.putInt(CourseDetailFragment.ARG_COURSE_ID,
                    getIntent().getIntExtra(CourseDetailFragment.ARG_COURSE_ID, 0));

            CourseDetailFragment fragment = new CourseDetailFragment();

            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.course_detail_container, fragment)
                    .commit();
        }
    }

    /**
     * This method inflates the menu to add items to the action bar if it is present
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_reminder, menu);
        return true;
    }

    /**
     * This method closes the database when called.
     */
    @Override
    public void onDestroy() {
        db.close();
        super.onDestroy();
    }

    /**
     * This method handles action to take place based on the item selected
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                // ID represents the Home or Up button; basically if this case activity, up button is shown
                // uses NavUtils to allow users to nav up a level in app structure.
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back  ***For my reference
                NavUtils.navigateUpTo(this, new Intent(this, AssessmentListActivity.class));
                return true;
            case R.id.action_reminder:

                String startNotificationTitle = "Course Start Reminder";
                String startNotificationText = selectedCourse.getTitle() + " begins today.";

                Intent startNotificationIntent = new Intent(this.getApplicationContext(), AlarmReceiver.class);
                startNotificationIntent.putExtra("mNotificationTitle", startNotificationTitle);
                startNotificationIntent.putExtra("mNotificationContent", startNotificationText);

                PendingIntent startPendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, startNotificationIntent, 0);
                AlarmManager startAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                String endNotificationTitle = "Course End Reminder";
                String endNotificationText = selectedCourse.getTitle() + " ends today.";

                Intent endNotificationIntent = new Intent(this.getApplicationContext(), AlarmReceiver.class);
                endNotificationIntent.putExtra("mNotificationTitle", endNotificationTitle);
                endNotificationIntent.putExtra("mNotificationContent", endNotificationText);

                PendingIntent endPendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 1, endNotificationIntent, 0);
                AlarmManager endAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

                    Date startDate = dateFormat.parse(selectedCourse.getStartDate());
                    Calendar startCal = Calendar.getInstance();
                    startCal.setTime(startDate);
                    startAlarmManager.set(AlarmManager.RTC_WAKEUP, startCal.getTimeInMillis(), startPendingIntent);

                    Date endDate = dateFormat.parse(selectedCourse.getEndDate());
                    Calendar endCal = Calendar.getInstance();
                    endCal.setTime(endDate);
                    endAlarmManager.set(AlarmManager.RTC_WAKEUP, endCal.getTimeInMillis(), endPendingIntent);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.action_home:
                Intent iHome = new Intent(this, MainActivity.class);
                startActivity(iHome);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method directs to the MentorEditorActivity
     * @param view
     */
    public void onMentorEdit(View view) {
        Intent intent = new Intent(this, MentorEditorActivity.class);
        intent.putExtra(CourseDetailFragment.ARG_COURSE_ID, selectedCourse.getId());
        startActivity(intent);
    }

    /**
     * This method directs to the AssessmentEditorActivity
     * @param view
     */
    public void onAssessmentEdit(View view) {
        Intent intent = new Intent(this, AssessmentEditorActivity.class);
        intent.putExtra(CourseDetailFragment.ARG_COURSE_ID, selectedCourse.getId());
        startActivity(intent);
    }

    /**
     * This method directs to the NoteEditorActivity
     * @param view
     */
    public void onNoteEdit(View view) {
        Intent intent = new Intent(this, NoteEditorActivity.class);
        intent.putExtra(CourseDetailFragment.ARG_COURSE_ID, selectedCourse.getId());
        startActivity(intent);
    }
}
