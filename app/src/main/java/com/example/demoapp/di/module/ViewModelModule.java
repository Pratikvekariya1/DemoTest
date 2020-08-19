package com.example.demoapp.di.module;

import androidx.lifecycle.ViewModel;


import com.example.demoapp.di.ViewModelKey;
import com.example.demoapp.ui.list.ListViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract public class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel.class)
    abstract ViewModel bindMainViewModel(ListViewModel listViewModel);
}
