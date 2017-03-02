package org.cloud.carassistant.app;

import android.app.Application;

import org.cloud.carassistant.di.components.AppComponent;
import org.cloud.carassistant.di.components.DaggerAppComponent;
import org.cloud.carassistant.di.modules.AppModule;
import org.cloud.carassistant.di.modules.DbModule;

/**
 * @author d05660ddw
 * @version 1.0 2017/2/28
 */

public class CarApplication extends Application {
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dbModule(new DbModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
