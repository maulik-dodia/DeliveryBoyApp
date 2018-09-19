package com.deliveryboyapp.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.deliveryboyapp.OnItemClickListener;
import com.deliveryboyapp.R;
import com.deliveryboyapp.adapters.DeliveriesAdapter;
import com.deliveryboyapp.beans.Delivery;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryListActivity extends BaseActivity {

    private String TAG = DeliveryListActivity.class.getSimpleName();

    @BindView(R.id.rv_deliveries)
    RecyclerView rv_deliveries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        getDeliveryData();
    }

    private void getDeliveryData() {

        displayLoading();

        mApiEndPoints.getDeliveries().enqueue(new Callback<List<Delivery>>() {

            @Override
            public void onResponse(Call<List<Delivery>> call, Response<List<Delivery>> deliveryResponse) {

                populateRecyclerView(deliveryResponse.body());

                displayToastMessage(getString(R.string.str_success));
                hideLoading();
            }

            @Override
            public void onFailure(Call<List<Delivery>> call, Throwable throwable) {

                displayToastMessage(throwable.getMessage());
                hideLoading();
            }
        });
    }

    private void populateRecyclerView(List<Delivery> deliveryList) {

        DeliveriesAdapter deliveriesAdapter = new DeliveriesAdapter(deliveryList, new OnItemClickListener() {
            @Override
            public void onItemClick(Delivery delivery) {

                navigateToOtherScreen(DeliveryListActivity.this, DeliveryDetailsActivity.class,
                        true, delivery, false);
            }
        });
        rv_deliveries.setLayoutManager(new LinearLayoutManager(this));
        rv_deliveries.setAdapter(deliveriesAdapter);
    }
}