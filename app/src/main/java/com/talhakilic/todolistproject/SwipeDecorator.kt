package com.talhakilic.todolistproject

import android.graphics.Canvas
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.talhakilic.todolistproject.Adapter.ToDoAdapter

class SwipeDecorator(
    private val c: Canvas,
    private val recyclerView: RecyclerView,
    private val viewHolder: RecyclerView.ViewHolder,
    private val dX: Float,
    private val dY: Float,
    private val actionState: Int,
    private val isCurrentlyActive: Boolean
) {
    private var swipeBackgroundColor: Int = Color.TRANSPARENT
    private var swipeActionIcon: Int = 0


    fun addSwipeBackgroundColor(color: Int): SwipeDecorator {
        swipeBackgroundColor = color
        return this
    }


    fun addSwipeActionIcon(iconRes: Int): SwipeDecorator {
        swipeActionIcon = iconRes
        return this
    }


    fun create(): SwipeDecorator {
        return this
    }


    fun decorate() {
        val itemView = viewHolder.itemView


        c.drawColor(swipeBackgroundColor)

        if (swipeActionIcon != 0) {
            val icon = ContextCompat.getDrawable(itemView.context, swipeActionIcon)
            val iconMargin = (itemView.height - icon!!.intrinsicHeight) / 2
            val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
            val iconLeft = if (dX > 0) {
                itemView.left + iconMargin
            } else {
                itemView.right - iconMargin - icon.intrinsicWidth
            }
            val iconBottom = iconTop + icon.intrinsicHeight
            val iconRight = iconLeft + icon.intrinsicWidth

            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            icon.draw(c)
        }
    }
}
