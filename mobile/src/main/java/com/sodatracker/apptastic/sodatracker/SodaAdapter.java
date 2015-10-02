package com.sodatracker.apptastic.sodatracker;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.net.InterfaceAddress;
import java.util.ArrayList;

/**
 * Created by mgarner on 10/1/2015.
 * SodaAdapter
 */
public class SodaAdapter extends RecyclerView.Adapter<SodaAdapter.SodaViewHolder> {
    private static ArrayList<Soda> mSodaList;
    private static SodaClickListener mSodaClickListener;

    public static class SodaViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private final TextView tvBrand;
        private final TextView tvTPDisplay;
        private final TextView tvCansDisplay;
        private final Button btnTPSubtract;
        private final Button btnTPAdd;
        private final Button btnCansSubtract;
        private final Button btnCansAdd;

        public SodaViewHolder(CardView cardView) {
            super(cardView);
            tvBrand = (TextView) cardView.findViewById(R.id.soda_card_tv_brand_label);
            tvTPDisplay = (TextView) cardView.findViewById(R.id.soda_card_tv_tp_display);
            tvCansDisplay = (TextView) cardView.findViewById(R.id.soda_card_tv_cans_display);
            btnTPSubtract = (Button) cardView.findViewById(R.id.soda_card_btn_tp_subtract);
            btnTPAdd = (Button) cardView.findViewById(R.id.soda_card_btn_tp_add);
            btnCansSubtract = (Button) cardView.findViewById(R.id.soda_card_btn_cans_subtract);
            btnCansAdd = (Button) cardView.findViewById(R.id.soda_card_btn_cans_add);
            btnTPSubtract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Soda soda = mSodaList.get(getAdapterPosition());
                    soda.subtractTwelvePack();
                    mSodaClickListener.onChildClick(getAdapterPosition());
                }
            });
            btnTPAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Soda soda = mSodaList.get(getAdapterPosition());
                    soda.addTwelvePack();
                    mSodaClickListener.onChildClick(getAdapterPosition());
                }
            });
            btnCansSubtract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Soda soda = mSodaList.get(getAdapterPosition());
                    soda.subtractCan();
                    mSodaClickListener.onChildClick(getAdapterPosition());
                }
            });
            btnCansAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Soda soda = mSodaList.get(getAdapterPosition());
                    soda.addCan();
                    mSodaClickListener.onChildClick(getAdapterPosition());
                }
            });
        }

        @Override
        public boolean onLongClick(View v) {
            mSodaClickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }

    public SodaAdapter(ArrayList<Soda> sodaList) {
        if (sodaList != null) {
            mSodaList = sodaList;
        } else {
            mSodaList = new ArrayList<>();
        }
    }

    @Override
    public SodaAdapter.SodaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView sodaCard = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.soda_card, parent, false);
        return new SodaViewHolder(sodaCard);
    }

    @Override
    public void onBindViewHolder(SodaViewHolder viewHolder, final int position) {
        final Soda soda = mSodaList.get(position);
        viewHolder.tvBrand.setText(soda.getBrandName());
        viewHolder.tvTPDisplay.setText(String.valueOf(soda.getNumTwelvePacks()));
        viewHolder.tvCansDisplay.setText(String.valueOf(soda.getNumCans()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mSodaList.size();
    }

    public void updateSodaList(ArrayList<Soda> sodaList) {
        this.mSodaList = sodaList;
    }

    public void setSodaClickListener(SodaClickListener clickListener) {
        mSodaClickListener = clickListener;
    }

    public interface SodaClickListener {
        void onItemLongClick(int position, View v);

        void onChildClick(int adapterPosition);
    }
}
