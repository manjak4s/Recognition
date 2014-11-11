package com.mayer.recognition.util;

import android.os.Bundle;
import android.util.Log;

import com.mayer.recognition.BuildConfig;


/**
 * Created by irikhmayer on 10.11.2014.
 */
public class Logger {

    private static ILogger LOGGER = BuildConfig.DEBUG ? new ConsoleLogger() : new ErrorLogger();

    public static void d(String msg){
        LOGGER.d(msg);
    }

    public static void d(String msg, Object...args){
        if(args != null && args.length > 0){
            LOGGER.d(String.format(msg, args));
        }else{
            LOGGER.d(msg);
        }
    }

    public static void e(String msg, Throwable t){
        LOGGER.e(msg, t);
    }

    public static void e(String msg){
        LOGGER.e(msg);
    }

    public static void d(String msg, Bundle bundle) {
        for (String key : bundle.keySet()) {
            LOGGER.d (String.format("%s : key %s holds %s", msg, key, bundle.get(key).toString()));
        }
    }


    private static interface ILogger{
        void d(String msg);

        void d(String msg, Object...args);

        void e(String msg, Throwable t);

        void e(String msg);

        void d(String msg, Bundle bundle);
    }

    private static class ConsoleLogger implements ILogger{

        private static final String TAG = "Recognition";

        @Override
        public void d(String msg){
            Log.d(TAG, msg);
        }

        @Override
        public void d(String msg, Object...args){
            if(args != null && args.length > 0){
                Log.d(TAG, String.format(msg, args));
            }else{
                Log.d(TAG, msg);
            }
        }

        @Override
        public void e(String msg, Throwable t){
            Log.e(TAG, msg, t);
        }

        @Override
        public void e(String msg){
            Log.e(TAG, msg);
        }

        @Override
        public void d(String msg, Bundle bundle) {
            for (String key : bundle.keySet()) {
                Log.d (TAG, String.format("%s : key %s holds %s", msg, key, bundle.get(key).toString()));
            }
        }
    }

    private static class ErrorLogger implements ILogger {

        private static final String TAG = "Recognition";

        @Override
        public void d(String msg) {}

        @Override
        public void d(String msg, Object... args) {}

        @Override
        public void d(String msg, Bundle bundle) {}

        @Override
        public void e(String msg, Throwable t){
            Log.e(TAG, msg, t);
        }

        @Override
        public void e(String msg){
            Log.e(TAG, msg);
        }

    }
}
