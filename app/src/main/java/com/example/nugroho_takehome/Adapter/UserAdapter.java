package com.example.nugroho_takehome.Adapter;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nugroho_takehome.Model.User;
import com.example.nugroho_takehome.R;
import com.jackandphantom.circularimageview.RoundedImage;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<User> list_item;

    public UserAdapter(List<User> list, Context context) {
        this.context = context;
        list_item = list;
    }

    public UserAdapter(Context context) {
        this.context = context;
        list_item = new ArrayList<>();
    }

    public List<User> getList_item() {
        return list_item;
    }


    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user, parent, false);
        final UserAdapter.ViewHolder holder = new UserAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        final User user = list_item.get(holder.getAdapterPosition());
        holder.tvnama.setText(list_item.get(position).getLogin());

        Glide.with(context)
                .load(user.getAvatar_url())
                .into(holder.civuser);
    }

    @Override
    public int getItemCount() {
        return list_item.size();
    }

    public void add(User cuti) {
        list_item.add(cuti);
        notifyItemInserted(list_item.size() - 1);
    }

    public void addAll(List<User> moveResult) {
        for (User result : moveResult) {
            add(result);
        }
    }

    private void remove(User user) {
        int position = list_item.indexOf(user);
        if (position > -1) {
            list_item.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public void clearAll() {
        if (!list_item.isEmpty()) {
            list_item.clear();
            notifyDataSetChanged();
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    private User getItem(int position) {
        if (list_item != null) {
            return list_item.get(position);
        }
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RoundedImage civuser;
        private TextView tvnama;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            civuser = itemView.findViewById(R.id.civuser);
            tvnama = itemView.findViewById(R.id.tvnama);
        }
    }
}
