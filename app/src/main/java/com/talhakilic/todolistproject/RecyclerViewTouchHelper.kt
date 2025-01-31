package com.talhakilic.todolistproject

import android.graphics.Canvas
import android.graphics.Color
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.talhakilic.todolistproject.Adapter.ToDoAdapter

class RecyclerViewTouchHelper(adapter: ToDoAdapter) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    private val adapter: ToDoAdapter = adapter

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if (direction == ItemTouchHelper.RIGHT) {

            adapter.deleteTask(position)
            Toast.makeText(adapter.getContext(), "Task deleted", Toast.LENGTH_SHORT).show()
        } else {
            adapter.editItem(position)
            Toast.makeText(adapter.getContext(), "Task edited", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val swipeBackground = if (dX > 0) {
            ContextCompat.getColor(adapter.getContext(), R.color.blue2)
        } else {
            Color.RED
        }

        val swipeIcon = if (dX > 0) {
            R.drawable.ic_baseline_edit
        } else {
            R.drawable.ic_baseline_delete
        }

        val builder = SwipeDecorator(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        builder.addSwipeBackgroundColor(swipeBackground)
            .addSwipeActionIcon(swipeIcon)
            .create()
            .decorate()

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}
