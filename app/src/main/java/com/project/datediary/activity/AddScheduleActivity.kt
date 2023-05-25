package com.project.datediary.activity

import RetrofitAPI
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.util.Log
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.project.datediary.R
import com.project.datediary.databinding.ActivityAddScheduleBinding
import com.project.datediary.model.ScheduleRequestBody
import com.project.datediary.model.ScheduleResponseBody
import retrofit2.Call
import retrofit2.Response
import java.util.Calendar


class AddScheduleActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddScheduleBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_schedule)

        binding = ActivityAddScheduleBinding.inflate(layoutInflater)

        var titleText = binding.emailEdittext1
        var contentText = binding.emailEdittext2
        var ADChkBox = binding.allDayCheckBox


/////////////////////////////////기본 기능 설정///////////////////////////////////////

        //1. Date&Time Picker
        //변수 초기화
        var startDate = ""
        var startTime = ""
        var endDate = ""
        var endTime = ""


        //datepicker 및 timepicker에서 선택한 날짜/시간 등을 버튼 텍스트에 셋팅
        binding.datepickerStart.setOnClickListener {
            val cal = Calendar.getInstance()    //캘린더뷰 만들기
            val dateSetListener =
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    startDate = "${year}년 ${month + 1}월 ${dayOfMonth}일"
                    binding.datepickerStart.text = startDate
                }
            DatePickerDialog(
                this,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


        binding.timepickerStart.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                if (hourOfDay > 12) {
                    startTime = "오후 ${hourOfDay - 12}:${minute}분"
                } else {
                    startTime = "오전 ${hourOfDay}:${minute}분"
                }
                binding.timepickerStart.text = startTime
                Log.d("hourOfDay", "onCreate: $hourOfDay")
            }
            TimePickerDialog(
                this,
                AlertDialog.THEME_HOLO_LIGHT,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                false
            ).show()
        }

        binding.datepickerEnd.setOnClickListener {
            val cal = Calendar.getInstance()    //캘린더뷰 만들기
            val dateSetListener =
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    startDate = "${year}년 ${month + 1}월 ${dayOfMonth}일"
                    binding.datepickerEnd.text = startDate
                }
            DatePickerDialog(
                this,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.timepickerEnd.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                if (hourOfDay > 12) {
                    startTime = "오후 ${hourOfDay - 12}:${minute}분"
                } else {
                    startTime = "오전 ${hourOfDay}:${minute}분"
                }
                binding.timepickerEnd.text = startTime
                Log.d("hourOfDay", "onCreate: $hourOfDay")
            }
            TimePickerDialog(
                this,
                AlertDialog.THEME_HOLO_LIGHT,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                false
            ).show()
        }

        //2. 스피너?리스트뷰?(방문장소)

        //3. 스피너?리스트뷰?(데이트미션)

        //4. 일정 등록하기 누르면 정보 주기


        binding.submitBtn.setOnClickListener {
            val scheduleData = ScheduleRequestBody(couple_index = "1")

            RetrofitAPI.emgMedService2.addUserByEnqueue2(scheduleData)
                .enqueue(object : retrofit2.Callback<java.util.ArrayList<ScheduleResponseBody>> {
                    override fun onResponse(
                        call: Call<ArrayList<ScheduleResponseBody>>,
                        response: Response<ArrayList<ScheduleResponseBody>>
                    ) {
                        Toast.makeText(applicationContext, "Call Success", Toast.LENGTH_SHORT)
                            .show()

                        if (response.isSuccessful) {

                            Log.d("리턴123", "${response.body().toString()}")
                        }
                    }

                    override fun onFailure(
                        call: Call<ArrayList<ScheduleResponseBody>>,
                        t: Throwable
                    ) {

                    }
                })
        }


/////////////////////////////////화면 전환///////////////////////////////////////
        //일정 등록하기로 들어오면 빈칸(초기화)
        //이미 있는 일정 꾹 눌러서 들어오면 내용 다 넣어주기(리퀘스트해서 넣어주기)





        setContentView(binding.root)

    }

    fun ClearView() {

    }

}