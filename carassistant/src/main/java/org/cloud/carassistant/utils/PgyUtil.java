package org.cloud.carassistant.utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.classic.android.consts.MIME;
import com.classic.android.utils.SDCardUtil;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.feedback.PgyFeedback;
import com.pgyersdk.feedback.PgyFeedbackShakeManager;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.pgyersdk.views.PgyerDialog;

import org.cloud.carassistant.R;
import org.cloud.carassistant.consts.Consts;

import java.lang.ref.WeakReference;

/**
 * @author d05660ddw
 * @version 1.0 2017/3/2
 */

public class PgyUtil {

    public static void checkUpdate(Activity activity, final boolean showHint) {
        if (!isNetworkAvailable(activity.getApplicationContext())) {
            if (showHint) {
                ToastUtil.showToast(activity.getApplicationContext(), R.string.network_error);
            }
            return;
        }
        final WeakReference<Activity> reference = new WeakReference<Activity>(activity);
        PgyUpdateManager.register(activity, new UpdateManagerListener() {
            @Override
            public void onNoUpdateAvailable() {
                final Activity act = reference.get();
                if (null != act && showHint) {
                    ToastUtil.showToast(act.getApplicationContext(), R.string.no_update);
                }
            }

            @Override
            public void onUpdateAvailable(String s) {
                Activity act = reference.get();
                if (null == act) return;
                final AppBean appBean = getAppBeanFromString(s);
                new MaterialDialog.Builder(act).title(R.string.update_dialog_title)
                        .titleColorRes(R.color.primary_text)
                        .backgroundColorRes(R.color.white)
                        .content(appBean.getReleaseNote())
                        .contentColorRes(R.color.primary_light)
                        .positiveText(R.string.update)
                        .negativeText(R.string.cancel)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull
                                    DialogAction which) {
                                Activity act = reference.get();
                                if (null != act) {
                                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                                        startDownloadTask(act, appBean.getDownloadURL());
                                    } else {
                                        download(act.getApplicationContext(), appBean
                                                .getDownloadURL());
                                    }
                                }
                            }
                        });
            }
        });
    }

    private static long download(Context context, String downloadURL) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context
                .DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadURL));
        request.setTitle(context.getString(R.string.update_dialog_title));
        request.setMimeType(MIME.APK);
        request.setDestinationInExternalPublicDir(SDCardUtil.getApkDirPath(), Consts.APK_NAME);
        request.setNotificationVisibility(DownloadManager.Request
                .VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        return downloadManager.enqueue(request);
    }

    private static boolean isNetworkAvailable(@NonNull Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = cm.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network network : networks) {
                networkInfo = cm.getNetworkInfo(network);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            if (null != cm) {
                //noinspection deprecation
                NetworkInfo[] info = cm.getAllNetworkInfo();
                if (null != info) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void register(Context context) {
        PgyCrashManager.register(context);
    }

    public static void destroy() {
        try {
            PgyUpdateManager.unregister();
            PgyCrashManager.unregister();
            PgyFeedbackShakeManager.unregister();
            PgyFeedback.getInstance().destroy();
        } catch (Exception e) {
        }
    }

    public static void setDialogStyle(String s, String s1) {
        PgyerDialog.setDialogTitleBackgroundColor(s);
        PgyerDialog.setDialogTitleTextColor(s1);
    }

    public static void feedback(Activity activity) {
        WeakReference<Activity> reference = new WeakReference<>(activity);
        Activity act = reference.get();
        try {
            if (null != act) {
                PgyFeedback.getInstance().showDialog(act);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
