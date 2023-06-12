package com.project.datediary.fragment

import RetrofitAPI
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.project.datediary.activity.MainActivity
import com.project.datediary.databinding.FragmentViewPager1Binding
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
class FragmentViewPager1 : Fragment() {
    lateinit var binding: FragmentViewPager1Binding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentViewPager1Binding.inflate(inflater, container, false)


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


            val userDataCal = StaticRequestBody(
                couple_index = MainActivity.coupleIndex,
                start_year = CalendarUtil.sYear,
                start_month = CalendarUtil.sMonth
            )

            var staticResponseBody = listOf<StaticResponseBody>()
            var countList = ArrayList<StaticResponseBody>()


            RetrofitAPI.emgMedService10.addUserByEnqueue(userDataCal)
                .enqueue(object : retrofit2.Callback<ArrayList<StaticResponseBody>> {
                    override fun onResponse(
                        call: Call<ArrayList<StaticResponseBody>>,
                        response: Response<ArrayList<StaticResponseBody>>
                    ) {
                        if (response.isSuccessful) {
                            Log.d("countList", "onResponse: ${response.body()}")

                            staticResponseBody = response.body() ?: listOf()


                            for (i in staticResponseBody.indices) {
                                countList.add(staticResponseBody[i])
                            }
                            Log.d("countList", "onResponse2: $countList")
                            Toast.makeText(context, "$countList", Toast.LENGTH_SHORT).show()

                            //그달 일정 내역이 없으면 startMonth로 13을 반환함
                            ////이번달
                            if(countList[0].startMonth != "13") {
                                binding.contain21.text = "${countList[0].startMonth}월"
                                binding.contain22.text = "번 "
                                binding.contain23.text = "\"${countList[0].count}번\" "
                                binding.contain24.text = "일정이 있어요"
                            } else {
                                binding.contain21.text = "${CalendarUtil.sMonth}월"
                                binding.contain22.text = "에"
                                binding.contain23.text = ""
                                binding.contain24.text = "아직 데이트 일정이 없어요"
                            }

                            ////지난달
                            if(countList[1].startMonth != "13") {
                                binding.contain31.text = "${countList[1].startMonth}월"
                                binding.contain32.text = "에 "
                                binding.contain33.text = "\"${countList[1].count}번\" "
                                binding.contain34.text = "데이트 했어요"
                            } else {
                                binding.contain31.text = "${CalendarUtil.sMonth.toInt() -1}월"
                                binding.contain32.text = "에"
                                binding.contain33.text = ""
                                binding.contain34.text = "데이트 일정이 없어요"
                            }

                            ////지지난달
                            if(countList[2].startMonth != "13") {
                                binding.contain41.text = "${countList[2].startMonth}월"
                                binding.contain42.text = "에 "
                                binding.contain43.text = "\"${countList[2].count}번\" "
                                binding.contain44.text = "데이트 했어요"
                            } else {
                                binding.contain41.text = "${CalendarUtil.sMonth.toInt() -2}월"
                                binding.contain42.text = "에 "
                                binding.contain43.text = ""
                                binding.contain44.text = "데이트 일정이 없어요"
                            }



                            //제일 많이 데이트한 달, 데이트 안 했으면 0으로 들어옴
                            var firstCount = countList[0].count!!.toInt()
                            var secondCount = countList[1].count!!.toInt()
                            var thirdCount  = countList[2].count!!.toInt()
                            val arr = intArrayOf(firstCount, secondCount, thirdCount)

                            Arrays.sort(arr)
                            var mostDated = arr[2]

                            //제일 많이 데이트한 달 찾기2
                            for (i in 0..2) {
                                if (countList[i].count!!.toInt() == arr[2]) {
                                    binding.contain5.text = "\" ${countList[i].startMonth}월에 ${mostDated}번으로 가장 많이 만났어요 \""
                                }
                            }
                        } else {
                            Toast.makeText(context, "리스폰스 없음", Toast.LENGTH_SHORT).show()
                        }



                    }

                    override fun onFailure(
                        call: Call<ArrayList<StaticResponseBody>>,
                        t: Throwable
                    ) {

                    }
                })


        return binding.root
    }
}

