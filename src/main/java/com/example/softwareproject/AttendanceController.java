package com.example.softwareproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


import java.sql.*;
import java.time.LocalDate;

public class AttendanceController {
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<AttendanceRecord> attendanceTable;

    @FXML
    private TableColumn<AttendanceRecord, Integer> employeeIdColumn;

    @FXML
    private TableColumn<AttendanceRecord, String> dateColumn;

    @FXML
    private TableColumn<AttendanceRecord, Boolean> statusColumn;

    // Initialize the table and data here

    private ObservableList<AttendanceRecord> attendanceRecords = FXCollections.observableArrayList();

    public void initialize() {
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        attendanceTable.setItems(attendanceRecords);
    }



    @FXML
    private void loadAttendanceData() {
        LocalDate selectedDate = datePicker.getValue();
        if (selectedDate != null) {
            ObservableList<AttendanceRecord> data = fetchAttendanceDataFromDatabase(selectedDate);
            displayAttendanceData(data);
        }
    }



    @FXML
    private TextField employeeIdInput;

    @FXML
    private CheckBox statusInput;

    // ... Other methods ...

    @FXML
    private void markAttendance() {
        String employeeIdText = employeeIdInput.getText();
        boolean isStatusSelected = statusInput.isSelected();
        LocalDate selectedDate = datePicker.getValue();

        if (!employeeIdText.isEmpty()) {
            try {
                int employeeId = Integer.parseInt(employeeIdText);

                if (selectedDate != null) {
                    boolean duplicateEntry = false;
                    for (AttendanceRecord existingRecord : attendanceRecords) {
                        if (existingRecord.getEmployeeId() == employeeId &&
                                existingRecord.getDate().equals(selectedDate.toString())) {
                            duplicateEntry = true;
                            break;
                        }
                    }

                    if (!duplicateEntry) {
                        AttendanceRecord record = new AttendanceRecord(employeeId, selectedDate.toString(), isStatusSelected);
                        attendanceRecords.add(record);
                        insertAttendanceToDatabase(employeeId, selectedDate, isStatusSelected);
                        errorLabel.setText("");
                    } else {
                        errorLabel.setText("Duplicate entry for Employee ID and Date.");
                    }
                } else {
                    errorLabel.setText("Please select a date.");
                }
            } catch (NumberFormatException e) {
                errorLabel.setText("Invalid employee ID. Please enter a valid number.");
            }
        } else {
            errorLabel.setText("Please enter an employee ID.");
        }
    }


    @FXML
    private void generateMonthlyAttendance() {
        LocalDate selectedDate = datePicker.getValue();
        if (selectedDate != null) {
            int year = selectedDate.getYear();
            int month = selectedDate.getMonthValue();

            new MonthlyAttendanceChart(year,month);
        }
    }


    private ObservableList<AttendanceRecord> fetchAttendanceDataFromDatabase(LocalDate selectedDate) {
        ObservableList<AttendanceRecord> data = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "200041123")) {
            String sql = "SELECT employee_id, status FROM attendance WHERE date = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, java.sql.Date.valueOf(selectedDate));

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int employeeId = resultSet.getInt("employee_id");
                boolean status = resultSet.getBoolean("status");
                data.add(new AttendanceRecord(employeeId, selectedDate.toString(), status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }



    private void displayAttendanceData(ObservableList<AttendanceRecord> data) {
        // Clear and update the TableView
        attendanceTable.getItems().clear();
        attendanceTable.getItems().addAll(data);
    }

    @FXML
    private Label errorLabel;

    private void insertAttendanceToDatabase(int employeeId, LocalDate date, boolean status) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "200041123")) {
            String sql = "INSERT INTO attendance (employee_id, date, status) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, employeeId);
            preparedStatement.setDate(2, java.sql.Date.valueOf(date));
            preparedStatement.setBoolean(3, status);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            errorLabel.setText("ok lol ");
            System.out.println("Error Code: " + e.getErrorCode()); // Print the error code
        }
        }




    public static class AttendanceRecord {
        private final int employeeId;
        private final String date;
        private final boolean status;

        public AttendanceRecord(int employeeId, String date, boolean status) {
            this.employeeId = employeeId;
            this.date = date;
            this.status = status;
        }

        public int getEmployeeId() {
            return employeeId;
        }

        public String getDate() {
            return date;
        }

        public boolean isStatus() {
            return status;
        }
    }
}