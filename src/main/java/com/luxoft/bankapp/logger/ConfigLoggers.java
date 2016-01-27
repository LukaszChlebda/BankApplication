package com.luxoft.bankapp.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Created by LChlebda on 2016-01-27.
 */
public class ConfigLoggers {
    public ConfigLoggers () {
        Logger l = Logger.getLogger("LogA");
        l = Logger.getLogger("LogB");

        try {
            l.addHandler(new FileHandler("LogA.log"));

            l.addHandler(new FileHandler ("LogB.log"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
