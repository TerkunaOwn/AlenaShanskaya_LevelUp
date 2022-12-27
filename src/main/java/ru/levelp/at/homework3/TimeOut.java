package ru.levelp.at.homework3;

import java.util.concurrent.TimeUnit;

public class TimeOut {

    public static void sleep(long timeout) {
        try {
            TimeUnit.MILLISECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
