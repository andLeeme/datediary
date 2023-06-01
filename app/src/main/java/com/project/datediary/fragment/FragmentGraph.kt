package com.project.datediary.fragment

import RetrofitAPI
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.datediary.adapter.ListAdapter
import com.project.datediary.databinding.FragmentGraphBinding
import com.project.datediary.model.Coin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentGraph : Fragment() {

    lateinit var binding: FragmentGraphBinding
    lateinit var listAdapter: ListAdapter
    var coinList = listOf<Coin>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGraphBinding.inflate(inflater, container, false)

        listAdapter = ListAdapter()


        binding.btn01.setOnClickListener {
            listAdapter.setList(coinList)
            listAdapter.notifyDataSetChanged()
        }

        binding.recycler01.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        initList()
        return binding.root
    }

    private fun initList() {
        val call = CoinAPI.getRetrofitService.getCoinAll()
        call.enqueue(object : Callback<List<Coin>> {
            override fun onResponse(call: Call<List<Coin>>, response: Response<List<Coin>>) {
//                Toast.makeText(context, "Call Success", Toast.LENGTH_SHORT).show()
                if (response.isSuccessful) {
                    coinList = response.body() ?: listOf()
                    listAdapter.setList(coinList)
                }
            }

            override fun onFailure(call: Call<List<Coin>>, t: Throwable) {
//                Toast.makeText(context, "Call Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }
}


