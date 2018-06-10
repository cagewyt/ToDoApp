package com.cagewyt.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
    }

    public void addTaskDoneClicked(View view)
    {
        EditText editText = findViewById(R.id.taskEditText);
        String taskName = editText.getText().toString();

        // save to database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Tasks");
        DatabaseReference task = databaseReference.push();

        task.child("name").setValue(taskName);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
        String dateString = sdf.format(new Date());
        task.child("time").setValue(dateString);

        Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);

        startActivity(intent);
    }

}
