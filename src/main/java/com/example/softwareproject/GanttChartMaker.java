package com.example.softwareproject;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GanttChartMaker extends JFrame {

    public GanttChartMaker(String title) {
        super(title);
        // Create dataset
        IntervalCategoryDataset dataset = getCategoryDataset();

        // Create chart
        boolean b = false;
        JFreeChart chart = ChartFactory.createGanttChart(
                "Gantt Chart Example", // Chart title
                "Software Development Phases", // X-Axis Label
                "Timeline", // Y-Axis Label
                dataset, b, b, b);

        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    static Date calculateStartDate(int startWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, startWeek);
        return calendar.getTime();
    }

    static Date calculateEndDate(int endWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, endWeek);
        return calendar.getTime();
    }

    private static IntervalCategoryDataset getCategoryDataset() {
        TaskSeries series1 = TaskScheduler.output();

        TaskSeriesCollection dataset = new TaskSeriesCollection();
        dataset.add(series1);
        return dataset;
    }

    public static void createGanttChart(String title) {
        SwingUtilities.invokeLater(() -> {
            GanttChartMaker example = new GanttChartMaker(title);
            example.setSize(800, 400);
            example.setLocationRelativeTo(null);

            example.setVisible(true);
        });
    }

    public static void main(String[] args) {
        createGanttChart("Gantt Chart Example");
    }
}
