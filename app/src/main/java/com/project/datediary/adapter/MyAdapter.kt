package com.project.datediary.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.project.datediary.Activity.AddScheduleActivity
import com.project.datediary.R
import com.project.datediary.util.DataVo


class MyAdapter(
    private val context: Context
) : RecyclerView.Adapter<MyAdapter.ItemViewHolder>() {

    var mPosition = 0


    inner class ItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val userPhoto = itemView.findViewById<ImageView>(R.id.userImg)
        private val userName = itemView.findViewById<TextView>(R.id.userNameTxt)
        private val userPay = itemView.findViewById<TextView>(R.id.payTxt)
        private val userAddress: TextView = itemView.findViewById<TextView>(R.id.addressTxt)

        fun bind(dataVo: DataVo, context: Context) {
            if (dataVo.photo != "") {
                val resourceId =
                    context.resources.getIdentifier(dataVo.photo, "drawable", context.packageName)

                if (resourceId > 0) {
                    userPhoto.setImageResource(resourceId)
                } else {
                    userPhoto.setImageResource(R.mipmap.ic_launcher_round)
                }
            } else {
                userPhoto.setImageResource(R.mipmap.ic_launcher_round)
            }

            //TextView에 데이터 세팅
            userName.text = dataVo.name
            userPay.text = dataVo.pay.toString()
            userAddress.text = dataVo.address
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

//    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        holder.bind(dataList[position], context)
//
//        holder.itemView.setOnClickListener { view ->
//            Toast.makeText(view.context, "$position 아이템 클릭!", Toast.LENGTH_SHORT).show()
//
//            // open another activity on item click
//            val intent = Intent(context, AddScheduleActivity::class.java)
//            intent.putExtra("image_name", dataList[position].photo) // put image data in Intent
//            context.startActivity(intent) // start Intent
//
//        }
//
//        holder.itemView.setOnLongClickListener { view ->
//            Toast.makeText(view.context, "$position 아이템 롱클릭!", Toast.LENGTH_SHORT).show()
//            return@setOnLongClickListener true
//        }
//    }

//    override fun getItemCount(): Int {
//        return dataList.size
//    }
//
//    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
//        val movedItem = dataList.removeAt(fromPosition)
//        dataList.add(toPosition, movedItem)
//        notifyItemMoved(fromPosition, toPosition)
//        return true
//    }
//
//    fun onItemDismiss(position: Int) {
//        dataList.removeAt(position)
//        notifyItemRemoved(position)
//    }
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val textView: TextView = itemView.findViewById(R.id.textView)
//    }
//}

    interface ItemTouchHelperAdapter {
        fun onItemMove(fromPosition: Int, toPosition: Int): Boolean
        fun onItemDismiss(position: Int)
    }

    class ItemTouchHelperCallback(private val adapter: ItemTouchHelperAdapter) :
        ItemTouchHelper.Callback() {

        override fun isLongPressDragEnabled(): Boolean {
            return true
        }

        override fun isItemViewSwipeEnabled(): Boolean {
            return false
        }

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            val dragFlags =
                ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            return makeMovementFlags(dragFlags, 0)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            adapter.onItemDismiss(viewHolder.adapterPosition)
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            super.onSelectedChanged(viewHolder, actionState)
            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                viewHolder?.itemView?.setBackgroundColor(Color.LTGRAY)
            }
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            viewHolder.itemView.setBackgroundColor(0)
        }
    }
}