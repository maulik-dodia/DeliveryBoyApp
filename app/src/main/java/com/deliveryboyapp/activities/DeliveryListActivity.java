package com.deliveryboyapp.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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

    @BindView(R.id.tv_no_internet)
    TextView tv_no_internet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveries_list);

        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        rv_deliveries.setLayoutManager(new LinearLayoutManager(this));
        rv_deliveries.setHasFixedSize(true);

        DeliveriesAdapter deliveriesAdapter = new DeliveriesAdapter(
                delivery -> navigateToOtherScreen(DeliveryListActivity.this, DeliveryDetailsActivity.class,
                        true, delivery, false));

        DeliveriesViewModel deliveriesViewModel = ViewModelProviders.of(this, mViewModelFactory).get(DeliveriesViewModel.class);

        deliveriesViewModel.getLiveDataStatus().observe(this, status -> {

            if (Objects.requireNonNull(status).equalsIgnoreCase(Constants.STR_LOADING)) {

                displayLoading();

            } else if (status.equalsIgnoreCase(Constants.STR_LOADED)) {

                hideLoading();
                tv_no_internet.setVisibility(View.GONE);
                rv_deliveries.setVisibility(View.VISIBLE);

            } else if (status.equalsIgnoreCase(Constants.STR_NO_MORE_DATA)) {

                hideLoading();
                tv_no_internet.setVisibility(View.VISIBLE);
                rv_deliveries.setVisibility(View.VISIBLE);
                tv_no_internet.setText(getString(R.string.str_internet_error));

            } else if (status.equalsIgnoreCase(Constants.STR_ERROR)) {

                hideLoading();
                rv_deliveries.setVisibility(View.GONE);
                tv_no_internet.setVisibility(View.VISIBLE);
                tv_no_internet.setText(getString(R.string.str_try_after_some_time_error));
            }
        });

        deliveriesViewModel.getListLiveData().observe(this, deliveries -> deliveriesAdapter.submitList(deliveries));
        rv_deliveries.setAdapter(deliveriesAdapter);
    }
}