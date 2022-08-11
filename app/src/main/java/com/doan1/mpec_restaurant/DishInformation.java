package com.doan1.mpec_restaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.doan1.mpec_restaurant.adapter.ReviewAdapter;
import com.doan1.mpec_restaurant.object.Dish;
import com.doan1.mpec_restaurant.object.Review;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DishInformation extends AppCompatActivity {

    private RecyclerView rcvReviews;
    private ReviewAdapter reviewAdapter;

    private CircleImageView imgDish;
    private TextView tvName;
    private TextView tvPrice;
    private TextView tvShortDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_information);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }

        Dish dish = (Dish) bundle.get("object_dish");

        imgDish = findViewById(R.id.img_dish);
        tvName = findViewById(R.id.tv_name);
        tvPrice = findViewById(R.id.tv_price);
        tvShortDescription = findViewById(R.id.tv_short_description);

        tvName.setText(dish.getName());
        tvPrice.setText(toString().valueOf(dish.getPrice()));
        tvShortDescription.setText(dish.getShortDescription());

        //decode base 64

        byte[] bytes = Base64.decode(dish.getImage(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        imgDish.setImageBitmap(bitmap);

        //
        rcvReviews = findViewById(R.id.rcv_reviews);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvReviews.setLayoutManager(linearLayoutManager);

        reviewAdapter = new ReviewAdapter(getListReviews());
        rcvReviews.setAdapter(reviewAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvReviews.addItemDecoration(itemDecoration);

    }

    private List<Review> getListReviews() {
        List<Review> list = new ArrayList<>();
        list.add(new Review(1,5,"Ngon tuyệt cú mèo",1,2));
        list.add(new Review(2,5,"Ngon chết đi được",2,2));
        list.add(new Review(3,5,"10 điểm chất lượng",3,2));
        list.add(new Review(4,5,"Ngon bổ rẻ",4,2));
        list.add(new Review(5,3,"Cũng được",5,2));
        list.add(new Review(6,4,"Okeee con dê",6,2));

        return list;
    }
}