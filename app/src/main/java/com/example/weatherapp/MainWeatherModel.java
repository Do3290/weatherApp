package com.example.weatherapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MainWeatherModel {
    @SerializedName("result")
    Result result;
    @SerializedName("weather")
    weather weather;

    public weather getWeather() {
        return weather;
    }

    public class Result {
        @SerializedName("message")
        String message;
        @SerializedName("code")
        String code;

        public String getMessage() {
            return message;
        }

        public String getCode() {
            return code;
        }
    }

    public class weather {
        public List<summary> summary = new ArrayList<>();

        public List<summary> getSummary() {
            return summary;
        }

        public class summary {
            @SerializedName("yesterday")
            yesterday yesterday;
            @SerializedName("today")
            today today;
            @SerializedName("tomorrow")
            tomorrow tomorrow;
            @SerializedName("dayAfterTomorrow")
            dayAftertomorrow dayAftertomorrow;

            public yesterday getYesterday() {
                return yesterday;
            }

            public today getToday() {
                return today;
            }

            public tomorrow getTomorrow() {
                return tomorrow;
            }

            public dayAftertomorrow getDayAftertomorrow() {
                return dayAftertomorrow;
            }

            public class yesterday {
                @SerializedName("sky")
                sky sky1;
                @SerializedName("temperature")
                temperature temperature1;

                public sky getSky1() {
                    return sky1;
                }

                public temperature getTemperature1() {
                    return temperature1;
                }

                public class sky {
                    @SerializedName("name")
                    String name1;
                    @SerializedName("code")
                    String code1;

                    public String getName1() {
                        return name1;
                    }

                    public String getCode1() {
                        return code1;
                    }
                }

                public class temperature {
                    @SerializedName("tmax")
                    Double tmax1;
                    @SerializedName("tmin")
                    Double tmin1;

                    public Double getTmax1() {
                        return tmax1;
                    }

                    public Double getTmin1() {
                        return tmin1;
                    }
                }
            }

            public class today {
                @SerializedName("sky")
                sky sky2;
                @SerializedName("temperature")
                temperature temperature2;

                public sky getSky2() {
                    return sky2;
                }

                public temperature getTemperature2() {
                    return temperature2;
                }

                public class sky {
                    @SerializedName("name")
                    String name2;
                    @SerializedName("code")
                    String code2;

                    public String getName2() {
                        return name2;
                    }

                    public String getCode2() {
                        return code2;
                    }
                }

                public class temperature {
                    @SerializedName("tmax")
                    Double tmax2;
                    @SerializedName("tmin")
                    Double tmin2;

                    public Double getTmax2() {
                        return tmax2;
                    }

                    public Double getTmin2() {
                        return tmin2;
                    }
                }
            }

            public class tomorrow {
                @SerializedName("sky")
                sky sky3;
                @SerializedName("temperature")
                temperature temperature3;

                public sky getSky3() {
                    return sky3;
                }

                public temperature getTemperature3() {
                    return temperature3;
                }

                public class sky {
                    @SerializedName("name")
                    String name3;
                    @SerializedName("code")
                    String code3;

                    public String getName3() {
                        return name3;
                    }

                    public String getCode3() {
                        return code3;
                    }
                }

                public class temperature {
                    @SerializedName("tmax")
                    Double tmax3;
                    @SerializedName("tmin")
                    Double tmin3;

                    public Double getTmax3() {
                        return tmax3;
                    }

                    public Double getTmin3() {
                        return tmin3;
                    }
                }
            }

            public class dayAftertomorrow {
                @SerializedName("sky")
                sky sky4;
                @SerializedName("temperature")
                temperature temperature4;

                public sky getSky4() {
                    return sky4;
                }

                public temperature getTemperature4() {
                    return temperature4;
                }

                public class sky {
                    @SerializedName("name")
                    String name4;
                    @SerializedName("code")
                    String code4;

                    public String getName4() {
                        return name4;
                    }

                    public String getCode4() {
                        return code4;
                    }
                }

                public class temperature {
                    @SerializedName("tmax")
                    Double tmax4;
                    @SerializedName("tmin")
                    Double tmin4;

                    public Double getTmax4() {
                        return tmax4;
                    }

                    public Double getTmin4() {
                        return tmin4;
                    }
                }
            }


        }

    }
}



