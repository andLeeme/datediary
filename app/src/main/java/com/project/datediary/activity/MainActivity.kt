package com.project.datediary.activity

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.project.datediary.R
import com.project.datediary.adapter.DayScheduleAdapter
import com.project.datediary.fragment.FragmentCalendar
import com.project.datediary.fragment.FragmentGraph
import com.project.datediary.fragment.FragmentHome
import com.project.datediary.fragment.FragmentMyPage
import com.project.datediary.fragment.FragmentStory
import com.project.datediary.databinding.ActivityMainBinding
import com.project.datediary.databinding.FragmentCalendarBinding
import com.project.datediary.model.ScheduleShowResponseBody
import com.project.datediary.util.CalendarUtil
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var binding2: FragmentCalendarBinding



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

                    //홈에서는 오늘 날짜로 초기화
                    CalendarUtil.sYear = LocalDateTime.now().format(CalendarUtil.formatterYear).toString()
                    CalendarUtil.sMonth = LocalDateTime.now().format(CalendarUtil.formatterMonth).toString()
                    CalendarUtil.sDay = LocalDateTime.now().format(CalendarUtil.formatterDay).toString()
                    CalendarUtil.logDate()


                    return@setOnItemSelectedListener true
                }

                R.id.story -> {

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, FragmentStory()).commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.calendar -> {

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, FragmentCalendar()).commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.graph -> {

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, FragmentGraph()).commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.myPage -> {

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, FragmentMyPage()).commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }
    }


    override fun onBackPressed() {
        Toast.makeText(applicationContext, "뒤로가기 눌러짐", Toast.LENGTH_SHORT).show()


        //현재 프래그먼트의 아이디를 가져옴 근데 머라머라 길게 나옴
        //FragmentGraph{ad92255} (fd9338a8-56a1-4298-93f6-fd00ca33e7b9 id=0x7f0a0115) 이렇게...
        val fragment  = supportFragmentManager.findFragmentById(R.id.main_frm)
        Log.d("fragmentTest1", "onBackPressed: $fragment")

        //프래그먼트 아이디의 앞부분만 split()해서 써야됨
        // 그러면 현재 프래그먼트가 FragmentGraph와 같이 나옴
        var splitedFragment = fragment.toString().split("{")
        Log.d("fragmentTest2", "onBackPressed: ${splitedFragment[0]}")


        //이제 home이면 앱이 꺼지게 하고
        //다른 프래그먼트였으면 홈으로 오게 함
        //홈과 캘린더 프래그먼트에서는 bottomsheet의 상태를 한 번 더 체크함



        when(splitedFragment[0]) {
            "FragmentHome" ->
                if(0 == 0){

                }else {

                }
            "FragmentStory", "FragmentGraph", "FragmentMyPage" -> {

                //홈이 선택된 상태로 만듦
                //2131362030 이게 홈 프래그먼트의 ID임
                binding.mainBnv.selectedItemId = 2131362030.toInt()
            }

            "FragmentCalendar" -> 0==0

        }
    }



}