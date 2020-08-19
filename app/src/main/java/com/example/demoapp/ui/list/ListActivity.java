package com.example.demoapp.ui.list;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.demoapp.data.model.UserPicture;
import com.example.demoapp.di.api.ListAPI;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;

public class ListActivity extends DaggerAppCompatActivity {
    @Inject
    ListAPI listAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listAPI.getList(2, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<List<UserPicture>, Throwable>() {
                    @Override
                    public void accept(List<UserPicture> userPictures, Throwable throwable) throws Exception {

                    }
                });
    }
}
