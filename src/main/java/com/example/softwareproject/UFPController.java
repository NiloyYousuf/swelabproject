package com.example.softwareproject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UFPController {

    @FXML
    private TableView<TaskRow> tableView;
    private ObservableList<Task> taskLists = FXCollections.observableArrayList();

  @FXML
    private void initialize() {

        TableColumn<TaskRow, String>TasknameColumn = new TableColumn<>("TaskName");
        TasknameColumn.setCellValueFactory(new PropertyValueFactory<>("TaskName"));

        TableColumn<TaskRow, String> WeightColumn = new TableColumn<>("Weight");
        WeightColumn.setCellValueFactory(new PropertyValueFactory<>("WeightLevel"));
        WeightColumn.setCellFactory(getWeightCellFactory());

        TableColumn<TaskRow, String> MeasurementParameterColumn = new TableColumn<>("MeasurementParameter");
        MeasurementParameterColumn.setCellValueFactory(new PropertyValueFactory<>("MeasurementParameter"));
        MeasurementParameterColumn.setCellFactory(getMeasurementParameterCellFactory());

        tableView.getColumns().addAll(TasknameColumn, WeightColumn,MeasurementParameterColumn);

    }

    private Callback<TableColumn<TaskRow, String>, TableCell<TaskRow, String>> getMeasurementParameterCellFactory() {
        return column -> new TableCell<TaskRow, String>() {
            private final String[] MeasurementParameters = {
                    "external inputs (EI)", "external outputs (EO)", "external inquiries (EQ)", "internal files (ILF)", "external interfaces (EIF)"
            };

            private final ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(MeasurementParameters));

            {
                comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                    if (isEditing()) {
                        commitEdit(newValue);
                    }
                    ((TaskRow) getTableRow().getItem()).setMeasurementParameter(newValue);
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    comboBox.setValue(item);
                    setGraphic(comboBox);
                }
            }

            @Override
            public void startEdit() {
                super.startEdit();
                comboBox.setValue(getItem());
                setGraphic(comboBox);
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                setGraphic(comboBox);
            }

            @Override
            public void commitEdit(String newValue) {
                super.commitEdit(newValue);
                setGraphic(comboBox);
            }
        };
    }



    private Callback<TableColumn<TaskRow, String>, TableCell<TaskRow, String>> getWeightCellFactory() {
        return column -> new TableCell<TaskRow, String>() {
            private final String[] WeightLevels = {
                    "LOW", "AVERAGE", "HIGH"
            };

            private final ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(WeightLevels));

            {
                comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                    if (isEditing()) {
                        commitEdit(newValue);
                    }
                    ((TaskRow) getTableRow().getItem()).setWeightLevel(newValue);
                });
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    comboBox.setValue(item);
                    setGraphic(comboBox);
                }
            }
            @Override
            public void startEdit() {
                super.startEdit();
                comboBox.setValue(getItem());
                setGraphic(comboBox);
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                setGraphic(comboBox);
            }
            @Override
            public void commitEdit(String newValue) {
                super.commitEdit(newValue);
                setGraphic(comboBox);
            }
        };
    }

    @FXML
        private void deleteSelectedRow() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            tableView.getItems().remove(selectedIndex);
        }
    }

    public  static  double AFP;
    @FXML
    private void calculateUFP(ActionEvent event) {
        int totalTDI = tableView.getItems().stream()
                .mapToInt(this::getUFPValue)
                .sum();

        System.out.println("Total UFP: " + totalTDI);
        AFP=totalTDI*VAFController.VAF;
        System.out.println(" AFP is : "+AFP);

    }

    @FXML
    TextArea taskName;
    @FXML
    private  void AddTaskButtonPressed(ActionEvent event)
    {

        tableView.getItems().add(0,new TaskRow(taskName.getText()));

    }



    public Integer getParameterValues(String parameterName,String low_average_high) {
        Connection connection = null;
        try {
            connection = DatabaseConnector.getConnection();
            System.out.println("Connected to database.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        String query = "SELECT Low, Average, High FROM MeasurementParameters WHERE ParameterName = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, parameterName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                System.out.println("Parameter: " + parameterName);
                if(low_average_high=="LOW")
                { return Integer.parseInt(resultSet.getString(1));}
                else if(low_average_high=="AVERAGE")
                { return Integer.parseInt(resultSet.getString(2));}
                else if(low_average_high=="HIGH")
                {return Integer.parseInt(resultSet.getString(3));}

            } else {
                System.out.println("Parameter not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    private int getUFPValue(TaskRow measurementParameterRow) {
        String WeightLevel = measurementParameterRow.getWeightLevel();
        String Measurement=measurementParameterRow.getMeasurementParameter();
        int value=getParameterValues(Measurement,WeightLevel);
        System.out.println(value);
        return  value;
    }

    public static class TaskRow {
        private final StringProperty TaskName = new SimpleStringProperty();
        private final StringProperty WeightLevel = new SimpleStringProperty();

        private final StringProperty MeasurementParameter=new SimpleStringProperty();

        public TaskRow(String TaskName) {
            this.TaskName.set(TaskName);
        }

        public String TaskName() {
            return TaskName.get();
        }

        public StringProperty TaskNameProperty() {
            return TaskName;
        }

        public String getWeightLevel() {
            return WeightLevel.get();
        }

        public StringProperty WeightLevelProperty() {
            return WeightLevel;
        }

        public void setWeightLevel(String WeightLevel) {
            this.WeightLevel.set(WeightLevel);
        }








        public String getMeasurementParameter() {
            return MeasurementParameter.get();
        }

        public StringProperty MeasurementParameterProperty() {
            return MeasurementParameter;
        }

        public void setMeasurementParameter(String MeasurementParameter) {
            this.MeasurementParameter.set(MeasurementParameter);
        }




    }


}
