package com.example.demoapp.ui.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.demoapp.data.model.UserPicture;
import com.example.demoapp.di.api.ListAPI;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends ViewModel {

    @Inject
    public ListViewModel() {
    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MutableLiveData<List<UserPicture>> listLiveData = new MutableLiveData<>();

    @Inject
    ListAPI listAPI;

    void fetchList() {
        compositeDisposable.add(
            listAPI.getList(2, 20)
                    .subscribeOn(Schedulers.io())
                    .subscribe((userPictures, throwable) -> {
                        if (throwable == null) {
                            listLiveData.postValue(userPictures);
                        } else {
                            // TODO error handling
                            System.out.println(">>>>>>> "+throwable.getMessage());
                            listLiveData.postValue(null);
                        }
                    })
        );
    }

    public LiveData<List<UserPicture>> getListLiveData() {
        return listLiveData;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
