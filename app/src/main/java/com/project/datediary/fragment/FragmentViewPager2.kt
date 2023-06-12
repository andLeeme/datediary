package com.project.datediary.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.project.datediary.activity.EditScheduleActivity
import com.project.datediary.activity.MainActivity
import com.project.datediary.databinding.FragmentViewPager2Binding
import com.project.datediary.model.Static2RequestBody
import com.project.datediary.model.Static2ResponseBody
import com.project.datediary.model.StaticRequestBody
import com.project.datediary.model.StaticResponseBody
import com.project.datediary.util.CalendarUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.util.Arrays


// Tab1Fragment.kt
class FragmentViewPager2 : Fragment() {
    lateinit var binding: FragmentViewPager2Binding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        binding = FragmentViewPager2Binding.inflate(inflater, container, false)

        binding.contain1.visibility = View.INVISIBLE
        binding.contain2.visibility = View.INVISIBLE
        binding.contain3.visibility = View.INVISIBLE
        binding.contain4.visibility = View.INVISIBLE
        binding.contain5.visibility = View.INVISIBLE

        CoroutineScope(Dispatchers.Main).launch {

            delay(100).run {
                val fadeOut1 = ObjectAnimator.ofFloat(binding.contain1, "alpha", 0f, 1f)
                fadeOut1.duration = 500
                fadeOut1.start()
                binding.contain1.visibility = View.VISIBLE
            }


            delay(200).run {
                val fadeOut2 = ObjectAnimator.ofFloat(binding.contain2, "alpha", 0f, 1f)
                fadeOut2.duration = 500
                fadeOut2.start()
                binding.contain2.visibility = View.VISIBLE
            }

            delay(300).run {
                val fadeOut3 = ObjectAnimator.ofFloat(binding.contain3, "alpha", 0f, 1f)
                fadeOut3.duration = 500
                fadeOut3.start()
                binding.contain3.visibility = View.VISIBLE
            }

            delay(400).run {
                val fadeOut4 = ObjectAnimator.ofFloat(binding.contain4, "alpha", 0f, 1f)
                fadeOut4.duration = 500
                fadeOut4.start()
                binding.contain4.visibility = View.VISIBLE
            }

            delay(500).run {
                val fadeOut5 = ObjectAnimator.ofFloat(binding.contain5, "alpha", 0f, 1f)
                fadeOut5.duration = 500
                fadeOut5.start()
                binding.contain5.visibility = View.VISIBLE
            }
        }



//        val userDataCal = Static2RequestBody(
//            couple_index = MainActivity.coupleIndex,
//        )

        var static2ResponseBody = listOf<Static2ResponseBody>()
        var countList2 = ArrayList<Static2ResponseBody>()


        RetrofitAPI.emgMedService13.addUserByEnqueue(MainActivity.coupleIndex)
            .enqueue(object : retrofit2.Callback<ArrayList<Static2ResponseBody>> {
                override fun onResponse(
                    call: Call<ArrayList<Static2ResponseBody>>,
                    response: Response<ArrayList<Static2ResponseBody>>
                ) {
                    if (response.isSuccessful) {
                        Log.d("countList2", "onResponse: ${response.body()}")

                        static2ResponseBody = response.body() ?: listOf()

                        for (i in static2ResponseBody.indices) {

                            //방문 장소 안 넣으면 ""로 카운트됨
                            //""면 예외처리
                            if(static2ResponseBody[i].placeCode !="") {
                            countList2.add(static2ResponseBody[i])
                            }
                        }

                        Log.d("countList2", "onResponse2: $countList2")
                        Toast.makeText(context, "$countList2", Toast.LENGTH_SHORT).show()

                        if(countList2.size<4) {
                            for( i in 1..(4-countList2.size)) {
                                countList2.add(Static2ResponseBody("", ""))
                            }
                        }

                        Log.d("countList2", "후처리: $countList2")


                        //장소코드로 방문장소 이름 알아내기
                        //setPlaceName(countList2[0].placeCode) 이런 식으로 밑에 함수에서 처리함


                        //문구 넣어주기
                        ////첫 번째 문구
                        if(countList2[0].placeCode != "") {
                            binding.contain21.text = "${setPlaceName(countList2[0].placeCode)}"
                            binding.contain22.text = "에서 "
                            binding.contain23.text = "\"${countList2[0].count}회\" "
                            binding.contain24.text = "데이트 했어요"
                        } else {
                            binding.contain21.text = ""
                            binding.contain22.text = ""
                            binding.contain23.text = ""
                            binding.contain24.text = "아직 데이트 일정이 없어요"
                        }

                        ////두 번째 문구
                        if(countList2[1].placeCode != "") {
                            binding.contain31.text = "${setPlaceName(countList2[1].placeCode)}"
                            binding.contain32.text = "에서 "
                            binding.contain33.text = "\"${countList2[1].count}회\" "
                            binding.contain34.text = "데이트 했어요"
                        } else {
                            binding.contain3.setBackgroundResource(0)
                            binding.contain31.text = ""
                            binding.contain32.text = ""
                            binding.contain33.text = ""
                            binding.contain34.text = ""
                        }

                        ////세 번째 문구
                        if(countList2[2].placeCode != "") {
                            binding.contain41.text = "${setPlaceName(countList2[2].placeCode)}"
                            binding.contain42.text = "에서 "
                            binding.contain43.text = "\"${countList2[2].count}회\" "
                            binding.contain44.text = "데이트 했어요"
                        } else {
                            binding.contain4.visibility = View.INVISIBLE
                            binding.contain41.text = ""
                            binding.contain42.text = ""
                            binding.contain43.text = ""
                            binding.contain44.text = "아직 데이트 일정이 없어요"
                        }

                        var mostDatedPlace = setPlaceName(countList2[0].placeCode)
                        binding.contain5.text = "\" ${mostDatedPlace}에서 가장 많은 추억을 담았어요 \""

                    } else {

                        Toast.makeText(context, "리스폰스2 없음", Toast.LENGTH_SHORT).show()


                    }



                }

                override fun onFailure(
                    call: Call<ArrayList<Static2ResponseBody>>,
                    t: Throwable
                ) {

                }
            })


        return binding.root
    }

    private fun setPlaceName(placeCode: String?): String {

        var placeName = ""
        when (placeCode) {
            "" -> placeName = "방문 장소"
            "1" -> placeName = "영화관"
            "2" -> placeName = "바/주점"
            "3" -> placeName = "보드게임"
            "4" -> placeName = "여행"
            "5" -> placeName = "식당"
            "6" -> placeName = "도서관"
            "7" -> placeName = "전시관"
            "8" -> placeName = "동물원"
            "9" -> placeName = "놀이공원"
            "10" -> placeName = "카페"
            "11" -> placeName = "관람"
            "12" -> placeName = "스포츠"
            "13" -> placeName = "공방"
            "14" -> placeName = "드라이브"
            "15" -> placeName = "식물원"
            "16" -> placeName = "기타"
        }
        return placeName
    }
}