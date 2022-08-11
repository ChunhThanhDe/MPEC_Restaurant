package com.doan1.mpec_restaurant.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.doan1.mpec_restaurant.R;
import com.doan1.mpec_restaurant.onClick_interface.IClickCardDishListener;
import com.doan1.mpec_restaurant.object.Dish;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> implements Filterable {

    private List<Dish> mlistDishs;
    private List<Dish> mlistDishsOld;
    private IClickCardDishListener iClickCardDishListener;

    public DishAdapter(List<Dish> mlistDishs, IClickCardDishListener listener) {
        this.mlistDishsOld = mlistDishs;
        this.mlistDishs = mlistDishs;
        this.iClickCardDishListener = listener;

    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish, parent, false);
        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        Dish dish = mlistDishs.get(position);
        if (dish == null) {
            return;
        }

        //decode base 64
        byte[] bytes = Base64.decode(dish.getImage(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        holder.imgDish.setImageBitmap(bitmap);
        holder.tvName.setText(dish.getName());
        holder.tvPrice.setText(String.valueOf(dish.getPrice()));
        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_two));

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickCardDishListener.deleteDish(dish);
            }
        });
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickCardDishListener.updateDish(dish);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mlistDishs != null) {
            return mlistDishs.size();
        }
        return 0;
    }


    public class DishViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView imgDish;
        private TextView tvName;
        private TextView tvPrice;
        private Button btnUpdate;
        private Button btnDelete;
        private CardView cardView;

        public DishViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            imgDish = itemView.findViewById(R.id.img_dish);
            btnDelete = itemView.findViewById(R.id.btn_delete);
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
                notifyDataSetChanged();

            }
        };
    }
}
