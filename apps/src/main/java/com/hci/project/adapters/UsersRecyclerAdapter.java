package com.hci.project.adapters;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hci.project.R;
import com.hci.project.model.Work;

import java.util.List;


public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder> {

    private List<Work> listWork;

    public UsersRecyclerAdapter(List<Work> listWork) {
        this.listWork = listWork;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_workout_recycler, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.textViewName.setText(listWork.get(position).getName());
        holder.textViewTime.setText(Long.toString(listWork.get(position).getTime()));
        holder.textViewRep.setText(Long.toString( listWork.get(position).getRep()));
        holder.textViewRest.setText(Long.toString(listWork.get(position).getRest()));
    }

    @Override
    public int getItemCount() {
        Log.v(UsersRecyclerAdapter.class.getSimpleName(),""+listWork.size());
        return listWork.size();
    }


    /**
     * ViewHolder class
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewName;
        public AppCompatTextView textViewTime;
        public AppCompatTextView textViewRep;
        public AppCompatTextView textViewRest;

        public UserViewHolder(View view) {
            super(view);
            textViewName = (AppCompatTextView) view.findViewById(R.id.textViewName);
            textViewTime = (AppCompatTextView) view.findViewById(R.id.textViewTime);
            textViewRep = (AppCompatTextView) view.findViewById(R.id.textViewRep);
            textViewRest = (AppCompatTextView) view.findViewById(R.id.textViewRest);
        }
    }


}
