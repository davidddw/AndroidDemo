package org.cloud.carassistant.utils;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * @author d05660ddw
 * @version 1.0 2017/3/3
 */

class OilMessFormatter implements IValueFormatter {

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler
            viewPortHandler) {
        return format(value);
    }

    private String format(float value) {
        return MoneyUtil.replace(MoneyUtil.newInstance(value).round(2).create());
    }

}
