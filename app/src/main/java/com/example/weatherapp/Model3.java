package com.example.weatherapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Model3 {

    @SerializedName("result") Result result;
    @SerializedName("weather") weather weather;

    public weather getWeather(){return weather;}

    public  class Result{
        @SerializedName("message") String message;
        @SerializedName("code") String code;

        public String getMessage() {return message;}
        public String getCode() {return code;}
    }
    public class weather{
        public List<Forecast3hours> forecast3hours = new ArrayList<>();
        public List<Forecast3hours> getForecast3hours() {return forecast3hours;}

        public class Forecast3hours{
            @SerializedName("sky") Sky sky;
            @SerializedName("temperature") Temperature temperature;

            public Sky getSky(){return sky;}
            public Temperature getTemperature(){return temperature;}

            public class Sky{
                @SerializedName("code1hour") String code1hour;
                @SerializedName("code2hour") String code2hour;
                @SerializedName("code3hour") String code3hour;

                public String getCode1hour(){return code1hour;}
                public String getCode2hour(){return code2hour;}
                public String getCode3hour(){return code3hour;}

            }
            public class Temperature{
                @SerializedName("temp1hour") String temp1hour;
                @SerializedName("temp2hour") String temp2hour;
                @SerializedName("temp3hour") String temp3hour;

                public String getTemp1hour(){return temp1hour;}
                public String getTemp2hour(){return temp2hour;}
                public String getTemp3hour(){return temp3hour;}
            }

        }
    }

}
