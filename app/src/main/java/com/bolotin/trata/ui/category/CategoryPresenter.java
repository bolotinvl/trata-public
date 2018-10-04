package com.bolotin.trata.ui.category;

import com.bolotin.trata.data.DataManager;
import com.bolotin.trata.data.db.model.Category;
import com.bolotin.trata.data.db.model.Transaction;
import com.bolotin.trata.ui.base.BasePresenter;
import com.bolotin.trata.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class CategoryPresenter<V extends CategoryMvpView> extends BasePresenter<V>
        implements CategoryMvpPresenter<V> {

    @Inject
    public CategoryPresenter(DataManager dataManager,
                             SchedulerProvider schedulerProvider,
                             CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared() {
        getCompositeDisposable().add(getDataManager().getCategories()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(categories -> getMvpView().updateCategories(categories)));
    }

    @Override
    public void updateCategories(List<Category> categories) {
        getCompositeDisposable().add(getDataManager().updateCategories(categories)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe());
    }

    @Override
    public void deleteCategory(String categoryId) {
        getCompositeDisposable().add(getDataManager().getCategoriesCount()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(categoriesCount -> {
                    if (categoriesCount == 1) {
                        getMvpView().showUnsuccessfulDeleteSnackbar();
                    } else {
                        getCompositeDisposable().add(getDataManager().getTransactionsByCategory(categoryId)
                                .subscribeOn(getSchedulerProvider().io())
                                .observeOn(getSchedulerProvider().ui())
                                .subscribe(relatedTransactions -> {
                                    if (relatedTransactions.isEmpty()) {
                                        removeCategoryFromDb(categoryId);
                                        getMvpView().setWasCategoryDeleted();
                                        return;
                                    }

                                    boolean hasActiveTransactions = false;
                                    for (Transaction transaction : relatedTransactions) {
                                        hasActiveTransactions = transaction.getActive();
                                        if (hasActiveTransactions) {
                                            break;
                                        }
                                    }
                                    if (!hasActiveTransactions) {
                                        for (Transaction transaction : relatedTransactions) {
                                            removeTransactionFromDb(transaction);
                                        }
                                        removeCategoryFromDb(categoryId);
                                        getMvpView().setWasCategoryDeleted();
                                        return;
                                    }

                                    getMvpView().showDeleteFragment(categoryId);
                                }));
                    }
                }));
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

    private void removeTransactionFromDb(Transaction transaction) {
        getCompositeDisposable().add(getDataManager().deleteTransaction(transaction)
                .subscribeOn(getSchedulerProvider().io())
                .subscribe());
    }
}