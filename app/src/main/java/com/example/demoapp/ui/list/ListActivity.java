package com.example.demoapp.ui.list;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.demoapp.R;
import com.example.demoapp.data.model.UserPicture;
import com.example.demoapp.databinding.ActivityListBinding;
import com.example.demoapp.di.ViewModelFactory;
import com.example.demoapp.di.adapter.UserPhotoListAdapter;
import com.example.demoapp.di.api.ListAPI;
import com.example.demoapp.dialog.DescriptionDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;

public class ListActivity extends DaggerAppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    @Inject
    ViewModelFactory viewModelFactory;

    private ListViewModel viewModel;

    private ActivityListBinding binding;

    private UserPhotoListAdapter userPhotoListAdapter;

    private DescriptionDialog descriptionDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        descriptionDialog = new DescriptionDialog(this);

        viewModel = new ViewModelProvider(this, viewModelFactory).get(ListViewModel.class);
        initSwipeRefresh();
        initUserPhotoRecycler();
    }


    private void initSwipeRefresh() {
        binding.swipeContainer.setOnRefreshListener(this);
        binding.swipeContainer.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        binding.swipeContainer.post(() -> {


            // Fetching data from server
            if (isNetworkConnected()) {
                binding.swipeContainer.setRefreshing(true);
                viewModel.fetchList();
            } else {
                if (descriptionDialog.isShowing()) {
                    descriptionDialog.dismissLoader();
                }
                descriptionDialog.setAuthor("Alert");
                descriptionDialog.setTextDescription("Please check your internet connection");
                descriptionDialog.setSubmitButtonListener(v1 -> {
                    if (descriptionDialog.isShowing()) {
                        descriptionDialog.dismissLoader();
                    }
                });
                descriptionDialog.showDialog();
            }
        });
    }

    private void initUserPhotoRecycler() {
        binding.rvItems.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rvItems.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                linearLayoutManager.getOrientation());
        binding.rvItems.addItemDecoration(dividerItemDecoration);
        if (userPhotoListAdapter == null) {
            userPhotoListAdapter = new UserPhotoListAdapter(getBaseContext());
            binding.rvItems.setAdapter(userPhotoListAdapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getListLiveData().observe(this, userPictures -> {
            System.out.println(">>>>>>>" + userPictures);
            binding.swipeContainer.setRefreshing(false);
            if (userPictures != null && userPictures.size() > 0) {
                if (userPhotoListAdapter != null) {
                    userPhotoListAdapter.submitList((ArrayList<UserPicture>) userPictures);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
//        System.out.println(">>>>>>> onRefresh");
        if (isNetworkConnected()) {
            viewModel.fetchList();
        } else {
            binding.swipeContainer.setRefreshing(false);
            if (descriptionDialog.isShowing()) {
                descriptionDialog.dismissLoader();
            }
            descriptionDialog.setAuthor("Alert");
            descriptionDialog.setTextDescription("Please check your internet connection");
            descriptionDialog.setSubmitButtonListener(v1 -> {
                if (descriptionDialog.isShowing()) {
                    descriptionDialog.dismissLoader();
                }
            });
            descriptionDialog.showDialog();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
        }
        return true;
    }
}
