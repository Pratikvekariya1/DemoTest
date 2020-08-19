package com.example.demoapp.di.component;

import com.example.demoapp.DemoApp;
import com.example.demoapp.di.module.AndroidInjectBuilder;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidInjectBuilder.class, AndroidSupportInjectionModule.class})
public interface ApplicationComponent extends AndroidInjector<DemoApp> {

    @Override
    void inject(DemoApp instance);

    @Component.Factory
    interface Factory extends AndroidInjector.Factory<DemoApp> {

    }
}
