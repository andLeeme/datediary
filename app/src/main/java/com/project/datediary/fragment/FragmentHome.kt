package com.project.datediary.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.project.datediary.activity.AddScheduleActivity
import com.project.datediary.activity.EditScheduleActivity
import com.project.datediary.activity.MainActivity
import com.project.datediary.activity.MainActivity.Companion.coupleIndex
import com.project.datediary.adapter.DayScheduleAdapter
import com.project.datediary.api.ImageUploadService
import com.project.datediary.databinding.FragmentHomeBinding
import com.project.datediary.model.TitleRequestBody
import com.project.datediary.model.TitleResponseBody
import com.project.datediary.util.CalendarUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FragmentHome : Fragment(), MainActivity.onBackPressedListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var selectedImageUri: Uri
    private lateinit var imageUploadService: ImageUploadService
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        setBackground(coupleIndex)


        //바텀시트에서 일정을 추가함
        binding.addBtn.setOnClickListener {
            val intent = Intent(context, AddScheduleActivity::class.java)
            startActivity(intent)


            //0.2초후에 바텀시트가 내려감
            CoroutineScope(Dispatchers.Main).launch {
                delay(200).run {
                    bottomDown()
                }
            }

        }


        //MainActivity staticField
        binding.name1.text = MainActivity.nickname1
        binding.name2.text = MainActivity.nickname2
        binding.dday.text = MainActivity.d_day + "일째"


        //스테이터스 바 색상 변경
        val activity = requireActivity()
        val window = activity.window
        window.statusBarColor = Color.TRANSPARENT

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

//
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
//                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        var colorCount = true

//        binding.changeStColor.setOnClickListener {
//
//            if (colorCount) {
//                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
//                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//
//                colorCount = false
//            } else {
//                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
//                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                colorCount = true
//
//            }
//        }

        //메인 배경화면 변경
        binding.addMainImage.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://andLeeme.iptime.org:60722")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            imageUploadService = retrofit.create(ImageUploadService::class.java)


            //갤러리 호출
            openGallery()
        }

        childFragmentManager.setFragmentResultListener("requestKey", viewLifecycleOwner,
            FragmentResultListener { _, bundle ->
                val result_coupleIndex = bundle.getString("coupleIndex")
                val result_imageUrl = bundle.getString("imageUrl")

                Toast.makeText(requireContext(), "$result_coupleIndex", Toast.LENGTH_SHORT)
                    .show()
                Toast.makeText(requireContext(), "$result_imageUrl", Toast.LENGTH_SHORT).show()
                Log.d("result_imageUrl", "onCreateViewPC: $result_coupleIndex")
                Log.d("result_imageUrl", "onCreateViewP: $result_imageUrl")

                //result_imageUrl = "http://andLeeme.iptime.org:60722/getImageFrom/${coupleIndex}"

                if (result_imageUrl != "") {
                    setBackground(coupleIndex)
                }
            })

