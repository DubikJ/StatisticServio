package ua.com.servio.statisticservio.modules;


import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.com.servio.statisticservio.utils.NetworkUtils;

@Module
public class NetworkUtilsApiModule {

    private Application application;

    public NetworkUtilsApiModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public NetworkUtils getNetworkUtils() {
        return new NetworkUtils(application.getBaseContext());
    }
}
