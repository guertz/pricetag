package com.guerzonica.app.components;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.geometry.Side;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

import java.util.HashMap;

public class Graph extends LineChart<String, Number> {

    public Graph(
        final CategoryAxis xAxis, 
        final NumberAxis yAxis, 
        final String product,
        final HashMap<String, Number> dataSet) {

            super(xAxis, yAxis);
        
            yAxis.setTickLabelsVisible(false);
            yAxis.setOpacity(0);
            yAxis.setForceZeroInRange(false);

            XYChart.Series<String, Number> series =
                new XYChart.Series<String, Number>();

            dataSet.forEach((k, v) -> {
                final XYChart.Data<String, Number> chartNode = new XYChart.Data<String, Number>(k, v);
                    chartNode.setNode(new ThresholdArea(product, k, v));
                series.getData().add(chartNode);
            });

            this.setTitle(product);
            this.setTitleSide(Side.TOP);

            this.getData().add(series);
    }
}