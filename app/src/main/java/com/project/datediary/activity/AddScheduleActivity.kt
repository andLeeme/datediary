package com.project.datediary.activity

import RetrofitAPI
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.project.datediary.R
import com.project.datediary.databinding.ActivityAddScheduleBinding
import com.project.datediary.model.Constants
import com.project.datediary.model.Place
import com.project.datediary.model.ScheduleRequestBody
import com.project.datediary.model.ScheduleResponseBody
import com.project.datediary.util.DialogList
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

        //0. 기본 시간(현재 시간 넣어주기)
        binding.datepickerStart.text = ""
        binding.scheduleAlert.text = ""
        binding.datepickerStart.text = ""
        binding.scheduleAlert.text = ""


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
                    binding.scheduleAlert.text = "${month + 1}월 ${dayOfMonth}일 일정에 추가돼요!"
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

        binding.selectPlace.setOnClickListener {
            showDialog()
            clearMissionDialog()
            //placeListDialog()
        }
        //3. 스피너?리스트뷰?(데이트미션)
        binding.selectMission.setOnClickListener {
            showMissionDialog()
        }


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

    private fun placeListDialog() {
        // Get the employee data from the Constants class
        val placeList: ArrayList<Place> = Constants.getPlaceData()
        // Create a new instance of the DialogList
        // dialog, passing in the activity
        // and employee data as parameters
        val listDialog = object : DialogList(this@AddScheduleActivity, placeList) {}
        // Show the dialog
        listDialog.show()
    }

    //다이얼로그 호출
    private fun showDialog(){

        //데이터 담기
        val places: Array<String> = resources.getStringArray(R.array.places)

        //AlertDialog 초기화
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        //제목 설정
        builder.setTitle("데이트 장소를 선택해주세요")

        //아이템 선택 이벤트
        builder.setItems(places){
                p0, p1 ->
            binding.selectPlace.text = places[p1]
//            Toast.makeText(this, "선택된 색깔은 ${places[p1]}",
//                Toast.LENGTH_SHORT).show()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun clearMissionDialog(){
        binding.selectMission.text = "데이트 미션!"
    }
    private fun showMissionDialog(){
        //데이터 담기
        var missions: Array<String> = resources.getStringArray(R.array.방문장소)
        when(binding.selectPlace.text){
            "방문 장소" -> missions = resources.getStringArray(R.array.방문장소)
            "영화관" -> missions = resources.getStringArray(R.array.영화관)
            "바/주점" -> missions = resources.getStringArray(R.array.바주점)
            "여행" -> missions = resources.getStringArray(R.array.여행)
            "식당" -> missions = resources.getStringArray(R.array.식당)
            "보드게임" -> missions = resources.getStringArray(R.array.보드게임)
            "도서관" -> missions = resources.getStringArray(R.array.도서관)
            "전시관" -> missions = resources.getStringArray(R.array.전시관)
            "놀이공원" -> missions = resources.getStringArray(R.array.놀이공원)
            "카페" -> missions = resources.getStringArray(R.array.카페)
            "관람" -> missions = resources.getStringArray(R.array.관람)
            "스포츠" -> missions = resources.getStringArray(R.array.스포츠)
            "당구장" -> missions = resources.getStringArray(R.array.당구장)
            "익스트랙션" -> missions = resources.getStringArray(R.array.익스트랙션)
            "공방" -> missions = resources.getStringArray(R.array.공방)
            "드라이브" -> missions = resources.getStringArray(R.array.드라이브)
            "식물원" -> missions = resources.getStringArray(R.array.식물원)
            "기타" -> missions = resources.getStringArray(R.array.기타)
        }


        //AlertDialog 초기화
        val builder2: AlertDialog.Builder = AlertDialog.Builder(this)

        //제목 설정
        builder2.setTitle("미션을 선택해주세요")

        //아이템 선택 이벤트
        builder2.setItems(missions){
                p0, p1 ->
            binding.selectMission.text = missions[p1]
//            Toast.makeText(this, "선택된 색깔은 ${missions[p1]}",
//                Toast.LENGTH_SHORT).show()
        }

        val alertDialog2: AlertDialog = builder2.create()
        alertDialog2.show()
    }

}