package com.bolotin.trata.ui.analytics;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.util.List;

import com.bolotin.trata.R;
import com.bolotin.trata.data.db.model.Category;
import com.bolotin.trata.listeners.OnClickListener;
import com.bolotin.trata.ui.barChart.BarChartData;
import com.bolotin.trata.ui.base.BaseViewHolder;
import com.bolotin.trata.utils.CommonUtils;
import com.bolotin.trata.utils.DateTimeConverter;
import com.bolotin.trata.utils.DecimalFormatter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommonAnalyticsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<BarChartData> barChartData;
    private List<Category> categories;
    private List<Integer> colors;
    private OnClickListener onClickListener;
    private String currency;

    public CommonAnalyticsAdapter(List<BarChartData> barChartData) {
        this.barChartData = barChartData;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AnalyticsViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.analytics_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return barChartData.size();
    }

    public void addItems(List<BarChartData> barChartData) {
        this.barChartData = barChartData;
        notifyDataSetChanged();
    }

    public String getXValue(int position) {
        return barChartData.get(position).getXValue();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setColors(List<Integer> colors) {
        this.colors = colors;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public class AnalyticsViewHolder extends BaseViewHolder {

        @BindView(R.id.analytics_list_item)
        LinearLayout listItem;

        @BindView(R.id.analytics_icon_view)
        ImageView iconView;

        @BindView(R.id.analytics_name_view)
        TextView nameView;

        @BindView(R.id.analytics_value_view)
        TextView valueView;

        @BindView(R.id.analytics_line_divider)
        ImageView lineDividerView;

        AnalyticsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            String iconId = null;
            String categoryName = null;

            if (categories != null) {
                for (Category category : categories) {
                    if (category.getId().equals(barChartData.get(position).getXValue())) {
                        iconId = category.getIcon();
                        categoryName = category.getName();
                    }
                }
            }

            if (iconId != null) {
                iconView.setImageResource(itemView.getContext().getResources()
                        .getIdentifier(CommonUtils.replaceHyphen(iconId),
                                "drawable",
                                itemView.getContext().getPackageName()));
                nameView.setText(categoryName);
            } else {
                try {
                    nameView.setText(DateTimeConverter.formatFullMonth(
                            DateTimeConverter.parseCategoryAnalytics(barChartData.get(position).getXValue())));
                } catch (ParseException e) {
                    e.getMessage();
                }
            }

            Drawable background = itemView.getContext().getResources().getDrawable(R.drawable.circle_background_reports);
            if (barChartData.get(position).getSum() > 0) {
                int colorPosition = position;
                while (colorPosition > 14) {
                    colorPosition -= 15;
                }
                iconView.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
                background.setColorFilter(colors.get(colorPosition), PorterDuff.Mode.SRC);
            } else {
                iconView.clearColorFilter();
            }

            iconView.setBackground(background);

            valueView.setText(String.format(itemView.getContext().getResources().getString(R.string.amount_with_currency),
                    DecimalFormatter.format(barChartData.get(position).getSum()),
                    currency));

            if (position == 0) {
                lineDividerView.setVisibility(View.GONE);
            } else {
                lineDividerView.setVisibility(View.VISIBLE);
            }

            listItem.setOnClickListener(v -> onClickListener.onClicked(v, position));
        }
    }
}