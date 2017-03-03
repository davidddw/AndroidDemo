package org.cloud.carassistant.utils;

import java.util.List;
import java.util.Map;

/**
 * @author d05660ddw
 * @version 1.0 2017/3/3
 */

public class DataUtil {

    private DataUtil() { }

    public static boolean isEmpty(String string) {
        return (string == null || string.length() == 0 || string.trim().length() == 0);
    }

    public static <V> boolean isEmpty(List<V> sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }

    public static <K, V> boolean isEmpty(Map<K, V> sourceMap) {
        return (sourceMap == null || sourceMap.size() == 0);
    }
}
