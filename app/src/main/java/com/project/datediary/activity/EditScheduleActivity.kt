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
import com.project.datediary.databinding.ActivityEditScheduleBinding
import com.project.datediary.model.ScheduleEditRequestBody
import com.project.datediary.model.ScheduleRequestBody
import com.project.datediary.util.CalendarUtil
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


class EditScheduleActivity : AppCompatActivity() {

    lateinit var binding: ActivityEditScheduleBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_schedule)

        binding = ActivityEditScheduleBinding.inflate(layoutInflater)

        //intent로 받아온 정보 변수에 저장
        var a_scheduleIndex = intent.getStringExtra("scheduleIndex")
        var a_startYear = intent.getStringExtra("startYear")
        var a_startMonth = intent.getStringExtra("startMonth")
        var a_startDay = intent.getStringExtra("startDay")
        var a_startTime = intent.getStringExtra("startTime")
        var a_endYear = intent.getStringExtra("endYear")
        var a_endMonth = intent.getStringExtra("endMonth")
        var a_endDay = intent.getStringExtra("endDay")
        var a_endTime = intent.getStringExtra("endTime")
        var a_title = intent.getStringExtra("title")
        var a_contents = intent.getStringExtra("contents")
        var ADChkBox = intent.getStringExtra("allDayCheck")
        var placeCode = intent.getStringExtra("placeCode")
        var missionCode = intent.getStringExtra("missionCode")


        var alertDate = "${a_startMonth}월 ${a_startDay}일"




////////////////////////////////초기화 드가자~///////////////////////////////////

        //a_startTime이 16:33이런 식으로 찍혀서 가공해줘야됨
        var startAorP = "오전"
        var endAorP = "오전"
        var a_startHour = 0
        var a_endHour = 0

        if (a_startTime != "" && a_endTime != "") {
            var startHourOfDay = a_startTime.toString().split(":")
            var endHourOfDay = a_endTime.toString().split(":")

            if (startHourOfDay[0].toInt() > 12) {
                startAorP = "오후"
                a_startHour = startHourOfDay[0].toInt() - 12
                a_startTime = "${(startHourOfDay[0].toInt() - 12)}:${startHourOfDay[1]}"
            }

            if (endHourOfDay[0].toInt() > 12) {
                endAorP = "오후"
                a_endHour = endHourOfDay[0].toInt() - 12
                a_endTime = "${(endHourOfDay[0].toInt() - 12)}:${endHourOfDay[1]}"
            }
        }


        //1. 데이트 피커 및 타임피커 초기화(넘어온 시간으로 세팅)
        binding.datepickerStart.text = "${a_startYear}년 ${a_startMonth}월 ${a_startDay}일"
        binding.datepickerEnd.text = "${a_endYear}년 ${a_endMonth}월 ${a_endDay}일"


        //1-1. allDayCheck여부에 따라 다르게 보이게 함
        if (a_startTime == "") {
            binding.timepickerStart.text = "-"
            binding.timepickerEnd.text = "-"
            binding.allDayCheckBox.isChecked = true
        } else {
            binding.timepickerStart.text = "${startAorP} ${a_startTime}"
            binding.timepickerEnd.text = "${endAorP} ${a_endTime}"
            binding.allDayCheckBox.isChecked = false
        }

        val current = LocalDateTime.now()
        var startYear = CalendarUtil.sYear
        var startMonth = CalendarUtil.sMonth
        var startDay = CalendarUtil.sDay
        var startHour = current.format(DateTimeFormatter.ofPattern("k"))
        var startMinute = current.format(DateTimeFormatter.ofPattern("mm"))
        var endYear = CalendarUtil.sYear
        var endMonth = CalendarUtil.sMonth
        var endDay = CalendarUtil.sDay
        var endHour = current.format(DateTimeFormatter.ofPattern("k"))
        var endMinute = current.format(DateTimeFormatter.ofPattern("mm"))


        val formatterDate = DateTimeFormatter.ofPattern("yyyy년 M월 d일")
        val formatterTime = DateTimeFormatter.ofPattern("a h:mm", Locale.KOREAN)
        val formatterAlert = DateTimeFormatter.ofPattern("M월 d일")
        var startDate = "${CalendarUtil.sYear}년 ${CalendarUtil.sMonth}월 ${CalendarUtil.sDay}일"
        var startTime = current.format(formatterTime)
        var endDate = "${CalendarUtil.sYear}년 ${CalendarUtil.sMonth}월 ${CalendarUtil.sDay}일"
        var endTime = current.format(formatterTime)
        //var alertDate = "${CalendarUtil.sMonth}월 ${CalendarUtil.sDay}일"


        //안내 문구 초기화
        binding.scheduleAlert.text = "$alertDate 일정이 수정돼요!"


        //2. title 및 contents 받아온 걸로 세팅
        binding.emailEdittext1.setText(a_title)
        binding.emailEdittext2.setText(a_contents)


        //3. 방문장소와 데이트 미션 받아온 걸로 세팅
        binding.selectPlace.text = setPlaceName(placeCode)
        binding.selectMission.text = setMissionName(missionCode)


