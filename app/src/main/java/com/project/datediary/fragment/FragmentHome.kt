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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.project.datediary.api.ImageUploadService
import com.project.datediary.databinding.FragmentHomeBinding
import com.project.datediary.util.SetBackground
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var selectedImageUri: Uri
    private lateinit var imageUploadService: ImageUploadService
    private val PICK_IMAGE_REQUEST = 1
    private val coupleIndex = "1"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setBackground()

        val activity = requireActivity()
        val window = activity.window
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        binding.addMainImage.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://andLeeme.iptime.org:60722")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            imageUploadService = retrofit.create(ImageUploadService::class.java)

            openGallery()
        }

        childFragmentManager.setFragmentResultListener("requestKey", viewLifecycleOwner,
            FragmentResultListener { _, bundle ->
                val result_coupleIndex = bundle.getString("coupleIndex")
                val result_imageUrl = bundle.getString("imageUrl")

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

        Glide.with(binding.root)
            .load(SetBackground.backgroundURI)
            .centerCrop()
//            .skipMemoryCache(true)
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
                    .skipMemoryCache(true)
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

        val data = HashMap<String, String>()
        data["couple_index"] = coupleIndex

//        Toast.makeText(context, "$data", Toast.LENGTH_SHORT).show()

        lifecycleScope.launch(Dispatchers.IO) {
            val call = imageUploadService.uploadImage(filePart, data)
            val response = call.execute()

            launch(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse != null) {
                        showToast("이미지 업로드 성공")
                        val coupleIndex = coupleIndex
                        val bundle_imageUrl = "$selectedImageUri"
                        parentFragmentManager.setFragmentResult(
                            "requestKey",
                            bundleOf("coupleIndex" to coupleIndex, "imageUrl" to bundle_imageUrl)
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
}
