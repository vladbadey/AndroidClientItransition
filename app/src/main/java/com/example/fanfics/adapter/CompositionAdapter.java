package com.example.fanfics.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fanfics.R;
import com.example.fanfics.activity.CompositionDetails;
import com.example.fanfics.dto.request.CompositionRequestDto;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CompositionAdapter extends RecyclerView.Adapter<CompositionAdapter.ViewHolder> {

    private final Context context;

    private final List<CompositionRequestDto> list;

    public CompositionAdapter(Context context, List<CompositionRequestDto> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public CompositionAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CompositionAdapter.ViewHolder holder, int position) {
        CompositionRequestDto composition = list.get(position);
        Picasso.with(context).load(composition.getImage()).into(holder.image);
        holder.name.setText(composition.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView image;
        public TextView name;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            image = itemView.findViewById(R.id.iv_image);
            name = itemView.findViewById(R.id.tv_name);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            CompositionRequestDto composition = list.get(position);
            Log.i("vlad", "" + position);
            Log.i("vlad", composition.getName() + " " + context);
            Intent intent = new Intent(context, CompositionDetails.class);
            intent.putExtra("image", composition.getImage());
            intent.putExtra("name", composition.getName());
            intent.putExtra("description", composition.getDescription());

            context.startActivity(intent);
        }
    }
}