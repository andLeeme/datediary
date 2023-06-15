//package com.project.datediary.fragment
//
//import android.content.Context
//import android.graphics.Rect
//import android.os.Bundle
//import android.view.*
//import android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
//import android.view.inputmethod.InputMethodManager
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.project.datediary.activity.MainActivity
//import com.project.datediary.adapter.ChatAdapter
//import com.project.datediary.databinding.FragmentChatBinding
//import com.project.datediary.model.ChatMessage
//import java.util.*
//
//class FragmentChat : Fragment() {
//
//    private lateinit var binding: FragmentChatBinding
//    private lateinit var chatAdapter: ChatAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentChatBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        activity?.window?.setSoftInputMode(SOFT_INPUT_ADJUST_RESIZE)
//
//        // RecyclerView 설정
//        chatAdapter = ChatAdapter()
//        binding.recyclerViewChat.apply {
//            adapter = chatAdapter
//            layoutManager = LinearLayoutManager(context)
//        }
//
//        binding.buttonSend.setOnClickListener {
//            // 키보드 숨기기
//            hideKeyboard()
//
//            // 채팅 메시지 전송
//            val message = binding.editTextMessage.text.toString().trim()
//            if (message.isNotEmpty()) {
//                val chatMessage = ChatMessage(MainActivity.coupleIndex, message, Date())
//                scrollToBottom()
//                binding.editTextMessage.setText("")
//
//
//                val curUser = GoogleSignIn.getLastSignedInAccount(requireContext())
//
//                val email = curUser?.email.toString()
//
////                var chatRequestBody = ChatRequestBody(
////                    couple_index = MainActivity.coupleIndex,
////                    email = email,
////                    message = message,
////                    sender = "지현이",
////                    timestamp = Date().toString()
////                )
//
////
////                RetrofitAPI.emgMedService12.addUserByEnqueue(chatRequestBody)
////                    .enqueue(object : retrofit2.Callback<ArrayList<ChatResponseBody>> {
////                        override fun onResponse(
////                            call: Call<ArrayList<ChatResponseBody>>,
////                            response: Response<ArrayList<ChatResponseBody>>
////                        ) {
////                            Log.d("coupleIndex", "Call Success")
////
////                            if (response.isSuccessful) {
////
////
////
////                                chatAdapter.addMessage(chatMessage)
////                            }
////                        }
////
////                        override fun onFailure(
////                            call: Call<ArrayList<ChatResponseBody>>,
////                            t: Throwable
////                        ) {
////                            Toast.makeText(context, "Call Failed", Toast.LENGTH_SHORT).show()
////                        }
////                    })
//
//            }
//        }
//    }
//
//    private fun hideKeyboard() {
//        val imm =
//            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(binding.editTextMessage.windowToken, 0)
//    }
//
//    private fun scrollToBottom() {
//        if (chatAdapter.itemCount > 0) {
//            binding.recyclerViewChat.scrollToPosition(chatAdapter.itemCount - 1)
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        binding.editTextMessage.viewTreeObserver
//            .addOnGlobalLayoutListener {
//                val rect = Rect()
//                binding.editTextMessage.getWindowVisibleDisplayFrame(rect)
//                val screenHeight = binding.editTextMessage.rootView.height
//                val keypadHeight = screenHeight - rect.bottom
//                if (keypadHeight > screenHeight * 0.15) {
//                    binding.layoutMessage.translationY = (-keypadHeight + 340).toFloat()
//
//                    // 리사이클러뷰 높이 조정
//                    val layoutParams =
//                        binding.recyclerViewChat.layoutParams as ViewGroup.MarginLayoutParams
//                    layoutParams.bottomMargin = keypadHeight
//                    binding.recyclerViewChat.layoutParams = layoutParams
//                    scrollToBottom()
//                } else {
//                    binding.layoutMessage.translationY = 0f
//
//                    // 리사이클러뷰 높이 원래대로
//                    val layoutParams =
//                        binding.recyclerViewChat.layoutParams as ViewGroup.MarginLayoutParams
//                    layoutParams.bottomMargin = 0
//                    binding.recyclerViewChat.layoutParams = layoutParams
//                }
//            }
//    }
//
//    override fun onPause() {
//        super.onPause()
//        binding.editTextMessage.viewTreeObserver.removeOnGlobalLayoutListener { }
//    }
//}