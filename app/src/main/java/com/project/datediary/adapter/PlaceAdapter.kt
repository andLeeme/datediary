package com.project.datediary.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.datediary.databinding.ItemListBinding
import com.project.datediary.databinding.ItemPlaceBinding
import com.project.datediary.model.Coin

class PlaceAdapter: RecyclerView.Adapter<PlaceAdapter.MyView>() {
    private var coinList = listOf<Coin>()

    val text = arrayOf("A Topic", "B Topic", "C Topic", "D Topic", "E Topic", "카테고리 없음")
    val placeList : ArrayList<Array<String>> = arrayListOf(text)

    inner class MyView(private val binding: ItemPlaceBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            //기본값일 때랑 값 지정했을 때 색깔 다르게 지정하기
            binding.placeList1.text = placeList[pos][0]
            Log.d("placeList", "bind: $placeList")

            //item클릭하면 dialog 종료되게 설정
//            binding.placeList.setOnClickListener {
//                @Override
//                public void onClick(View v) {
//                    String topic = mData.get(getAdapterPosition());
//                    PostingActivity.textViewPostingTopic.setText(topic);
//                    if (topic.equals("카테고리 없음")) { // 카테고리 없음 선택 시
//                        PostingActivity.textViewPostingTopic.setTextColor(Color.LTGRAY);
//                        // 회색 처리
//                    } else {
//                        PostingActivity.textViewPostingTopic.setTextColor(Color.BLACK);
//                        // 그 이외엔 검은색
//                    }
//                    PostingActivity.dialog.dismiss(); // dialog 종료
//                }
//        }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val view = ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyView(view)
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return placeList.size
    }

    fun setList(list: List<Coin>) {
        coinList = list
        Log.d("titleResponse", "titleResponse: $coinList")
    }
}