package com.kye.utils;


import com.kye.pda.biz.common.utils.BuildConfig;

public class AKUtils {
    public static int getGBS(int x, int y) {
        for (int i = 1; i <= x * y; i++) {
            if (i % x == 0 && i % y == 0)
                return i;
        }
        return x * y;
    }

    public static StringBuilder getAk1() {
        StringBuilder sb = new StringBuilder();
        sb.append(getGBS(2, 5));
        return sb;
    }

    public static String getAk() {
        return getAk1().append("1").append(BuildConfig.key_release_part2).toString();
    }
}
