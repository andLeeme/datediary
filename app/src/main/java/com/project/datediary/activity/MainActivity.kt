package com.project.datediary.activity

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var binding2: FragmentCalendarBinding


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
                View.TRANSLATION_X,
                0f,
                -splashScreenView.height.toFloat()
            )
            slideUp.interpolator = AnticipateInterpolator()
            slideUp.duration = 700L

            // Call SplashScreenView.remove at the end of your custom animation.
            slideUp.doOnEnd { splashScreenView.remove() }

            // Run your animation.
            slideUp.start()

            val curUser = GoogleSignIn.getLastSignedInAccount(this)
            curUser?.let {
                Toast.makeText(this, "${curUser.displayName.toString()}님 반가워요", Toast.LENGTH_SHORT)
                    .show()
            }

            if (curUser == null) {
                CoroutineScope(Dispatchers.Main).launch {
                    binding.mainFrm.visibility = View.INVISIBLE
                    binding.mainBnv.visibility = View.INVISIBLE

                    delay(600).run {
                        val intent = Intent(applicationContext, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }

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
                        false
                    }
                }
            }
        )


        setContentView(binding.root)



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
                    CalendarUtil.sYear =
                        LocalDateTime.now().format(CalendarUtil.formatterYear).toString()
                    CalendarUtil.sMonth =
                        LocalDateTime.now().format(CalendarUtil.formatterMonth).toString()
                    CalendarUtil.sDay =
                        LocalDateTime.now().format(CalendarUtil.formatterDay).toString()
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

    interface onBackPressedListener {
        fun onBackPressed()
    }


    fun callHome() {
        binding.mainBnv.selectedItemId = R.id.home
    }

    private var finishCount = false

    override fun onBackPressed() {
        //아래와 같은 코드를 추가하도록 한다
        //해당 엑티비티에서 띄운 프래그먼트에서 뒤로가기를 누르게 되면 프래그먼트에서 구현한 onBackPressed 함수가 실행되게 된다.
        val fragmentList = supportFragmentManager.fragments
        for (fragment in fragmentList) {
            if (fragment is onBackPressedListener) {
                (fragment as onBackPressedListener).onBackPressed()
                return
            }
        }

//        Toast.makeText(applicationContext, "뒤로가기 눌러짐", Toast.LENGTH_SHORT).show()

        //현재 프래그먼트의 아이디를 가져옴 근데 머라머라 길게 나옴
        //FragmentGraph{ad92255} (fd9338a8-56a1-4298-93f6-fd00ca33e7b9 id=0x7f0a0115) 이렇게...
        val fragment = supportFragmentManager.findFragmentById(R.id.main_frm)

        var splitedFragment = fragment.toString().split("{")

        if (finishCount == true) {
            finish()
        }

        when (splitedFragment[0]) {
            "FragmentHome" -> {
                Toast.makeText(this, "한번 더 버튼을 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
                finishCount = true

                CoroutineScope(Dispatchers.Main).launch {
                    delay(2000).run {
                        finishCount = false
                    }
                }
            }

            "FragmentStory", "FragmentGraph", "FragmentMyPage" -> {
                //홈이 선택된 상태로 만듦
                //2131362030 이게 홈 프래그먼트의 ID임
                callHome()
            }


//            "FragmentCalendar" -> {}

        }

    }

}