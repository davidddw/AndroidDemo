package org.cloud.carassistant.utils;

import android.widget.EditText;

import org.cloud.carassistant.R;
import org.cloud.carassistant.consts.Consts;

import java.util.Locale;

/**
 * @author d05660ddw
 * @version 1.0 2017/3/1
 */

public class Util {

    public static String format(String format, Number number) {
        return String.format(Locale.CHINA, format, MoneyUtil.replace(number));
    }

    public static String formatMoney(float money){
        return format(Consts.FORMAT_MONEY, money);
    }

    public static int getBackgroundByType(int type){
        switch (type){
            case Consts.TYPE_FUEL:
                return R.drawable.bg_chartreuse_light;
            case Consts.TYPE_PARKING:
                return R.drawable.bg_blue_light;
            case Consts.TYPE_REPAIR:
                return R.drawable.bg_orange_light;
            case Consts.TYPE_ROAD_TOLL:
                return R.drawable.bg_saffron_light;
            case Consts.TYPE_PREMIUM:
                return R.drawable.bg_pale_red;
            case Consts.TYPE_MAINTENANCE:
                return R.drawable.bg_green_light;
            case Consts.TYPE_EXAMINATION:
                return R.drawable.bg_purple_light;
            case Consts.TYPE_TRAFFIC_VIOLATION:
                return R.drawable.bg_sienna_light;
            default:
                return R.drawable.bg_mediumorchid_light;
        }
    }

    public static int getColorByType(int type){
        switch (type){
            case Consts.TYPE_FUEL: //加油费
                return R.color.chartreuse_light;
            case Consts.TYPE_PARKING: //停车费
                return R.color.blue_light;
            case Consts.TYPE_REPAIR: //维修费
                return R.color.orange_light;
            case Consts.TYPE_ROAD_TOLL: //过路费
                return R.color.saffron_light;
            case Consts.TYPE_PREMIUM: //保险费
                return R.color.pale_red;
            case Consts.TYPE_MAINTENANCE: //汽车保养费
                return R.color.green_light;
            case Consts.TYPE_EXAMINATION: //汽车年审费
                return R.color.purple_light;
            case Consts.TYPE_TRAFFIC_VIOLATION: //交通违章罚款
                return R.color.sienna_light;
            default: //其它
                return R.color.mediumorchid_light;
        }
    }

    public static int getIconByType(int type){
        switch (type){
            case Consts.TYPE_FUEL:
                return R.drawable.ic_fuel;
            case Consts.TYPE_PARKING:
                return R.drawable.ic_parking;
            case Consts.TYPE_REPAIR:
                return R.drawable.ic_repair;
            case Consts.TYPE_ROAD_TOLL:
                return R.drawable.ic_road_toll;
            case Consts.TYPE_PREMIUM:
                return R.drawable.ic_premium;
            case Consts.TYPE_MAINTENANCE:
                return R.drawable.ic_maintenance;
            case Consts.TYPE_EXAMINATION:
                return R.drawable.ic_examination;
            case Consts.TYPE_TRAFFIC_VIOLATION:
                return R.drawable.ic_traffic_violation;
            default:
                return R.drawable.ic_other;
        }
    }

    public static void setText(EditText editText, String value){
        editText.setText(MoneyUtil.replace(value));
        editText.setSelection(editText.getText().length());
    }
    public static void setText(EditText editText, Number value){
        setText(editText, String.valueOf(value));
    }

    public static void setFocusable(EditText editText){
        editText.setFocusable(true);
        editText.requestFocus();
    }
}
