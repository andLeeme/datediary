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
import retrofit2.Call
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        account?.let {
            Toast.makeText(this, "로그인 되어있음", Toast.LENGTH_SHORT).show()
        } ?: Toast.makeText(this, "로그인 안되어있음", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        // ActivityResultLauncher
        setResultSignUp()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        with(binding) {
            btnSignIn.setOnClickListener {
                signIn()
            }

            btnSignOut.setOnClickListener {
                signOut()
            }
            btnGetProfile.setOnClickListener {
                GetCurrentUserProfile()
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
            val email = account?.email.toString()
            val displayName = account?.displayName.toString()

            RetrofitAPI.emgMedService7.addUserByEnqueue2(email)
                .enqueue(object : retrofit2.Callback<Int> {
                    override fun onResponse(
                        call: Call<Int>,
                        response: Response<Int>
                    ) {
                        Toast.makeText(applicationContext, "Call Success", Toast.LENGTH_SHORT)
                            .show()

                        if (response.isSuccessful) {
                            if (email == "rarara773@gmail.com") {
                                val intent = Intent(applicationContext, SignUpActivity::class.java)
                                startActivity(intent)
                            } else {
                                val intent = Intent(applicationContext, MainActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(
                                    applicationContext,
                                    "${account.displayName.toString()}님 반가워요",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<Int>, t: Throwable) {
                        Toast.makeText(applicationContext, "Call Failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                })


        } catch (e: ApiException) {

            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("failed", "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        resultLauncher.launch(signInIntent)

    }

    private fun signOut() {
        val curUser = GoogleSignIn.getLastSignedInAccount(this)
        curUser?.let {
            mGoogleSignInClient.signOut()
                .addOnCompleteListener(this) {
                    Toast.makeText(this, "로그아웃 완료", Toast.LENGTH_SHORT).show()
                    finish()
                }
        }
        if (curUser == null) {
            Toast.makeText(this, "로그인 안되어있음", Toast.LENGTH_SHORT).show()
        }

    }

    private fun revokeAccess() {
        mGoogleSignInClient.revokeAccess()
            .addOnCompleteListener(this) {
                // ...
            }
    }

    private fun GetCurrentUserProfile() {
        val curUser = GoogleSignIn.getLastSignedInAccount(this)
        curUser?.let {
            val email = curUser.email.toString()
            val familyName = curUser.familyName.toString()
            val givenName = curUser.givenName.toString()
            val displayName = curUser.displayName.toString()
            val photoUrl = curUser.photoUrl.toString()

            Log.d("현재 로그인 되어있는 유저의 이메일", email)
            Log.d("현재 로그인 되어있는 유저의 성", familyName)
            Log.d("현재 로그인 되어있는 유저의 이름", givenName)
            Log.d("현재 로그인 되어있는 유저의 전체이름", displayName)
            Log.d("현재 로그인 되어있는 유저의 프로필 사진의 주소", photoUrl)
            Toast.makeText(this, "이메일: $email 이름 : $displayName ", Toast.LENGTH_SHORT).show()

        }
        if (curUser == null) {
            Toast.makeText(this, "로그인 안되어있음", Toast.LENGTH_SHORT).show()
        }

    }
}