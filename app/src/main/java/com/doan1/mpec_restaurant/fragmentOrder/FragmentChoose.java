package com.doan1.mpec_restaurant.fragmentOrder;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doan1.mpec_restaurant.DishInformation;
import com.doan1.mpec_restaurant.R;
import com.doan1.mpec_restaurant.adapter.MenuAdapter;
import com.doan1.mpec_restaurant.api.ApiDish;
import com.doan1.mpec_restaurant.onClick_interface.IClickCardDishListener;
import com.doan1.mpec_restaurant.object.Dish;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentChoose extends Fragment {

    private RecyclerView rcvDishs;
    private MenuAdapter menuAdapter;
    private SearchView searchView;

    private View mView;
    private List<Dish> mListDish;

    private ISendDataListener mISendDataListener;
    public interface ISendDataListener {
        void sendData(Dish dish);
    }

    public FragmentChoose() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mISendDataListener = (ISendDataListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_choose, container, false);

        searchView = mView.findViewById(R.id.searchview);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                menuAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                menuAdapter.getFilter().filter(newText);
                return false;
            }
        });

        rcvDishs = mView.findViewById(R.id.rcv_dishs);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvDishs.setLayoutManager(linearLayoutManager);

        mListDish = new ArrayList<>();
        ApiDish.apiDish.getListDish().enqueue(new Callback<List<Dish>>() {
            @Override
            public void onResponse(Call<List<Dish>> call, Response<List<Dish>> response) {
                mListDish = response.body();
                menuAdapter = new MenuAdapter(mListDish, new IClickCardDishListener() {
                    @Override
                    public void onClickCardDish(Dish dish) {
                        uploadListOrder(dish);
                    }

                    @Override
                    public void updateDish(Dish dish) {

                    }

                    @Override
                    public void deleteDish(Dish dish) {

                    }
                });
                rcvDishs.setAdapter(menuAdapter);
            }

            @Override
            public void onFailure(Call<List<Dish>> call, Throwable t) {

            }
        });

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        rcvDishs.addItemDecoration(itemDecoration);


        return mView;
    }


    private void uploadListOrder(Dish dish) {
        mISendDataListener.sendData(dish);

    }

}
