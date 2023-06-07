import com.project.datediary.api.CoupleMatchService
import com.project.datediary.api.AddCoupleEmailService
import com.project.datediary.api.CoupleIndexService
import com.project.datediary.api.ScheduleDeleteService
import com.project.datediary.api.ScheduleEditService
import com.project.datediary.api.ScheduleService
import com.project.datediary.api.ScheduleShowService
import com.project.datediary.api.SearchEmailService
import com.project.datediary.api.SignUpService
import com.project.datediary.api.TitleService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAPI {
    //학원
        //private const val BASE_URL = "http://192.168.150.120:8080"

    //학원(현하)
    //private const val BASE_URL = "http://192.168.250.44:8080"

    //집
    //    private const val BASE_URL = "http://112.173.111.193:8080"

    //서버
    private const val BASE_URL = "http://andLeeme.iptime.org:60722"


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

    fun getInstance(): Retrofit{
        return retrofit
    }

    //로그인 테스트
    val emgMedService: SignUpService by lazy {
        retrofit.create(SignUpService::class.java)
    }

    //스케줄 등록
    val emgMedService2: ScheduleService by lazy {
        retrofit.create(ScheduleService::class.java)
    }

    //달력 + 그날 일정 보여주는 용
    val emgMedService3: TitleService by lazy {
        retrofit.create(TitleService::class.java)
    }

    //스케줄 수정 내용 등록용
    val emgMedService4: ScheduleEditService by lazy {
        retrofit.create(ScheduleEditService::class.java)
    }

    //스케줄 불러오기용
    val emgMedService5: ScheduleShowService by lazy {
        retrofit.create(ScheduleShowService::class.java)
    }

    //스케줄 삭제
    val emgMedService6: ScheduleDeleteService by lazy {
        retrofit.create(ScheduleDeleteService::class.java)
    }

    val emgMedService7: SearchEmailService by lazy {
        retrofit.create(SearchEmailService::class.java)
    }

    val emgMedService8: AddCoupleEmailService by lazy {
        retrofit.create(AddCoupleEmailService::class.java)
    }

    val emgMedService9: CoupleMatchService by lazy {
        retrofit.create(CoupleMatchService::class.java)
    }

//    val emgMedService10: MainPhotoService by lazy {
//        retrofit.create(MainPhotoService::class.java)
//    }

    val emgMedService11: CoupleIndexService by lazy {
        retrofit.create(CoupleIndexService::class.java)
    }
}