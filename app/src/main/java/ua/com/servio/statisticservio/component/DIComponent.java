package ua.com.servio.statisticservio.component;
import javax.inject.Singleton;

import dagger.Component;
import ua.com.servio.statisticservio.activity.BasicActivity;
import ua.com.servio.statisticservio.activity.BootAct;
import ua.com.servio.statisticservio.modules.ActivityUtilsApiModule;
import ua.com.servio.statisticservio.modules.NetworkUtilsApiModule;

@Singleton
@Component(modules = {NetworkUtilsApiModule.class, ActivityUtilsApiModule.class})
public interface DIComponent {
    void inject(BootAct bootActivity);
    void inject(BasicActivity basicActivity);
}
