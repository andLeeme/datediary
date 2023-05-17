package com.project.datediary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.project.datediary.databinding.ActivityAddScheduleBinding
import com.project.datediary.model.HTTP_GET_Model
import com.project.datediary.model.PostModel
import com.project.datediary.model.PostResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddScheduleActivity : AppCompatActivity() {
    val binding by lazy { ActivityAddScheduleBinding.inflate(layoutInflater) }
    val api = APIS.create();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.getbutton.setOnClickListener {
            api.get_users().enqueue(object : Callback<HTTP_GET_Model> {
                override fun onResponse(
                    call: Call<HTTP_GET_Model>,
                    response: Response<HTTP_GET_Model>
                ) {
                    Log.d("log", response.toString())
                    Log.d("log", response.body().toString())
                    if (!response.body().toString().isEmpty())
                        binding.text.setText(response.body().toString());
                }

                override fun onFailure(call: Call<HTTP_GET_Model>, t: Throwable) {
                    // 실패
                    Log.d("log", t.message.toString())
                    Log.d("log", "fail")
                }
            })
        }

        binding.postbutton.setOnClickListener {
            val data = PostModel(
                binding.idedt.text.toString(),
                binding.pwdedt.text.toString(),
                binding.nickedt.text.toString()
            )
            api.post_users(data).enqueue(object : Callback<PostResult> {
                override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
                    Log.d("log", response.toString())
                    Log.d("log", response.body().toString())
                    if (!response.body().toString().isEmpty())
                        binding.text.setText(response.body().toString());
                }

                override fun onFailure(call: Call<PostResult>, t: Throwable) {
                    // 실패
                    Log.d("log", t.message.toString())
                    Log.d("log", "fail")
                }
            })
        }

    }
}