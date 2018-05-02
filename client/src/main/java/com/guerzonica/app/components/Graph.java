package com.guerzonica.app.components;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.geometry.Side;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

import com.guerzonica.app.models.data.ProductDetails;

public class Graph extends LineChart<String, Number> {

    public Graph(
        final CategoryAxis xAxis, 
        final NumberAxis yAxis, 
        final ProductDetails p
    ) {
        super(xAxis, yAxis);
    
        yAxis.setTickLabelsVisible(false);
        yAxis.setOpacity(0);
        yAxis.setForceZeroInRange(false);

        XYChart.Series<String, Number> series =
            new XYChart.Series<String, Number>();

        p.history.forEach(h -> {
            final XYChart.Data<String, Number> chartNode = 
                new XYChart.Data<String, Number>(h.getDate(), h.getPrice());

            chartNode.setNode(new ThresholdArea(p.getName(), h.getDate(), h.getPrice()));
            series.getData().add(chartNode);
        });

        this.setTitle(p.getName());
        this.setTitleSide(Side.TOP);

        this.getData().add(series);
    }
}