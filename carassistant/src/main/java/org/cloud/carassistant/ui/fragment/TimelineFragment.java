package org.cloud.carassistant.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.classic.adapter.CommonRecyclerAdapter;

import org.cloud.carassistant.R;
import org.cloud.carassistant.app.CarApplication;
import org.cloud.carassistant.db.dao.ConsumerDao;
import org.cloud.carassistant.entity.ConsumerDetail;
import org.cloud.carassistant.ui.activity.MainActivity;
import org.cloud.carassistant.ui.adapter.TimeLineAdapter;
import org.cloud.carassistant.ui.base.AppBaseFragment;
import org.cloud.carassistant.utils.RxUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Subscription;

/**
 * @author d05660ddw
 * @version 1.0 2017/3/2
 */

public class TimelineFragment extends AppBaseFragment {

    @BindView(R.id.timeline_recycle_view)
    RecyclerView mRecycleView;

    @Inject
    ConsumerDao mConsumerDao;

    private TimeLineAdapter mAdapter;

    public static TimelineFragment newInstance() {
        return new TimelineFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_timeline;
    }

    @Override
    public void initView(View parentView, Bundle savedInstanceState) {
        ((CarApplication) mActivity.getApplicationContext()).getAppComponent().inject(this);
        super.initView(parentView, savedInstanceState);
        mRecycleView.setLayoutManager(new LinearLayoutManager(mAppContext));
        mRecycleView.addOnScrollListener(new CommonRecyclerAdapter.AbsScrollControl() {

            @Override
            public void onShow() {
                ((MainActivity)mActivity).onShow();
            }

            @Override
            public void onHide() {
                ((MainActivity)mActivity).onHide();
            }
        });
        mAdapter = new TimeLineAdapter(mAppContext, R.layout.item_timeline);
        mRecycleView.setAdapter(mAdapter);
        addSubscription(loadData());
    }

    private Subscription loadData() {
        return mConsumerDao.queryAll()
                .compose(RxUtil.<List<ConsumerDetail>>applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(mAdapter, RxUtil.ERROR_ACTION);
    }
}
