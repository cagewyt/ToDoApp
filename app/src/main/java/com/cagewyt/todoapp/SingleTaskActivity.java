package com.cagewyt.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SingleTaskActivity extends AppCompatActivity {

    private String taskId;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);

        taskId = getIntent().getExtras().getString("TaskId");

        // Load task from database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Tasks");

        databaseReference.child(taskId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String taskTitle = (String) dataSnapshot.child("name").getValue();
                String taskTime = (String) dataSnapshot.child("time").getValue();

                TextView taskTitleView = findViewById(R.id.singleTask);
                taskTitleView.setText(taskTitle);

                TextView taskTimeView = findViewById(R.id.singleTaskTime);
                taskTimeView.setText(taskTime);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void deleteTask(View view) {
        // delete the current task from database
        databaseReference.child(taskId).removeValue();

        // move back to task list
        Intent intent = new Intent(SingleTaskActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void updateTask(View view) {
        Intent intent = new Intent(SingleTaskActivity.this, UpdateTaskActivity.class);
        intent.putExtra("TaskId", taskId);
        startActivity(intent);
    }
}
