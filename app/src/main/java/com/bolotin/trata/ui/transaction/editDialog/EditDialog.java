package com.bolotin.trata.ui.transaction.editDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.bolotin.trata.R;
import com.bolotin.trata.di.component.ActivityComponent;
import com.bolotin.trata.ui.base.BaseDialog;
import com.bolotin.trata.ui.editTransaction.EditTransactionActivity;

import javax.inject.Inject;

public class EditDialog extends BaseDialog implements EditDialogMvpView{

    private static final String TAG = "EditDialog";

    @Inject
    EditDialogMvpPresenter<EditDialogMvpView> presenter;

    private AlertDialog.Builder builder;
    private String transactionId;

    public static EditDialog newInstance(String transactionId) {
        Bundle bundle = new Bundle();
        bundle.putString("transactionId", transactionId);
        EditDialog dialog = new EditDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);

            presenter.onAttach(this);
        }

        setUp(getView());

        return builder.create();
    }

    @Override
    protected void setUp(View view) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            transactionId = bundle.getString("transactionId");
        }

        setTheme(presenter.getTheme());

        builder.setItems(R.array.transaction_dialog_array, ((dialog, which) -> {
            switch (which) {
                case 0:
                    presenter.deleteTransaction(transactionId);
                    break;
                case 1:
                    Intent intent = EditTransactionActivity.getStartIntent(getActivity());
                    intent.putExtra("transactionId", transactionId);
                    startActivity(intent);
                    break;
            }
        }));
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    private void setTheme(String theme) {
        switch (theme) {
            case "default":
                builder = new AlertDialog.Builder(getActivity(), R.style.PopupStyle_Default);
                break;
            case "dark":
                builder = new AlertDialog.Builder(getActivity(), R.style.PopupStyle_Dark);
                break;
            default:
                builder = new AlertDialog.Builder(getActivity(), R.style.PopupStyle_Default);
        }
    }
}