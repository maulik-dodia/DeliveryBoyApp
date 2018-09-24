package com.deliveryboyapp.adapters;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeliveriesAdapter extends PagedListAdapter<Delivery, DeliveriesAdapter.DeliveryViewHolder> {

    private String TAG = DeliveriesAdapter.class.getSimpleName();

    private OnItemClickListener mOnItemClickListener;

    public DeliveriesAdapter(OnItemClickListener listener) {
        super(DIFF_CALLBACK);
        mOnItemClickListener = listener;
    }

    @NonNull
    @Override
    public DeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {

        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_delivery, parent, false);

        return new DeliveryViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryViewHolder deliveryViewHolder, int position) {
        deliveryViewHolder.bindView(getItem(position));
    }

    private static DiffUtil.ItemCallback<Delivery> DIFF_CALLBACK = new DiffUtil.ItemCallback<Delivery>() {

        @Override
        public boolean areItemsTheSame(Delivery oldItem, Delivery newItem) {
            return oldItem.getId().equalsIgnoreCase(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(Delivery oldItem, Delivery newItem) {
            return oldItem.equals(newItem);
        }
    };

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

            root_card_view.setOnClickListener(v -> mOnItemClickListener.onItemClick(delivery));
        }
    }
}