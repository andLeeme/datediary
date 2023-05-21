package com.project.datediary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.datediary.adapter.ProductAdapter
import com.project.datediary.databinding.FragmentStoryEditBinding
import com.project.datediary.model.Schedule


class FragmentStoryEdit : Fragment() {

    lateinit var binding: FragmentStoryEditBinding
    private val productAdapter = ProductAdapter()

    var coinList = listOf<Schedule>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoryEditBinding.inflate(inflater, container, false)

        binding.recycler01.layoutManager = LinearLayoutManager(context)
        binding.recycler01.adapter = productAdapter


        binding.btn01.setOnClickListener {
            productAdapter.setList(coinList)
            productAdapter.notifyDataSetChanged()
        }

        binding.recycler01.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
//        initList()
        return binding.root
    }

//    private fun initList() {
//        val call = ApiObject.getRetrofitService.getCoinAll()
//        call.enqueue(object : Callback<List<Schedule>> {
//            override fun onResponse(call: Call<List<Schedule>>, response: Response<List<Schedule>>) {
//                Toast.makeText(context, "Call Success", Toast.LENGTH_SHORT).show()
//                if (response.isSuccessful) {
//                    coinList = response.body() ?: listOf()
//                    productAdapter.setList(coinList)
//                }
//            }
//
//            override fun onFailure(call: Call<List<Schedule>>, t: Throwable) {
//                Toast.makeText(context, "Call Failed", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }

}