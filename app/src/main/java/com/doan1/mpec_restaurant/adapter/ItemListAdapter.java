package com.doan1.mpec_restaurant.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doan1.mpec_restaurant.R;
import com.doan1.mpec_restaurant.object.Category;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.CategoryViewHolder>  {

    private List<Category> mListCategory;

    public ItemListAdapter(List<Category> mListCategory) {
        this.mListCategory = mListCategory;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = mListCategory.get(position);
        if (category == null) {
            return;
        }

        holder.tvName.setText(category.getName());

    }

    @Override
    public int getItemCount() {
        if (mListCategory != null) {
            return mListCategory.size();
        }
        return 0;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);

        }
    }
}
