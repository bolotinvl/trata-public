package com.bolotin.trata.ui.barChart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class BarChartView extends View {
    private Paint linePaint;
    private Paint textPaint;
    private Paint barPaint;

    private List<String> labels;
    private List<BarChartData> barChartData;

    private Integer textSize;
    private float xStart;
    private float textPadding;
    private List<Float> yPtsText;
    private List<Float> yPtsLines;
    private float[] pts;
    private List<float[]> rects;
    private float barWidth;

    public BarChartView(Context context) {
        super(context);
        init();
    }

    public BarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BarChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //Calculate view height
        int height = MeasureSpec.getSize(heightMeasureSpec);

        //Calculate label padding
        String maxLabelLength = "0";
        for (String label : labels) {
            maxLabelLength = label.length() > maxLabelLength.length() ? label : maxLabelLength;
        }
        textPadding = textPaint.measureText(maxLabelLength) + 10;

        //Calculate view width
        int width = (int) (xStart + textPadding + barWidth * barChartData.size() + (barWidth * (barChartData.size() - 1)) / 2 + getPaddingRight());
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int dpWidth = displayMetrics.widthPixels;
        width = width < dpWidth ? dpWidth : width;

        //Calculate y point where view starts
        float yStart = height - getPaddingBottom();

        //Calculate x point where view starts
        xStart = getPaddingLeft();

        //Calculate interval between chart lines
        float interval = (height - getPaddingBottom() - getPaddingTop()) / 10;

        //Calculate coordinates for labels
        yPtsText.clear();
        for (int i = 0; i < 11; i++) {
            yPtsText.add(yStart - i * interval + textSize / 4);
        }

        //Calculate coordinates to draw lines
        yPtsLines.clear();
        for (int i = 0; i < 11; i++) {
            yPtsLines.add(xStart + textPadding);
            yPtsLines.add(yStart - i * interval);
            yPtsLines.add((float) width - getPaddingRight());
            yPtsLines.add(yStart - i * interval);
        }
        pts = createPtsArray(yPtsLines);
        for (int i = 0; i < yPtsLines.size(); i++) {
            pts[i] = yPtsLines.get(i);
        }

        //Calculate coordinates to draw bars
        rects.clear();
        long maxSumValue = 0;
        for (BarChartData barChartData : barChartData) {
            maxSumValue = barChartData.getSum() > maxSumValue ? (long) barChartData.getSum() : maxSumValue;
        }
        float maxHeight = yStart - getPaddingTop();
        for (int i = 0; i < barChartData.size(); i++) {
            long barChartValue = (long) barChartData.get(i).getSum();
            float leftEdge = xStart + textPadding + i * (barWidth + barWidth / 2);
            float rightEdge = xStart + textPadding + barWidth + i * (barWidth + barWidth / 2);
            float topEdge = yStart - maxHeight * (float) barChartValue / (float) maxSumValue;
            rects.add(createRectArray(leftEdge, topEdge, rightEdge, yStart));
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Draw labels
        for (int i = 0; i < 11; i++) {
            canvas.drawText(labels.get(i), xStart + textPadding - 10, yPtsText.get(i), textPaint);
        }

        //Draw horizontal lines
        canvas.drawLines(pts, linePaint);

        //Draw bars
        for (int i = 0; i < barChartData.size(); i++) {
            List<Integer> colors = getColors();
            int j = i;
            while (j > 14) {
                j -= 15;
            }
            barPaint.setColor(colors.get(j));
            float[] edges = rects.get(i);
            canvas.drawRect(edges[0], edges[1], edges[2], edges[3], barPaint);
        }

    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
        invalidate();
        requestLayout();
    }

    public void setBarChartData(List<BarChartData> barChartData) {
        this.barChartData = barChartData;
        invalidate();
        requestLayout();
    }

    private void init() {
        yPtsText = new ArrayList<>();
        labels = new ArrayList<>();
        yPtsLines = new ArrayList<>();
        rects = new ArrayList<>();

        barChartData = new ArrayList<>();
        BarChartData defaultBarChartData = new BarChartData();
        defaultBarChartData.setXValue("0");
        defaultBarChartData.setSum(0);
        barChartData.add(defaultBarChartData);

        for (int i = 0; i < 11; i++) {
            labels.add("0");
        }

        textSize = dipsToPx(12);
        barWidth = dipsToPx(16);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.GRAY);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.GRAY);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.RIGHT);

        barPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        barPaint.setColor(getColors().get(0));
    }

    private int dipsToPx(float dips) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (dips * scale + 0.5f);
    }

    private float[] createPtsArray(List<Float> ptsList) {
        return new float[ptsList.size()];
    }

    private float[] createRectArray(float leftEdge, float topEdge, float rightEdge, float bottomEdge) {
        return new float[] {leftEdge, topEdge, rightEdge, bottomEdge};
    }

    public static List<Integer> getColors() {
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#459EFF"));
        colors.add(Color.parseColor("#8FC93A"));
        colors.add(Color.parseColor("#E4CC37"));
        colors.add(Color.parseColor("#E18335"));
        colors.add(Color.parseColor("#5CA5D3"));
        colors.add(Color.parseColor("#62B6CB"));
        colors.add(Color.parseColor("#EAC5D8"));
        colors.add(Color.parseColor("#FF5964"));
        colors.add(Color.parseColor("#9DACFF"));
        colors.add(Color.parseColor("#F4EBE8"));
        colors.add(Color.parseColor("#817595"));
        colors.add(Color.parseColor("#9EADC8"));
        colors.add(Color.parseColor("#F9EAE1"));
        colors.add(Color.parseColor("#F1F0CC"));
        colors.add(Color.parseColor("#91B9AC"));
        return colors;
    }
}