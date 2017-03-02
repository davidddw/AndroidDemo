package org.cloud.carassistant.di.modules;

import android.app.Application;

import org.cloud.carassistant.app.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author d05660ddw
 * @version 1.0 2017/2/28
 */
@Module(includes = {DbModule.class})
public class AppModule {
    private Application mApplication;

    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    RxBus provideRxBus() {
        return new RxBus();
    }
}
