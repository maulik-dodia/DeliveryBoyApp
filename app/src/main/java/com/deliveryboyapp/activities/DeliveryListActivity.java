package com.deliveryboyapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.deliveryboyapp.R;
import com.deliveryboyapp.adapters.DeliveriesAdapter;
import com.deliveryboyapp.beans.Delivery;
import com.deliveryboyapp.interfaces.OnItemClickListener;
import com.deliveryboyapp.net.DeliveriesViewModel;
import com.deliveryboyapp.net.ViewModelFactory;

import javax.inject.Inject;

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
                deliveriesAdapter.submitList(deliveries);
            }
        });

        rv_deliveries.setAdapter(deliveriesAdapter);
    }
}