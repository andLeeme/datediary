package com.project.datediary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.datediary.R
import com.project.datediary.model.Employee
import java.util.ArrayList

// Define a class for the adapter,
// which extends RecyclerView.Adapter
class EmployeeAdapter(
    private val context: Context,
    // The adapter needs a list of data to display
    private var list: ArrayList<Employee>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // This method is called when the RecyclerView needs a new ViewHolder to display an item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // Use the LayoutInflater to
        // create a new view from the
        // item_employee layout file
        val view = LayoutInflater.from(context).inflate(R.layout.item_employee, parent, false)
        // Return a new ViewHolder that
        // holds the newly created view
        return MyViewHolder(view)
    }

    // This method is called when the RecyclerView needs
    // to display the data at a certain position
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Get the item at the specified position from the list
        val item = list[position]

        // Check if the ViewHolder is of type MyViewHolder
        if (holder is MyViewHolder) {
            // If it is, set the name and email TextViews
            // to the corresponding values in the item
            holder.btn.text = item.name

        }
    }

    // This method returns the total
    // number of items in the list
    override fun getItemCount(): Int {
        return list.size
    }

    // Define a ViewHolder class that holds references
    // to the TextViews in the item_employee layout file
    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val btn = view.findViewById<Button>(R.id.selectBtn)
    }
}
