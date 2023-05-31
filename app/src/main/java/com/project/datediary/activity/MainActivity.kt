package com.project.datediary.activity

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.project.datediary.R
import com.project.datediary.adapter.DayScheduleAdapter
import com.project.datediary.fragment.FragmentCalendar
import com.project.datediary.fragment.FragmentGraph
import com.project.datediary.fragment.FragmentHome
import com.project.datediary.fragment.FragmentMyPage
import com.project.datediary.fragment.FragmentStory
import com.project.datediary.databinding.ActivityMainBinding
import com.project.datediary.databinding.FragmentCalendarBinding
import com.project.datediary.model.Coin
import com.project.datediary.model.ScheduleResponseBody
import com.project.datediary.model.ScheduleShowResponseBody
import com.project.datediary.util.CalendarUtil
import com.project.datediary.util.CalendarUtil.Companion.sDay
import com.project.datediary.util.CalendarUtil.Companion.sMonth
import com.project.datediary.util.CalendarUtil.Companion.sYear
import com.project.datediary.util.bottomSheetBehavior
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var binding2: FragmentCalendarBinding

    lateinit var DayScheduleAdapter: DayScheduleAdapter



    //bottomSheetBehavior 객체 생성
//    companion object {
//        private lateinit var binding: ActivityMainBinding
//        var bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
//    }



    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding2 = FragmentCalendarBinding.inflate(layoutInflater)

        splashScreen.setOnExitAnimationListener { splashScreenView ->
            // Create your custom animation.
            val slideUp = ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_Y,
                0f,
                -splashScreenView.height.toFloat()
            )
            slideUp.interpolator = AnticipateInterpolator()
            slideUp.duration = 700L

            // Call SplashScreenView.remove at the end of your custom animation.
            slideUp.doOnEnd { splashScreenView.remove() }

            // Run your animation.
            slideUp.start()
        }


        //스플래쉬화면을 더 오래 실행하는법.
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {

                    //조건이 true일때 화면 전환
                    return if (true) {
                        // The content is ready; start drawing.
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content is not ready; suspend.
                        false
                    }
                }
            }
        )


        setContentView(binding.root)


        var bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)

        binding.image1.setOnClickListener {

            //BottomSheetBehavior.from(bottomSheetBehavior의 자식 요소 넣어주기)
            if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)}
            Log.d("DateFromUtil", "onCreate: ${CalendarUtil.sMonth}.${CalendarUtil.sDay}.${CalendarUtil.sYear}")
        }

//        if(bottomSheetBehavior.bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
//        Log.d("DateFromUtil", "onCreate: ${CalendarUtil.sMonth}.${CalendarUtil.sDay}.${CalendarUtil.sYear}")
//        }



        DayScheduleAdapter = DayScheduleAdapter()

        binding.recycler10.apply {
            adapter = DayScheduleAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        val scheduleShowList : ArrayList<ScheduleShowResponseBody> = ArrayList<ScheduleShowResponseBody>()

        scheduleShowList.add(ScheduleShowResponseBody("1","1","2023","5",
            "30","09:00","2023","5","30","10:00","1","영화관",
            "현하랑 영화보기","1","1"))

        scheduleShowList.add(ScheduleShowResponseBody("1","1","2023","5",
            "30","20:00","2023","5","30","21:00","1","산책",
            "현하랑 산책하기","1","1"))

        scheduleShowList.add(ScheduleShowResponseBody("1","1","2023","5",
            "30","21:00","2023","5","30","22:00","1","노래방",
            "현하랑 노래부르기","1","1"))

        scheduleShowList.add(ScheduleShowResponseBody("1","1","2023","5",
            "30","21:00","2023","5","30","22:00","1","노래방",
            "현하랑 노래부르기","1","1"))

        scheduleShowList.add(ScheduleShowResponseBody("1","1","2023","5",
            "30","21:00","2023","5","30","22:00","1","노래방",
            "현하랑 노래부르기","1","1"))

        scheduleShowList.add(ScheduleShowResponseBody("1","1","2023","5",
            "30","21:00","2023","5","30","22:00","1","노래방",
            "현하랑 노래부르기","1","1"))

        scheduleShowList.add(ScheduleShowResponseBody("1","1","2023","5",
            "30","21:00","2023","5","30","22:00","1","노래방",
            "현하랑 노래부르기","1","1"))


        DayScheduleAdapter.setList(scheduleShowList)


        binding.addBtn.setOnClickListener {
            val intent = Intent(applicationContext, AddScheduleActivity::class.java)
            startActivity(intent)
        }


//        binding.sheetText1.setOnClickListener {
//            if (binding.sheetText1.text.toString() == "바뀐 일정") {
//                binding.sheetText1.text = "오늘의 일정이에요"
//            } else {
//                binding.sheetText1.text = "바뀐 일정"
//                Toast.makeText(this, "내용 바뀜", Toast.LENGTH_SHORT).show()
//            }
//            return@setOnClickListener
//        }


        initBottomNavigation()
    }


    private fun initBottomNavigation() {

        supportFragmentManager.beginTransaction().replace(R.id.main_frm, FragmentHome())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {

                    supportFragmentManager.beginTransaction().replace(R.id.main_frm, FragmentHome())
                        .commitAllowingStateLoss()
                    binding.bottomSheetDrawer.visibility = View.VISIBLE
                    return@setOnItemSelectedListener true
                }

                R.id.story -> {

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, FragmentStory()).commitAllowingStateLoss()
                    binding.bottomSheetDrawer.visibility = View.INVISIBLE
                    return@setOnItemSelectedListener true
                }

                R.id.calendar -> {

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, FragmentCalendar()).commitAllowingStateLoss()
                    binding.bottomSheetDrawer.visibility = View.VISIBLE
                    return@setOnItemSelectedListener true
                }

                R.id.graph -> {

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, FragmentGraph()).commitAllowingStateLoss()
                    binding.bottomSheetDrawer.visibility = View.INVISIBLE
                    return@setOnItemSelectedListener true
                }

                R.id.myPage -> {

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, FragmentMyPage()).commitAllowingStateLoss()
                    binding.bottomSheetDrawer.visibility = View.INVISIBLE
                    return@setOnItemSelectedListener true
                }

                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }
    }

}