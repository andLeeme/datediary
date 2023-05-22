package com.project.datediary.fragment

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.DragEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import com.project.datediary.databinding.FragmentStaggeredGridBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.project.datediary.R


class FragmentStaggeredGrid : Fragment() {

    lateinit var binding: FragmentStaggeredGridBinding


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        recyclerView = binding.recyclerView

        binding = FragmentStaggeredGridBinding.inflate(inflater, container, false)


        val layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        recyclerView.layoutManager = layoutManager

        // 아이템 데이터 생성
        val items = mutableListOf<String>()
        for (i in 1..20) {
            items.add("Item $i")
        }

        // 어댑터 설정
        adapter = ItemAdapter(requireContext(), items)
        recyclerView.adapter = adapter

        // 아이템 터치 헬퍼 설정
        val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)

        return binding.root
    }

    inner class ItemAdapter(private val context: Context, private val items: List<String>) :
        RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val view =
                LayoutInflater.from(context).inflate(R.layout.item_view, parent, false)
            return ItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val item = items[position]
            holder.textView.text = item

            // 드래그 앤 드롭 이벤트 처리
            holder.itemView.setOnLongClickListener { v ->
                val clipText = ClipData.newPlainText("text", item)
                val shadowBuilder = View.DragShadowBuilder(v)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(
                        clipText,
                        shadowBuilder,
                        v,
                        0
                    )
                } else {
                    v.startDrag(
                        clipText,
                        shadowBuilder,
                        v,
                        0
                    )
                }
            }

            holder.itemView.setOnDragListener(DragListener())
        }

        override fun getItemCount(): Int {
            return items.size
        }

        inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView: TextView = itemView.findViewById(R.id.textView)
        }
    }

    inner class ItemTouchHelperCallback(private val adapter: ItemAdapter) :
        ItemTouchHelper.Callback() {

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            return makeMovementFlags(dragFlags, 0)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            adapter.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            // 스와이프 동작 처리
        }

        override fun isLongPressDragEnabled(): Boolean {
            return true
        }
    }

    inner class DragListener : View.OnDragListener {

        override fun onDrag(v: View, event: DragEvent): Boolean {
            val action = event.action
            when (action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        v.setBackgroundColor(resources.getColor(R.color.black))
                        v.invalidate()
                        return true
                    }
                    return false
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    v.setBackgroundColor(resources.getColor(R.color.black))
                    v.invalidate()
                    return true
                }
                DragEvent.ACTION_DRAG_LOCATION -> {
                    return true
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    v.setBackgroundColor(resources.getColor(android.R.color.white))
                    v.invalidate()
                    return true
                }
                DragEvent.ACTION_DROP -> {
                    val item = event.clipData.getItemAt(0).text.toString()
                    val targetView = v as TextView
                    targetView.text = item
                    v.setBackgroundColor(resources.getColor(android.R.color.white))
                    v.invalidate()
                    return true
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    v.setBackgroundColor(resources.getColor(android.R.color.white))
                    v.invalidate()
                    return true
                }
                else -> return false
            }
        }

    }
}

