package com.daileymichael.wgutracker.UI.Fragments;

import android.app.Activity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.daileymichael.wgutracker.Database.Database;
import com.daileymichael.wgutracker.Models.Note;
import com.daileymichael.wgutracker.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;


public class NoteDetailFragment extends Fragment {
    public static final String ARG_NOTE_ID = "com.daileymichael.wgutracker.assessmentdetailfragment.assessmentid";
    private Note mNote;
    public Database db;

    public NoteDetailFragment() {
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new Database(getContext());
        db.open();

        if (getArguments().containsKey(ARG_NOTE_ID)) {
            mNote = db.noteDAO.getNoteById(getArguments().getInt(ARG_NOTE_ID));

            Activity activity = this.getActivity();

            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mNote.getTitle());
            }
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
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.note_detail, container, false);

        if (mNote != null) {
            ((TextView) rootView.findViewById(R.id.note_title_field)).setText(mNote.getTitle());
            ((TextView) rootView.findViewById(R.id.note_text_field)).setText(mNote.getText());
        }
        return rootView;
    }
}
