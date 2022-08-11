package com.doan1.mpec_restaurant.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filterable;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.doan1.mpec_restaurant.R;
import com.doan1.mpec_restaurant.onClick_interface.IClickCardCategoryListener;
import com.doan1.mpec_restaurant.object.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> implements  Filterable {

    private List<Category> mListCategory;
    private List<Category> mListCategoryOld;
    private IClickCardCategoryListener iClickCardCategoryListener;

    public CategoryAdapter(List<Category> mListCategory, IClickCardCategoryListener iClickCardCategoryListener) {
        this.mListCategory = mListCategory;
        this.mListCategoryOld = mListCategory;
        this.iClickCardCategoryListener = iClickCardCategoryListener;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = mListCategory.get(position);
        if (category == null) {
            return;
        }

        holder.tvName.setText(category.getName());
        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_three));

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickCardCategoryListener.updateCategory(category);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickCardCategoryListener.deleteCategory(category);
            }
        });
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
        private Button btnUpdate;
        private Button btnDelete;
        private CardView cardView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            btnDelete = itemView.findViewById(R.id.btn_delete);
            tvName = itemView.findViewById(R.id.tv_name);
            btnUpdate = itemView.findViewById(R.id.btn_update);
            cardView = itemView.findViewById(R.id.eachCardView);

        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()){
                    mListCategory = mListCategoryOld;
                } else {
                    List<Category> list = new ArrayList<>();
                    for (Category category: mListCategoryOld) {
                        if (category.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(category);
                        }
                        mListCategory = list;
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListCategory;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                mListCategory = (List<Category>) results.values;
                notifyDataSetChanged();

            }
        };
    }
}
