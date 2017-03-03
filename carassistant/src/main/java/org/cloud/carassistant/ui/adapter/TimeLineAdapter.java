package org.cloud.carassistant.ui.adapter;

import android.content.Context;
import android.support.v4.graphics.ColorUtils;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;

import org.cloud.carassistant.R;
import org.cloud.carassistant.consts.Consts;
import org.cloud.carassistant.entity.ConsumerDetail;
import org.cloud.carassistant.utils.DateUtil;
import org.cloud.carassistant.utils.Util;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.functions.Action1;


/**
 * @author d05660ddw
 * @version 1.0 2017/3/2
 */
public class TimeLineAdapter extends CommonRecyclerAdapter<ConsumerDetail> implements
        Action1<List<ConsumerDetail>> {

    private static final int ALPHA = 100;

    private Context mContext;
    private int mLayoutResId;

    public TimeLineAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
        this.mContext = context;
        this.mLayoutResId = layoutResId;
    }

    @Override
    public void call(List<ConsumerDetail> consumerDetails) {
        consumerDetails.add(0, new ConsumerDetail());
        replaceAll(consumerDetails);
    }

    @Override
    public void onUpdate(BaseAdapterHelper helper, ConsumerDetail item, int position) {
        if (position == 0) return;
        CircleImageView civ = helper.getView(R.id.item_timeline_icon_bg);
        int color = mContext.getResources().getColor(Util.getColorByType(item.getType()));
        civ.setFillColor(color);
        civ.setBorderColor(ColorUtils.setAlphaComponent(color, ALPHA));
        helper.setText(R.id.item_timeline_time,
                DateUtil.formatDate(DateUtil.FORMAT_DATE, item.getConsumptionTime()) + "\n" +
                        DateUtil.formatDate(DateUtil.FORMAT_TIME, item.getConsumptionTime()))
                .setImageResource(R.id.item_timeline_icon, Util.getIconByType(item.getType()))
                .setText(R.id.item_timeline_content, Consts.TYPE_MENUS[item.getType()])
                .setText(R.id.item_timeline_money, Util.formatMoney(item.getMoney()))
                .setTextColorRes(R.id.item_timeline_content, Util.getColorByType(item.getType()))
                .setTextColorRes(R.id.item_timeline_money, Util.getColorByType(item.getType()));
    }

    @Override
    public int getLayoutResId(ConsumerDetail item, int position) {
        return position == 0 ? R.layout.item_header : mLayoutResId;
    }
}
