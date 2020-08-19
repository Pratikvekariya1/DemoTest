package com.example.demoapp.di.component;

import com.example.demoapp.DemoApp;
import com.example.demoapp.di.module.APIModule;
import com.example.demoapp.di.module.AndroidInjectBuilder;
import com.example.demoapp.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidInjectBuilder.class, AndroidSupportInjectionModule.class,
        APIModule.class, ViewModelModule.class})
public interface ApplicationComponent extends AndroidInjector<DemoApp> {

    @Override
    void inject(DemoApp instance);

    @Component.Factory
    interface Factory extends AndroidInjector.Factory<DemoApp> {

    }
}
