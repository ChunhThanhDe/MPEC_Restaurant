package com.doan1.mpec_restaurant.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doan1.mpec_restaurant.R;
import com.doan1.mpec_restaurant.object.Dish;

import java.util.ArrayList;
import java.util.List;

public class DishMenuAdapter extends RecyclerView.Adapter<DishMenuAdapter.DishViewHolder> implements Filterable {

    private List<Dish> mlistDishs;
    private List<Dish> mlistDishsOld;

    public DishMenuAdapter(List<Dish> mlistDishs) {
        this.mlistDishsOld = mlistDishs;
        this.mlistDishs = mlistDishs;
    }
    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish_menu , parent,false);
        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        Dish dish = mlistDishs.get(position);
        if (dish == null){
            return;
        }
        holder.tvName.setText(dish.getName());
        holder.tvName.setText(dish.getCategoryName());
        holder.tvPrice.setText(String.valueOf(dish.getPrice()));
    }

    @Override
    public int getItemCount() {
        if (mlistDishs != null){
            return mlistDishs.size();
        }
        return 0;
    }

    public class DishViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvPrice;
        private TextView tvCategory;

        public DishViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvCategory = itemView.findViewById(R.id.tv_categoryName);
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()){
                    mlistDishs = mlistDishsOld;
                } else {
                    List<Dish> list = new ArrayList<>();
                    for (Dish dish: mlistDishsOld) {
                        if (dish.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(dish);
                        }
                        mlistDishs = list;
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mlistDishs;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                mlistDishs = (List<Dish>) results.values;

            }
        };
    }
}
