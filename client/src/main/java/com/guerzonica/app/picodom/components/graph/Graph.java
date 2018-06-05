package com.guerzonica.app.picodom.components.graph;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

import java.util.Map;

import com.guerzonica.app.storage.models.Offer;
import com.guerzonica.app.storage.models.ProductPrices;

import javafx.geometry.Side;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

/**
* Handles Graph. In particular it show a line chart.
* @author Singh Amarjot, Matteo Guerzoni
*/
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

        for(Map.Entry<Long, Offer> entry : item.prices.entrySet()) {
            Offer offer = entry.getValue();

            final XYChart.Data<String, Number> chartNode =
                new XYChart.Data<String, Number>(offer.getDate(), offer.getPrice());

            chartNode.setNode(new ThresholdArea(item.getName(), offer.getDate(),offer.getPrice()));
            series.getData().add(chartNode);
        }

        this.setTitle(item.getName());
        this.setTitleSide(Side.TOP);

        this.getData().add(series);
    }
}
