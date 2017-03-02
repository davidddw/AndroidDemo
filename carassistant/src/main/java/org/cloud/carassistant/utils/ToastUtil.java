package org.cloud.carassistant.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

/**
 * @author d05660ddw
 * @version 1.0 2017/3/1
 */

public class ToastUtil {
    private ToastUtil() { }

    public static void showToast(@NonNull Context context, @NonNull String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(@NonNull Context context, int resId) {
        Toast.makeText(context, context.getResources().getText(resId), Toast.LENGTH_SHORT).show();
    }
}
