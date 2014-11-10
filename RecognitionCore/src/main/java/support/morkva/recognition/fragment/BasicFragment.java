package support.morkva.recognition.fragment;

import android.support.v4.app.Fragment;

import support.morkva.recognition.RecognitionApplication;

/**
 * Created by irikhmayer on 10.11.2014.
 */
public abstract class BasicFragment extends Fragment {

    protected RecognitionApplication getApp() {
        if (getActivity() == null) {
            throw new IllegalStateException("getActivity returned null");
        }
        return (RecognitionApplication)getActivity().getApplicationContext();
    }
}
