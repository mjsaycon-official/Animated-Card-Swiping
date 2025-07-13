package com.example.animated_card_swiping.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.animated_card_swiping.R;
import com.example.animated_card_swiping.databinding.ImageItemBinding;

import java.util.List;

public class ImageViewPagerAdapter extends RecyclerView.Adapter<ImageViewPagerAdapter.ViewPagerViewHolder> {

    private final List<Integer> imageUrlList;

    public ImageViewPagerAdapter(List<Integer> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    public static class ViewPagerViewHolder extends RecyclerView.ViewHolder {

        ImageItemBinding binding;

        public ViewPagerViewHolder(ImageItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(int imageUrl) {
            Glide.with(binding.getRoot().getContext())
                    .load(imageUrl)
                    .error(R.drawable.ic_baseline_error_outline_24)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.ivImage);
        }
    }

    @NonNull
    @Override
    public ViewPagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ImageItemBinding binding = ImageItemBinding.inflate(inflater, parent, false);
        return new ViewPagerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerViewHolder holder, int position) {
        holder.setData(imageUrlList.get(position));
    }

    @Override
    public int getItemCount() {
        return imageUrlList.size();
    }
}
