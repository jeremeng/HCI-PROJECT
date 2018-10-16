package com.hci.project.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lineartimer.LinearTimer;
import com.example.lineartimer.LinearTimerView;
import com.hci.project.R;
import com.tooltip.Tooltip;

import java.util.concurrent.TimeUnit;


public class HomeFragment extends Fragment implements LinearTimer.TimerListener {
    private LinearTimerView linearTimerView;
    private LinearTimer linearTimer;
    private TextView time;
    private Button start,restart;
    Tooltip tes1,test2;

    private long duration = 15 * 1000;
    private Button satu,dua,tiga;
    int state = 0;
    int statein = 0;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        linearTimerView = (LinearTimerView) getActivity().findViewById(R.id.linearTimer);
        time = (TextView) getActivity().findViewById(R.id.time);

        linearTimer = new LinearTimer.Builder()
                .linearTimerView(linearTimerView)
                .duration(duration)
                .timerListener(this)
                .getCountUpdate(LinearTimer.COUNT_UP_TIMER, 1000)
                .build();

        start = (Button) getActivity().findViewById(R.id.startTimer);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(state==0){
                    try {
                        linearTimer.startTimer();
                        state=1;
                        restart.setVisibility(View.INVISIBLE);
                        start.setBackgroundResource(R.mipmap.pause);
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
                else if (state==1){
                    if(statein==0){
                        try {
                            linearTimer.pauseTimer();
                            restart.setVisibility(View.VISIBLE);
                            tes1 = new Tooltip.Builder(start).setText("Click To start again").show();

                            start.setBackgroundResource(R.mipmap.play);
                            statein=1;
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                            tes1.show();
                        }
                    }
                    else if(statein==1){
                        try {
                            linearTimer.resumeTimer();
                            statein=0;
                            restart.setVisibility(View.INVISIBLE);
                            start.setBackgroundResource(R.mipmap.pause);

                            tes1.dismiss();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        // Restart  timer.
       // getActivity().findViewById(R.id.restartTimer).setOnClickListener(new View.OnClickListener() {
        //    @Override
          //  public void onClick(View view) {
            //    linearTimer.restartTimer();
//
//
  //          }
    //    });



        restart = (Button) getActivity().findViewById(R.id.resetTimer);
        if(state==0){
            restart.setVisibility(View.INVISIBLE);
        }

        // Reset the timer
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    linearTimer.resetTimer();
                    if(state==0){
                        if(statein==0){
                            statein = 1;
                        }
                        else{
                            statein=0;
                        }
                        state=1;
                    }
                    else{
                        if(statein==0){
                            statein = 1;
                        }
                        else{
                            statein=0;
                        }
                        state=0;
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();

                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_main, container, false);
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
    public void animationComplete() {
        restart.setVisibility(View.VISIBLE);
    }

    @Override
    public void timerTick(long tickUpdateInMillis) {
        Log.i("Time left", String.valueOf(tickUpdateInMillis));

        String formattedTime = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(tickUpdateInMillis),
                TimeUnit.MILLISECONDS.toSeconds(tickUpdateInMillis)
                        + TimeUnit.MINUTES
                        .toSeconds(TimeUnit.MILLISECONDS.toHours(tickUpdateInMillis)));

        time.setText(formattedTime);
    }

    public void onTimerReset() {
        time.setText("");
    }

}

