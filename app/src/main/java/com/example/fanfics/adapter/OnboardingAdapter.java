package com.example.fanfics.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fanfics.R;
import com.example.fanfics.model.Fandom;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.ViewHolder> {

    private final Context context;

    private final List<Fandom> list;

    private static final ArrayList<Boolean> status = new ArrayList<>();

    public OnboardingAdapter(Context context, List<Fandom> list) {
        this.context = context;
        this.list = list;
        for (int i = 0; i < list.size(); i++) {
            status.add(false);
        }
    }

    public static ArrayList<Boolean> getStatus() {
        return status;
    }

    @NonNull
    @NotNull
    @Override
    public OnboardingAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.onboarding_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OnboardingAdapter.ViewHolder holder, int position) {
        Fandom fandom = list.get(position);
        Picasso.with(context).load(fandom.getImage()).into(holder.image);
        holder.name.setText(fandom.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView name;
        public CheckBox checkBox;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.iv_onboarding_image);
            name = itemView.findViewById(R.id.tv_onboarding_name);
            checkBox = itemView.findViewById(R.id.cb_onboarding);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        status.set(getAdapterPosition(), true);
                    } else {
                        status.set(getAdapterPosition(), false);
                    }
                }
            });
        }
    }
}