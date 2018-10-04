package com.bolotin.trata.ui.category.deleteDialog;

import com.bolotin.trata.data.DataManager;
import com.bolotin.trata.data.db.model.Transaction;
import com.bolotin.trata.ui.base.BasePresenter;
import com.bolotin.trata.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class DeleteCategoryPresenter<V extends DeleteCategoryMvpView> extends BasePresenter<V>
        implements DeleteCategoryMvpPresenter<V> {

    @Inject
    public DeleteCategoryPresenter(DataManager dataManager,
                                   SchedulerProvider schedulerProvider,
                                   CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared(String deletedCategoryId) {
        getCompositeDisposable().add(getDataManager().getCategoriesExceptOne(deletedCategoryId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(categories -> getMvpView().updateCategories(categories)));
    }

    @Override
    public void deleteCategory(String deletedCategoryId, String newCategoryId) {
        getCompositeDisposable().add(getDataManager().getTransactionsByCategory(deletedCategoryId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().io())
                .subscribe(transactions -> {
                    List<Transaction> updatedTransactions = new ArrayList<>();
                    for (Transaction transaction : transactions) {
                        transaction.setCategoryId(newCategoryId);
                        updatedTransactions.add(transaction);
                    }
                    updateTransactions(updatedTransactions);

                    removeCategoryFromDb(deletedCategoryId);
                }));
    }

    private void updateTransactions(List<Transaction> transactions) {
        getCompositeDisposable().add(getDataManager().updateTransactions(transactions)
                .subscribeOn(getSchedulerProvider().io())
                .subscribe());
    }

    private void removeCategoryFromDb(String categoryId) {
        getCompositeDisposable().add(getDataManager().getCategoryById(categoryId)
                .firstOrError()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().io())
                .subscribe(category -> getCompositeDisposable().add(getDataManager().deleteCategory(category)
                        .subscribeOn(getSchedulerProvider().io())
                        .subscribe())));
    }
}
