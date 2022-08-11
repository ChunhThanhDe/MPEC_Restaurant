package com.doan1.mpec_restaurant.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.doan1.mpec_restaurant.R;
import com.doan1.mpec_restaurant.object.Customer;
import com.doan1.mpec_restaurant.object.Dish;
import com.doan1.mpec_restaurant.object.Review;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewsViewHolder> {

    private List<Review> mlistReviews;
    private List<Review> mlistReviewsOld;

    public ReviewAdapter(List<Review> mlistReviews) {
        this.mlistReviewsOld = mlistReviews;
        this.mlistReviews = mlistReviews;
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int position) {
        Review review = mlistReviews.get(position);

        if (review == null) {
            return;
        }
        holder.tvName.setText(toString().valueOf(review.getCustomer_id()));
        holder.tvComment.setText(review.getComment());
        holder.ratingDish.setRating(review.getStar());
        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_four));


    }

    @Override
    public int getItemCount() {
        if (mlistReviews != null) {
            return mlistReviews.size();
        }
        return 0;
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView imgCus;
        private TextView tvName;
        private TextView tvComment;
        private RatingBar ratingDish;
        private CardView cardView;


        public ReviewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCus = itemView.findViewById(R.id.img_cus);
            tvName = itemView.findViewById(R.id.tv_name);
            tvComment = itemView.findViewById(R.id.tv_comment);
            ratingDish = itemView.findViewById(R.id.ratingBar);
            cardView = itemView.findViewById(R.id.eachCardView);
        }
    }
}
