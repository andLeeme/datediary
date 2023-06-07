import com.project.datediary.api.ScheduleService
import com.project.datediary.api.SignUpService
import com.project.datediary.api.TitleService
import com.project.datediary.api.UpbitAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CoinAPI {

    private const val BASE_URL = "https://api.upbit.com/"


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
    val getRetrofitService : UpbitAPI by lazy {
        retrofit.create(UpbitAPI::class.java)
    }
}