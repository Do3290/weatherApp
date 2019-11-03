package com.example.weatherapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MainWeatherModel2 {

    @SerializedName("result")
    Result result;
    @SerializedName("weather")
    weather weather;

    public weather getWeather(){return weather;}

    public  class Result{
        @SerializedName("message") String message;
        @SerializedName("code") String code;

        public String getMessage() {return message;}
        public String getCode() {return code;}
    }
    public class weather{
        public List<minutely> minutely = new ArrayList<>();
        public List<minutely> getMinutely() {return minutely;}
        public class minutely{
            @SerializedName("wind") Wind wind;
            @SerializedName("temperature") temperature temperature;
            @SerializedName("rain") Rain rain;
            @SerializedName("humidity") Double humidity;

            public Double getHumidity(){return this.humidity;}

            public Rain getRain(){return rain;}
            public Wind getWind(){return wind;}
            public temperature getTemperature(){return temperature;}

            public class Rain{
                @SerializedName("sinceMidnight") double sinceMidnight;

                public double getSinceMidnight(){return sinceMidnight;}
            }
            public class Wind{
                @SerializedName("wdir") Double wdir;
                @SerializedName("wspd") Double wspd;

                public Double getWdir(){return wdir;}
                public Double getWspd(){return wspd;}

            }
            public class temperature{
                @SerializedName("tc") Double tc;

                public Double getTc() {return tc;}

            }


        }
    }

}
