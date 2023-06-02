package com.project.datediary.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.datediary.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)

        binding.submitBtn.setOnClickListener {
            val intent = Intent(applicationContext, MatchingActivity::class.java)
            startActivity(intent)
            finish()
        }
        setContentView(binding.root)
    }

}