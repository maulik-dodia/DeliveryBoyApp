package com.deliveryboyapp;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.deliveryboyapp.adapters.DeliveriesAdapter;
import com.deliveryboyapp.beans.Delivery;
import com.deliveryboyapp.beans.Location;

import java.util.ArrayList;

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

        populateDummyData();
    }

    private void populateDummyData() {

        ArrayList<Delivery> deliveryArrayList = new ArrayList<>();

        for (int i = 0; i < 50; i++) {

            Delivery delivery = new Delivery();

            delivery.setDescription(i + " --> Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
            delivery.setId(String.valueOf(i));
            delivery.setImageUrl("https://cdn2.iconfinder.com/data/icons/social-media-network-fill-flat-icon/512/Diigo-512.png");

            Double lat = 13.016465;
            Double lng = 77.556628;

            String address = "S 904, 9th Floor, World Trade Center, Brigade Gateway Campus, Dr. Rajkumar Road, Malleswaram (W), Bengaluru, Karnataka 560055";

            Location location = new Location();
            location.setLat(lat);
            location.setLng(lng);
            location.setAddress(address);

            delivery.setLocation(location);

            deliveryArrayList.add(delivery);
        }

        DeliveriesAdapter deliveriesAdapter = new DeliveriesAdapter(deliveryArrayList, new OnItemClickListener() {
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