//////////////////////////////ADD스케쥴과 같은 기능들//////////////////////////////
        //1. Date&Time Picker
        //datepicker 및 timepicker에서 선택한 날짜/시간 등을 버튼 텍스트에 셋팅
        binding.datepickerStart.setOnClickListener {
            val cal = Calendar.getInstance()    //캘린더뷰 만들기
            val dateSetListener =
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    startDate = "${year}년 ${month + 1}월 ${dayOfMonth}일"
                    startYear = year.toString()
                    startMonth = (month + 1).toString()
                    startDay = dayOfMonth.toString()
                    binding.datepickerStart.text = startDate
                    binding.scheduleAlert.text = "${month + 1}월 ${dayOfMonth}일 일정이 수정돼요!"
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

                ///////시간 설정/////
                startAorP = "오전"     //오전/오후
                startHour = hourOfDay.toString()
                startMinute = minute.toString()

                //시간 24시간 표기 -> 12시간 표기로 바꾸기
                if (hourOfDay > 12) {
                    startAorP = "오후"
                    startHour = (hourOfDay - 12).toString()
                }

                //10분 미만일 때 0 붙여주기
                if (minute < 10) {
                    startMinute = "0${minute}"
                }

                startTime = "$startAorP ${startHour}:${startMinute}분"
                binding.timepickerStart.text = startTime
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
                    endDate = "${year}년 ${month + 1}월 ${dayOfMonth}일"
                    endYear = year.toString()
                    endMonth = (month + 1).toString()
                    endDay = dayOfMonth.toString()
                    binding.datepickerEnd.text = endDate
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

                ///////시간 설정/////
                endAorP = "오전"     //오전/오후
                endHour = hourOfDay.toString()
                endMinute = minute.toString()

                //시간 24시간 표기 -> 12시간 표기로 바꾸기
                if (hourOfDay > 12) {
                    endAorP = "오후"
                    endHour = (hourOfDay - 12).toString()
                }

                //10분 미만일 때 0 붙여주기
                if (minute < 10) {
                    endMinute = "0${minute}"
                }

                endTime = "$endAorP ${endHour}:${endMinute}분"
                binding.timepickerEnd.text = endTime


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
        var startAorP12 = 0

        //1-1. alldaycheck 체크 여부에 따라 텍스트와 clickable 속성 변경

        binding.allDayCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.timepickerStart.text = "-"
                binding.timepickerEnd.text = "-"
                binding.timepickerStart.isClickable = false
                binding.timepickerEnd.isClickable = false
                ADChkBox = "1"
            } else {
                binding.timepickerStart.text = LocalDateTime.now().format(DateTimeFormatter.ofPattern("a h:mm", Locale.KOREAN))
                binding.timepickerEnd.text = LocalDateTime.now().format(DateTimeFormatter.ofPattern("a h:mm", Locale.KOREAN))

                binding.timepickerStart.isClickable = true
                binding.timepickerEnd.isClickable = true
                ADChkBox = "0"
            }
        }


        //2. 스피너?리스트뷰?(방문장소)

        binding.selectPlace.setOnClickListener {
            showPlaceDialog()
            clearMissionDialog()
        }

        //3. 스피너?리스트뷰?(데이트미션)
        binding.selectMission.setOnClickListener {
            showMissionDialog()
        }


        //4. 일정 수정하기 누르면 정보 DB에 넣기
        binding.editBtn.setOnClickListener {

            val title = binding.emailEdittext1.text.toString()
            val contents = binding.emailEdittext2.text.toString()
            //alldaycheck는  val ADChkBox = "1" 또는 "0"으로 위의 체크리스트에서 만듦
            //오후 시간 DB에 넣을 때는 다시 오후 2시 -> 14시
            if (startAorP == "오후") {
                //startHour = (startHour.toInt() + 12).toString()
            }
            if (endAorP == "오후") {
                //endHour = (endHour.toInt() + 12).toString()
            }
            Log.d(
                "Date1231231",
                "onCreate: $startAorP, $startYear, $startMonth, $startDay, $startHour, $startMinute, $ADChkBox"
            )
            Log.d(
                "Date1231232",
                "onCreate: $endAorP, $endYear, $endMonth, $endDay, $endHour, $endMinute"
            )

            var startTime1 = ""
            var endTime1 = ""

            if (ADChkBox == "1") {
                startTime1 = ""
                endTime1 = ""
            } else {
                startTime1 = "$startHour:$startMinute"
                endTime1 = "$endHour:$endMinute"
            }

            if (title == "") {
                Toast.makeText(applicationContext, "일정 제목을 입력해주세요", Toast.LENGTH_SHORT).show()
            } else if (startYear.toInt() > endYear.toInt() || startMonth.toInt() > endMonth.toInt() || startDay.toInt() > endDay.toInt()) {
                Toast.makeText(applicationContext, "시작 날짜와 종료 날짜를 확인해주세요", Toast.LENGTH_SHORT)
                    .show()
            } else {

                val scheduleData = ScheduleEditRequestBody(
                    couple_index = "1",
                    schedule_index = a_scheduleIndex,
                    start_year = startYear,
                    start_month = startMonth,
                    start_day = startDay,
                    start_time = startTime1,
                    end_year = endYear,
                    end_month = endMonth,
                    end_day = endDay,
                    end_time = endTime1,
                    allDayCheck = ADChkBox,
                    title = title,
                    contents = contents,
                    place_code = matchPlaceCode(),
                    mission_code = matchMissionCode()
                )
                Log.d("scheduleData", "onCreate: $scheduleData")



                //5. request 후 액티비티 종료
                finish()
            }
        }


        setContentView(binding.root)
    }


    //장소선택 다이얼로그 호출
    private fun showPlaceDialog() {

        //데이터 담기
        val places: Array<String> = resources.getStringArray(R.array.places)

        //AlertDialog 초기화
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        //제목 설정
        builder.setTitle("데이트 장소를 선택해주세요")

        //아이템 선택 이벤트
        builder.setItems(places) { p0, p1 ->
            binding.selectPlace.text = places[p1]
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    //선택된 장소의 장소코드를 DB에 넣어주기 위한 작업
    private fun matchPlaceCode(): String? {

        var Pcode = ""
        when (binding.selectPlace.text.toString()) {
            "" -> Pcode = ""
            "영화관" -> Pcode = "1"
            "바/주점" -> Pcode = "2"
            "보드게임" -> Pcode = "3"
            "여행" -> Pcode = "4"
            "식당" -> Pcode = "5"
            "도서관" -> Pcode = "6"
            "전시관" -> Pcode = "7"
            "동물원" -> Pcode = "8"
            "놀이공원" -> Pcode = "9"
            "카페" -> Pcode = "10"
            "관람" -> Pcode = "11"
            "스포츠" -> Pcode = "12"
            "공방" -> Pcode = "13"
            "드라이브" -> Pcode = "14"
            "식물원" -> Pcode = "15"
            "기타" -> Pcode = "16"
        }
        return Pcode
    }


    //미션 코드 초기화
    private fun clearMissionDialog() {
        binding.selectMission.text = "데이트 미션!"
    }

    //미션 선택 다이얼로그 호출
    private fun showMissionDialog() {
        //데이터 담기
        var missions: Array<String> = resources.getStringArray(R.array.방문장소)
        when (binding.selectPlace.text) {
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
            "공연" -> missions = resources.getStringArray(R.array.공연)
            "스포츠" -> missions = resources.getStringArray(R.array.스포츠)
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
        builder2.setItems(missions) { p0, p1 ->
            binding.selectMission.text = missions[p1]
        }

        val alertDialog2: AlertDialog = builder2.create()
        alertDialog2.show()
    }

    //선택된 미션의 미션 코드를 DB에 넣어주기 위한 작업
    private fun matchMissionCode(): String? {

        var Mcode = ""
        when (binding.selectMission.text.toString()) {
            "" -> Mcode = ""
            "팝콘 받아 먹은 사람이 사랑한다고 말해주기" -> Mcode = "1"
            "바/영화 보는 동안 팔짱 끼기" -> Mcode = "2"
            "서로 마실 술 골라주기" -> Mcode = "3"
            "러브샷" -> Mcode = "4"
            "보드게임 쿼리도 해보기" -> Mcode = "5"
            "보드게임 고스트 해보기" -> Mcode = "6"
            "가위바위보해서 진 사람이 밥 사주기" -> Mcode = "7"
            "여행지를 배경으로 손잡은 사진 찍기" -> Mcode = "8"
            "정성을 다한 한 숟갈 먹여주기" -> Mcode = "9"
            "매운 음식 먹으러 가기~" -> Mcode = "10"
            "옆자리에서 먹기" -> Mcode = "11"
            "서로 공유하고 싶은 책 골라주기" -> Mcode = "12"
            "책장 사이에서 몰래 뽀뽀" -> Mcode = "13"
            "관람 끝난 후 감상 나누기" -> Mcode = "14"
            "다음에 보러갈 전시 고르기" -> Mcode = "15"
            "동물 옆에서 동물 따라하는 사진 찍기(ex. 원숭이 흉내~)" -> Mcode = "16"
            "안 타본 놀이기구 타보기" -> Mcode = "18"
            "놀이기구 타기 전에 같이 사진 찍기" -> Mcode = "19"
            "대관람차 꼭대기에서 사진찍기" -> Mcode = "20"
            "상대방이 좋아하는 메뉴 맞추기" -> Mcode = "21"
            "서로 메뉴 골라주기" -> Mcode = "22"
            "다음에 보러갈 공연 고르기" -> Mcode = "23"
            "공연 후 기념 사진 찍기" -> Mcode = "24"
            "점수 내기!" -> Mcode = "25"
            "멋진 모습 보여주기" -> Mcode = "26"
            "서로를 닮은 물건 만들기" -> Mcode = "27"
            "그날 만든 물건을 서로에게 선물하기" -> Mcode = "28"
            "조수석에 앉은 사람이 좋아하는 노래 틀기" -> Mcode = "29"
            "조수석에서 노래 불러주기" -> Mcode = "30"
            "서로에게 어울리는 식물 고르고 이유 말해주기" -> Mcode = "31"
            "식물 이름 많이 맞추기" -> Mcode = "32"
            "마음에 드는 꽃의 꽃말 찾아보기" -> Mcode = "33"
            "서로 바라보고 웃어주기" -> Mcode = "34"
            "서로에게 편지 써주기" -> Mcode = "35"
            "지역 축제 함께 참가하기" -> Mcode = "36"
            "함께 악기 배우러 가기" -> Mcode = "37"
            "1분 동안 손 잡고 있기" -> Mcode = "38"
            "사랑한다고 다섯 번 말하기" -> Mcode = "39"
            "30초동안 눈 마주치기" -> Mcode = "40"
        }
        return Mcode
    }


    //선택된 장소 이름 찾기
    private fun setPlaceName(placeCode: String?): String {

        var placeName = ""
        when (placeCode) {
            "" -> placeName = "방문 장소"
            "1" -> placeName = "영화관"
            "2" -> placeName = "바/주점"
            "3" -> placeName = "보드게임"
            "4" -> placeName = "여행"
            "5" -> placeName = "식당"
            "6" -> placeName = "도서관"
            "7" -> placeName = "전시관"
            "8" -> placeName = "동물원"
            "9" -> placeName = "놀이공원"
            "10" -> placeName = "카페"
            "11" -> placeName = "관람"
            "12" -> placeName = "스포츠"
            "13" -> placeName = "공방"
            "14" -> placeName = "드라이브"
            "15" -> placeName = "식물원"
            "16" -> placeName = "기타"
        }
        return placeName
    }


    private fun setMissionName(missionCode: String?): String {
        var missionName = ""

        when (missionCode) {
            "" -> missionName = "데이트 미션!"
            "1" -> missionName = "팝콘 받아 먹은 사람이 사랑한다고 말해주기"
            "2" -> missionName = "바/영화 보는 동안 팔짱 끼기"
            "3" -> missionName = "서로 마실 술 골라주기"
            "4" -> missionName = "러브샷"
            "5" -> missionName = "보드게임 쿼리도 해보기"
            "6" -> missionName = "보드게임 고스트 해보기"
            "7" -> missionName = "가위바위보해서 진 사람이 밥 사주기"
            "8" -> missionName = "여행지를 배경으로 손잡은 사진 찍기"
            "9" -> missionName = "정성을 다한 한 숟갈 먹여주기"
            "10" -> missionName = "매운 음식 먹으러 가기~"
            "11" -> missionName = "옆자리에서 먹기"
            "12" -> missionName = "서로 공유하고 싶은 책 골라주기"
            "13" -> missionName = "책장 사이에서 몰래 뽀뽀"
            "14" -> missionName = "관람 끝난 후 감상 나누기"
            "15" -> missionName = "다음에 보러갈 전시 고르기"
            "16" -> missionName = "동물 옆에서 동물 따라하는 사진 찍기(ex. 원숭이 흉내~)"
            "18" -> missionName = "안 타본 놀이기구 타보기"
            "19" -> missionName = "놀이기구 타기 전에 같이 사진 찍기"
            "20" -> missionName = "대관람차 꼭대기에서 사진찍기"
            "21" -> missionName = "상대방이 좋아하는 메뉴 맞추기"
            "22" -> missionName = "서로 메뉴 골라주기"
            "23" -> missionName = "다음에 보러갈 공연 고르기"
            "24" -> missionName = "공연 후 기념 사진 찍기"
            "25" -> missionName = "점수 내기!"
            "26" -> missionName = "멋진 모습 보여주기"
            "27" -> missionName = "서로를 닮은 물건 만들기"
            "28" -> missionName = "그날 만든 물건을 서로에게 선물하기"
            "29" -> missionName = "조수석에 앉은 사람이 좋아하는 노래 틀기"
            "30" -> missionName = "조수석에서 노래 불러주기"
            "31" -> missionName = "서로에게 어울리는 식물 고르고 이유 말해주기"
            "32" -> missionName = "식물 이름 많이 맞추기"
            "33" -> missionName = "마음에 드는 꽃의 꽃말 찾아보기"
            "34" -> missionName = "서로 바라보고 웃어주기"
            "35" -> missionName = "서로에게 편지 써주기"
            "36" -> missionName = "지역 축제 함께 참가하기"
            "37" -> missionName = "함께 악기 배우러 가기"
            "38" -> missionName = "1분 동안 손 잡고 있기"
            "39" -> missionName = "사랑한다고 다섯 번 말하기"
            "40" -> missionName = "30초동안 눈 마주치기"
        }

        return missionName
    }


}