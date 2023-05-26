package com.project.datediary.util

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.datediary.R
import com.project.datediary.activity.AddScheduleActivity2
import com.project.datediary.adapter.EmployeeAdapter
import com.project.datediary.model.Employee

// Define a DialogList class that extends Dialog
abstract class DialogList(
    context: AddScheduleActivity2,
    private var list: ArrayList<Employee>,
) : Dialog(context) {

    private var adapter: EmployeeAdapter? = null

    // This method is called when the Dialog is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState ?: Bundle())

        // Use the LayoutInflater to inflate the
        // dialog_list layout file into a View object
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_list, null)

        // Set the dialog's content view
        // to the newly created View object
        setContentView(view)

        // Allow the dialog to be dismissed
        // by touching outside of it
        setCanceledOnTouchOutside(true)

        // Allow the dialog to be canceled
        // by pressing the back button
        setCancelable(true)
        // Set up the RecyclerView in the dialog
        setUpRecyclerView(view)
    }

    // This method sets up the RecyclerView in the dialog
    private fun setUpRecyclerView(view: View) {
        // Find the RecyclerView in the layout file and set
        // its layout manager to a LinearLayoutManager
        view.findViewById<RecyclerView>(R.id.rvList).layoutManager = LinearLayoutManager(context)
        // Create a new instance of the EmployeeAdapter
        // and set it as the RecyclerView's adapter
        adapter = EmployeeAdapter(context, list)
        view.findViewById<RecyclerView>(R.id.rvList).adapter = adapter
    }
}
