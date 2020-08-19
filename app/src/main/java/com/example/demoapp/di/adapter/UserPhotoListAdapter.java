package com.example.demoapp.di.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.demoapp.data.model.UserPicture;
import com.example.demoapp.databinding.ItemPhotoViewBinding;
import com.example.demoapp.dialog.DescriptionDialog;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import java.util.ArrayList;

public class UserPhotoListAdapter extends RecyclerView.Adapter<UserPhotoListAdapter.UserPhotoViewHolder> {
    private Lorem lorem;
    private ArrayList<UserPicture> userPictures;
    private final int PIC_DIM;
    private DescriptionDialog descriptionDialog;

    public UserPhotoListAdapter(Context context) {
        this.userPictures = new ArrayList<>();
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        PIC_DIM = Math.round(150 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @NonNull
    @Override
    public UserPhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPhotoViewBinding binding = ItemPhotoViewBinding.inflate(LayoutInflater.from(parent.getContext()));
        descriptionDialog = new DescriptionDialog(parent.getContext());
        return new UserPhotoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserPhotoViewHolder holder, int position) {
        if (userPictures.size() > 0) {
            holder.bind(userPictures.get(position));

            holder.itemPhotoViewBinding.getRoot().setOnClickListener(v -> {
                if (descriptionDialog.isShowing()) {
                    descriptionDialog.dismissLoader();
                }
                descriptionDialog.setAuthor("Author : \n" + userPictures.get(position).getAuthor());
                descriptionDialog.setTextDescription(holder.itemPhotoViewBinding.txtDescription.getText().toString());
                descriptionDialog.setSubmitButtonListener(v1 -> {
                    if (descriptionDialog.isShowing()) {
                        descriptionDialog.dismissLoader();
                    }
                });
                descriptionDialog.showDialog();
            });
        }
    }

    @Override
    public int getItemCount() {
        return userPictures != null ? userPictures.size() : 0;
    }

    public void submitList(ArrayList<UserPicture> chatNumberModels) {
        this.userPictures = chatNumberModels;
        notifyDataSetChanged();
    }

    public class UserPhotoViewHolder extends RecyclerView.ViewHolder {
        ItemPhotoViewBinding itemPhotoViewBinding;

        public UserPhotoViewHolder(ItemPhotoViewBinding binding) {
            super(binding.getRoot());
            itemPhotoViewBinding = binding;
        }

        public void bind(UserPicture userPicture) {
            lorem = LoremIpsum.getInstance();
            RequestOptions option = RequestOptions
                    .formatOf(DecodeFormat.PREFER_ARGB_8888)
                    .override(PIC_DIM, PIC_DIM);

            Glide.with(itemPhotoViewBinding.imgUserPic.getContext())
                    .load(userPicture.getDownloadUrl())
                    .apply(option)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(itemPhotoViewBinding.imgUserPic);
            itemPhotoViewBinding.txtDescription.setText("Description : \n" + lorem.getParagraphs(2, 4));
            itemPhotoViewBinding.txtAuthor.setText("Author : \n" + userPicture.getAuthor());
        }
    }
}

