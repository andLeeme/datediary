//package com.project.datediary.fragment
//
//import android.content.Intent
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.project.datediary.R
//import com.project.datediary.activity.LoginActivity
//import com.project.datediary.activity.MainActivity
//import com.project.datediary.activity.ViewActivity2
//import com.project.datediary.databinding.ActivityMainBinding
//import com.project.datediary.databinding.FragmentTestBinding
//
//class FragmentTest : Fragment() {
//
//    lateinit var binding: FragmentTestBinding
//    lateinit var bindingMain: ActivityMainBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
//    ): View? {
//
//        binding = FragmentTestBinding.inflate(inflater, container, false)
//
//        bindingMain = ActivityMainBinding.inflate(inflater, container, false)
//
//
//        binding.button1.setOnClickListener {
//            val intent = Intent(requireActivity(), AddScheduleActivity2::class.java)
//            startActivity(intent)
//        }
//        binding.button2.setOnClickListener {
//            childFragmentManager.beginTransaction().replace(R.id.editContainer, FragmentWz())
//                .commit()
//            binding.fragmentStory.visibility = View.INVISIBLE
//
//        }
//        binding.button3.setOnClickListener {
//            childFragmentManager.beginTransaction()
//                .replace(R.id.editContainer, FragmentStaggeredGrid())
//                .commit()
//            binding.fragmentStory.visibility = View.INVISIBLE
//
//        }
//
//        binding.button4.setOnClickListener {
//            childFragmentManager.beginTransaction()
//                .replace(R.id.editContainer, FragmentPhoto())
//                .commit()
//            binding.fragmentStory.visibility = View.INVISIBLE
//
//        }
//
//        binding.button5.setOnClickListener {
//            childFragmentManager.beginTransaction()
//                .replace(R.id.editContainer, UploadFragment())
//                .commit()
//            binding.fragmentStory.visibility = View.INVISIBLE
//
//        }
//
//
//        binding.button6.setOnClickListener {
//            childFragmentManager.beginTransaction()
//                .replace(R.id.editContainer, FragmentLoginTest())
//                .commit()
//            binding.fragmentStory.visibility = View.INVISIBLE
//
//        }
//
//        binding.button7.setOnClickListener {
//            val intent = Intent(requireActivity(), LoginActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.button8.setOnClickListener {
//            val intent = Intent(requireActivity(), ViewActivity2::class.java)
//            startActivity(intent)
//        }
//
//        return binding.root
//    }
//}