package com.eot.utils;

public class Wait {

    public static void waitFor(int durationInSec) {
        try {
            System.out.println(String.format("Sleep for %d sec", durationInSec));
            Thread.sleep(durationInSec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
