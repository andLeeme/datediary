package com.project.datediary.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.project.datediary.activity.MainActivity
import com.project.datediary.databinding.FragmentViewPager3Binding
import com.project.datediary.model.Static3RequestBody
import com.project.datediary.model.Static3ResponseBody
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
class FragmentViewPager3 : Fragment() {
    lateinit var binding: FragmentViewPager3Binding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentViewPager3Binding.inflate(inflater, container, false)

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

            //세 번째 칸 안 씀
//            delay(400).run {
//                val fadeOut4 = ObjectAnimator.ofFloat(binding.contain4, "alpha", 0f, 1f)
//                fadeOut4.duration = 500
//                fadeOut4.start()
//                binding.contain4.visibility = View.VISIBLE
//            }

            delay(500).run {
                val fadeOut5 = ObjectAnimator.ofFloat(binding.contain5, "alpha", 0f, 1f)
                fadeOut5.duration = 500
                fadeOut5.start()
                binding.contain5.visibility = View.VISIBLE
            }
        }


        val userDataCal = Static3RequestBody(
            couple_index = MainActivity.coupleIndex,
            start_month = CalendarUtil.sMonth,
            start_year = CalendarUtil.sMonth
        )

        var static3ResponseBody = listOf<Static3ResponseBody>()
        var countList3 = ArrayList<Static3ResponseBody>()


        RetrofitAPI.emgMedService14.addUserByEnqueue(userDataCal)
            .enqueue(object : retrofit2.Callback<ArrayList<Static3ResponseBody>> {
                override fun onResponse(
                    call: Call<ArrayList<Static3ResponseBody>>,
                    response: Response<ArrayList<Static3ResponseBody>>
                ) {
                    if (response.isSuccessful) {
                        Log.d("countList3", "onResponse3: ${response.body()}")

                        static3ResponseBody = response.body() ?: listOf()


                        for (i in static3ResponseBody.indices) {
                            countList3.add(static3ResponseBody[i])
                        }
                        Log.d("countList3", "onResponse3: $countList3")
                        Toast.makeText(context, "$countList3", Toast.LENGTH_SHORT).show()


                        var countSum = 0  //미션 수 총합
                        for(i in 0 until countList3.size) {
                            countSum += countList3[i].count!!.toInt()
                        }


                        //미션을 한 번도 수행하지 않았을 경우
                        if(countSum == 0) {
                            binding.contain21.text = ""
                            binding.contain22.text = "아직 받은 미션이 없어요"
                            binding.contain23.text = ""
                            binding.contain24.text = ""

                            binding.contain3.setBackgroundResource(0)  // 안 보이게
                            binding.contain31.text = ""
                            binding.contain32.text = ""
                            binding.contain33.text = ""
                            binding.contain34.text = ""

                            binding.contain4.setBackgroundResource(0)  // 안 보이게
                            binding.contain31.text = ""
                            binding.contain32.text = ""
                            binding.contain33.text = ""
                            binding.contain34.text = ""

                        }
                        //미션 수행 횟수가 있을 경우
                        else {

                            //첫 번째 줄 "총 ~번 미션을 받았어요"
                            binding.contain21.text = ""
                            binding.contain22.text = "총 "
                            binding.contain23.text = "\"${countSum}번\" "
                            binding.contain24.text = "미션을 받았어요"

                            //두 번째 줄 "이번달에는 ~번 미션을 받았어요"
                            ////이번달 찾기
                            var thisMonthCount = 0
                            for(i in 0 until countList3.size) {
                                if(countList3[i].year == CalendarUtil.sYear
                                    && countList3[i].month == CalendarUtil.sMonth) {
                                    thisMonthCount = countList3[i].count!!.toInt()
                                }
                            }

                            //이번달 미션 수행 횟수가 없으면
                            if(thisMonthCount == 0) {
                                binding.contain31.text = ""
                                binding.contain32.text = "이번달에는 "
                                binding.contain33.text = ""
                                binding.contain34.text = "받은 미션이 없어요"
                            }
                            //이번달 미션 수행 횟수가 있으면
                            else {
                                binding.contain31.text = ""
                                binding.contain32.text = "이번달에는 "
                                binding.contain33.text = "\"${thisMonthCount}번\" "
                                binding.contain34.text = "미션을 받았어요"
                            }


                            //이번달 미션 횟수와 지난달 미션 횟수 비교해서 contain5에 넣기
                            //"지난달보다 ~번 더 많이/적게 미션을 수행했어요"
                            ////지난달 미션 횟수 null체크
                            var lastMonthCount = 0

                            //////이번달이 1월인지 아닌지 체크
                            when(CalendarUtil.sMonth.toInt()) {
                                1 -> {
                                    for(i in 0 until countList3.size) {
                                        if(countList3[i].year == (CalendarUtil.sYear.toInt()-1).toString()
                                            && countList3[i].month == "12") {
                                            lastMonthCount = countList3[i].count!!.toInt()
                                        }
                                    }
                                }

                                else -> {
                                    for(i in 0 until countList3.size) {
                                        if(countList3[i].year == CalendarUtil.sYear
                                            && countList3[i].month == (CalendarUtil.sMonth.toInt()-1).toString()) {
                                            lastMonthCount = countList3[i].count!!.toInt()
                                        }
                                    }
                                }

                            }

                            //////////비교
                            if(thisMonthCount >= lastMonthCount) {
                                binding.contain5.text = "\" 지난달보다 ${thisMonthCount - lastMonthCount}번 더 많은 미션을 수행했어요 \""
                            } else {
                                binding.contain5.text = "\" 지난달보다 ${lastMonthCount - thisMonthCount}번 더 적은 미션을 수행했어요 \""
                            }


                        }





                    } else {
                        Toast.makeText(context, "리스폰스3 없음", Toast.LENGTH_SHORT).show()
                    }


                }

                override fun onFailure(
                    call: Call<ArrayList<Static3ResponseBody>>,
                    t: Throwable
                ) {

                }
            })






        return binding.root
    }
}