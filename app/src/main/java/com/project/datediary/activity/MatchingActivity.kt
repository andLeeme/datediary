package com.project.datediary.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.project.datediary.databinding.ActivityMatchingBinding

class MatchingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMatchingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMatchingBinding.inflate(layoutInflater)


        var a = 1
        binding.submitBtn.setOnClickListener {
            val curUser = GoogleSignIn.getLastSignedInAccount(this)
            curUser?.let {
                if (a == 2) {
                    Toast.makeText(
                        this,
                        "${curUser.displayName.toString()}님 반가워요",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    a = 2
                    Toast.makeText(
                        this,
                        "연결되지않음",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


        }
        setContentView(binding.root)
    }
}