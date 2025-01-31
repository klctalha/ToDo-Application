package com.talhakilic.todolistproject.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talhakilic.todolistproject.GorevEkle
import com.talhakilic.todolistproject.MainActivity
import com.talhakilic.todolistproject.Model.ToDoModel
import com.talhakilic.todolistproject.YRDM.DataBaseHelper
import com.talhakilic.todolistproject.databinding.TaskLayoutBinding

class ToDoAdapter(private val myDB: DataBaseHelper, private val activity: MainActivity) :
    RecyclerView.Adapter<ToDoAdapter.MyViewHolder>() {

    private var mList: MutableList<ToDoModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = TaskLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    fun getContext(): Context {
        return activity.applicationContext
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: ToDoModel = mList!![position]
        holder.binding.mcheckbox.text = item.task
        holder.binding.mcheckbox.isChecked = toBoolean(item.status)

        holder.binding.mcheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                myDB.updateStatus(item.id, 1)
            } else {
                myDB.updateStatus(item.id, 0)
            }
        }
    }

    private fun toBoolean(num: Int): Boolean {
        return num != 0
    }

    fun setTasks(mList: MutableList<ToDoModel>?) {
        this.mList = mList
        notifyDataSetChanged()
    }

    fun deleteTask(position: Int) {
        val item: ToDoModel = mList!![position]
        myDB.deleteTask(item.id)
        mList!!.removeAt(position)
        notifyItemRemoved(position)
    }

    fun editItem(position: Int) {
        val item: ToDoModel = mList!![position]
        val bundle = Bundle().apply {
            putInt("id", item.id)
            putString("task", item.task)
        }

        val task = GorevEkle().apply {
            arguments = bundle
        }
        task.show(activity.supportFragmentManager, task.tag)
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    class MyViewHolder(val binding: TaskLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}
