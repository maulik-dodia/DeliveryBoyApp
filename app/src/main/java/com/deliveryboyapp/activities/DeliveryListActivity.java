package com.deliveryboyapp.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.deliveryboyapp.Constants;
import com.deliveryboyapp.R;
import com.deliveryboyapp.adapters.DeliveriesAdapter;
import com.deliveryboyapp.net.DeliveriesViewModel;

import java.util.Objects;

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

        DeliveriesViewModel deliveriesViewModel = ViewModelProviders.of(this, mViewModelFactory).get(DeliveriesViewModel.class);

        final DeliveriesAdapter deliveriesAdapter = new DeliveriesAdapter(
                delivery -> navigateToOtherScreen(DeliveryListActivity.this, DeliveryDetailsActivity.class,
                        true, delivery, false));

        deliveriesViewModel.getLiveDataStatus().observe(this, status -> {
            if (Objects.requireNonNull(status).equalsIgnoreCase(Constants.STR_LOADING)) {
                displayLoading();
            } else if (status.equalsIgnoreCase(Constants.STR_LOADED)) {
                hideLoading();
            }
        });

        deliveriesViewModel.mLiveDataPagedList.observe(this, deliveries -> deliveriesAdapter.submitList(deliveries));

        rv_deliveries.setAdapter(deliveriesAdapter);
    }
}