//        var behavior = BottomSheetBehavior.from(binding.bottomSheet)
//        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                // 상태가 변경될 때 호출
//                // newState 변수로 상태확인
//                when (newState) {
//                    BottomSheetBehavior.STATE_COLLAPSED -> {
//                        // 바텀 시트가 축소
//                        Log.d("바텀시트", "줄였나?")
//                    }
//                    BottomSheetBehavior.STATE_EXPANDED -> {
//                        // 바텀 시트가 확장
//                    }
//                    // 추가 코드
//                }
//            }
//
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//            }
//        })


        //바텀시트 내용 그려주기
        setBottomSheet()


        return binding.root
    }

    //에딧하고 돌아오면 재시작해주기
    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.Main).launch {
            delay(200).run {
                setBottomSheet()
            }
        }
    }


    private fun setBottomSheet() {
        //바텀시트에 그려주기
        binding.selectedDay.text = CalendarUtil.sDay
        binding.selectedDW.text = CalendarUtil.sDOW

        //바텀시트 리사이클러뷰 채워주기
        val userDataCal = TitleRequestBody(
            couple_index = MainActivity.coupleIndex,
            selected_month = CalendarUtil.sMonth
        )
        Log.d("유저데이터", "userDataCal: $userDataCal")

        var TitleResponseBody = listOf<TitleResponseBody>()


        RetrofitAPI.emgMedService3.addUserByEnqueue(userDataCal)
            .enqueue(object : retrofit2.Callback<ArrayList<TitleResponseBody>> {
                override fun onResponse(
                    call: Call<ArrayList<TitleResponseBody>>,
                    response: Response<ArrayList<TitleResponseBody>>
                ) {

                    if (response.isSuccessful) {
                        Log.d("리턴", "onResponse: ${response.body()}")

                        TitleResponseBody = response.body() ?: listOf()


                        //////////// ///////오늘 정보 일정 바텀시트에 그려주기////////////////////////
                        //오늘 정보 가공
                        var scheduleList = ArrayList<TitleResponseBody>()
                        Log.d("scheduleList1", "bind: ${TitleResponseBody}")


                        var List = ArrayList<TitleResponseBody>()
                        for (i in TitleResponseBody.indices) {
                            for (j in 0..TitleResponseBody[i].endDay!!.toInt() - TitleResponseBody[i].startDay!!.toInt()) {
                                List.add(
                                    TitleResponseBody(
                                        "${TitleResponseBody[i].coupleIndex}",
                                        "${TitleResponseBody[i].scheduleIndex}",
                                        "${TitleResponseBody[i].startYear}",
                                        "${TitleResponseBody[i].startMonth}",
                                        "${TitleResponseBody[i].startDay!!.toInt() + j}",
                                        "${TitleResponseBody[i].startTime}",
                                        "${TitleResponseBody[i].endYear}",
                                        "${TitleResponseBody[i].endMonth}",
                                        "${TitleResponseBody[i].endDay}",
                                        "${TitleResponseBody[i].endTime}",
                                        "${TitleResponseBody[i].allDayCheck}",
                                        "${TitleResponseBody[i].title}",
                                        "${TitleResponseBody[i].contents}",
                                        "${TitleResponseBody[i].placeCode}",
                                        "${TitleResponseBody[i].missionCode}"
                                    )
                                )
                            }
                        }

                        for (i in List.indices) {
                            if (List[i].startDay == CalendarUtil.sDay) {
                                scheduleList.add(List[i])
                            }
                        }


//                        for (i in TitleResponseBody.indices) {
//                            if (TitleResponseBody[i].startDay == CalendarUtil.sDay) {
//                                scheduleList.add(TitleResponseBody[i])
//                            }
//                        }
//                        Log.d("scheduleList2", "bind: $scheduleList")

                        //어댑터에 넣어주기
                        val adapter2 = DayScheduleAdapter(scheduleList)

                        //레이아웃 설정
                        var manager2: RecyclerView.LayoutManager = LinearLayoutManager(context)

                        //레이아웃 적용
                        binding.recycler10.layoutManager = manager2

                        //어댑터 적용
                        binding.recycler10.adapter = adapter2

                        //아이템 클릭하면 EditSchedule Activity 소환!
                        adapter2.dayScheduleSetItemClickListener(object :
                            DayScheduleAdapter.DayScheduleOnItemClickListener {
                            override fun dayScheduleOnClick(v: View, position: Int) {
                                Log.d(
                                    "testIndex",
                                    "dayScheduleOnClick: ${scheduleList[position].scheduleIndex}"
                                )
                                val intent =
                                    Intent(context, EditScheduleActivity::class.java)
                                Log.d(
                                    "testIndex1",
                                    "dayScheduleOnClick: ${scheduleList[position].scheduleIndex}"
                                )
                                intent.putExtra(
                                    "scheduleIndex",
                                    scheduleList[position].scheduleIndex
                                )
                                intent.putExtra("startYear", scheduleList[position].startYear)
                                intent.putExtra("startMonth", scheduleList[position].startMonth)
                                intent.putExtra("startDay", scheduleList[position].startDay)
                                intent.putExtra("startTime", scheduleList[position].startTime)
                                intent.putExtra("endYear", scheduleList[position].endYear)
                                intent.putExtra("endMonth", scheduleList[position].endMonth)
                                intent.putExtra("endDay", scheduleList[position].endDay)
                                intent.putExtra("endTime", scheduleList[position].endTime)
                                intent.putExtra("title", scheduleList[position].title)
                                intent.putExtra("contents", scheduleList[position].contents)
                                intent.putExtra(
                                    "allDayCheck",
                                    scheduleList[position].allDayCheck
                                )
                                intent.putExtra("placeCode", scheduleList[position].placeCode)
                                intent.putExtra(
                                    "missionCode",
                                    scheduleList[position].missionCode
                                )
                                Log.d(
                                    "testIndex2",
                                    "dayScheduleOnClick: ${scheduleList[position].scheduleIndex}"
                                )
                                startActivity(intent)
                            }
                        })


                    }
                }

                override fun onFailure(
                    call: Call<ArrayList<TitleResponseBody>>,
                    t: Throwable
                ) {

                }
            })
    }


    private fun setBackground(coupleIndex: String) {

        Glide.with(binding.root)
            .load("http://andLeeme.iptime.org:60722/getImageFrom/${coupleIndex}")
            .centerCrop()
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(binding.backgroundHome)

        binding.fragmentHome.visibility = View.VISIBLE
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data
            imageUri?.let {
                selectedImageUri = it
                Glide.get(requireContext()).clearMemory()
                Glide.with(requireContext())
                    .load(selectedImageUri)
//                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .centerCrop()
                    .into(binding.backgroundHome)
                uploadImage(selectedImageUri)
                Glide.get(requireContext()).clearMemory()

            }
        }
    }


    private fun uploadImage(imageUri: Uri) {
        val file = File(getRealPathFromURI(imageUri))

        val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val filePart = MultipartBody.Part.createFormData("file", file.name, requestBody)

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분")
        val formatted = current.format(formatter)


        val data = HashMap<String, String>()
        data["couple_index"] = MainActivity.coupleIndex
        data["timestamp2"] = formatted
        data["name2"] = MainActivity.nickname1
        data["type2"] = "2"


//        Toast.makeText(context, "$data", Toast.LENGTH_SHORT).show()

        lifecycleScope.launch(Dispatchers.IO) {
            val call = imageUploadService.uploadImage(filePart, data)
            val response = call.execute()

            launch(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse != null) {
                        showToast("이미지 업로드 성공")
                        val coupleIndex = MainActivity.coupleIndex
                        val bundle_imageUrl = "$selectedImageUri"
                        parentFragmentManager.setFragmentResult(
                            "requestKey",
                            bundleOf(
                                "coupleIndex" to coupleIndex,
                                "imageUrl" to bundle_imageUrl
                            )
                        )
                    } else {
                        showToast("이미지 업로드 실패")
                    }
                } else {
                    showToast("이미지 업로드 실패")
                }
            }
        }
    }

    private fun getRealPathFromURI(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireActivity().contentResolver.query(uri, projection, null, null, null)
        val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        val path = columnIndex?.let { cursor?.getString(it) }
        cursor?.close()
        return path
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun bottomDown() {
        var bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
        var bottomState = bottomSheetBehavior.getState()
        Log.d("BTStateSet", "onResponse: $bottomState")
    }

    private var finishCount = false
    override fun onBackPressed() {

        var bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)

        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
        } else {

            if (finishCount == true) {
                (activity as MainActivity).finish()
            }

            Toast.makeText(context, "한번 더 버튼을 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            finishCount = true

            CoroutineScope(Dispatchers.Main).launch {
                delay(2000).run {
                    finishCount = false
                }


            }

        }
    }

}


