package com.deliveryboyapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deliveryboyapp.interfaces.OnItemClickListener;
import com.deliveryboyapp.R;
import com.deliveryboyapp.beans.Delivery;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeliveriesAdapter extends RecyclerView.Adapter<DeliveriesAdapter.ViewHolder> {

    private String TAG = DeliveriesAdapter.class.getSimpleName();

    private List<Delivery> mDeliveryArrayList;
    private OnItemClickListener mOnItemClickListener;

    public DeliveriesAdapter(List<Delivery> deliveryArrayList, OnItemClickListener itemClickListener) {
        this.mDeliveryArrayList = deliveryArrayList;
        this.mOnItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int position) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_delivery, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return mDeliveryArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.root_card_view)
        CardView root_card_view;

        @BindView(R.id.iv_photo)
        ImageView iv_photo;

        @BindView(R.id.tv_desc_short)
        TextView tv_desc_short;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void bindView(int position) {

            final Delivery delivery = mDeliveryArrayList.get(position);

            Picasso.get()
                    .load(delivery.getImageUrl())
                    .error(R.drawable.ic_error)
                    .into(iv_photo);

            tv_desc_short.setText(delivery.getDescription().substring(0, 20));

            root_card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mOnItemClickListener.onItemClick(delivery);
                }
            });
        }
    }
}