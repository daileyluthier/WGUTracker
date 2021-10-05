package com.daileymichael.wgutracker.UI.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.daileymichael.wgutracker.Database.Database;

import com.daileymichael.wgutracker.Models.Assessment;
import com.daileymichael.wgutracker.Models.Course;
import com.daileymichael.wgutracker.Models.Mentor;
import com.daileymichael.wgutracker.Models.Note;

import com.daileymichael.wgutracker.R;
import com.daileymichael.wgutracker.UI.Activity.AssessmentEditorActivity;
import com.daileymichael.wgutracker.UI.Activity.MentorEditorActivity;
import com.daileymichael.wgutracker.UI.Activity.NoteDetailActivity;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.List;

/**
 *
 */
public class CourseDetailFragment extends Fragment {
    public static final String ARG_COURSE_ID = "com.example.daileymichael.WGUTracker.coursedetailfragment.courseid";
    public static final String ARG_MENTOR_ID = "com.example.daileymichael.WGUTracker.coursedetailfragment.mentorid";
    private Course mCourse;
    public Database db;

    public CourseDetailFragment() {
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

        if (getArguments().containsKey(ARG_COURSE_ID)) {
            mCourse = db.courseDAO.getCourseById(getArguments().getInt(ARG_COURSE_ID));

            Activity activity = this.getActivity();
            //for using master/detail layout
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mCourse.getTitle());
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
     * This method set the course list height
     * @param adapter
     * @param lv
     */
    private void setListHeight(ArrayAdapter adapter, ListView lv) {
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, lv);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = lv.getLayoutParams();
        params.height = totalHeight + (lv.getDividerHeight() * (lv.getCount() - 1));
        lv.setLayoutParams(params);
        lv.requestLayout();
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
        View rootView = inflater.inflate(R.layout.course_detail, container, false);

        if (mCourse != null) {
            ((TextView) rootView.findViewById(R.id.assessment_title_field)).setText(mCourse.getTitle());
            ((TextView) rootView.findViewById(R.id.assessment_type_field)).setText(mCourse.getStatus());
            ((TextView) rootView.findViewById(R.id.assessment_date_field)).setText(mCourse.getStartDate());
            ((TextView) rootView.findViewById(R.id.course_end_field)).setText(mCourse.getEndDate());

            final int mCourseId = mCourse.getId();
            List<Mentor> mentorList = db.mentorDAO.getMentorsByCourse(mCourseId);
            if (mentorList.size() > 0) {
                rootView.findViewById(R.id.mentor_list).setVisibility(View.VISIBLE);

                ListView mentorListView = rootView.findViewById(R.id.mentor_list);
                ArrayAdapter<Mentor> mentorListDataAdapter = new ArrayAdapter<Mentor>(getActivity(),
                        android.R.layout.simple_list_item_1, mentorList);

                mentorListView.setAdapter(mentorListDataAdapter);

                mentorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                        Mentor m = (Mentor) adapter.getItemAtPosition(position);
                        Intent intent = new Intent(getActivity(), MentorEditorActivity.class);

                        intent.putExtra(ARG_MENTOR_ID, m.getId());
                        startActivity(intent);
                    }
                });
                setListHeight(mentorListDataAdapter, mentorListView);
            } else {
                rootView.findViewById(R.id.mentor_list).setVisibility(View.GONE);
            }
            List<Assessment> assessmentList = db.assessmentDAO.getAssessmentsByCourse(mCourseId);

            if (assessmentList.size() > 0) {
                rootView.findViewById(R.id.assessment_list).setVisibility(View.VISIBLE);

                ListView assessmentListView = rootView.findViewById(R.id.assessment_list);
                ArrayAdapter<Assessment> assessmentListDataAdapter = new ArrayAdapter<Assessment>(getActivity(),
                        android.R.layout.simple_list_item_1, assessmentList);

                assessmentListView.setAdapter(assessmentListDataAdapter);
                assessmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                        Assessment a = (Assessment) adapter.getItemAtPosition(position);
                        Intent intent = new Intent(getActivity(), AssessmentEditorActivity.class);

                        intent.putExtra(AssessmentDetailFragment.ARG_ASSESSMENT_ID, a.getId());
                        startActivity(intent);
                    }
                });
                setListHeight(assessmentListDataAdapter, assessmentListView);
            } else {
                rootView.findViewById(R.id.assessment_list).setVisibility(View.GONE);
            }
            List<Note> noteList = db.noteDAO.getNotesByCourse(mCourseId);

            if (noteList.size() > 0) {
                rootView.findViewById(R.id.note_list).setVisibility(View.VISIBLE);

                ListView noteListView = rootView.findViewById(R.id.note_list);
                ArrayAdapter<Note> noteListDataAdapter = new ArrayAdapter<Note>(getActivity(),
                        android.R.layout.simple_list_item_1, noteList);

                noteListView.setAdapter(noteListDataAdapter);
                noteListView.setOnItemClickListener((adapter, v, position, id) -> {
                    Note n = (Note) adapter.getItemAtPosition(position);
                    Intent intent = new Intent(getActivity(), NoteDetailActivity.class);

                    intent.putExtra(NoteDetailFragment.ARG_NOTE_ID, n.getId());
                    startActivity(intent);
                });
                setListHeight(noteListDataAdapter, noteListView);
            } else {
                rootView.findViewById(R.id.note_list).setVisibility(View.GONE);
            }
        }
        return rootView;
    }
}
