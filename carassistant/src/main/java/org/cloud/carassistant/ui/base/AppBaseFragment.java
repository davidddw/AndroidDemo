package org.cloud.carassistant.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.classic.android.base.BaseFragment;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author d05660ddw
 * @version 1.0 2017/2/28
 */

public abstract class AppBaseFragment extends BaseFragment {

    protected Context mAppContext;
    private CompositeSubscription mCompositeSubscription;

    @Override
    public void initView(View parentView, Bundle savedInstanceState) {
        super.initView(parentView, savedInstanceState);
        ButterKnife.bind(this, parentView);
        mAppContext = mActivity.getApplicationContext();
    }

    protected void addSubscription(Subscription subscription) {
        if (null == mCompositeSubscription) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void unRegister() {
        if (null != mCompositeSubscription) {
            mCompositeSubscription.unsubscribe();
        }
    }
}
