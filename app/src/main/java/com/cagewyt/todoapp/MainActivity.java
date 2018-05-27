package com.cagewyt.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.cagewyt.todoapp.model.Task;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private RecyclerView taskListView;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String day = sdf.format(today);
        TextView bannerDay = findViewById(R.id.bannerDay);
        bannerDay.setText(day);

        SimpleDateFormat sdf2 = new SimpleDateFormat("MMM dd, yyy h:mm a");
        String date = sdf2.format(today);
        TextView bannerDate = findViewById(R.id.bannerDate);
        bannerDate.setText(date);

        taskListView = findViewById(R.id.task_list);
        taskListView.setHasFixedSize(true);
        taskListView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Tasks");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Task, TaskViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Task, TaskViewHolder>(
                Task.class,
                R.layout.task_row,
                TaskViewHolder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(TaskViewHolder viewHolder, Task model, int position) {
                final String taskKey = getRef(position).getKey().toString();

                viewHolder.setName(model.getName());
                viewHolder.setTime(model.getTime());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent singleTaskActivity = new Intent(MainActivity.this, AddTaskActivity.class);
                        singleTaskActivity.putExtra("TaskId", taskKey);
                        startActivity(singleTaskActivity);
                    }
                });
            }
        };

        taskListView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public TaskViewHolder(View itemView)
        {
            super(itemView);
            mView = itemView;
        }

        public void setName(String name)
        {
            TextView taskName = mView.findViewById(R.id.taskName);
            taskName.setText(name);
        }

        public void setTime(String time)
        {
            TextView taskName = mView.findViewById(R.id.taskTime);
            taskName.setText(time);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.addTask) {
            // move to AddTaskActivity
            Intent intent = new Intent(this, AddTaskActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
