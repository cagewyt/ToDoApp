package com.cagewyt.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateTaskActivity extends AppCompatActivity {

    private String taskId;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        taskId = getIntent().getExtras().getString("TaskId");

        // Load task from database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Tasks");

        databaseReference.child(taskId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String taskTitle = (String) dataSnapshot.child("name").getValue();

                EditText taskTitleView = findViewById(R.id.taskEditText);
                taskTitleView.setText(taskTitle);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateTaskDoneClicked(View view)
    {
        EditText editText = findViewById(R.id.taskEditText);
        String taskName = editText.getText().toString();

        // update to database
        DatabaseReference task = databaseReference.child(taskId);

        task.child("name").setValue(taskName);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
        String dateString = sdf.format(new Date());
        task.child("time").setValue(dateString);

        Intent intent = new Intent(UpdateTaskActivity.this, MainActivity.class);

        startActivity(intent);
    }
}
