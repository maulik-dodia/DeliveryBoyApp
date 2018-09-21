package com.deliveryboyapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.deliveryboyapp.R;
import com.deliveryboyapp.adapters.DeliveriesAdapter;
import com.deliveryboyapp.beans.Delivery;
import com.deliveryboyapp.interfaces.OnItemClickListener;
import com.deliveryboyapp.net.DeliveriesViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        rv_deliveries.setLayoutManager(new LinearLayoutManager(this));
        rv_deliveries.setHasFixedSize(true);

        //DeliveriesViewModel deliveriesViewModel = ViewModelProviders.of(this).get(DeliveriesViewModel.class);

        DeliveriesViewModel deliveriesViewModel = ViewModelProviders.of(this, mViewModelFactory).get(DeliveriesViewModel.class);

        final DeliveriesAdapter deliveriesAdapter = new DeliveriesAdapter(new OnItemClickListener() {
            @Override
            public void onItemClick(Delivery delivery) {

                navigateToOtherScreen(DeliveryListActivity.this, DeliveryDetailsActivity.class,
                        true, delivery, false);
            }
        });

        deliveriesViewModel.itemPagedList.observe(this, new Observer<PagedList<Delivery>>() {
            @Override
            public void onChanged(@Nullable PagedList<Delivery> deliveries) {

                Log.e(TAG, "Size: " + deliveries.size());

                deliveriesAdapter.submitList(deliveries);
            }
        });

        /*mApiEndPoints.getDeliveries(0, 20).enqueue(new Callback<List<Delivery>>() {
            @Override
            public void onResponse(Call<List<Delivery>> call, Response<List<Delivery>> response) {

                rv_deliveries.setLayoutManager(new LinearLayoutManager(DeliveryListActivity.this));

                TestAdapter testAdapter = new TestAdapter(response.body(), new OnItemClickListener() {
                    @Override
                    public void onItemClick(Delivery delivery) {
                        navigateToOtherScreen(DeliveryListActivity.this, DeliveryDetailsActivity.class,
                                true, delivery, false);
                    }
                });
                rv_deliveries.setAdapter(testAdapter);
            }

            @Override
            public void onFailure(Call<List<Delivery>> call, Throwable t) {

            }
        });*/
    }
}