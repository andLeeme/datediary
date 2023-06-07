package com.project.datediary.fragment

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.project.datediary.R
import com.project.datediary.api.ImageUploadService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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


    fun makeAlarmNotification(context: Context, messageBody: String) {
        //Timber.d("make notification")
        // notification 클릭 시 MainActivity를 열게 한다
        val intent = Intent(context, UploadFragment::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // notification의 channel id를 정의한다
        val channelId = "channel1"
        val channelName = "channel name"

        // notification를 정의한다
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.icon_home)
            .setContentTitle("notification title")
            .setContentText(messageBody)
            .setAutoCancel(false)   // 전체 삭제해도 안되게하기
            .setSound(null)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setOngoing(true)   // 알람이 계속 뜬 상태로 있게하기

        // 정의한 내용과 channel을 사용하여 notification을 생성한다
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Android SDK 26 이상에서는 notification을 만들 때 channel을 지정해줘야 한다
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // notification 띄우기
        notificationManager.notify(100, notificationBuilder.build())
    }




}