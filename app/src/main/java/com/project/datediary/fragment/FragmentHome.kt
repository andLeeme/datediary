package com.project.datediary.fragment

import android.R.attr.height
import android.R.attr.width
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import com.bumptech.glide.Glide
import com.project.datediary.R
import com.project.datediary.databinding.FragmentHomeBinding
import com.project.datediary.util.SetBackground


class FragmentHome : Fragment() {

    lateinit var binding: FragmentHomeBinding

    private var result_coupleIndex: String? = ""
    private var result_imageUrl: String? = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

//        if (result_imageUrl != "") {
//            setBackground()
//        }


        setBackground()

        binding.addMainImage.setOnClickListener {
            childFragmentManager.beginTransaction()
                .replace(R.id.editContainerHome, UploadFragment())
                .commit()
            binding.fragmentHome.visibility = View.INVISIBLE

        }



        childFragmentManager.setFragmentResultListener("requestKey", viewLifecycleOwner,
            FragmentResultListener { key, bundle ->


                result_coupleIndex = bundle.getString("coupleIndex")
                result_imageUrl = bundle.getString("imageUrl")

                Toast.makeText(requireContext(), "$result_coupleIndex", Toast.LENGTH_SHORT).show()
                Toast.makeText(requireContext(), "$result_imageUrl", Toast.LENGTH_SHORT).show()
                Log.d("result_imageUrl", "onCreateViewPC: $result_coupleIndex")
                Log.d("result_imageUrl", "onCreateViewP: $result_imageUrl")

                SetBackground.backgroundURI  = result_imageUrl

                if (result_imageUrl != "") {
                    setBackground()
                }
//
//                Glide.with(binding.root).load(result_imageUrl).override(28, 28)
//                    .into(binding.backgroundHome) //이미지
                //binding.resultMovietitle.text = result_title //작품명

            })

        //되긴 하는디, 어플 다시 켜면 날아감
//                Glide
//                    .with(this)
//                    .asBitmap() // Bitmap 으로 변환
//                    .load(R.drawable.heart1)
//                    .into(object : CustomTarget<Bitmap>() {
//                        override fun onResourceReady(a_resource: Bitmap, transition: Transition<in Bitmap>?) {
//                            var layout = binding.FragHomeBackground
//                                //val layout = findViewById<ConstraintLayout>(R.id.fragmentHome)
//                            layout.background = BitmapDrawable(resources, a_resource)
//                        }
//
//                        override fun onLoadCleared(placeholder: Drawable?) {}
//                    })


        /*수신받은 영화 이미지, 작품명 바인딩*/
//        Glide.with(binding.root).load(result_imageUrl).override(28, 28)
//            .into(binding.backgroundHome) //이미지
//        //binding.resultMovietitle.text = result_title //작품명
//            })

        return binding.root
    }

    fun setBackground() {

        //binding.FragHomeBackground.background(@drawable/test4)
       // binding.fragmentHome.setBackgroundResource(R.drawable.heart1)

//        val display: Display = getWindowManager().getDefaultDisplay()
//        val size = Point()
//        display.getSize(size)
//        width = size.x
//        height = size.y




        Glide
            .with(binding.root)
            .load(SetBackground.backgroundURI)
            .fitCenter()
            .optionalFitCenter()
            .into(binding.backgroundHome)

//        Glide
//            .with(binding.root)
//            .load(result_imageUrl)
//            //.override(1000, 1200)
//            .into(binding.backgroundHome)

        binding.fragmentHome.visibility = View.VISIBLE
    }
}










