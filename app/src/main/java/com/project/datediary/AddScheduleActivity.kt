package com.project.datediary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.project.datediary.api.ApiObject
import com.project.datediary.databinding.ActivityAddScheduleBinding
import com.project.datediary.model.addScheduleRequest
import com.project.datediary.model.addScheduleResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddScheduleActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addBtn()
    }

    private fun addBtn() {
        binding.AddScheduleButton.setOnClickListener {
            val edittext1 = binding.edittext1.text.toString()
            val edittext2 = binding.edittext2.text.toString()
            val edittext3 = binding.edittext3.text.toString()
            val edittext4 = binding.edittext4.text.toString()
            val edittext5 = binding.edittext5.text.toString()

            val addScheduleRequest1 = addScheduleRequest(
                edittext1 = edittext1,
                edittext2 = edittext2,
                edittext3 = edittext3,
                edittext4 = edittext4,
                edittext5 = edittext5,
            )

            ApiObject.AddScheduleAPI.addScheduleRequest(addScheduleRequest1)
                .enqueue(object : Callback<addScheduleResponse> {
                    override fun onResponse(
                        call: Call<addScheduleResponse>,
                        response: Response<addScheduleResponse>
                    ) {
                        if (response.isSuccessful) {
                            Log.d("test", response.body().toString())
                            var data = response.body() // GsonConverter를 사용해 데이터매핑
                        }
                    }

                    override fun onFailure(call: Call<addScheduleResponse>, t: Throwable) {
                        Log.d("test", "실패$t")
                    }

                })
        }
    }
}