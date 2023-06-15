package com.project.datediary.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.project.datediary.activity.LoginActivity
import com.project.datediary.activity.MainActivity
import com.project.datediary.databinding.FragmentPageBinding
import com.project.datediary.model.NicknameChangeRequestBody
import retrofit2.Call
import retrofit2.Response


class FragmentPage : Fragment() {

    lateinit var binding: FragmentPageBinding

    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPageBinding.inflate(inflater, container, false)



        //스테이터스 바 색상 변경
        val activity = requireActivity()
        val window = activity.window
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR


        //MainActivity static field
        binding.eamil.text = MainActivity.googleEmail

        binding.name.text = MainActivity.googleName

        binding.nickname.setText( MainActivity.nickname1)




        //닉네임 바꿔주기
        var editText = binding.nickname
        var editButton = binding.nameEdit

        editText.isClickable = false
        editText.isFocusable = false

        editButton.setOnClickListener{

            //수정한 거 집어넣을 때
            if(editText.hasFocus()) {
                editText.isClickable = false
                editText.isFocusable = false
                MainActivity.nickname1 = editText.text.toString()

                //서버로 보내서 바꿔주기
                val userData = NicknameChangeRequestBody(
                    user_email = MainActivity.googleEmail,
                    edited_nickname = editText.text.toString(),
                )
                Log.d("nicknameChangeData", "onCreate: $userData")

                RetrofitAPI.emgMedService15.addUserByEnqueue2(userData)
                    .enqueue(object : retrofit2.Callback<Int> {
                        override fun onResponse(
                            call: Call<Int>,
                            response: Response<Int>
                        ) {
                            if (response.isSuccessful) {
                                Toast.makeText(context, "닉네임이 변경되었습니다", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                        override fun onFailure(
                            call: Call<Int>,
                            t: Throwable
                        ) {
                        }
                    })


            } else {
                //수정할 때
                editText.isClickable = true
                editText.isFocusable = true
                editText.isFocusableInTouchMode = true
                MainActivity.nickname1 = editText.text.toString()
                Log.d("nickname123", "onCreateView: ${MainActivity.nickname1}")




            }
        }




        //구글 로그인 정보
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);


        //로그아웃
        with(binding) {

            btnSignOut.setOnClickListener {
                signOut()
            }
        }



        return binding.root


    }

    private fun signOut() {
        val curUser = GoogleSignIn.getLastSignedInAccount(requireContext())
        curUser?.let {
            mGoogleSignInClient.signOut()
                .addOnCompleteListener(requireActivity()) {


                    val intent = Intent(requireActivity(), LoginActivity::class.java)
                    startActivity(intent)



                    (activity as MainActivity?)?.finish()

                }
        }
    }
}




