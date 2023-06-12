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



        val userDataCal = Static2RequestBody(
            couple_index = MainActivity.coupleIndex,
        )

        var static2ResponseBody = listOf<Static2ResponseBody>()
        var countList2 = ArrayList<Static2ResponseBody>()


        RetrofitAPI.emgMedService13.addUserByEnqueue(userDataCal)
            .enqueue(object : retrofit2.Callback<ArrayList<Static2ResponseBody>> {
                override fun onResponse(
                    call: Call<ArrayList<Static2ResponseBody>>,
                    response: Response<ArrayList<Static2ResponseBody>>
                ) {
                    if (response.isSuccessful) {
                        Log.d("countList", "onResponse: ${response.body()}")

                        static2ResponseBody = response.body() ?: listOf()

                        for (i in static2ResponseBody.indices) {
                            countList2.add(static2ResponseBody[i])
                        }
                        Log.d("countList", "onResponse2: $countList2")
                        Toast.makeText(context, "$countList2", Toast.LENGTH_SHORT).show()


                    } else {
                        Toast.makeText(context, "리스폰스 없음", Toast.LENGTH_SHORT).show()
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
}