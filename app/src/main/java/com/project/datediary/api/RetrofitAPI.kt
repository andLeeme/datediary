import com.project.datediary.api.ScheduleService
import com.project.datediary.api.SignUpService
import com.project.datediary.api.TitleService
import com.project.datediary.api.UpbitAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAPI {
    //학원
//        private const val BASE_URL = "http://192.168.150.120:8080"

    //학원(현하)
    //private const val BASE_URL = "http://192.168.250.44:8080"

    //집
    //    private const val BASE_URL = "http://112.173.111.193:8080"

    //서버
    private const val BASE_URL = "http://andLeeme.iptime.org:60722"

//    private const val BASE_URL = "https://api.upbit.com/"


    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient) //
            .build()
    }

    val emgMedService: SignUpService by lazy {
        retrofit.create(SignUpService::class.java)
    }

    val emgMedService2: ScheduleService by lazy {
        retrofit.create(ScheduleService::class.java)
    }

    val emgMedService3: TitleService by lazy {
        retrofit.create(TitleService::class.java)
    }
    val getRetrofitService : UpbitAPI by lazy { retrofit.create(UpbitAPI::class.java) }
}