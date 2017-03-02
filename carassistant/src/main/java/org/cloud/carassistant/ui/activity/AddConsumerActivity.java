package org.cloud.carassistant.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.cloud.carassistant.R;
import org.cloud.carassistant.app.CarApplication;
import org.cloud.carassistant.consts.Consts;
import org.cloud.carassistant.db.dao.ConsumerDao;
import org.cloud.carassistant.entity.ConsumerDetail;
import org.cloud.carassistant.ui.base.AppBaseActivity;
import org.cloud.carassistant.ui.fragment.DatePickerFragment;
import org.cloud.carassistant.utils.DateUtil;
import org.cloud.carassistant.utils.ToastUtil;
import org.cloud.carassistant.utils.Util;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import rx.functions.Action1;

public class AddConsumerActivity extends AppBaseActivity implements Toolbar
        .OnMenuItemClickListener, MaterialSpinner.OnItemSelectedListener, DatePickerFragment
        .Callback {

    public static final int TYPE_ADD = 0;
    public static final int TYPE_MODIFY = 1;
    public static final String PARAMS_TYPE = "type";
    public static final String TAG = "dataPicker";
    public static final String PARAMS_CONSUMER = "consumerDetail";
    public static final String EMPTY = "";

    @BindView(R.id.add_consumer_fuel_layout)
    View mFuelLayout;
    @BindView(R.id.add_consumer_spinner)
    MaterialSpinner mSpinner;
    @BindView(R.id.add_consumer_fuel_spinner)
    MaterialSpinner mFuelSpinner;
    @BindView(R.id.add_consumer_time)
    TextView mConsumerTime;
    @BindView(R.id.add_consumer_money)
    MaterialEditText mConsumerMoney;
    @BindView(R.id.add_consumer_unit_price)
    MaterialEditText mConsumerUnitPrice;
    @BindView(R.id.add_consumer_current_mileage)
    MaterialEditText mConsumerCurrentMileage;
    @BindView(R.id.add_consumer_notes)
    MaterialEditText mConsumerNotes;

    @Inject
    ConsumerDao mConsumerDao;
    private DatePickerFragment mDatePickerFragment;
    private Calendar mSelectCalendar;
    private Integer mType;
    private ConsumerDetail mConsumerDetail;

    public static void start(Activity activity, int type, ConsumerDetail consumerDetail) {
        Intent intent = new Intent(activity, AddConsumerActivity.class);
        intent.putExtra(PARAMS_TYPE, type);
        if (null != consumerDetail) {
            intent.putExtra(PARAMS_CONSUMER, consumerDetail);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && activity.isInMultiWindowMode()) {
            intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT);
        }
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_add_consumer;
    }

    @Override
    protected boolean canBack() {
        return true;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        ((CarApplication) getApplicationContext()).getAppComponent().inject(this);
        super.initView(savedInstanceState);

        if (getIntent().hasExtra(PARAMS_TYPE)) {
            mType = getIntent().getIntExtra(PARAMS_TYPE, TYPE_ADD);
        }
        if (getIntent().hasExtra(PARAMS_CONSUMER)) {
            mConsumerDetail = (ConsumerDetail) getIntent().getSerializableExtra(PARAMS_CONSUMER);
        }
        if (null == mType || (mType == TYPE_MODIFY && null == mConsumerDetail)) {
            finish();
            return;
        }

        getToolbar().setOnMenuItemClickListener(this);
        setTitle(mType == TYPE_ADD ? R.string.add_consumer_title : R.string.modify_consumer_title);
        mSpinner.setItems(Consts.TYPE_MENUS);
        mFuelSpinner.setItems(Consts.FUEL_MENUS);
        mSpinner.setOnItemSelectedListener(this);
        addSubscription(RxView.clicks(mConsumerTime).throttleFirst(Consts.SHIELD_TIME, TimeUnit
                .SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                showDatePickerFragment();
            }
        }));
        initValues();
    }

    private void initValues() {
        if (mType == TYPE_ADD) {
            mSpinner.setSelectedIndex(Consts.TYPE_FUEL);
            mFuelSpinner.setSelectedIndex(Consts.FUEL_GASOLINE_92);
        } else if (mType == TYPE_MODIFY) {
            mSelectCalendar = Calendar.getInstance();
            mSelectCalendar.setTimeInMillis(mConsumerDetail.getConsumptionTime());
            mSpinner.setSelectedIndex(mConsumerDetail.getType());
            mConsumerTime.setText(DateUtil.formatDate(DateUtil.FORMAT_DATE, mConsumerDetail
                    .getConsumptionTime()));
            Util.setText(mConsumerMoney, mConsumerDetail.getMoney());
            mConsumerNotes.setText(TextUtils.isEmpty(mConsumerDetail.getNotes()) ? EMPTY :
                    mConsumerDetail.getNotes());
            setFuelViews(mConsumerDetail.getType() == Consts.TYPE_FUEL ? View.VISIBLE : View.GONE);
            if (mConsumerDetail.getType() == Consts.TYPE_FUEL) {
                mFuelSpinner.setSelectedIndex(mConsumerDetail.getOilType());
                Util.setText(mConsumerUnitPrice, mConsumerDetail.getUnitPrice());
                Util.setText(mConsumerCurrentMileage, mConsumerDetail.getCurrentMileage());
            }

        }
    }

    private void setFuelViews(int visibility) {
        mConsumerUnitPrice.setVisibility(visibility);
        mConsumerCurrentMileage.setVisibility(visibility);
        mFuelLayout.setVisibility(visibility);
    }

    private void showDatePickerFragment() {
        if (null == mDatePickerFragment) {
            mDatePickerFragment = DatePickerFragment.newInstance();
            mDatePickerFragment.setCallback(this);
        }
        mDatePickerFragment.show(getSupportFragmentManager(), TAG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(mType == TYPE_ADD ? R.menu.add_menu : R.menu.modify_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            addConsumer();
        } else if (item.getItemId() == R.id.action_modify) {
            modifyConsumer();
        }
        return true;
    }

    private void reset() {
        mSpinner.setSelectedIndex(Consts.TYPE_FUEL);
        mFuelSpinner.setSelectedIndex(Consts.FUEL_GASOLINE_92);
        mConsumerMoney.setText(EMPTY);
        mConsumerUnitPrice.setText(EMPTY);
        mConsumerCurrentMileage.setText(EMPTY);
        mConsumerNotes.setText(EMPTY);
        mConsumerTime.setText(R.string.consumer_select_time_hint);
        setFuelViews(View.VISIBLE);
        Util.setFocusable(mConsumerMoney);
        mSelectCalendar = null;
    }

    private void modifyConsumer() {
        if (!verifyData()) {
            return;
        }
        if (mConsumerDao.update(mConsumerDetail) > 0) {
            ToastUtil.showToast(mAppContext, R.string.modify_consumer_success);
            finish();
        } else {
            ToastUtil.showToast(mAppContext, R.string.modify_consumer_fail);
        }
    }

    private void addConsumer() {
        if (!verifyData()) {
            return;
        }
        if (mConsumerDao.insert(mConsumerDetail) > 0) {
            ToastUtil.showToast(mAppContext, R.string.add_consumer_success);
            reset();
        } else {
            ToastUtil.showToast(mAppContext, R.string.add_consumer_fail);
        }
    }

    private boolean verifyData() {
        if (mType == TYPE_ADD) {
            mConsumerDetail = new ConsumerDetail();
        }
        mConsumerDetail.setType(mSpinner.getSelectedIndex());
        final String money = mConsumerMoney.getText().toString().trim();
        if (TextUtils.isEmpty(money)) {
            ToastUtil.showToast(mAppContext, R.string.consumer_money_hint);
            Util.setFocusable(mConsumerMoney);
            return false;
        }
        mConsumerDetail.setMoney(Float.valueOf(money));
        if (null == mSelectCalendar) {
            ToastUtil.showToast(mAppContext, R.string.consumer_select_time_hint);
            return false;
        }
        mConsumerDetail.setConsumptionTime(mSelectCalendar.getTimeInMillis());
        if (mSpinner.getSelectedIndex() == Consts.TYPE_FUEL) {
            final String unitPrice = mConsumerUnitPrice.getText().toString().trim();
            if (TextUtils.isEmpty(unitPrice)) {
                ToastUtil.showToast(mAppContext, R.string.consumer_unit_price_hint);
                Util.setFocusable(mConsumerUnitPrice);
                return false;
            }
            final String currentMileage = mConsumerCurrentMileage.getText().toString().trim();
            if (TextUtils.isEmpty(currentMileage)) {
                ToastUtil.showToast(mAppContext, R.string.consumer_current_mileage_hint);
                Util.setFocusable(mConsumerCurrentMileage);
                return false;
            }
            mConsumerDetail.setOilType(mFuelSpinner.getSelectedIndex());
            mConsumerDetail.setUnitPrice(Float.valueOf(unitPrice));
            mConsumerDetail.setCurrentMileage(Long.valueOf(currentMileage));
        }
        final String notes = mConsumerNotes.getText().toString().trim();
        if (!TextUtils.isEmpty(notes)) {
            mConsumerDetail.setNotes(notes);
        }
        return true;
    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        final int visibility = position == Consts.TYPE_FUEL ? View.VISIBLE : View.GONE;
        setFuelViews(visibility);
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onFinish(Calendar calendar) {
        this.mSelectCalendar = calendar;
        mConsumerTime.setText(DateUtil.formatDate(DateUtil.FORMAT_DATE, calendar.getTimeInMillis()));
    }
}
