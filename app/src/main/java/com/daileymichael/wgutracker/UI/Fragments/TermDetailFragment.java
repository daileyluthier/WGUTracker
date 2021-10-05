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
import com.daileymichael.wgutracker.Models.Course;
import com.daileymichael.wgutracker.Models.Term;

import com.daileymichael.wgutracker.R;
import com.daileymichael.wgutracker.UI.Activity.CourseDetailActivity;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.List;

/**
 *
 */
public class TermDetailFragment extends Fragment {
    public static final String ARG_TERM_ID = "com.daileymichael.wgutracker.termdetailfragment.termid";
    private Term mTerm;
    public Database db;

    public TermDetailFragment() {
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

        if (getArguments().containsKey(ARG_TERM_ID)) {
            mTerm = db.termDAO.getTermById(getArguments().getInt(ARG_TERM_ID));

            Activity activity = this.getActivity();

            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mTerm.getTitle());
            }
        }
    }

    /**
     *
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
        View rootView = inflater.inflate(R.layout.term_detail, container, false);

        if (mTerm != null) {
            final int mTermId = mTerm.getId();
            ((TextView) rootView.findViewById(R.id.term_start_field)).setText(mTerm.getStartDate());
            ((TextView) rootView.findViewById(R.id.term_end_field)).setText(mTerm.getEndDate());

            ListView courseListView = rootView.findViewById(R.id.course_list);
            List<Course> courseList = db.courseDAO.getCoursesByTerm(mTermId);

            if (courseList.size() > 0) {
                rootView.findViewById(R.id.course_list).setVisibility(View.VISIBLE);

                ArrayAdapter<Course> courseListDataAdapter = new ArrayAdapter<Course>(getActivity(),
                        android.R.layout.simple_list_item_1, courseList);

                courseListView.setAdapter(courseListDataAdapter);
                courseListView.setOnItemClickListener((adapter, v, position, id) -> {
                    Course c = (Course) adapter.getItemAtPosition(position);
                    Intent intent = new Intent(getActivity(), CourseDetailActivity.class);

                    intent.putExtra(CourseDetailFragment.ARG_COURSE_ID, c.getId());
                    startActivity(intent);
                });
                setListHeight(courseListDataAdapter, courseListView);
            } else {
                rootView.findViewById(R.id.course_list).setVisibility(View.GONE);
            }
        }
        return rootView;
    }
}
