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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.project.datediary.R
import com.project.datediary.fragment.FragmentCalendar
import com.project.datediary.fragment.FragmentGraph
import com.project.datediary.fragment.FragmentHome
import com.project.datediary.databinding.ActivityMainBinding
import com.project.datediary.databinding.FragmentCalendarBinding
import com.project.datediary.fragment.FragmentPage
import com.project.datediary.fragment.FragmentStory
import com.project.datediary.util.CalendarUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var binding2: FragmentCalendarBinding

    companion object {
        var coupleIndex: String = "default";
        var nickname1: String = "김인호"
        var nickname2: String = "이수영"
        var d_day: String = "default"
        var googleEmail: String = ""
        var googleName: String = ""
        var cYear = ""
        var cMonth = ""
        var cDay = ""
    }


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

        val curUser = GoogleSignIn.getLastSignedInAccount(this)

        val email = curUser?.email.toString()

        googleEmail = email

        googleName = curUser?.displayName.toString()


        binding.mainFrm.visibility = View.INVISIBLE
        binding.mainBnv.visibility = View.INVISIBLE

        if (curUser == null) {
            CoroutineScope(Dispatchers.Main).launch {

                delay(650).run {
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        } else if (curUser != null) {
            RetrofitAPI.emgMedService7.addUserByEnqueue2(email)
                .enqueue(object : retrofit2.Callback<Int> {
                    override fun onResponse(
                        call: Call<Int>,
                        response: Response<Int>

                    ) {
                        Log.d("ChkUserData", "Call Success")

                        if (response.isSuccessful) {
                            if (response.body() == 1) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    delay(650).run {
                                        val intent = Intent(
                                            applicationContext,
                                            SignUpActivity::class.java
                                        )
                                        startActivity(intent)
                                        finish()
                                    }
                                }
                                //완전 성공
                            } else if (response.body() == 0) {

                                RetrofitAPI.emgMedService11.addUserByEnqueue2(email)
                                    .enqueue(object :
                                        retrofit2.Callback<HashMap<String, String>> {
                                        override fun onResponse(
                                            call: Call<HashMap<String, String>>,
                                            response: Response<HashMap<String, String>>

                                        ) {
                                            Log.d("coupleIndex", "Call Success")

                                            if (response.isSuccessful) {

                                                coupleIndex =
                                                    response.body()?.get("couple_index")
                                                        .toString()


                                                nickname1 =
                                                    response.body()?.get("nickname1").toString()

                                                nickname2 =
                                                    response.body()?.get("nickname2").toString()

                                                cYear =
                                                    response.body()?.get("year").toString()

                                                cMonth =
                                                    response.body()?.get("month").toString()

                                                cDay =
                                                    response.body()?.get("day").toString()

                                                //디데이 카운트
                                                val today = LocalDate.now()
                                                val targetDate = LocalDate.of(cYear.toInt(), cMonth.toInt(), cDay.toInt())  // 대상 날짜 설정 동적으로 변경예정

                                                val daysUntilTarget = ChronoUnit.DAYS.between(today, targetDate)

                                                if (daysUntilTarget > 0) {
                                                    Log.d("d-day", "$daysUntilTarget")

                                                    d_day = daysUntilTarget.toString()

                                                } else if (daysUntilTarget == 0L) {

                                                } else {
                                                    Log.d("d-day", "${abs(daysUntilTarget)}")

                                                    d_day = abs(daysUntilTarget - 1).toString()
                                                }



                                                //홈 프래그먼트 호출
                                                supportFragmentManager.beginTransaction()
                                                    .replace(R.id.main_frm, FragmentHome())
                                                    .commitAllowingStateLoss()
                                            }
                                        }

                                        override fun onFailure(
                                            call: Call<HashMap<String, String>>,
                                            t: Throwable
                                        ) {
                                            Toast.makeText(
                                                applicationContext,
                                                "Call Failed",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                        }
                                    })

                                binding.mainFrm.visibility = View.VISIBLE
                                binding.mainBnv.visibility = View.VISIBLE
                                Toast.makeText(
                                    applicationContext,
                                    "${curUser.displayName} Login",
                                    Toast.LENGTH_SHORT
                                ).show()


                            } else if (response.body() == 2) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    delay(650).run {
                                        val intent = Intent(
                                            applicationContext,
                                            SignUpActivity::class.java
                                        )
                                        startActivity(intent)
                                        Toast.makeText(
                                            applicationContext,
                                            "$email\n커플 미연동 회원입니다",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        finish()
                                    }
                                }

                            } else if (response.body() == 99) {
                                Toast.makeText(applicationContext, "서버 오류!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<Int>, t: Throwable) {
                        Toast.makeText(applicationContext, "Call Failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
        }


        var loading = false
        //스플래쉬화면을 더 오래 실행하는법.
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {

                    CoroutineScope(Dispatchers.Main).launch {
                        delay(2000).run {
                            loading = true
                        }
                    }


                    //조건이 true일때 화면 전환
                    return if (loading) {
                        // The content is ready; start drawing.
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        false
                    }
                }
            }
        )

        supportFragmentManager.beginTransaction().replace(R.id.main_frm, FragmentHome())
            .commitAllowingStateLoss()

        setContentView(binding.root)


        initBottomNavigation()

    }

    private fun initBottomNavigation() {

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

                R.id.memory -> {

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, FragmentGraph()).commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.calendar -> {

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, FragmentCalendar()).commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.notice -> {

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, FragmentStory()).commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.myPage -> {

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, FragmentPage()).commitAllowingStateLoss()
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

    fun callFinish() {
        finish()
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

        if (finishCount) {
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

            "FragmentStory", "FragmentGraph", "FragmentPage" -> {
                //홈이 선택된 상태로 만듦
                //2131362030 이게 홈 프래그먼트의 ID임
                callHome()
            }

//            "FragmentCalendar" -> {}

        }

    }

}