package com.example.screens;

import static com.example.screens.service.Service.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.Downsampler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.AdapterView> {
    private final ArrayList<String> category = new ArrayList<>();
    private final ArrayList<Integer> color = new ArrayList<>();
    private final ArrayList<String> date = new ArrayList<>();
    private final ArrayList<String> name = new ArrayList<>();
    private final ArrayList<Bitmap> photo = new ArrayList<>();
    private final int length;

    public RecyclerAdapter(JSONArray allProblems) throws Exception {
        for (int i = 0; i < allProblems.length(); i++) {
            JSONObject jsonObject = allProblems.getJSONObject(i);
            int clr = Color.YELLOW;

            switch (jsonObject.getString("color")) {
                case "green":
                    clr = Color.GREEN;
                    break;
                case "red":
                    clr = Color.RED;
                    break;
                case "grey":
                    clr = Color.GRAY;
                    break;
            }

            category.add(jsonObject.getString("category"));
            color.add(clr);
            date.add(jsonObject.getString("date"));
            name.add(jsonObject.getString("name"));

            photo.add(Glide.with(activity)
                    .asBitmap()
                    .load(Base64.decode(jsonObject.getString("photo"), Base64.DEFAULT))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .set(Downsampler.ALLOW_HARDWARE_CONFIG, true)
                    .submit()
                    .get());
        }

        length = category.size();
    }

    @NonNull
    @Override
    public AdapterView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterView(LayoutInflater.from(activity).inflate(R.layout.recycle_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterView holder, int i) {
        holder.problemText.setText(name.get(i));
        holder.typeText.setText(category.get(i));
        holder.typeText.setTextColor(color.get(i));
        holder.dateText.setText(date.get(i));
        holder.descText.setText(name.get(i));
        holder.imageView.setImageBitmap(photo.get(i));
    }

    @Override
    public int getItemCount() {
        return length;
    }

    public void dispose() {
        for (Bitmap bitmap : photo) {
            bitmap.recycle();
        }
    }

    protected static class AdapterView extends RecyclerView.ViewHolder {
        final TextView problemText, typeText, dateText, descText;
        final ImageView imageView;

        public AdapterView(@NonNull View itemView) {
            super(itemView);

            problemText = itemView.findViewById(R.id.textViewName);
            typeText = itemView.findViewById(R.id.textViewType);
            dateText = itemView.findViewById(R.id.textViewData);
            descText = itemView.findViewById(R.id.textViewDescription);
            imageView = itemView.findViewById(R.id.imageViewProblem);
        }
    }
}
