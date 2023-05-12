package com.project.datediary.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.datediary.adapter.ArticleAdapter
import com.project.datediary.model.ArticleModel
import com.project.datediary.databinding.FragmentHomeBinding

class FragmentHome : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val articleAdapter = ArticleAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.recycler01.layoutManager = LinearLayoutManager(context)
        binding.recycler01.adapter = articleAdapter

        articleAdapter.submitList(mutableListOf<ArticleModel>().apply {
            add(ArticleModel("1","맥북 프로16인치",100000,"1,000,000",""))
            add(ArticleModel("1","갤럭시S22",101010,"800,000",""))
        })

        return binding.root
    }

}
