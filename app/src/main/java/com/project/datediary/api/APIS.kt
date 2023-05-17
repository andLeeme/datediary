
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.project.datediary.model.HTTP_GET_Model
import com.project.datediary.model.PostModel
import com.project.datediary.model.PostResult
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface APIS {

    @POST("/api/postTest")
    @Headers("accept: application/json",
        "content-type: application/json")
    fun post_users(
        @Body jsonparams: PostModel
    ): Call<PostResult>

    @GET("/api/getTest")
    @Headers("accept: application/json",
        "content-type: application/json"
    )
    fun get_users(
    ): Call<HTTP_GET_Model>


    companion object { // static 처럼 공유객체로 사용가능함. 모든 인스턴스가 공유하는 객체로서 동작함.
        private const val BASE_URL = "http://192.168.150.120:8080" // 주소

        fun create(): APIS {


            val gson :Gson =   GsonBuilder().setLenient().create();

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(APIS::class.java)
        }
    }
}