package com.project.datediary.activity

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.project.datediary.databinding.ActivityMatchingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.util.Calendar

class MatchingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMatchingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMatchingBinding.inflate(layoutInflater)


        //캘린더뷰 만들기
        binding.dateBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener =
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    var startDate = "${year}년 ${month + 1}월 ${dayOfMonth}일"
                    MainActivity.cYear = year.toString()
                    MainActivity.cMonth = (month + 1).toString()
                    MainActivity.cDay = dayOfMonth.toString()
                    binding.dateBtn.text = startDate
                }
            DatePickerDialog(
                this,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


        //입력받은 데이터를 서버에 저장
        binding.submitBtn.setOnClickListener {

            val curUser = GoogleSignIn.getLastSignedInAccount(this)
            val email = curUser?.email.toString()

            val name = binding.nameEdittext1.text.toString()

            val data = HashMap<String, String>()

            data["email"] = email

            data["name"] = name

            data["Year"] = MainActivity.cYear.toString()

            data["Month"] = MainActivity.cMonth.toString()

            data["day"] = MainActivity.cDay.toString()


            RetrofitAPI.emgMedService9.addUserByEnqueue2(data)
                .enqueue(object : retrofit2.Callback<Int> {
                    override fun onResponse(
                        call: Call<Int>,
                        response: Response<Int>
                    ) {
                        if (response.isSuccessful) {
                            if (response.body() == 0) {
                                Toast.makeText(
                                    applicationContext,
                                    "${curUser?.displayName}님 안녕하세요  ",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(applicationContext, MainActivity::class.java)
                                startActivity(intent)

                                RetrofitAPI.emgMedService11.addUserByEnqueue2(email)
                                    .enqueue(object : retrofit2.Callback<HashMap<String, String>> {
                                        override fun onResponse(
                                            call: Call<HashMap<String, String>>,
                                            response: Response<HashMap<String, String>>

                                        ) {
                                            Log.d("coupleIndex", "Call Success")

                                            if (response.isSuccessful) {
//                                                MainActivity.coupleIndex = response.body()?.get("nickname").toString()

                                                MainActivity.coupleIndex =
                                                    response.body()?.get("couple_index").toString()

//                                                Toast.makeText(
//                                                    applicationContext,
//                                                    "coupleIndex : ${MainActivity.coupleIndex}",
//                                                    Toast.LENGTH_SHORT
//                                                )
//                                                    .show()
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

                                finish()

                            } else if (response.body() == 99) {
                                Toast.makeText(applicationContext, "상대방과 연결되지 않았어요", Toast.LENGTH_SHORT)
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


        setContentView(binding.root)
    }

    var finishCount = false

    override fun onBackPressed() {


        CoroutineScope(Dispatchers.Main).launch {
            finishCount = true
            Toast.makeText(applicationContext, "한번 더 버튼을 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            delay(2000).run {
                finishCount = false
            }
        }

        if (finishCount) {
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}