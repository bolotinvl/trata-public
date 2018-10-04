package com.bolotin.trata.ui.barChart;

import com.bolotin.trata.utils.LongFormatter;

import java.util.ArrayList;
import java.util.List;

public class BarChartUtil {

    public static double getSum(List<BarChartData> barChartData) {
        double sum = 0;
        for (BarChartData barChartDataItem : barChartData) {
            sum += barChartDataItem.getSum();
        }
        return sum;
    }

    public static List<String> getLabels(List<BarChartData> barChartData) {
        double maxSum = 0;
        for (BarChartData barChartDataItem : barChartData) {
            maxSum = barChartDataItem.getSum() > maxSum ? barChartDataItem.getSum() : maxSum;
        }
        maxSum = maxSum == 0 ? 1000 : maxSum;

        List<Long> range = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            Double value = maxSum / 10 * i;
            range.add(value.longValue());
        }

        List<String> labels = new ArrayList<>();
        for (Long item : range) {
            labels.add(LongFormatter.format(item));
        }

        return labels;
    }
}