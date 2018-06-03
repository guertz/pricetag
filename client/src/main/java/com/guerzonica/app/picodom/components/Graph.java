package com.guerzonica.app.picodom.components;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

import java.util.Date;
import java.util.Map;

import com.guerzonica.app.storage.models.Offer;
import com.guerzonica.app.storage.models.ProductPrices;

import javafx.geometry.Side;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

public class Graph extends LineChart<String, Number> {

    public Graph(
        final CategoryAxis xAxis, 
        final NumberAxis yAxis, 
        final ProductPrices item
    ) {
        super(xAxis, yAxis);
    
        yAxis.setTickLabelsVisible(false);
        yAxis.setOpacity(0);
        yAxis.setForceZeroInRange(false);

        XYChart.Series<String, Number> series =
            new XYChart.Series<String, Number>();

        for(Map.Entry<Date, Offer> entry : item.entrySet()) {
            Offer offer = entry.getValue();

            final XYChart.Data<String, Number> chartNode = 
                new XYChart.Data<String, Number>(offer.getDate(), offer.getPrice());

            chartNode.setNode(new ThresholdArea(item.getProduct().getName(), offer.getDate(),offer.getPrice()));
            series.getData().add(chartNode);
        }

        this.setTitle(item.getProduct().getName());
        this.setTitleSide(Side.TOP);

        this.getData().add(series);
    }
}