//package com.project.datediary.fragment
//
//import android.app.Activity.RESULT_OK
//import android.content.Intent
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.activity.result.ActivityResultLauncher
//import androidx.activity.result.contract.ActivityResultContracts
//import com.bumptech.glide.Glide
//import com.project.datediary.databinding.FragmentPhotoBinding
//
//class FragmentPhoto : Fragment() {
//
//    lateinit var binding: FragmentPhotoBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentPhotoBinding.inflate(inflater, container, false)
//
//
//        //버튼 이벤트
//        binding.galleryBtn.setOnClickListener {
//
//            //갤러리 호출
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.type = "image/*"
//            activityResult.launch(intent)
//
//        }
//        return binding.root
//    }
//
//    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) {
//
//        //결과 코드 OK , 결과값 null 아니면
//        if (it.resultCode == RESULT_OK && it.data != null) {
//            //값 담기
//            val uri = it.data!!.data
//
//            //화면에 보여주기
//            Glide.with(this)
//                .load(uri) //이미지
//                .into(binding.imageView) //보여줄 위치
//
//        }
//    }
//}
//
//
//
//
//
//
