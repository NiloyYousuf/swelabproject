package com.example.softwareproject;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class BurndownChartExample extends JFrame {

    public BurndownChartExample(String title) {
        super(title);

        DefaultCategoryDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(1080, 720));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Sample data for planned work
        dataset.addValue(100, "Planned Work", "Day 0");
        dataset.addValue(90, "Planned Work", "Day 1");
        dataset.addValue(80, "Planned Work", "Day 2");
        // Add more data points for planned work

        // Sample data for actual work remaining
        dataset.addValue(100, "Actual Work Remaining", "Day 0");
        dataset.addValue(45, "Actual Work Remaining", "Day 1");
        dataset.addValue(70, "Actual Work Remaining", "Day 2");
        // Add more data points for actual work remaining

        return dataset;
    }

    private JFreeChart createChart(DefaultCategoryDataset dataset) {
        return ChartFactory.createLineChart(
                "Burndown Chart",
                "Day",
                "Work Remaining",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BurndownChartExample example = new BurndownChartExample("Burndown Chart Example");
            example.pack();
            example.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}
