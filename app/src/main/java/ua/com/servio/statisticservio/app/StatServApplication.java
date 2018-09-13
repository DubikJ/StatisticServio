package ua.com.servio.statisticservio.app;


import android.app.Application;

import ua.com.servio.statisticservio.component.DIComponent;
import ua.com.servio.statisticservio.component.DaggerDIComponent;
import ua.com.servio.statisticservio.modules.ActivityUtilsApiModule;
import ua.com.servio.statisticservio.modules.NetworkUtilsApiModule;

public class StatServApplication extends Application {

    DIComponent diComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        diComponent = DaggerDIComponent.builder()
                .networkUtilsApiModule(new NetworkUtilsApiModule(this))
                .activityUtilsApiModule(new ActivityUtilsApiModule())
                .build();
    }

    public DIComponent getComponent() {
        return diComponent;
    }
}
