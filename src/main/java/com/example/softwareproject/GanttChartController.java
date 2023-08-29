package com.example.softwareproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class GanttChartController {

    @FXML
    private TableView<Task> tableView;

    @FXML
    private TextField nameField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    // Create an ObservableList to hold tasks
    private ObservableList<Task> taskList = FXCollections.observableArrayList();

    @FXML
    private void addTask(ActionEvent event) {
        // Add the task to the taskList
        String name = nameField.getText();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (name != null && startDate != null && endDate != null) {
            Task newTask = new Task(name, startDate, endDate);
            taskList.add(newTask);

            // Clear input fields and refresh the TableView
            nameField.clear();
            startDatePicker.setValue(null);
            endDatePicker.setValue(null);

            // Refresh the TableView
            tableView.refresh();
        }
    }

    @FXML
    private void initialize() {
        TableColumn<Task, String> nameColumn = new TableColumn<>("Task Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Task, LocalDate> startDateColumn = new TableColumn<>("Start Date");
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        TableColumn<Task, LocalDate> endDateColumn = new TableColumn<>("End Date");
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));





        tableView.getColumns().addAll(nameColumn, startDateColumn, endDateColumn);
        tableView.setItems(taskList);
    }

    @FXML
    private void deleteSelectedRow() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            tableView.getItems().remove(selectedIndex);
        }
    }
    @FXML
    private void createArrayList(ActionEvent event) {
        ArrayList<Task> taskArrayList = TaskUtils.convertToArrayList(taskList);;

        // Now you have an ArrayList containing all the tasks from the TableView
        // You can use this ArrayList as needed
        for (Task task : TaskUtils.tasks) {
            System.out.println("Name: " + task.getName() + ", Start Date: " + task.getStartDate() + ", End Date: " + task.getEndDate());
        }
        SwingUtilities.invokeLater(() -> {
            GanttChartMaker example = new GanttChartMaker("Gantt Chart Example");
            example.setSize(720, 480);
            example.setLocationRelativeTo(null);
         //   example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}
