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
import java.util.List;

public class GanttChartController {

    @FXML
    private TableView<Task> tableView;

    @FXML
    private ComboBox dependencyComboBox;
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

        // LocalDate startDate = startDatePicker.getValue();
        // LocalDate endDate = endDatePicker.getValue();



//        if (name != null && startDate != null && endDate != null) {
//            Task newTask = new Task(name, startDate, endDate);
//            taskList.add(newTask);
//
//            // Clear input fields and refresh the TableView
//            nameField.clear();
//            startDatePicker.setValue(null);
//            endDatePicker.setValue(null);
//
//            // Refresh the TableView
//            tableView.refresh();
//        }

        if (name != null ) {
            Task newTask = new Task(name);
            taskList.add(newTask);

            // Clear input fields and refresh the TableView
            nameField.clear();
            populateDependencyComboBox();
            // Refresh the TableView
            tableView.refresh();
            TaskUtils.convertToArrayList(taskList);

        }


    }

    @FXML
    private void initialize() {
        TableColumn<Task, String> nameColumn = new TableColumn<>("Task Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Task, Integer> weeksColumn = new TableColumn<>("Weeks");
        weeksColumn.setCellValueFactory(new PropertyValueFactory<>("noOfWeeks"));

        TableColumn<Task,String > dependencyColumn = new TableColumn<>("Dependency");
        dependencyColumn.setCellValueFactory(new PropertyValueFactory<>("Dependencies"));



        tableView.getColumns().addAll(nameColumn, weeksColumn,dependencyColumn);
        tableView.setItems(taskList);



        ObservableList<Integer> weekValues = FXCollections.observableArrayList();

        // Populate the list with values from 1 to 10
        for (int i = 1; i <= 10; i++) {
            weekValues.add(i);
        }

        // Set the items of weekComboBox
        weekComboBox.setItems(weekValues);

        // Set an initial selection (optional)
        weekComboBox.getSelectionModel().selectFirst();
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
        ArrayList<Task> taskArrayList = TaskUtils.convertToArrayList(taskList);
        ;
        // Now you have an ArrayList containing all the tasks from the TableView
        // You can use this ArrayList as needed
      //  TaskScheduler.createGanttChartTasks(taskList);
       // TaskScheduler.calculateWeekRange();
        for (Task task : TaskUtils.tasks) {
            System.out.println("Name: " + task.getName() + ", No of weeks" + task.getNoOfWeeks());

        }
            SwingUtilities.invokeLater(() -> {
//                GanttChartMaker example = new GanttChartMaker("Gantt Chart Example");
//                example.setSize(720, 480);
//                example.setLocationRelativeTo(null);
//                //   example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//                example.setVisible(true);
            });
       GanttChartMaker.createGanttChart("yeay");
        }



    @FXML
    private Button Dependency_button;



    @FXML
    private void Add_dependency() {
        Task selectedTask = tableView.getSelectionModel().getSelectedItem();

        if (selectedTask != null) {
            String taskName = selectedTask.getName();
            // Now you have the name of the selected task
            System.out.println("Selected Task Name: " + taskName);
        } else {
            // Handle case when no task is selected
            System.out.println("No task selected.");

        }
        selectedTask.addDependency(dependencyComboBox.getValue().toString());
        tableView.refresh();
        Task.printAllTasks();
       // System.out.println(dependencyComboBox.getValue().toString());

    }


    @FXML
   private ComboBox weekComboBox;

    @FXML
    private void Add_weeks() {
        Task selectedTask = tableView.getSelectionModel().getSelectedItem();

        if (selectedTask != null) {
            String taskName = selectedTask.getName();
            // Now you have the name of the selected task
            System.out.println("Selected Task Name: " + taskName);

            // Assuming you have a ComboBox for selecting the number of weeks
            Integer weeks = (Integer) weekComboBox.getValue(); // Explicit casting to Integer

            if (weeks != null) {
                selectedTask.setNoOfWeeks(weeks);
                tableView.refresh();
                Task.printAllTasks();
                System.out.println("Added " + weeks + " weeks to the task.");
            } else {
                System.out.println("No week selected.");
            }
        } else {
            // Handle case when no task is selected
            System.out.println("No task selected.");
        }
    }



    private void populateDependencyComboBox() {
        dependencyComboBox.getItems().clear(); // Clear existing items

        for (Task task : taskList) {
            dependencyComboBox.getItems().add(task.getName());

        }
    }


}
