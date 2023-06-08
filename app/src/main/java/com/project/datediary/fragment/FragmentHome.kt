package com.project.datediary.fragment


import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.project.datediary.R
import com.project.datediary.api.ImageUploadService
import com.project.datediary.databinding.FragmentHomeBinding
import com.project.datediary.util.SetBackground


class FragmentHome : Fragment() {

    lateinit var binding: FragmentHomeBinding

    private var result_coupleIndex: String? = ""
    private var result_imageUrl: String? = ""


    //이미지 처리
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var selectedImageUri: Uri
    private lateinit var imageUploadService: ImageUploadService
    private lateinit var rootView: View
    private val coupleIndex = "1"



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setBackground()

        val activity = requireActivity()
        val window = activity.window

        // 상태 표시줄 영역을 투명하게 만듦
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN





        binding.addMainImage.setOnClickListener {
            childFragmentManager.beginTransaction()
                .replace(R.id.editContainerHome, UploadFragment())
                .commit()
            binding.fragmentHome.visibility = View.INVISIBLE

        }


        childFragmentManager.setFragmentResultListener("requestKey", viewLifecycleOwner,
            FragmentResultListener { _, bundle ->

                result_coupleIndex = bundle.getString("coupleIndex")
                result_imageUrl = bundle.getString("imageUrl")

                Toast.makeText(requireContext(), "$result_coupleIndex", Toast.LENGTH_SHORT).show()
                Toast.makeText(requireContext(), "$result_imageUrl", Toast.LENGTH_SHORT).show()
                Log.d("result_imageUrl", "onCreateViewPC: $result_coupleIndex")
                Log.d("result_imageUrl", "onCreateViewP: $result_imageUrl")

                SetBackground.backgroundURI = result_imageUrl

                if (result_imageUrl != "") {
                    setBackground()
                }
            })


        return binding.root
    }

    private fun setBackground() {

        Glide

            .with(binding.root)
            .load(SetBackground.backgroundURI)
            .centerCrop()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(binding.backgroundHome)



        binding.fragmentHome.visibility = View.VISIBLE
    }
}










