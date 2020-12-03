package guice;

import com.google.inject.AbstractModule;
import service.FxmlLoaderService;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(FxmlLoaderService.class).asEagerSingleton();
    }
}
