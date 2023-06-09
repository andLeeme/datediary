package com.project.datediary.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.project.datediary.databinding.ActivitySignUpBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)


        //내 이메일 확인
        val curUser = GoogleSignIn.getLastSignedInAccount(this)

        binding.scheduleAlert.text = "내 이메일 확인하기!"

        binding.scheduleAlert.setOnClickListener {
            binding.scheduleAlert.text = "\"나의 이메일은 ${curUser?.email} 이예요!\""
        }


        //커플이메일 등록
        binding.submitBtn.setOnClickListener {

            var coupleEmail = binding.emailEdittext2.text.toString()

            var email = curUser?.email.toString()

            var emailMap = HashMap<String, String>()

            emailMap["coupleEmail"] = coupleEmail

            emailMap["email"] = email


            RetrofitAPI.emgMedService8.addUserByEnqueue2(emailMap)
                .enqueue(object : retrofit2.Callback<Int> {
                    override fun onResponse(
                        call: Call<Int>,
                        response: Response<Int>

                    ) {
                        Log.d("ChkUserData", "Call Success")

                        if (response.isSuccessful) {

                            //이미 등록된 이메일
                            if (response.body() == -1) {
                                Toast.makeText(
                                    applicationContext,
                                    "이미 등록된 이메일 입니다",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            } else if(response.body() == 0) {
                                val intent =
                                    Intent(applicationContext, MatchingActivity::class.java)
                                startActivity(intent)
                                finish()
                            }else if(response.body() == 99) {
                                Toast.makeText(
                                    applicationContext,
                                    "서버 오류!",
                                    Toast.LENGTH_SHORT
                                )
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
            finish()
        }
    }

}