package com.guerzonica.app.picodom.components;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

import java.util.Iterator;

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

        Iterator<Offer> priceSet = item.prices.iterator();

        while(priceSet.hasNext()) {
            Offer offer = priceSet.next();

            final XYChart.Data<String, Number> chartNode = 
                new XYChart.Data<String, Number>(offer.getDate(), offer.getPrice());

            chartNode.setNode(new ThresholdArea(item.product.getName(), offer.getDate(),offer.getPrice()));
            series.getData().add(chartNode);
        }

        this.setTitle(item.product.getName());
        this.setTitleSide(Side.TOP);

        this.getData().add(series);
    }
}