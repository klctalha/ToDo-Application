package com.talhakilic.todolistproject

import android.app.Activity
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.talhakilic.todolistproject.Model.ToDoModel
import com.talhakilic.todolistproject.YRDM.DataBaseHelper
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class GorevEkle : BottomSheetDialogFragment() {
    companion object {
        const val TAG = "GorevEkle"

        fun newInstance(): GorevEkle {
            return GorevEkle()
        }
    }

    private lateinit var mEditText: EditText
    private lateinit var mSaveButton: Button
    private lateinit var myDb: DataBaseHelper
    private lateinit var mCancelButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_newtask, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mEditText = view.findViewById(R.id.edittext)
        mSaveButton = view.findViewById(R.id.button_save)
        mCancelButton = view.findViewById(R.id.buttonCancel)
        myDb = DataBaseHelper(requireActivity())

        var isUpdate = false
        val bundle = arguments
        if (bundle != null) {
            isUpdate = true
            val task = bundle.getString("task")
            mEditText.setText(task)

            if (task!!.isNotEmpty()) {
                mSaveButton.isEnabled = false
            }
        }
        mCancelButton.setOnClickListener {
            dismiss()
        }


        mEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty()) {
                    mSaveButton.isEnabled = false
                    mSaveButton.setBackgroundColor(Color.GRAY)
                } else {
                    mSaveButton.isEnabled = true
                    mSaveButton.setBackgroundColor(resources.getColor(R.color.blue2))
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        mSaveButton.setOnClickListener {
            val text = mEditText.text.toString()

            if (isUpdate) {
                myDb.updateTask(bundle!!.getInt("id"), text)
            } else {
                val item = ToDoModel().apply {
                    task = text
                    status = 0
                }
                myDb.insertTask(item)
            }

            dismiss()
            (activity as? OnDialogCloseListener)?.onDialogClose(dialog!!) // Corrected here
        }
    }
    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        dismiss()
    }


}