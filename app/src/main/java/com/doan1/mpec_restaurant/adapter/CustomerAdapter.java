package com.doan1.mpec_restaurant.adapter;

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
import com.doan1.mpec_restaurant.onClick_interface.IClickCardCategoryListener;
import com.doan1.mpec_restaurant.object.Category;
import com.doan1.mpec_restaurant.object.Customer;
import com.doan1.mpec_restaurant.onClick_interface.IClickCardCustomerListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> implements  Filterable {

    private List<Customer> mListCustomer;
    private List<Customer> mListCustomerOld;
    private IClickCardCustomerListener iClickCardCustomerListener;

    public CustomerAdapter(List<Customer> mListCustomer, IClickCardCustomerListener iClickCardCustomerListener) {
        this.mListCustomer = mListCustomer;
        this.mListCustomerOld = mListCustomer;
        this.iClickCardCustomerListener = iClickCardCustomerListener;
    }


    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer, parent,false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer customer = mListCustomer.get(position);
        if (customer == null) {
            return;
        }

        holder.tvName.setText(customer.getName());

        holder.tvEmail.setText(customer.getEmail());
        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_one));


        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickCardCustomerListener.updateCustomer(customer);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickCardCustomerListener.deleteCustomer(customer);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListCustomer != null) {
            return mListCustomer.size();
        }
        return 0;
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private Button btnUpdate;
        private Button btnDelete;
        private TextView tvEmail;
        private CardView cardView;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);

            btnDelete = itemView.findViewById(R.id.btn_delete);
            tvName = itemView.findViewById(R.id.tv_name);
            btnUpdate = itemView.findViewById(R.id.btn_update);
            tvEmail = itemView.findViewById(R.id.tv_email);
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
                    mListCustomer = mListCustomerOld;
                } else {
                    List<Customer> list = new ArrayList<>();
                    for (Customer customer: mListCustomerOld) {
                        if (customer.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(customer);
                        }
                        mListCustomer = list;
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListCustomer;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                mListCustomer = (List<Customer>) results.values;
                notifyDataSetChanged();

            }
        };
    }
}
