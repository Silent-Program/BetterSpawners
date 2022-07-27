package me.silentprogram.betterspawners.util;

public class NumberUtils {
    public static int getTime(long time) {
        long newTime = System.currentTimeMillis() - time;
        time /= 60000;
        return (int) newTime;
    }
}
