package org.cloud.carassistant.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author d05660ddw
 * @version 1.0 2017/2/28
 */

public class CloseUtil {
    private CloseUtil() { }

    public static void close(Closeable... params) {
        if (null != params) {
            try {
                for (Closeable closeable : params) {
                    closeable.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
