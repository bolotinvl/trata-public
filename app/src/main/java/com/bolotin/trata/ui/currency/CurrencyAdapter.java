package com.bolotin.trata.ui.currency;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bolotin.trata.R;
import com.bolotin.trata.listeners.OnClickListener;
import com.bolotin.trata.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CurrencyAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private OnClickListener onClickListener;
    private List<HashMap<String, String>> currencies;
    private int selectedPosition;

    public CurrencyAdapter(ArrayList<HashMap<String, String>> currencies) {
        this.currencies = currencies;
    }

    @Override
    @NonNull
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CurrencyViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if (currencies != null) {
            return currencies.size();
        } else {
            return 1;
        }

    }

    public void toggleSelection(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    void addItems(List<HashMap<String, String>> currencies) {
        this.currencies = currencies;
        notifyDataSetChanged();
    }

    public String getCurrencyCode() {
        return currencies.get(selectedPosition).get("currencyCode");
    }

    public String getCurrencySymbol() {
        return currencies.get(selectedPosition).get("currencySymbol");
    }

    void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class CurrencyViewHolder extends BaseViewHolder {

        @BindView(R.id.currency_list_item)
        LinearLayout currencyListItem;

        @BindView(R.id.currency_text_view)
        TextView currencyTextView;

        @BindView(R.id.currency_tick_image_view)
        ImageView currencyTickImageView;

        @BindView(R.id.currency_line_divider)
        ImageView currencyLineDivider;

        CurrencyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            final HashMap<String, String> currency = currencies.get(position);

            String currencyName = currency.get("currencyName");
            String currencySymbol = currency.get("currencySymbol");

            String currencyStr = currencyName + " " + currencySymbol;
            currencyTextView.setText(currencyStr);

            currencyTickImageView.setVisibility(View.GONE);

            if (position == 0) {
                currencyLineDivider.setVisibility(View.GONE);
            } else {
                currencyLineDivider.setVisibility(View.VISIBLE);
            }

            currencyListItem.setSelected(selectedPosition == position);
            currencyListItem.setOnClickListener(v -> onClickListener.onClicked(v, position));

            if (currencyListItem.isSelected()) {
                currencyTickImageView.setVisibility(View.VISIBLE);
            }
        }
    }
}