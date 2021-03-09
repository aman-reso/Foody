package com.dev.foody;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements interfaces {
    List<Models> list;
    Context context;

    public RecyclerViewAdapter(List<Models> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String img_url = list.get(position).getImgUrl();
        String title = list.get(position).getTitle();
        holder.titleText.setText(title);
        Glide.with(context)
                .load(img_url)
                .centerCrop()
                .placeholder(R.drawable.place_holder)
                .into(holder.imageView);
    }

    public void setData(List<Models> list) {
        System.out.println("--" + list.size());
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public void RecyclerViewItemClick(int position) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cus_image);
            titleText = itemView.findViewById(R.id.cus_title_text);
        }
    }
}
