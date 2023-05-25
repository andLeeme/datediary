package com.project.datediary.activity

import RetrofitAPI
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.project.datediary.databinding.ActivityAddSchedule2Binding
import com.project.datediary.model.SignUpRequestBody
import retrofit2.Call
import retrofit2.Response

class AddScheduleActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityAddSchedule2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSchedule2Binding.inflate(layoutInflater)
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

