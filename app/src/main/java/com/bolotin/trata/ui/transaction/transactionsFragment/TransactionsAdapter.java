package com.bolotin.trata.ui.transaction.transactionsFragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bolotin.trata.R;
import com.bolotin.trata.data.db.model.Category;
import com.bolotin.trata.data.db.model.Transaction;
import com.bolotin.trata.data.db.model.TransactionGeneralItem;
import com.bolotin.trata.data.db.model.TransactionHeaderItem;
import com.bolotin.trata.data.db.model.TransactionListItem;
import com.bolotin.trata.listeners.OnClickListener;
import com.bolotin.trata.ui.base.BaseViewHolder;
import com.bolotin.trata.utils.AppConstants;
import com.bolotin.trata.utils.CommonUtils;
import com.bolotin.trata.utils.DecimalFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int ITEM_TYPE_GENERAL = 0;
    private static final int ITEM_TYPE_HEADER = 1;

    private static List<Category> categories;
    private List<TransactionListItem> itemList;

    private OnClickListener onClickListener;
    private String mode;

    private String amountString;
    private String currency;


    public TransactionsAdapter(List<TransactionListItem> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_GENERAL) {
            switch (mode) {
                case AppConstants.DEFAULT_MODE:
                    return new DefaultModeViewHolder(LayoutInflater.from(
                            parent.getContext()).inflate(R.layout.transactions_list_item_default,
                            parent,
                            false));
                case AppConstants.COMPACT_MODE:
                    return new CompactModeViewHolder(LayoutInflater.from(
                            parent.getContext()).inflate(R.layout.transactions_list_item_compact,
                            parent,
                            false));
                default:
                    return new DefaultModeViewHolder(LayoutInflater.from(
                            parent.getContext()).inflate(R.layout.transactions_list_item_default,
                            parent,
                            false));
            }
        } else {
            return new HeaderViewHolder(LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.transactions_header_item,
                    parent,
                    false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void addItems(List<Transaction> itemList) {
        this.itemList = getConsolidatedList(itemList);
        notifyDataSetChanged();
    }

    public void addCategories(List<Category> categories) {
        TransactionsAdapter.categories = categories;
        notifyDataSetChanged();
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setAmountString(String amountString) {
        this.amountString = amountString;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    Transaction getTransaction(int position) {
        TransactionGeneralItem transactionGeneralItem = (TransactionGeneralItem) itemList.get(position);
        return transactionGeneralItem.getTransaction();
    }

    String getTransactionId(int position) {
        return ((TransactionGeneralItem) itemList.get(position)).getTransaction().getId();
    }

    private List<TransactionListItem> getConsolidatedList(List<Transaction> transactions) {
        Map<String, List<Transaction>> groupedTransactions = groupTransactionsByDate(transactions);

        List<TransactionListItem> consolidatedList = new ArrayList<>();

        for (String date : groupedTransactions.keySet()) {
            Double totalAmount = 0d;
            TransactionHeaderItem transactionHeaderItem = new TransactionHeaderItem();
            transactionHeaderItem.setDate(date);

            for (Transaction transaction : groupedTransactions.get(date)) {
                totalAmount += transaction.getValue();
                TransactionGeneralItem transactionGeneralItem = new TransactionGeneralItem();
                transactionGeneralItem.setTransaction(transaction);
                transactionGeneralItem.setValue(String.format(
                        amountString,
                        DecimalFormatter.format(transaction.getValue()),
                        currency));
                consolidatedList.add(0, transactionGeneralItem);
            }

            transactionHeaderItem.setTotalAmount(String.format(
                    amountString,
                    DecimalFormatter.format(totalAmount),
                    currency));
            consolidatedList.add(0, transactionHeaderItem);
        }
        return consolidatedList;
    }

    private Map<String, List<Transaction>> groupTransactionsByDate(final List<Transaction> transactions) {
        Map<String, List<Transaction>> groupedTransactions = new LinkedHashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        for (Transaction transaction : transactions) {
            try {
                Date transactionDate = dateFormatter.parse(transaction.getDate());
                String hashMapKey = simpleDateFormat.format(transactionDate);

                if (groupedTransactions.containsKey(hashMapKey)) {
                    groupedTransactions.get(hashMapKey).add(transaction);
                } else {
                    List<Transaction> list = new ArrayList<>();
                    list.add(transaction);
                    groupedTransactions.put(hashMapKey, list);
                }
            } catch (ParseException e) {
                e.getMessage();
            }

        }
        return groupedTransactions;
    }

    public class CompactModeViewHolder extends BaseViewHolder {

        @BindView(R.id.list_item_transaction_compact)
        LinearLayout linearLayout;

        @BindView(R.id.category_icon_transaction_compact)
        ImageView categoryIcon;

        @BindView(R.id.note_transaction_compact)
        TextView note;

        @BindView(R.id.value_transaction_compact)
        TextView value;

        @BindView(R.id.line_divider_transaction_compact)
        ImageView lineDivider;

        CompactModeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            Transaction transaction = ((TransactionGeneralItem) itemList.get(position)).getTransaction();
            String transactionValue = ((TransactionGeneralItem) itemList.get(position)).getValue();
            String iconId = null;
            for (Category category : categories) {
                if (category.getId().equals(transaction.getCategoryId())) {
                    iconId = category.getIcon();
                    break;
                }
            }

            categoryIcon.setImageResource(
                    itemView.getContext().getResources()
                            .getIdentifier(CommonUtils.replaceHyphen(iconId),
                                    "drawable",
                                    itemView.getContext().getPackageName()));

            note.setText(transaction.getNote());

            value.setText(transactionValue);

            if (itemList.get(position - 1).getType() == ITEM_TYPE_HEADER) {
                lineDivider.setVisibility(View.GONE);
            } else {
                lineDivider.setVisibility(View.VISIBLE);
            }

            linearLayout.setOnClickListener(v -> {
                if (onClickListener != null) {
                    onClickListener.onClicked(v, getAdapterPosition());
                }
            });

            linearLayout.setOnLongClickListener(v -> {
                if (onClickListener != null) {
                    onClickListener.onLongClicked(v, getAdapterPosition());
                    return true;
                }
                return false;
            });
        }
    }

    public class DefaultModeViewHolder extends BaseViewHolder {

        @BindView(R.id.list_item_transaction_default)
        LinearLayout linearLayout;

        @BindView(R.id.category_icon_transaction_default)
        ImageView categoryIcon;

        @BindView(R.id.note_transaction_default)
        TextView note;

        @BindView(R.id.category_name_transaction_default)
        TextView categoryNameView;

        @BindView(R.id.value_transaction_default)
        TextView value;

        @BindView(R.id.line_divider_transaction_default)
        ImageView lineDivider;

        DefaultModeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            Transaction transaction = ((TransactionGeneralItem) itemList.get(position)).getTransaction();
            String transactionValue = ((TransactionGeneralItem) itemList.get(position)).getValue();
            String iconId = null;
            String categoryName = null;
            for (Category category : categories) {
                if (category.getId().equals(transaction.getCategoryId())) {
                    iconId = category.getIcon();
                    categoryName = category.getName();
                    break;
                }
            }

            categoryIcon.setImageResource(itemView.getContext().getResources()
                    .getIdentifier(CommonUtils.replaceHyphen(iconId),
                            "drawable",
                            itemView.getContext().getPackageName()));

            note.setText(transaction.getNote());

            value.setText(transactionValue);

            categoryNameView.setText(categoryName);

            if (itemList.get(position - 1).getType() == ITEM_TYPE_HEADER) {
                lineDivider.setVisibility(View.GONE);
            } else {
                lineDivider.setVisibility(View.VISIBLE);
            }

            linearLayout.setOnClickListener(v -> {
                if (onClickListener != null) {
                    onClickListener.onClicked(v, getAdapterPosition());
                }
            });

            linearLayout.setOnLongClickListener(v -> {
                if (onClickListener != null) {
                    onClickListener.onLongClicked(v, getAdapterPosition());
                    return true;
                }
                return false;
            });
        }
    }

    public class HeaderViewHolder extends BaseViewHolder {

        @BindView(R.id.header_date_text_view)
        TextView date;

        @BindView(R.id.header_sum_text_view)
        TextView totalValue;

        HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            TransactionHeaderItem headerItem = (TransactionHeaderItem) itemList.get(position);

            date.setText(headerItem.getDate());

            totalValue.setText(headerItem.getTotalAmount());
        }
    }
}