package com.project.datediary.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.project.datediary.activity.LoginActivity
import com.project.datediary.activity.MainActivity
import com.project.datediary.databinding.FragmentPageBinding


class FragmentPage : Fragment() {

    lateinit var binding: FragmentPageBinding

    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPageBinding.inflate(inflater, container, false)

        val activity = requireActivity()
        val window = activity.window
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR


        binding.eamil.text = MainActivity.googleEmail

        binding.name.text = MainActivity.googleName

        binding.nickname.text = MainActivity.nickname2


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()


        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

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




