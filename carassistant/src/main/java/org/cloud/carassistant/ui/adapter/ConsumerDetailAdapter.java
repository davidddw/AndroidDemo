package org.cloud.carassistant.ui.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;

import org.cloud.carassistant.R;
import org.cloud.carassistant.consts.Consts;
import org.cloud.carassistant.entity.ConsumerDetail;
import org.cloud.carassistant.utils.DateUtil;
import org.cloud.carassistant.utils.Util;

import java.util.List;

import rx.functions.Action1;

/**
 * @author d05660ddw
 * @version 1.0 2017/3/1
 */

public class ConsumerDetailAdapter extends CommonRecyclerAdapter<ConsumerDetail> implements
        Action1<List<ConsumerDetail>> {

    private int mLayoutResId;

    public ConsumerDetailAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
        this.mLayoutResId = layoutResId;
    }

    @Override
    public void onUpdate(BaseAdapterHelper helper, ConsumerDetail item, int position) {
        if (0 == position) return;
        boolean isNoteEmpty = TextUtils.isEmpty(item.getNotes());
        helper.setText(R.id.item_consumer_detail_money, Util.formatMoney(item.getMoney()))
                .setText(R.id.item_consumer_detail_tag, Consts.TYPE_MENUS[item.getType()])
                .setText(R.id.item_consumer_detail_time, DateUtil.formatDate(DateUtil
                        .FORMAT_DATE, item.getConsumptionTime()))
                .setText(R.id.item_consumer_detail_notes, item.getNotes())
                .setBackgroundRes(R.id.item_consumer_detail_top_layout, Util.getBackgroundByType
                        (item.getType()))
                .setTextColorRes(R.id.item_consumer_detail_tag, Util.getColorByType(item.getType()))
                .setImageResource(R.id.item_consumer_detail_icon, Util.getIconByType(item.getType
                        ()))
                .setVisible(R.id.item_consumer_detail_notes, !isNoteEmpty)
                .setVisible(R.id.item_consumer_detail_notes_icon, !isNoteEmpty);
    }

    @Override
    public int getLayoutResId(ConsumerDetail item, int position) {
        return position == 0 ? R.layout.item_header : mLayoutResId;
    }

    @Override
    public void call(List<ConsumerDetail> consumerDetails) {
        consumerDetails.add(0, new ConsumerDetail());
        replaceAll(consumerDetails);
    }
}
