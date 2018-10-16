package com.hci.project.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hci.project.MainActivity;
import com.hci.project.R;
import com.hci.project.helpers.InputValidation;
import com.hci.project.model.User;
import com.hci.project.model.Work;
import com.hci.project.sql.DatabaseHelper;


public class addWorkouts extends Fragment implements View.OnClickListener{
    private NestedScrollView nestedScrollView;

    private TextInputLayout Name;
    private TextInputLayout Rep;
    private TextInputLayout Rest;
    private TextInputLayout Time;

    private TextInputEditText nameText;
    private TextInputEditText repText;
    private TextInputEditText restText;
    private TextInputEditText timeText;

    private AppCompatButton appCompatButtonAdd;


    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private Work work;
    private String id;
    private User user;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        MainActivity myActivity = (MainActivity) getActivity();
        id =  myActivity.getId();

        nestedScrollView = (NestedScrollView) getActivity().findViewById(R.id.nestedScrollView);

        Name = (TextInputLayout) getActivity().findViewById(R.id.Name);
        Time = (TextInputLayout) getActivity().findViewById(R.id.time);
        Rep = (TextInputLayout) getActivity().findViewById(R.id.Rep);
        Rest = (TextInputLayout) getActivity().findViewById(R.id.Rest);


       nameText = (TextInputEditText) getActivity().findViewById(R.id.NameText);
        timeText = (TextInputEditText) getActivity().findViewById(R.id.TimeText);
        repText = (TextInputEditText) getActivity().findViewById(R.id.repText);
       restText = (TextInputEditText) getActivity().findViewById(R.id.restText);


        appCompatButtonAdd = (AppCompatButton) getActivity().findViewById(R.id.appCompatButtonAdd);

        appCompatButtonAdd.setOnClickListener(this);
        inputValidation = new InputValidation(getActivity());
        databaseHelper = new DatabaseHelper(getActivity());
        work = new Work();
        user = new User();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_workout, container, false);


        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonAdd:
                postDataToSQLite();
                break;

        }
    }

    private void postDataToSQLite() {
            work.setUser(id);
            work.setName(nameText.getText().toString().trim());
            work.setTime(Long.parseLong(String.valueOf(timeText.getText())));
            work.setRep(Long.parseLong(String.valueOf(repText.getText())));
            work.setRest(Long.parseLong(String.valueOf(restText.getText())));

            databaseHelper.addWorkout(work);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView, "Workout Added", Snackbar.LENGTH_LONG).show();



        }

    private void emptyInputEditText() {
        nameText.setText(null);
        timeText.setText(null);
        repText.setText(null);
        restText.setText(null);
    }

}
