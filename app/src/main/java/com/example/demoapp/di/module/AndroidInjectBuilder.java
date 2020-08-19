package com.example.demoapp.di.module;

import com.example.demoapp.ui.SplashActivity;
import com.example.demoapp.ui.list.ListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AndroidInjectBuilder {

    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector
    abstract ListActivity bindListActivity();

}
