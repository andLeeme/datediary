//package com.project.datediary.adapter
//
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.project.datediary.R
//import com.project.datediary.util.Item
//
//
//class MyAdapter(private val items: ArrayList<Item>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item_layout, parent, false)
//        return ViewHolder(view)
//    }
//
//    fun addItem(item: Item) {
//        items.add(item)
//        notifyItemInserted(items.size-1)
//        Log.d("작동 테스트", "addItem: 성공! ")
//    }
//
//    fun addItems(newItems: List<Item>) {
//        val startPosition = items.size
//        items.addAll(newItems)
//        notifyItemRangeInserted(startPosition, newItems.size)
//    }
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val imageView: ImageView = itemView.findViewById(R.id.imageView2323)
//    }
//
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val currentItem = items[position]
//        holder.imageView.setImageResource(currentItem.imageResId)
//    }
//
//    override fun getItemCount(): Int {
//        return items.size
//    }
//}