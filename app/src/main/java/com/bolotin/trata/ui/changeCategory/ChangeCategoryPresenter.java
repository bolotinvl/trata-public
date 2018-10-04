package com.bolotin.trata.ui.changeCategory;

import com.bolotin.trata.R;
import com.bolotin.trata.data.DataManager;
import com.bolotin.trata.data.db.model.Category;
import com.bolotin.trata.ui.base.BasePresenter;
import com.bolotin.trata.utils.DateTimeConverter;
import com.bolotin.trata.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ChangeCategoryPresenter<V extends ChangeCategoryMvpView> extends BasePresenter<V>
        implements ChangeCategoryMvpPresenter<V> {

    @Inject
    ChangeCategoryPresenter(DataManager dataManager,
                                   SchedulerProvider schedulerProvider,
                                   CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared() {
        getMvpView().updateCategoryIcons(getCategoryIcons());
    }

    @Override
    public void onViewPrepared(String categoryId) {
        onViewPrepared();
        getCompositeDisposable().add(getDataManager().getCategoryById(categoryId)
                .firstOrError()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(category -> getMvpView().prepareEditCategoryActivity(category.getName(),
                        category.getIcon())));
    }

    @Override
    public void createCategory(String categoryName, String categoryIcon) {
        getCompositeDisposable().add(getDataManager().getCategoriesCount()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().io())
                .subscribe(categoriesCount -> {
                    String currentDate = DateTimeConverter.formatDb(Calendar.getInstance().getTime());
                    Category category = new Category(UUID.randomUUID().toString(),
                            categoryName,
                            categoryIcon,
                            categoriesCount + 1,
                            currentDate,
                            currentDate);

                    insertCategoryToDb(category);
                }));

    }

    @Override
    public void updateCategory(String categoryId, String categoryName, String categoryIcon) {
        getCompositeDisposable().add(getDataManager().getCategoryById(categoryId)
                .firstOrError()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(category -> {
                    String currentDate = DateTimeConverter.formatDb(Calendar.getInstance().getTime());
                    category.setName(categoryName);
                    category.setIcon(categoryIcon);
                    category.setUpdatedAt(currentDate);

                    updateCategoryInDb(category);
                }));
    }

    @Override
    public List<Integer> getCategoryIcons() {
        List<Integer> categoryIcons = new ArrayList<>();
        categoryIcons.add(R.drawable.airplane);
        categoryIcons.add(R.drawable.album);
        categoryIcons.add(R.drawable.apple);
        categoryIcons.add(R.drawable.balloon);
        categoryIcons.add(R.drawable.bank);
        categoryIcons.add(R.drawable.basketball);
        categoryIcons.add(R.drawable.beauty);
        categoryIcons.add(R.drawable.bicycle);
        categoryIcons.add(R.drawable.book);
        categoryIcons.add(R.drawable.box_in);
        categoryIcons.add(R.drawable.box_out);
        categoryIcons.add(R.drawable.briefcase);
        categoryIcons.add(R.drawable.building);
        categoryIcons.add(R.drawable.bus);
        categoryIcons.add(R.drawable.camera);
        categoryIcons.add(R.drawable.car);
        categoryIcons.add(R.drawable.cloud);
        categoryIcons.add(R.drawable.cocktail);
        categoryIcons.add(R.drawable.coffee);
        categoryIcons.add(R.drawable.compass);
        categoryIcons.add(R.drawable.computer);
        categoryIcons.add(R.drawable.credit_card);
        categoryIcons.add(R.drawable.crown);
        categoryIcons.add(R.drawable.disc);
        categoryIcons.add(R.drawable.dumbbell);
        categoryIcons.add(R.drawable.emoji);
        categoryIcons.add(R.drawable.envelope);
        categoryIcons.add(R.drawable.flash);
        categoryIcons.add(R.drawable.fork_and_knife);
        categoryIcons.add(R.drawable.gamepad);
        categoryIcons.add(R.drawable.gas);
        categoryIcons.add(R.drawable.gift);
        categoryIcons.add(R.drawable.globe);
        categoryIcons.add(R.drawable.hanger);
        categoryIcons.add(R.drawable.headphones);
        categoryIcons.add(R.drawable.heart);
        categoryIcons.add(R.drawable.house);
        categoryIcons.add(R.drawable.ice_cream);
        categoryIcons.add(R.drawable.image);
        categoryIcons.add(R.drawable.key);
        categoryIcons.add(R.drawable.laptop);
        categoryIcons.add(R.drawable.lock);
        categoryIcons.add(R.drawable.mail);
        categoryIcons.add(R.drawable.man);
        categoryIcons.add(R.drawable.map);
        categoryIcons.add(R.drawable.medical_cross);
        categoryIcons.add(R.drawable.money);
        categoryIcons.add(R.drawable.mortar_board);
        categoryIcons.add(R.drawable.movie_alt);
        categoryIcons.add(R.drawable.movie);
        categoryIcons.add(R.drawable.news);
        categoryIcons.add(R.drawable.paint_roller);
        categoryIcons.add(R.drawable.pill);
        categoryIcons.add(R.drawable.pizza);
        categoryIcons.add(R.drawable.router);
        categoryIcons.add(R.drawable.scissors);
        categoryIcons.add(R.drawable.shield);
        categoryIcons.add(R.drawable.shopping_basket);
        categoryIcons.add(R.drawable.shopping_cart);
        categoryIcons.add(R.drawable.smart_phone);
        categoryIcons.add(R.drawable.store);
        categoryIcons.add(R.drawable.streaming_music);
        categoryIcons.add(R.drawable.ticket);
        categoryIcons.add(R.drawable.towel);
        categoryIcons.add(R.drawable.train);
        categoryIcons.add(R.drawable.trees);
        categoryIcons.add(R.drawable.users);
        categoryIcons.add(R.drawable.video);
        categoryIcons.add(R.drawable.wallet);
        categoryIcons.add(R.drawable.wifi);
        categoryIcons.add(R.drawable.wine);
        categoryIcons.add(R.drawable.woman);
        categoryIcons.add(R.drawable.world);
        categoryIcons.add(R.drawable.wranch);

        return categoryIcons;
    }

    private void insertCategoryToDb(Category category) {
        getCompositeDisposable().add(getDataManager().insertCategory(category)
                .subscribeOn(getSchedulerProvider().io())
                .subscribe());
    }

    private void updateCategoryInDb(Category category) {
        getCompositeDisposable().add(getDataManager().updateCategory(category)
                .subscribeOn(getSchedulerProvider().io())
                .subscribe());
    }
}
