package com.example.demoapp.di.module;

import androidx.lifecycle.ViewModel;

import com.example.demoapp.di.ViewModelFactory;


import java.util.Map;

import javax.inject.Provider;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModelFactoryModule {

    @Provides
    ViewModelFactory viewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
        return new ViewModelFactory(providerMap);
    }
}
