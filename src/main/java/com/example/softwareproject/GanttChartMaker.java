package com.example.softwareproject;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JFrame;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;


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
                dataset,b,b,b);

        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private IntervalCategoryDataset getCategoryDataset() {

        TaskSeries series1 = new TaskSeries("Estimated Date");

      int i=TaskUtils.tasks.size();
      for(int k=0;k<i;k++)
      {

          LocalDate start_date=TaskUtils.tasks.get(k).getStartDate();
          LocalDate end_date=TaskUtils.tasks.get(k).getEndDate();


          ZoneId zoneId = ZoneId.systemDefault(); // or a specific ZoneId
          Instant start_instant = start_date.atStartOfDay(zoneId).toInstant();
          Instant end_instant= end_date.atStartOfDay(zoneId).toInstant();

          series1.add(new Task(TaskUtils.tasks.get(k).getName(),
                  Date.from(start_instant),
                  Date.from(end_instant)
          ));
      }

        TaskSeriesCollection dataset = new TaskSeriesCollection();
        dataset.add(series1);
        return dataset;
    }

} 