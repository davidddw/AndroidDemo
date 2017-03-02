package org.cloud.carassistant.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.classic.adapter.CommonRecyclerAdapter;

import org.cloud.carassistant.R;
import org.cloud.carassistant.app.CarApplication;
import org.cloud.carassistant.db.dao.ConsumerDao;
import org.cloud.carassistant.entity.ConsumerDetail;
import org.cloud.carassistant.ui.activity.AddConsumerActivity;
import org.cloud.carassistant.ui.activity.MainActivity;
import org.cloud.carassistant.ui.adapter.ConsumerDetailAdapter;
import org.cloud.carassistant.ui.base.AppBaseFragment;
import org.cloud.carassistant.utils.RxUtil;
import org.cloud.carassistant.utils.ToastUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * @author d05660ddw
 * @version 1.0 2017/2/28
 */

public class MainFragment extends AppBaseFragment implements CommonRecyclerAdapter
        .OnItemClickListener, CommonRecyclerAdapter.OnItemLongClickListener {

    @BindView(R.id.main_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.main_fab)
    FloatingActionButton mFab;
    @Inject
    ConsumerDao mConsumerDao;

    private ConsumerDetailAdapter mConsumerDetailAdapter;
    private int mFabOffset;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main;
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void initView(View parentView, Bundle savedInstanceState) {
        ((CarApplication) mActivity.getApplicationContext()).getAppComponent().inject(this);
        super.initView(parentView, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mAppContext));
        mConsumerDetailAdapter = new ConsumerDetailAdapter(mAppContext, R.layout
                .item_consumer_detail);
        mRecyclerView.setAdapter(mConsumerDetailAdapter);
        mConsumerDetailAdapter.setOnItemClickListener(this);
        mConsumerDetailAdapter.setOnItemLongClickListener(this);
        mRecyclerView.addOnScrollListener(new CommonRecyclerAdapter.AbsScrollControl() {
            @Override
            public void onShow() {
                mFab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2))
                        .start();
                ((MainActivity) mActivity).onShow();
            }

            @Override
            public void onHide() {
                if (mFabOffset == 0) {
                    mFabOffset = mFab.getHeight() + mFab.getBottom();
                }
                mFab.animate().translationY(mFabOffset).setInterpolator(new
                        AccelerateInterpolator(2));
                ((MainActivity) mActivity).onHide();
            }
        });
        addSubscription(loadData());
    }

    private Subscription loadData() {
        return mConsumerDao.queryAll()
                .compose(RxUtil.<List<ConsumerDetail>>applySchedulers(RxUtil.IO_ON_UI_TRANSFORMER))
                .subscribe(mConsumerDetailAdapter, RxUtil.ERROR_ACTION);
    }

    @OnClick(R.id.main_fab)
    public void onFabClick() {
        AddConsumerActivity.start(mActivity, AddConsumerActivity.TYPE_ADD, null);
    }


    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, View view, int position) {
        AddConsumerActivity.start(mActivity, AddConsumerActivity.TYPE_MODIFY,
                mConsumerDetailAdapter.getItem(position));
    }

    @Override
    public void onItemLongClick(RecyclerView.ViewHolder viewHolder, View view, final int position) {
        new MaterialDialog.Builder(mActivity).backgroundColorRes(R.color.white)
                .content(R.string.delete_dialog_content)
                .contentColorRes(R.color.primary_light)
                .positiveText(R.string.confirm)
                .negativeText(R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        int rows = mConsumerDao.delete(mConsumerDetailAdapter.getItem
                                (position).getId());
                        ToastUtil.showToast(mAppContext,
                                rows > 0 ? R.string.delete_success : R.string.delete_fail);
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
