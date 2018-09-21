package com.deliveryboyapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deliveryboyapp.R;
import com.deliveryboyapp.beans.Delivery;
import com.deliveryboyapp.interfaces.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.DeliveryViewHolder> {

    private List<Delivery> mDeliveriesList;
    private OnItemClickListener mOnItemClickListener;

    public TestAdapter(List<Delivery> deliveriesList, OnItemClickListener listener) {
        this.mDeliveriesList = deliveriesList;
        this.mOnItemClickListener = listener;
    }

    @NonNull
    @Override
    public DeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_delivery, parent, false);

        return new TestAdapter.DeliveryViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryViewHolder deliveryViewHolder, int position) {
        deliveryViewHolder.bindView(mDeliveriesList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDeliveriesList.size();
    }

    class DeliveryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.root_card_view)
        CardView root_card_view;

        @BindView(R.id.iv_photo)
        ImageView iv_photo;

        @BindView(R.id.tv_desc_short)
        TextView tv_desc_short;

        DeliveryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindView(final Delivery delivery) {

            if (delivery != null) {

                Picasso.get()
                        .load(delivery.getImageUrl())
                        .error(R.drawable.ic_error)
                        .into(iv_photo);

                tv_desc_short.setText(delivery.getDescription().substring(0, 20));
            }

            root_card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mOnItemClickListener.onItemClick(delivery);
                }
            });
        }
    }
}