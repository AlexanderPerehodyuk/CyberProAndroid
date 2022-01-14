package com.example.screens;

import static com.example.screens.service.Service.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.AdapterView> {
    private final ArrayList<String> category, color, date, name, photo;

    public RecyclerAdapter(ArrayList<String> category, ArrayList<String> color, ArrayList<String> date, ArrayList<String> name, ArrayList<String> photo) {
        this.category = category;
        this.color = color;
        this.date = date;
        this.name = name;
        this.photo = photo;
    }

    @NonNull
    @Override
    public RecyclerAdapter.AdapterView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterView(LayoutInflater.from(activity).inflate(R.layout.row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterView holder, int i) {
        holder.problemText.setText(name.get(i));
        holder.typeText.setText(category.get(i));
    }

    @Override
    public int getItemCount() {
        return photo.size();
    }

    public static class AdapterView extends RecyclerView.ViewHolder {
        final TextView problemText, typeText, dateText, descText;;
        final ImageView imageView;

        public AdapterView(@NonNull View itemView) {
            super(itemView);

            problemText = activity.findViewById(R.id.textViewName);
            typeText = activity.findViewById(R.id.textViewType);
            dateText = activity.findViewById(R.id.textViewData);
            descText = activity.findViewById(R.id.textViewDescription);
            imageView = activity.findViewById(R.id.imageViewProblem);
        }
    }
}
