package com.project.datediary.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.project.datediary.activity.MainActivity
import com.project.datediary.databinding.FragmentLoginTestBinding


class FragmentLoginTest : Fragment() {
    private lateinit var binding: FragmentLoginTestBinding
    private val googleSignInClient: GoogleSignInClient by lazy { getGoogleClient() }
    private val googleAuthLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

            try {
                val account = task.getResult(ApiException::class.java)
                Toast.makeText(context, "혹시..?", Toast.LENGTH_SHORT).show()
                // 이름, 이메일 등이 필요하다면 아래와 같이 account를 통해 각 메소드를 불러올 수 있다.
                val userName = account.givenName
                val serverAuth = account.serverAuthCode

                Toast.makeText(context, "$userName", Toast.LENGTH_SHORT).show()

                moveSignUpActivity()

            } catch (e: ApiException) {
                Log.e(FragmentLoginTest::class.java.simpleName, e.stackTraceToString())
                Toast.makeText(context, "안된거같아요", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginTestBinding.inflate(inflater, container, false)

        addListener()
        return binding.root
    }

    private fun addListener() {
        binding.googleLoginBtn.setOnClickListener {
            requestGoogleLogin()
        }
    }

    private fun requestGoogleLogin() {
        googleSignInClient.signOut()
        val signInIntent = googleSignInClient.signInIntent
        googleAuthLauncher.launch(signInIntent)
    }

    private fun getGoogleClient(): GoogleSignInClient {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestScopes(Scope("https://www.googleapis.com/auth/pubsub"))
            .requestServerAuthCode("973907992931-32u4m6qq52gm4qgqahnko61kleskqe7h.apps.googleusercontent.com") // string 파일에 저장해둔 client id 를 이용해 server authcode를 요청한다.
            .requestEmail() // 이메일도 요청할 수 있다.
            .build()

        return GoogleSignIn.getClient(requireActivity(), googleSignInOption)
    }

    private fun moveSignUpActivity() {
        requireActivity().run {
            startActivity(Intent(requireContext(), MainActivity::class.java))
            finish()
        }
    }
}