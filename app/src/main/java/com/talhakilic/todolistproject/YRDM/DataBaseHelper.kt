package com.talhakilic.todolistproject.YRDM

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.talhakilic.todolistproject.Model.ToDoModel

class DataBaseHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    private var db: SQLiteDatabase? = null

    companion object {
        private const val DATABASE_NAME = "TODO_DATABASE"
        private const val TABLE_NAME = "TODO_TABLE"
        private const val COL_1 = "ID"
        private const val COL_2 = "TASK"
        private const val COL_3 = "STATUS"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME (ID INTEGER PRIMARY KEY AUTOINCREMENT, TASK TEXT, STATUS INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertTask(model: ToDoModel) {
        db = writableDatabase
        val values = ContentValues().apply {
            put(COL_2, model.task)
            put(COL_3, 0)
        }
        db?.insert(TABLE_NAME, null, values)
    }

    fun updateTask(id: Int, task: String) {
        db = writableDatabase
        val values = ContentValues().apply {
            put(COL_2, task)
        }
        db?.update(TABLE_NAME, values, "ID=?", arrayOf(id.toString()))
    }

    fun updateStatus(id: Int, status: Int) {
        db = writableDatabase
        val values = ContentValues().apply {
            put(COL_3, status)
        }
        db?.update(TABLE_NAME, values, "ID=?", arrayOf(id.toString()))
    }

    fun deleteTask(id: Int) {
        db = writableDatabase
        db?.delete(TABLE_NAME, "ID=?", arrayOf(id.toString()))
    }

    fun getAllTasks(): List<ToDoModel> {
        db = writableDatabase
        val modelList = mutableListOf<ToDoModel>()
        var cursor: Cursor? = null

        db?.beginTransaction()
        try {
            cursor = db?.query(TABLE_NAME, null, null, null, null, null, null)
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    val task = ToDoModel(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_1)),
                        task = cursor.getString(cursor.getColumnIndexOrThrow(COL_2)),
                        status = cursor.getInt(cursor.getColumnIndexOrThrow(COL_3))
                    )
                    modelList.add(task)
                } while (cursor.moveToNext())
            }
        } finally {
            db?.endTransaction()
            cursor?.close()
        }

        return modelList
    }
}
