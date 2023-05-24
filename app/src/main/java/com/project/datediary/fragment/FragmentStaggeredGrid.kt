package com.project.datediary.fragment

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.project.datediary.R
import com.project.datediary.adapter.MyAdapter
import com.project.datediary.databinding.FragmentStaggeredGridBinding
import com.project.datediary.util.Item
import java.util.Collections
import kotlin.math.abs


class FragmentStaggeredGrid : Fragment() {

    lateinit var binding: FragmentStaggeredGridBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStaggeredGridBinding.inflate(inflater, container, false)

        val recyclerView = binding.recyclerView

        val layoutManager = StaggeredGridLayoutManager(
            6, // 열의 수
            StaggeredGridLayoutManager.VERTICAL // 방향
        )
        recyclerView.layoutManager = layoutManager

        val items = ArrayList<Item>()

        val adapter = MyAdapter(items)
        recyclerView.adapter = adapter

        binding.addBtn1.setOnClickListener {
            adapter.addItem(Item(R.drawable.heart1))
            for (i in 0..30) {
                adapter.addItem(Item(R.drawable.text_img_50))
            }
        }


        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or
                        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                return makeMovementFlags(dragFlags, 0)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition = viewHolder.bindingAdapterPosition
                val toPosition = target.bindingAdapterPosition

                // 아이템 위치 변경
                Collections.swap(items, fromPosition, toPosition)
                adapter.notifyItemMoved(fromPosition, toPosition)

                return true
            }


            // 스와이프
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            }


            // 아이템을 드래그하는 동안 아이템의 투명도를 조정
            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    viewHolder?.itemView?.alpha = 0.5f
                }
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)
                viewHolder.itemView.alpha = 1.0f
            }

            //드래그 중 아이템의 크기를 조정하는 효과
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    val itemView = viewHolder.itemView
                    val itemHeight = itemView.height.toFloat()
                    val itemWidth = itemView.width.toFloat()
                    val heightOffset = (itemHeight * 0.1f).coerceAtMost(20f)

                    val newHeight = itemHeight - abs(dY) - heightOffset
                    val scale = newHeight / itemHeight

                    itemView.scaleX = scale
                    itemView.scaleY = scale
                    itemView.translationY = dY

                    // 아이템 그리기
                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )


                }

                //드래그 한 아이템이 그리드 영역을 벗어나면 스크롤이 이동하게 함
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    val recyclerViewTop = recyclerView.paddingTop
                    val recyclerViewBottom = recyclerView.height - recyclerView.paddingBottom

                    val itemView = viewHolder.itemView
                    val itemTop = itemView.top + itemView.translationY.toInt()
                    val itemBottom = itemTop + itemView.height

                    // 스크롤 처리 로직
                    if (itemTop < recyclerViewTop && dY < 0) {
                        recyclerView.smoothScrollBy(0, -5)
                    } else if (itemBottom > recyclerViewBottom && dY > 0) {
                        recyclerView.smoothScrollBy(0, 5)
                    }
                }
            }


        })
        itemTouchHelper.attachToRecyclerView(recyclerView)


        return binding.root
    }


}

