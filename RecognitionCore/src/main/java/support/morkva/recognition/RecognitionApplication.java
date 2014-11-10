package support.morkva.recognition;

import android.app.Application;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EApplication;

/**
 * Created by irikhmayer on 06.11.2014.
 */
@EApplication
public class RecognitionApplication extends Application {

    private static RecognitionApplication self;

    public static final RecognitionApplication get() {
        return self;
    }

    @Override public void onCreate() {
        super.onCreate();
        self = this;
    }

    @Background
    public void initPref() {

    }
}
