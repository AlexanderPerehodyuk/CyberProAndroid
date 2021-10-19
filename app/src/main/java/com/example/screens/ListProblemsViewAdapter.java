package com.example.screens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListProblemsViewAdapter extends ArrayAdapter<JSONObject> {

    int listLayout;
    ArrayList<JSONObject> usersList;
    Context context;

    public ListProblemsViewAdapter(Context context, int listLayout, int field, ArrayList<JSONObject> usersList) {
        super(context, listLayout, field, usersList);
        this.context = context;
        this.listLayout = listLayout;
        this.usersList = usersList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listViewItem = inflater.inflate(listLayout, null, false);
        TextView name = listViewItem.findViewById(R.id.textViewName);
//        TextView email = listViewItem.findViewById(R.id.textView2);
        try {
            name.setText(usersList.get(position).getString("name"));
//            email.setText(usersList.get(position).getString("email"));
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return listViewItem;
    }
}