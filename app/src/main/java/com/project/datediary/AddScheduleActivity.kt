package com.project.datediary

import RetrofitAPI
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.project.datediary.databinding.ActivityAddScheduleBinding
import com.project.datediary.model.SignUpRequestBody
import retrofit2.Call
import retrofit2.Response

class AddScheduleActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.postbutton.setOnClickListener {
            val userData = SignUpRequestBody(
                id = binding.editText1.text.toString(), password = binding.editText2.text.toString()
            )

            RetrofitAPI.emgMedService.addUserByEnqueue(userData)
                .enqueue(object : retrofit2.Callback<ArrayList<HashMap<String, Object>>> {
                    override fun onResponse(
                        call: Call<ArrayList<HashMap<String, Object>>>,
                        response: Response<ArrayList<HashMap<String, Object>>>
                    ) {
                        Toast.makeText(applicationContext, "Call Success", Toast.LENGTH_SHORT)
                            .show()

                        if (response.isSuccessful) {

                            binding.text1234.text = "${response.body().toString()}"
                        }
                    }

                    override fun onFailure(
                        call: Call<ArrayList<HashMap<String, Object>>>,
                        t: Throwable
                    ) {

                    }
                })
        }
    }
}

