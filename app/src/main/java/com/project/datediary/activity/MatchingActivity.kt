package com.project.datediary.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.project.datediary.databinding.ActivityMatchingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MatchingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMatchingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMatchingBinding.inflate(layoutInflater)


        binding.submitBtn.setOnClickListener {

            val curUser = GoogleSignIn.getLastSignedInAccount(this)
            val email = curUser?.email.toString()


            RetrofitAPI.emgMedService9.addUserByEnqueue2(email)
                .enqueue(object : retrofit2.Callback<Int> {
                    override fun onResponse(
                        call: Call<Int>,
                        response: Response<Int>) {
                        if (response.isSuccessful) {
                            if (response.body() == 0) {
                                Toast.makeText(applicationContext, "커플인덱스 발급완료", Toast.LENGTH_SHORT).show()
                                val intent = Intent(applicationContext, SignUpActivity::class.java)
                                startActivity(intent)
                                finish()

                            } else if (response.body() == 99) {
                                Toast.makeText(applicationContext, "서버 오류!", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(applicationContext, "한번 더 버튼을 누르면 이메일 입력화면으로 돌아갑니다.", Toast.LENGTH_SHORT).show()
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