package com.mayer.solution;

import com.mayer.solution.processor.OCROperator;
import com.mayer.solution.processor.SnapshotOperator;

/**
 * Created by dot on 09.11.2014.
 * Singleton
 * Mediator
 * Proxy
 */
public class Controller {

    private Controller() {}

    public static Controller get() {
        return HOLDER.instance;
    }

    public SnapshotOperator getNewOperator() {

        return new OCROperator();
    }

    private static final class HOLDER {
        private static Controller instance = new Controller();
    }
}
