package com.project.datediary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.datediary.databinding.ItemListBinding

class CoinListAdapter: RecyclerView.Adapter<CoinListAdapter.MyView>() {
    private var coinList = listOf<Coin>()

    inner class MyView(private val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            binding.text01.text = coinList[pos].product_name
            binding.text02.text = coinList[pos].provider
            binding.text03.text = coinList[pos].sub_category
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val view = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyView(view)
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return coinList.size
    }

    fun setList(list: List<Coin>) {
        coinList = list
    }
}