package com.example.weatherapp;

import android.content.Context;
import com.firebase.jobdispatcher.*;

public class JobSchedulerStart {
    private static final int JOB_ID = 1111;

    public static void start(Context context) {

        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        Job myJob = dispatcher.newJobBuilder()
                .setService(NotificationJobFireBaseService.class) // 잡서비스 등록
                .setTag("TSLetterNotification")        // 태그 등록
                .setRecurring(false) //재활용
                .setLifetime(Lifetime.FOREVER) //다시켜도 작동을 시킬껀지?
                .setTrigger(Trigger.executionWindow(0, 60)) //트리거 시간
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .build();
        dispatcher.mustSchedule(myJob);
    }
    public static void stop(Context context) {
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        dispatcher.cancelAll();
    }
}