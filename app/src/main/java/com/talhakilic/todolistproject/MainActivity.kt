package com.talhakilic.todolistproject

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.talhakilic.todolistproject.Adapter.ToDoAdapter
import com.talhakilic.todolistproject.Model.ToDoModel
import com.talhakilic.todolistproject.YRDM.DataBaseHelper
import java.util.Collections

class MainActivity : AppCompatActivity(), OnDialogCloseListener {

    private lateinit var mRecyclerview: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var myDB: DataBaseHelper
    private var mList: MutableList<ToDoModel> = mutableListOf()
    private lateinit var adapter: ToDoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerview = findViewById(R.id.recyclerview)
        fab = findViewById(R.id.fab)
        myDB = DataBaseHelper(this)
        adapter = ToDoAdapter(myDB, this)

        mRecyclerview.setHasFixedSize(true)
        mRecyclerview.layoutManager = LinearLayoutManager(this)
        mRecyclerview.adapter = adapter

        mList = myDB.getAllTasks().toMutableList()
        if (mList.isNotEmpty()) {
            Collections.reverse(mList)
        }
        adapter.setTasks(mList)

        fab.setOnClickListener {
            GorevEkle.newInstance().show(supportFragmentManager, GorevEkle.TAG)
        }

        val itemTouchHelper = ItemTouchHelper(RecyclerViewTouchHelper(adapter))
        itemTouchHelper.attachToRecyclerView(mRecyclerview)
    }

    override fun onDialogClose(dialogInterface: DialogInterface) {
        mList = myDB.getAllTasks().toMutableList()
        if (mList.isNotEmpty()) {
            Collections.reverse(mList)
        }
        adapter.setTasks(mList)
        adapter.notifyDataSetChanged()
    }
}
