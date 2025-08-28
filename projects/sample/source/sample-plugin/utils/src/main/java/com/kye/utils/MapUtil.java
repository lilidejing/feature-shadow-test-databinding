package com.kye.utils;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class MapUtil {
    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<String, String>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }
}


/**
 * 比较器类
 */
class MapKeyComparator implements Comparator<String> {

    @Override
    public int compare(String str1, String str2) {
        if (str1.length() < str2.length()) {
            return -1;
        } else if (str1.length() == str2.length()) {
            return str1.compareTo(str2);
        } else {
            return 1;
        }
    }
}

