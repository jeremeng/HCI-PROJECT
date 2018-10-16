package com.hci.project.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hci.project.R;

/**
 * Created by Gerrys on 05/12/2017.
 */


public class Help extends Fragment implements View.OnClickListener  {


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        Button help1 = (Button) getActivity().findViewById(R.id.button);
        Button help2 = (Button) getActivity().findViewById(R.id.button3);
        Button help3 = (Button) getActivity().findViewById(R.id.button4);
        Button help4 = (Button) getActivity().findViewById(R.id.button5);

        help1.setOnClickListener(this);
        help2.setOnClickListener(this);
        help3.setOnClickListener(this);
        help4.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_help, container, false);
        return v;



        // Assign attribute values to the view programmatically
        // This will override the attributes set via the XML.
        /*linearTimerView.setStrokeWidthInDp(5);
        linearTimerView.setCircleRadiusInDp(40);
        linearTimerView.setStartingPoint(90);
        linearTimerView.setInitialColor(Color.BLACK);
        linearTimerView.setProgressColor(Color.GREEN);*/



        // Start the timer.

    }



    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.button:
                fragment = new HelpWork();
                replaceFragment(fragment);
                break;

            case R.id.button3:
                fragment = new HelpList();
                replaceFragment(fragment);
                break;
            case R.id.button4:
                fragment = new HelpRegister();
                replaceFragment(fragment);
                break;
            case R.id.button5:
                fragment = new HelpContact();
                replaceFragment(fragment);
                break;
        }

    }
    public void replaceFragment(Fragment someFragment) {
        android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.contents_frame, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

