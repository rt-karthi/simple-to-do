package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;

import com.example.simpletodo.Adapter.ToDoAdapter;
import com.example.simpletodo.Model.ToDoModel;
import com.example.simpletodo.Utils.DataBaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListner{
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private DataBaseHelper myDb;
    private List<ToDoModel> mList;
    private ToDoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);
        myDb = new DataBaseHelper(MainActivity.this);
        mList = new ArrayList<>();
        adapter = new ToDoAdapter(MainActivity.this, myDb);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);

        mList = myDb.getAllTasks();
        Collections.reverse(mList);
        adapter.setTask(mList);

        fab.setOnClickListener( v -> {
            AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mList = myDb.getAllTasks();
        Collections.reverse(mList);
        adapter.setTask(mList);
        adapter.notifyDataSetChanged();
    }
}