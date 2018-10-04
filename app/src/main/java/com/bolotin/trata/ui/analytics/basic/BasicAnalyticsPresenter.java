package com.bolotin.trata.ui.analytics.basic;

import com.bolotin.trata.data.DataManager;
import com.bolotin.trata.data.db.model.Category;
import com.bolotin.trata.ui.barChart.BarChartData;
import com.bolotin.trata.ui.barChart.BarChartUtil;
import com.bolotin.trata.ui.base.BasePresenter;
import com.bolotin.trata.utils.DateTimeConverter;
import com.bolotin.trata.utils.DecimalFormatter;
import com.bolotin.trata.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

public class BasicAnalyticsPresenter<V extends BasicAnalyticsMvpView> extends BasePresenter<V>
        implements BasicAnalyticsMvpPresenter<V> {

    private static final String START_DATE = "start_date";
    private static final String END_DATE = "end_date";

    private Map<String, Date> dateRange;
    private PublishSubject<Map<String, Date>> dateObservable;

    private List<Category> categories;

    @Inject
    BasicAnalyticsPresenter(DataManager dataManager,
                            SchedulerProvider schedulerProvider,
                            CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
        dateObservable = PublishSubject.create();
        dateRange = new HashMap<>();
    }

    @Override
    public void onViewPrepared() {
        setDateRangeObservable();
        getCategories();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        dateRange.put(START_DATE, calendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        dateRange.put(END_DATE, calendar.getTime());
        dateObservable.onNext(dateRange);
    }

    @Override
    public String getCurrency() {
        return getDataManager().getCurrencySymbol();
    }

    @Override
    public void updateDateRange(Date startDate, Date endDate) {
        dateRange.put(START_DATE, startDate);
        dateRange.put(END_DATE, endDate);
        dateObservable.onNext(dateRange);
    }



    private void setDateRangeObservable() {
        getCompositeDisposable().add(dateObservable
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().io())
                .subscribe(this::getBarChartData));
    }

    private void getBarChartData(Map<String, Date> dateRange) {
        getCompositeDisposable().add(getDataManager().getBarChartDataGroupedByCategory(
                DateTimeConverter.formatAnalytics(dateRange.get(START_DATE)),
                DateTimeConverter.formatAnalytics(dateRange.get(END_DATE)))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(barChartData -> {
                    getMvpView().updateBarChartData(prepareBarChartData(barChartData));

                    getMvpView().updateTitle(DateTimeConverter.formatDayOfMonth(dateRange.get(START_DATE)),
                            DateTimeConverter.formatDayOfMonth(dateRange.get(END_DATE)));

                    getMvpView().updateSubtitle(DecimalFormatter.format(BarChartUtil.getSum(barChartData)), getCurrency());

                    getMvpView().updateDatesRange(dateRange.get(START_DATE), dateRange.get(END_DATE));
                }));
    }

    private void getCategories() {
        getCompositeDisposable().add(getDataManager().getCategories()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(categories -> {
                    this.categories = categories;
                    getMvpView().updateCategories(categories);
                }));
    }

    /*
    Fill bar chart data with empty categories
     */
    private List<BarChartData> prepareBarChartData(List<BarChartData> barChartData) {
        List<String> allCategoriesId = new ArrayList<>();
        for (Category category : categories) {
            allCategoriesId.add(category.getId());
        }
        List<String> categoriesIdFromBarChartData = new ArrayList<>();
        for (BarChartData item : barChartData) {
            categoriesIdFromBarChartData.add(item.getXValue());
        }
        allCategoriesId.removeAll(categoriesIdFromBarChartData);
        List<BarChartData> preparedList = new ArrayList<>(barChartData);
        for (String categoryId : allCategoriesId) {
            BarChartData barChartDataItem = new BarChartData();
            barChartDataItem.setXValue(categoryId);
            barChartDataItem.setSum(0);
            preparedList.add(barChartDataItem);
        }
        return preparedList;
    }
}