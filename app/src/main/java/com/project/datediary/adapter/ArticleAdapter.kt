//package com.project.datediary.adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.project.datediary.model.ArticleModel
//import com.project.datediary.databinding.ItemListBinding
//import java.text.SimpleDateFormat
//import java.util.*
//
//class ArticleAdapter : ListAdapter<ArticleModel, ArticleAdapter.ViewHolder>(diffUtil){
//    inner class ViewHolder (private val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root){
//        fun bind(articleModel: ArticleModel){
//            val format = SimpleDateFormat("MM년dd일")
//            val date = Date(articleModel.createdAt)
//
//
//
//            binding.text01.text = articleModel.title
//            binding.text02.text = format.format(date).toString()
//            binding.text03.text = articleModel.price
////            if(articleModel.imageUrl.isNotEmpty()){
////                Glide.with(binding.thumbnailImageView)
////                    .load(articleModel.imageUrl)
////                    .into(binding.thumbnailImageView)
////            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(currentList[position])
//    }
//
//    companion object{
//        val diffUtil = object  : DiffUtil.ItemCallback<ArticleModel>(){
//            override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
//                return oldItem.createdAt == newItem.createdAt
//            }
//
//            override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
//                return oldItem == newItem
//            }
//        }
//    }
//}