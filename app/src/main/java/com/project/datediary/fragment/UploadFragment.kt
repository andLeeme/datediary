package com.project.datediary.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.lifecycleScope
import com.project.datediary.R
import com.project.datediary.api.ImageUploadService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File

class UploadFragment : Fragment() {
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var selectedImageUri: Uri
    private lateinit var imageUploadService: ImageUploadService
    private lateinit var rootView: View
    private val coupleIndex = "1"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_upload, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://andLeeme.iptime.org:60722")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        imageUploadService = retrofit.create(ImageUploadService::class.java)

        rootView.findViewById<Button>(R.id.selectImageButton).setOnClickListener {
            openGallery()
        }

        rootView.findViewById<Button>(R.id.uploadImageButton).setOnClickListener {
            if (::selectedImageUri.isInitialized) {
                uploadImage(selectedImageUri)

                //이미지 정보 넘겨주기
                val coupleIndex = coupleIndex
                val bundle_imageUrl = "$selectedImageUri"
                parentFragmentManager.setFragmentResult("requestKey", bundleOf("coupleIndex" to coupleIndex,"imageUrl" to bundle_imageUrl))
                parentFragmentManager.beginTransaction().remove(this@UploadFragment).commit()//현재 프레그먼트 닫기

                Log.d("result_imageUrl", "onCreateViewC: $bundle_imageUrl")


            } else {
                showToast("이미지를 선택하세요.")
            }
        }





    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        //intent.putExtra( "crop", "true" )
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data
            imageUri?.let {
                selectedImageUri = it
                rootView.findViewById<ImageView>(R.id.imageView).setImageURI(selectedImageUri)
            }
        }
    }

    private fun uploadImage(imageUri: Uri) {
        val file = File(getRealPathFromURI(imageUri))

        val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val filePart = MultipartBody.Part.createFormData("file", file.name, requestBody)

        val data  = HashMap<String, String>()

        data["couple_index"] = "1"

        Toast.makeText(context, "$data", Toast.LENGTH_SHORT).show()

        lifecycleScope.launch(Dispatchers.IO) {
            val call = imageUploadService.uploadImage(filePart, data)
            val response = call.execute()

            launch(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse != null) {
                        showToast("이미지 업로드 성공: $apiResponse")


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