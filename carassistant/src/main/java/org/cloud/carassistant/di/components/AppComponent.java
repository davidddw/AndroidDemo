package org.cloud.carassistant.di.components;

import org.cloud.carassistant.di.modules.AppModule;
import org.cloud.carassistant.ui.activity.AddConsumerActivity;
import org.cloud.carassistant.ui.fragment.ChartFragment;
import org.cloud.carassistant.ui.fragment.MainFragment;
import org.cloud.carassistant.ui.fragment.TimelineFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author d05660ddw
 * @version 1.0 2017/2/28
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(MainFragment fragment);
    void inject(AddConsumerActivity activity);
    void inject(TimelineFragment timelineFragment);
    void inject(ChartFragment chartFragment);
}
