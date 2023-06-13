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