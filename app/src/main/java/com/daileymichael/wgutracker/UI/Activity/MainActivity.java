package com.daileymichael.wgutracker.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.view.MenuItem;

import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.daileymichael.wgutracker.R;
import com.google.android.material.navigation.NavigationView;

/**
 *C196 Mobile Application Assessment
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    NavigationView navigationView;

    /**
     * This method sets the contentView and toolbar action items
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * This method inflates the menu to add items to the action bar if it is present
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * This method directs the user to the show the assessments using AssessmentListActivity
     * @param view
     */
    public void showAssessments(View view) {
        Intent intent = new Intent(this, AssessmentListActivity.class);
        startActivity(intent);
    }

    /**
     * This method directs the user to the show the courses using CourseListActivity
     * @param view
     */
    public void showCourses(View view) {
        Intent intent = new Intent(this, CourseListActivity.class);
        startActivity(intent);
    }

    /**
     * This method directs the user to the show the mentor add screen using MentorEditorActivity
     * @param view
     */
    public void showMentorAdd(View view) {
        Intent intent = new Intent(this, MentorEditorActivity.class);
        startActivity(intent);
    }

    /**
     * This method directs the user to the show the terms using TermListActivity
     * @param view
     */
    public void showTerms(View view) {
        Intent intent = new Intent(this, TermListActivity.class);
        startActivity(intent);
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
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method enables the sidebar navigation
     * @param menuItem
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            case R.id.nav_terms:
                Intent nTerms = new Intent(MainActivity.this, TermListActivity.class);
                startActivity(nTerms);
                break;
            case R.id.nav_courses:
                Intent nCourses = new Intent(MainActivity.this, CourseListActivity.class);
                startActivity(nCourses);
                break;
            case R.id.nav_assessments:
                Intent nAssessments = new Intent(MainActivity.this, AssessmentListActivity.class);
                startActivity(nAssessments);
                break;
            case R.id.nav_mentors:
                Intent nMentors = new Intent(MainActivity.this, MentorEditorActivity.class);
                startActivity(nMentors);
                break;
            case R.id.nav_settings:
                Intent nSettings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(nSettings);
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
