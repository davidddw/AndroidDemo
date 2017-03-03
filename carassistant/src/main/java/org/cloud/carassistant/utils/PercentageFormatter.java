package org.cloud.carassistant.utils;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * @author d05660ddw
 * @version 1.0 2017/3/3
 */

class PercentageFormatter implements IValueFormatter {
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler
            viewPortHandler) {
        return Util.formatPercentage(value);
    }
}
