package com.daileymichael.wgutracker.UI.Activity;

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
import com.daileymichael.wgutracker.Models.Note;

import com.daileymichael.wgutracker.R;
import com.daileymichael.wgutracker.UI.Fragments.NoteDetailFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 *
 */
public class NoteDetailActivity extends AppCompatActivity {
    private Note modifiedNote;
    public Database db;

    /**
     * This method sets the contentView and toolbar & floating action items
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        db = new Database(this);
        db.open();
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        final int selectedNoteId = getIntent().getIntExtra(NoteDetailFragment.ARG_NOTE_ID, -1);
        modifiedNote = db.noteDAO.getNoteById(selectedNoteId);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(NoteDetailActivity.this, NoteEditorActivity.class);
            intent.putExtra(NoteDetailFragment.ARG_NOTE_ID, selectedNoteId);
            startActivity(intent);
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //fragments api guide: http://developer.android.com/guide/components/fragments.html ***For my reference
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putInt(NoteDetailFragment.ARG_NOTE_ID,
                    getIntent().getIntExtra(NoteDetailFragment.ARG_NOTE_ID, 0));

            NoteDetailFragment fragment = new NoteDetailFragment();

            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.note_detail_container, fragment)
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
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
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
                NavUtils.navigateUpTo(this, new Intent(this, CourseListActivity.class));
                return true;
            case R.id.action_share:
                Intent intent = new Intent(this, SharingActivity.class);
                intent.putExtra(NoteDetailFragment.ARG_NOTE_ID, modifiedNote.getId());
                startActivity(intent);
                return true;
            case R.id.action_home:
                Intent iHome = new Intent(this, MainActivity.class);
                startActivity(iHome);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
