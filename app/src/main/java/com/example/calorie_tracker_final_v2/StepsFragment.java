package com.example.calorie_tracker_final_v2;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.Inet4Address;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StepsFragment extends Fragment {

    View vSteps;
    EditText stepsTaken;
    TextView readDB;
    Button addSteps;
    Button updateSteps;
    Button deleteSteps;
    Button readSteps;
    static StepsRoomDB db = null;
    String timeStamp;
    private Object StepsRoom;
    String time;
    EditText editTime;
    EditText editSteps;
    String convert = "s";
    int steps = 1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vSteps = inflater.inflate(R.layout.fragment_steps, container, false);
        stepsTaken = vSteps.findViewById(R.id.editSteps);
        addSteps = vSteps.findViewById(R.id.addStepsBtn);
        updateSteps = vSteps.findViewById(R.id.updateStepsBtn);
        editTime = vSteps.findViewById(R.id.editTime);
        editSteps = vSteps.findViewById(R.id.editTextSteps);
        readSteps = vSteps.findViewById(R.id.readStepsBtn);
        timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        time = timeStamp.toString();
        readDB = vSteps.findViewById(R.id.readView);




        db = Room.databaseBuilder(getActivity(),
                StepsRoomDB.class, "StepsRoomDB")
                .fallbackToDestructiveMigration()
                .build();

        addSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertDatabase addDatabase = new InsertDatabase();
                addDatabase.execute();

            }
        });

        updateSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convert = editSteps.getText().toString();
                steps = Integer.parseInt(convert);
                UpdateDatabase updateDatabase = new UpdateDatabase();
                updateDatabase.execute();
            }
        });

     /*   deleteSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteDatabase deleteDatabase = new DeleteDatabase();
                deleteDatabase.execute();
            }
        });*/

        readSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadDatabase readDatabase = new ReadDatabase();
                readDatabase.execute();
            }
        });
        return vSteps;
    }

    private class InsertDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            if (!(stepsTaken.getText().toString().isEmpty())) {
                String convert = stepsTaken.getText().toString();
                int step = Integer.parseInt(convert);
                StepsRoom stepsRoom = new StepsRoom(time, step);
                long id = db.stepsRoomDBDao().insert(stepsRoom);
            }
            return " ";
        }
    }



    private class ReadDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            List<StepsRoom> steps = db.stepsRoomDBDao().getAll();
            if (!(steps.isEmpty() || steps == null) ){
                String allUsers = "";
                for (StepsRoom temp : steps) {
                    String userstr = (temp.getTime()) + " \t\t\t\t" + temp.getSteps();
                    allUsers = allUsers +" "+ userstr+" \n";
                }
                return allUsers;
            }
            else
                return " ";
        }
        @Override
        protected void onPostExecute(String details) {
            readDB.setText("All data:\n" + details);
        }
    }

    private class UpdateDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            StepsRoom user=null;

            String time = editTime.getText().toString();
            user = db.stepsRoomDBDao().findStepsRoom(time);
            user.setSteps(steps);

            if (user!=null) {
                db.stepsRoomDBDao().updateUsers(user);
                return "";
            }
            return "";
        }
        @Override
        protected void onPostExecute(String details) {
            //readDB.setText("Updated data:\n" + details);
        }
    }


}











