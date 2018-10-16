package com.hci.project.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hci.project.MainActivity;
import com.hci.project.R;
import com.hci.project.adapters.UsersRecyclerAdapter;
import com.hci.project.model.Work;
import com.hci.project.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;



public class WorkoutList extends Fragment{

    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewUsers;
    private List<Work> listWorks;
    private UsersRecyclerAdapter usersRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    private String id;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        recyclerViewUsers = (RecyclerView) getActivity().findViewById(R.id.recyclerViewUsers);
        listWorks = new ArrayList<>();
        usersRecyclerAdapter = new UsersRecyclerAdapter(listWorks);
        MainActivity myActivity = (MainActivity) getActivity();
        id=myActivity.getId();


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext() );
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(usersRecyclerAdapter);
        databaseHelper = new DatabaseHelper(getActivity());
        getDataFromSQLite();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_list, container, false);


        return view;
    }
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listWorks.clear();
                listWorks.addAll(databaseHelper.getAllWorkout(id));

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                usersRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

}
