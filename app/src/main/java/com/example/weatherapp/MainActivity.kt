package com.example.weatherapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.location.Geocoder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import io.nlopez.smartlocation.OnActivityUpdatedListener
import io.nlopez.smartlocation.OnLocationUpdatedListener
import io.nlopez.smartlocation.SmartLocation
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_simple.*
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    var year:String? =null
    var month:String? =null
    var day:String? =null
    var hour:String? =null
    var min:String? =null
    var sec:String? =null


    var main_lat: Double?=null //위도
    var main_lon: Double? = null //경도


    val permissionlistener = object : PermissionListener {
        override fun onPermissionGranted() {
        }

        override fun onPermissionDenied(deniedPermissions: ArrayList<String>) {
            finish()
        }


    }
    val geocoder = Geocoder(this, Locale.KOREA)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //퍼미션 체크
        TedPermission.with(this)
            .setPermissionListener(permissionlistener)
            .setPermissions(Manifest.permission.INTERNET)
            .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION)
            .check()



        backsetting()
        init()
        buttonActive()

        alarminit()

    }


    fun fetchJson(){
        val key ="aADZBcnoeyl%2BEghGttFwlCygiY4ia71rpKSq7i3H2GOQcFX0aPr157mYQ5zOOVVCMZomX6CxnmQjlfZb6UNndA%3D%3D"
        val url ="http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?dataTerm=DAILY&pageNo=1&numOfRows=1&ver=1.3&_returnType=json&stationName=정왕동&ServiceKey=aADZBcnoeyl%2BEghGttFwlCygiY4ia71rpKSq7i3H2GOQcFX0aPr157mYQ5zOOVVCMZomX6CxnmQjlfZb6UNndA%3D%3D"

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: okhttp3.Callback {
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                val body = response?.body()?.string()
                var gson = Gson()
                var ss = gson.fromJson(body,DustModel::class.java)
                Log.d("ssss3",ss.list.get(0).pm10Value)
                Log.d("ssss3",ss.list.get(0).o3Value)
                Log.d("ssss3",ss.list.get(0).no2Value)
                Log.d("ssss3",ss.list.get(0).pm25Value)




                this@MainActivity.runOnUiThread(java.lang.Runnable {
                    //text1.text = ss.list.get(0).pm25Grade1h.toString()
                    dustcode(ss.list.get(0).pm10Grade1h.toString())
                    dust2code(ss.list.get(0).pm25Grade1h.toString())
                    detail_l2.text = ss.list.get(0).pm10Value+"㎍/㎥"
                    detail_r2.text = ss.list.get(0).pm25Value+"㎍/㎥"
                    detail_l3.text = ss.list.get(0).o3Value+" ppm"
                    detail_r3.text = ss.list.get(0).no2Value+" ppm"
                })
            }
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.d("ssss2",e.toString())
            }
        })
    }

    fun alarminit(){
        alarm_btn.setOnClickListener {
            val intent = Intent(this, Alarm::class.java)
            startActivity(intent)
        }
    }


    fun mainweather(){
        var retrofit = Retrofit.Builder()
            .baseUrl("https://api2.sktelecom.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(MainWeatherApi::class.java)

        val call =  apiService.getRequest(MainWeatherApi.APPKEY, 1, main_lat, main_lon)
        call.enqueue(object: Callback<MainWeatherModel> {
            override fun onFailure(call: Call<MainWeatherModel>?, t: Throwable?) {
                Log.d("cccc",t.toString())
            }

            override fun onResponse(call: Call<MainWeatherModel>?, response: Response<MainWeatherModel>?) {
                //var a =Integer.parseInt(response?.body()?.getWeather()?.getSummary()?.get(0)?.getToday()?.getTemperature2()?.getTmax2())
                val obect = response?.body()
                Log.d("ccc123",obect.toString())
                //심플 날씨 상단부분
                mainweatherCode(obect?.getWeather()?.getSummary()?.get(0)?.getToday()?.getSky2()?.getCode2().toString())
                simple_temp_max.text = String.format("%.0f",obect?.getWeather()?.getSummary()?.get(0)?.getToday()?.getTemperature2()?.getTmax2())+"º"
                simple_temp_min.text = String.format("%.0f",obect?.getWeather()?.getSummary()?.get(0)?.getToday()?.getTemperature2()?.getTmin2())+"º"
                //디테일 지금 날씨 파트
                weather5code(obect?.getWeather()?.getSummary()?.get(0)?.getToday()?.getSky2()?.getCode2().toString())

                //어제 날씨 부분
                weather1Code(obect?.getWeather()?.getSummary()?.get(0)?.getYesterday()?.getSky1()?.getCode1().toString())
                simple_day1_max.text = String.format("%.0f",obect?.getWeather()?.getSummary()?.get(0)?.getYesterday()?.getTemperature1()?.getTmax1())+"º"
                simple_day1_min.text = String.format("%.0f",obect?.getWeather()?.getSummary()?.get(0)?.getYesterday()?.getTemperature1()?.getTmin1())+"º"

                //내일 날씨 부분
                weather3Code(obect?.getWeather()?.getSummary()?.get(0)?.getTomorrow()?.getSky3()?.getCode3().toString())
                simple_day2_max.text = String.format("%.0f",obect?.getWeather()?.getSummary()?.get(0)?.getTomorrow()?.getTemperature3()?.getTmax3())+"º"
                simple_day2_min.text = String.format("%.0f",obect?.getWeather()?.getSummary()?.get(0)?.getTomorrow()?.getTemperature3()?.getTmin3())+"º"

                //모레 날씨 부분
                weather4Code(obect?.getWeather()?.getSummary()?.get(0)?.getDayAftertomorrow()?.getSky4()?.getCode4().toString())
                simple_day3_max.text = String.format("%.0f",obect?.getWeather()?.getSummary()?.get(0)?.getDayAftertomorrow()?.getTemperature4()?.getTmax4())+"º"
                simple_day3_min.text = String.format("%.0f",obect?.getWeather()?.getSummary()?.get(0)?.getDayAftertomorrow()?.getTemperature4()?.getTmin4())+"º"
            }
        })
    }
    fun mainweather2(){
        var retrofit2 = Retrofit.Builder()
            .baseUrl("https://api2.sktelecom.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService2 = retrofit2.create(MainWeatherApi::class.java)

        val call =  apiService2.getRequest2(MainWeatherApi.APPKEY, 1, main_lat, main_lon)
        Log.d("sex",call.toString())
        call.enqueue(object: Callback<MainWeatherModel2> {
            override fun onFailure(call: Call<MainWeatherModel2>?, t: Throwable?) {
                Log.d("cccc",t.toString())
            }

            override fun onResponse(call: Call<MainWeatherModel2>?, response: Response<MainWeatherModel2>?) {
                val obect2 = response?.body()
                simple_temp.text = String.format("%.0f",obect2?.getWeather()?.getMinutely()?.get(0)?.getTemperature()?.getTc())+"º"
                detail_temp1.text = String.format("%.0f",obect2?.getWeather()?.getMinutely()?.get(0)?.getTemperature()?.getTc())+"º"
                wind_direction.text = windcheck(obect2?.getWeather()?.getMinutely()?.get(0)?.getWind()?.getWdir()?:0.0)
                wind_velocity.text = String.format("%.0f",obect2?.getWeather()?.getMinutely()?.get(0)?.getWind()?.getWspd())+"m/s"
                detail_l1.text = String.format("%.1f",obect2?.getWeather()?.getMinutely()?.get(0)?.getRain()?.getSinceMidnight())+"cm"
                detail_r1.text = String.format("%.0f",obect2?.getWeather()?.getMinutely()?.get(0)?.getHumidity())+"%"

            }
        })

    }
    fun mainweather3(){
        var retrofit3 = Retrofit.Builder()
            .baseUrl("https://api2.sktelecom.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService3 = retrofit3.create(MainWeatherApi::class.java)

        val call =  apiService3.getRequest3(MainWeatherApi.APPKEY, 1, main_lat, main_lon)
        call.enqueue(object: Callback<Model3> {
            override fun onFailure(call: Call<Model3>?, t: Throwable?) {
                Log.d("c2c2",t.toString())
            }

            override fun onResponse(call: Call<Model3>?, response: Response<Model3>?) {
                val obect3 = response?.body()
                Log.d("c1c1",obect3?.getWeather()?.getForecast3hours().toString())
                weather6code(obect3?.getWeather()?.getForecast3hours()?.get(0)?.getSky()?.getCode1hour().toString())
                weather7code(obect3?.getWeather()?.getForecast3hours()?.get(0)?.getSky()?.getCode2hour().toString())
                weather8code(obect3?.getWeather()?.getForecast3hours()?.get(0)?.getSky()?.getCode3hour().toString())

//                detail_temp2.text = obect3?.getWeather()?.getForecast3hours()?.get(0)?.getTemperature()?.getTemp1hour().toString()
//                detail_temp3.text = obect3?.getWeather()?.getForecast3hours()?.get(0)?.getTemperature()?.getTemp2hour().toString()
//                detail_temp4.text = obect3?.getWeather()?.getForecast3hours()?.get(0)?.getTemperature()?.getTemp3hour().toString()
                detail_temp2.text = nullcheck(obect3?.getWeather()?.getForecast3hours()?.get(0)?.getTemperature()?.getTemp1hour().toString())
                detail_temp3.text =  nullcheck(obect3?.getWeather()?.getForecast3hours()?.get(0)?.getTemperature()?.getTemp2hour().toString())
                detail_temp4.text =  nullcheck(obect3?.getWeather()?.getForecast3hours()?.get(0)?.getTemperature()?.getTemp3hour().toString())

            }
        })

    }
    fun nullcheck(code:String):String{
        var result1:String
        if(code==""){
            result1 = "예보없음"
        }else{
            result1 = BigDecimal(code).stripTrailingZeros().toPlainString()+"º"
        }
        return result1

    }
    fun windcheck(a: Double): String {
        val a1 = Math.round(a)
        var check = ""
        if (a1 >= 0 && 20 > a1)
            check = "북"
        else if (a1 >= 21 && 65 >= a1)
            check = "북동"
        else if (a1 >= 66 && 110 >= a1)
            check = "동"
        else if (a1 >= 111 && 155 >= a1)
            check = "남동"
        else if (a1 >= 156 && 200 >= a1)
            check = "남"
        else if (a1 >= 201 && 245 >= a1)
            check = "남서"
        else if (a1 >= 246 && 290 >= a1)
            check = "서"
        else if (a1 >= 291 && 335 >= a1)
            check = "북서"
        else if (a1 >= 336 && 360 >= a1) check = "북"
        return check
    }

    fun mainweatherCode(code: String) {
        Log.d("cccm",code)
        when (code) {
            "SKY_D01" -> {simple_img.setImageResource(R.drawable.weather_sun)
            }
            "SKY_D02" -> {simple_img.setImageResource(R.drawable.weather_cloud)
            }
            "SKY_D03" -> {simple_img.setImageResource(R.drawable.weather_cloud_more)
            }
            "SKY_D04" -> {simple_img.setImageResource(R.drawable.weather_blur)
            }
            "SKY_D05" -> {simple_img.setImageResource(R.drawable.weather_rain)
            }
            "SKY_D06" -> {simple_img.setImageResource(R.drawable.weather_snow)
            }
            "SKY_D07" -> {simple_img.setImageResource(R.drawable.weather_snow_or_rain)
            }
            else -> {
            }
        }
    }//심플 지금
    fun weather1Code(code: String) {
        Log.d("ccc1",code)

        when (code) {
            "SKY_Y01" -> {simple_day1_img.setImageResource(R.drawable.weather_sun)
            }
            "SKY_Y02" -> {simple_day1_img.setImageResource(R.drawable.weather_cloud)
            }
            "SKY_Y03" -> {simple_day1_img.setImageResource(R.drawable.weather_cloud_more)
            }
            "SKY_Y04" -> {simple_day1_img.setImageResource(R.drawable.weather_blur)
            }
            "SKY_Y05" -> {simple_day1_img.setImageResource(R.drawable.weather_rain)
            }
            "SKY_Y06" -> {simple_day1_img.setImageResource(R.drawable.weather_snow)
            }
            "SKY_Y07" -> {simple_day1_img.setImageResource(R.drawable.weather_snow_or_rain)
            }

            else -> {
            }
        }
    }//심플 어제
    fun weather3Code(code: String) {
        Log.d("ccc3",code)
        when (code) {
            "SKY_M01" -> {simple_day2_img.setImageResource(R.drawable.weather_sun)
            }
            "SKY_M02" -> {simple_day2_img.setImageResource(R.drawable.weather_cloud)
            }
            "SKY_M03" -> {simple_day2_img.setImageResource(R.drawable.weather_cloud_more)
            }
            "SKY_M04" -> {simple_day2_img.setImageResource(R.drawable.weather_blur)
            }
            "SKY_M05" -> {simple_day2_img.setImageResource(R.drawable.weather_rain)
            }
            "SKY_M06" -> {simple_day2_img.setImageResource(R.drawable.weather_snow)
            }
            "SKY_M07" -> {simple_day2_img.setImageResource(R.drawable.weather_snow_or_rain)
            }
            else -> {
            }
        }
    }//심플 내일
    fun weather4Code(code: String) {
        Log.d("ccc4",code)
        when (code) {
            "SKY_M01" -> {simple_day3_img.setImageResource(R.drawable.weather_sun)
            }
            "SKY_M02" -> {simple_day3_img.setImageResource(R.drawable.weather_cloud)
            }
            "SKY_M03" -> {simple_day3_img.setImageResource(R.drawable.weather_cloud_more)
            }
            "SKY_M04" -> {simple_day3_img.setImageResource(R.drawable.weather_blur)
            }
            "SKY_M05" -> {simple_day3_img.setImageResource(R.drawable.weather_rain)
            }
            "SKY_M06" -> {simple_day3_img.setImageResource(R.drawable.weather_snow)
            }
            "SKY_M07" -> {simple_day3_img.setImageResource(R.drawable.weather_snow_or_rain)
            }
            else -> {
            }
        }
    }//심플 모레
    fun weather5code(code: String) {
        when (code) {
            "SKY_D01" -> {detail_img1.setImageResource(R.drawable.weather_sun)
            }
            "SKY_D02" -> {detail_img1.setImageResource(R.drawable.weather_cloud)
            }
            "SKY_D03" -> {detail_img1.setImageResource(R.drawable.weather_cloud_more)
            }
            "SKY_D04" -> {detail_img1.setImageResource(R.drawable.weather_blur)
            }
            "SKY_D05" -> {detail_img1.setImageResource(R.drawable.weather_rain)
            }
            "SKY_D06" -> {detail_img1.setImageResource(R.drawable.weather_snow)
            }
            "SKY_D07" -> {detail_img1.setImageResource(R.drawable.weather_snow_or_rain)
            }
        }
    }//디테일 지금
    fun weather6code(code: String) {
        Log.d("c1c1",code)
        when (code) {
            "SKY_V01" -> {detail_img2.setImageResource(R.drawable.weather_sun)
            }
            "SKY_V02" -> {detail_img2.setImageResource(R.drawable.weather_cloud)
            }
            "SKY_V03" -> {detail_img2.setImageResource(R.drawable.weather_cloud_more)
            }
            "SKY_V04" -> {detail_img2.setImageResource(R.drawable.weather_rain)
            }
            "SKY_V05" -> {detail_img2.setImageResource(R.drawable.weather_snow)
            }
            "SKY_V06" -> {detail_img2.setImageResource(R.drawable.weather_snow_or_rain)
            }
            "SKY_V07" -> {detail_img2.setImageResource(R.drawable.weather_blur)
            }
            "SKY_V08" -> {detail_img2.setImageResource(R.drawable.weather_rain)
            }
            "SKY_V09" -> {detail_img2.setImageResource(R.drawable.weather_snow)
            }
            "SKY_V10" -> {detail_img2.setImageResource(R.drawable.weather_snow_or_rain)
            }
            "SKY_V11" -> {detail_img2.setImageResource(R.drawable.weather_blur)
            }
            "SKY_V12" -> {detail_img2.setImageResource(R.drawable.weather_rain)
            }
            "SKY_V13" -> {detail_img2.setImageResource(R.drawable.weather_snow)
            }
            "SKY_V14" -> {detail_img2.setImageResource(R.drawable.weather_snow_or_rain)
            }
            else -> {
            }
        }
    }//디테일 +1
    fun weather7code(code: String) {
        when (code) {
            "SKY_V01" -> {detail_img3.setImageResource(R.drawable.weather_sun)
            }
            "SKY_V02" -> {detail_img3.setImageResource(R.drawable.weather_cloud)
            }
            "SKY_V03" -> {detail_img3.setImageResource(R.drawable.weather_cloud_more)
            }
            "SKY_V04" -> {detail_img3.setImageResource(R.drawable.weather_rain)
            }
            "SKY_V05" -> {detail_img3.setImageResource(R.drawable.weather_snow)
            }
            "SKY_V06" -> {detail_img3.setImageResource(R.drawable.weather_snow_or_rain)
            }
            "SKY_V07" -> {detail_img3.setImageResource(R.drawable.weather_blur)
            }
            "SKY_V08" -> {detail_img3.setImageResource(R.drawable.weather_rain)
            }
            "SKY_V09" -> {detail_img3.setImageResource(R.drawable.weather_snow)
            }
            "SKY_V10" -> {detail_img3.setImageResource(R.drawable.weather_snow_or_rain)
            }
            "SKY_V11" -> {detail_img3.setImageResource(R.drawable.weather_blur)
            }
            "SKY_V12" -> {detail_img3.setImageResource(R.drawable.weather_rain)
            }
            "SKY_V13" -> {detail_img3.setImageResource(R.drawable.weather_snow)
            }
            "SKY_V14" -> {detail_img3.setImageResource(R.drawable.weather_snow_or_rain)
            }
            else -> {
            }
        }
    }//디테일 +2
    fun weather8code(code: String) {
        when (code) {
            "SKY_V01" -> {detail_img4.setImageResource(R.drawable.weather_sun)
            }
            "SKY_V02" -> {detail_img4.setImageResource(R.drawable.weather_cloud)
            }
            "SKY_V03" -> {detail_img4.setImageResource(R.drawable.weather_cloud_more)
            }
            "SKY_V04" -> {detail_img4.setImageResource(R.drawable.weather_rain)
            }
            "SKY_V05" -> {detail_img4.setImageResource(R.drawable.weather_snow)
            }
            "SKY_V06" -> {detail_img4.setImageResource(R.drawable.weather_snow_or_rain)
            }
            "SKY_V07" -> {detail_img4.setImageResource(R.drawable.weather_blur)
            }
            "SKY_V08" -> {detail_img4.setImageResource(R.drawable.weather_rain)
            }
            "SKY_V09" -> {detail_img4.setImageResource(R.drawable.weather_snow)
            }
            "SKY_V10" -> {detail_img4.setImageResource(R.drawable.weather_snow_or_rain)
            }
            "SKY_V11" -> {detail_img4.setImageResource(R.drawable.weather_blur)
            }
            "SKY_V12" -> {detail_img4.setImageResource(R.drawable.weather_rain)
            }
            "SKY_V13" -> {detail_img4.setImageResource(R.drawable.weather_snow)
            }
            "SKY_V14" -> {detail_img4.setImageResource(R.drawable.weather_snow_or_rain)
            }
            else -> {
            }
        }
    }//디테일 +3

    fun dustcode(code: String ){
        when(code){
            "1" -> {simple_img2.setImageResource(R.drawable.a1_white)
                    simple_dust1.text = "좋음"}
            "2" -> {simple_img2.setImageResource(R.drawable.a2_white)
                    simple_dust1.text = "보통"}
            "3" -> {simple_img2.setImageResource(R.drawable.a3_white)
                    simple_dust1.text = "나쁨"}
            "4" -> {simple_img2.setImageResource(R.drawable.a4_white)
                    simple_dust1.text = "최악"}
        }
    }
    fun dust2code(code: String ){
        when(code){
            "1" -> simple_dust2.text = "좋음"
            "2" -> simple_dust2.text = "보통"
            "3" -> simple_dust2.text = "나쁨"
            "4" -> simple_dust2.text = "최악"
        }
    }


    override fun onResume() {
        super.onResume()
        //위치 가져오기

        val provider: LocationGooglePlayServicesProvider? = LocationGooglePlayServicesProvider()
        provider?.setCheckLocationSettings(true)
        val smartLocation = SmartLocation.Builder(this).logging(true).build()
        smartLocation.location(provider).start(OnLocationUpdatedListener {  })
        smartLocation.activity().start(OnActivityUpdatedListener {  })
        val lastLocation = SmartLocation.with(this).location().lastLocation
        if (lastLocation != null) {


            main_lat = lastLocation.latitude //위도
            main_lon = lastLocation.longitude //경도
            mainweather()
            mainweather2()
            mainweather3()
            fetchJson()
            //lat.text = main_lat
            //lon.text = main_lon

            var a = geocoder.getFromLocation(lastLocation.latitude,lastLocation.longitude,1).get(0).locality.toString()
            var b = geocoder.getFromLocation(lastLocation.latitude,lastLocation.longitude,1).get(0).thoroughfare.toString()
            main_local.text = a + " "+ b
            //Toast.makeText(this, lastLocation.latitude.toString() + " " + lastLocation.longitude.toString(), Toast.LENGTH_SHORT).show()
        }

    }
    override fun onStop() {
        super.onStop()
        //오류방지를 위한 서비스 종료
        SmartLocation.with(this).location().stop()
        SmartLocation.with(this).activity().stop()
        SmartLocation.with(this).geofencing().stop()
    }

    fun backsetting(){
        val tz = TimeZone.getTimeZone("Asia/Seoul")

        val gc = GregorianCalendar(tz)

        year = gc.get(GregorianCalendar.YEAR).toString()
        month = gc.get(GregorianCalendar.MONTH).toString()
        day = gc.get(GregorianCalendar.DATE).toString()
        hour= gc.get(GregorianCalendar.HOUR).toString()
        min = gc.get(GregorianCalendar.MINUTE).toString()
        sec = gc.get(GregorianCalendar.SECOND).toString()

        when(gc.get(GregorianCalendar.HOUR_OF_DAY)){
            in 6..18 -> main_back.setBackgroundResource(R.drawable.a5)
            else -> main_back.setBackgroundResource(R.drawable.a6)
        }
    }
    fun init(){
        var adapter = VPAdater(supportFragmentManager)
        vp.adapter = adapter
        img_btn.setTag(0)
        img_btn2.setTag(1)

    }
    fun buttonActive(){
        img_btn.setOnClickListener {
            vp.setCurrentItem(it.getTag() as Int)
            img_btn.visibility = View.GONE
            img_btn2.visibility = View.VISIBLE
        }
        img_btn2.setOnClickListener {
            vp.setCurrentItem(it.getTag() as Int)
            img_btn2.visibility = View.GONE
            img_btn.visibility = View.VISIBLE
        }
    }
}
