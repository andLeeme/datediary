package com.project.datediary.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.project.datediary.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var resultLauncher: ActivityResultLauncher<Intent>


    //액티비티가 실행되면 toast를 띄운다
    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        account?.let {
        } ?: Toast.makeText(this, "dateDiary", Toast.LENGTH_SHORT).show()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)


        // ActivityResultLauncher
        setResultSignUp()


        //구글로그인
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        with(binding) {
            btnSignIn.setOnClickListener {
                signIn()

            }

        }

        setContentView(binding.root)
    }

    private fun setResultSignUp() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                // 정상적으로 결과가 받아와진다면 조건문 실행
                if (result.resultCode == Activity.RESULT_OK) {
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    handleSignInResult(task)

                }
            }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val email = account?.email


            RetrofitAPI.emgMedService7.addUserByEnqueue2(email)
                .enqueue(object : retrofit2.Callback<Int> {
                    override fun onResponse(
                        call: Call<Int>,
                        response: Response<Int>

                    ) {
                        Log.d("ChkUserData", "Call Success")

                        if (response.isSuccessful) {
                            if (response.body() == 1) {
                                Toast.makeText(
                                    applicationContext,
                                    "$email\n신규회원입니다",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                val intent = Intent(applicationContext, SignUpActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else if (response.body() == 0) {

                                RetrofitAPI.emgMedService11.addUserByEnqueue2(email)
                                    .enqueue(object : retrofit2.Callback<HashMap<String, String>> {
                                        override fun onResponse(
                                            call: Call<HashMap<String, String>>,
                                            response: Response<HashMap<String, String>>

                                        ) {
                                            if (response.isSuccessful) {
                                                MainActivity.coupleIndex =
                                                    response.body().toString()
//                                                Toast.makeText(
//                                                    applicationContext,
//                                                    "${MainActivity.coupleIndex}",
//                                                    Toast.LENGTH_SHORT
//                                                ).show()


//
                                                val intent = Intent(
                                                    applicationContext,
                                                    MainActivity::class.java
                                                )
                                                startActivity(intent)
//                                                Toast.makeText(
//                                                    applicationContext,
//                                                    "$email\n Login",
//                                                    Toast.LENGTH_SHORT
//                                                ).show()

                                                finish()
                                            }
                                        }

                                        override fun onFailure(
                                            call: Call<HashMap<String, String>>,
                                            t: Throwable
                                        ) {
//                                            Toast.makeText(
//                                                applicationContext,
//                                                "Call Failed",
//                                                Toast.LENGTH_SHORT
//                                            )
//                                                .show()
                                        }
                                    })
                            } else if (response.body() == 2) {
                                val intent = Intent(applicationContext, SignUpActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(
                                    applicationContext,
                                    "$email\n커플 미연동 회원입니다",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            } else if (response.body() == 99) {
                                Toast.makeText(applicationContext, "서버 오류!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<Int>, t: Throwable) {
//                        Toast.makeText(applicationContext, "Call Failed", Toast.LENGTH_SHORT)
//                            .show()
                    }
                })


        } catch (e: ApiException) {

            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("failed", "signInResult:failed code=" + e.statusCode)
        }
    }


    //로그인 실행
    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        resultLauncher.launch(signInIntent)

    }


    //종료 카운트
    var finishCount = false

    override fun onBackPressed() {

        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(applicationContext, "한번 더 버튼을 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            finishCount = true
            delay(2000).run {
                finishCount = false
            }
        }

        if (finishCount) {
            finish()
        }
    }


}