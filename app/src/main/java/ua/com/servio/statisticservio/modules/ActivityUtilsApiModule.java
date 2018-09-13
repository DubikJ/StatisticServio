package ua.com.servio.statisticservio.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.com.servio.statisticservio.utils.ActivityUtils;

@Module
public class ActivityUtilsApiModule {

    public ActivityUtilsApiModule() {
    }

    @Provides
    @Singleton
    public ActivityUtils getActivityUtils() {
        return new ActivityUtils();
    }
}
