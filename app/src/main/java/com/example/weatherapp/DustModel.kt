package com.example.weatherapp

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class DustModel {
    @SerializedName("list")
    var list: List<Item> = ArrayList<Item>()

    inner class Item {
        @SerializedName("pm10Grade1h")
        var pm10Grade1h: String? = null
            internal set
        @SerializedName("pm25Grade1h")
        var pm25Grade1h: String? = null
            internal set
        @SerializedName("pm10Value")
        var pm10Value: String? = null
            internal set
        @SerializedName("pm25Value")
        var pm25Value: String? = null
            internal set
        @SerializedName("o3Value")
        var o3Value: String? = null
            internal set
        @SerializedName("no2Value")
        var no2Value: String? = null
            internal set
    }
}