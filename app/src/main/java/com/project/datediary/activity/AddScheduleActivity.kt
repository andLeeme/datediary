package com.project.datediary.activity

import RetrofitAPI
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
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

        //일정 등록하기로 들어오면 빈칸(초기화)
        //이미 있는 일정 꾹 눌러서 들어오면 내용 다 넣어주기(리퀘스트해서 넣어주기)


        //일정 등록하기 누르면 정보 주기(지현이가 로그인한 정보 주는 거 확인해보기)
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

        //DatePickerDialog(context: Context, listener: DatePickerDialog.onDateSetListener?, year: Int, month: Int, dayOfMonth: Int)

//        binding.datepickerStart.setOnClickListener {
//
//            DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener{
//                override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
//                    Log.d("TAG", "year : $p1, month : ${p2 + 1}, dayOfMonth : $p3")
//                }
//            }, 2020, 8, 21).show()
//            binding.datepickerStart.text = "${p1}년 "
//        }

        //변수 초기화
        var startDate = ""
        var startTime = ""
        var endDate = ""
        var endTime = ""

        //datepicker 및 timepicker에서 선택한 날짜/시간 등을 버튼 텍스트에 셋팅
        binding.datepickerStart.setOnClickListener {
            val cal = Calendar.getInstance()    //캘린더뷰 만들기
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                startDate = "${year}년 ${month+1}월 ${dayOfMonth}일"
                binding.datepickerStart.text = startDate
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.timepickerStart.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                startTime = "${hourOfDay}시 ${minute}분"
                binding.timepickerStart.text = startTime
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),true).show()
        }

        binding.datepickerEnd.setOnClickListener {
            val cal = Calendar.getInstance()    //캘린더뷰 만들기
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                startDate = "${year}년 ${month+1}월 ${dayOfMonth}일"
                binding.datepickerEnd.text = startDate
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.timepickerEnd.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                startTime = "${hourOfDay}시 ${minute}분"
                binding.timepickerEnd.text = startTime
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),true).show()
        }







        setContentView(binding.root)

    }

    fun ClearView() {

    }

}