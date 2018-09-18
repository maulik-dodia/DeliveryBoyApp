package com.deliveryboyapp;

import android.os.Bundle;
import android.widget.TextView;

import com.deliveryboyapp.beans.Delivery;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeliveryDetailsActivity extends BaseActivity implements OnMapReadyCallback {

    private String TAG = DeliveryDetailsActivity.class.getSimpleName();

    private Delivery mDelivery;

    @BindView(R.id.tv_address)
    TextView tv_address;

    @BindView(R.id.tv_desc_full)
    TextView tv_desc_full;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        String objAsJson = bundle.getString(Constants.KEY_PUT_EXTRA);
        mDelivery = Delivery.fromJson(objAsJson);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tv_address.setText(mDelivery.getLocation().getAddress());
        tv_desc_full.setText(mDelivery.getDescription());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Double lat = mDelivery.getLocation().getLat();
        Double lng = mDelivery.getLocation().getLng();

        // Add a marker in Sydney and move the camera
        LatLng destinationLatLng = new LatLng(lat, lng);

        googleMap.addMarker(new MarkerOptions().position(destinationLatLng).title(getString(R.string.str_delivery_destination)));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destinationLatLng, 13.0f));
    }
}