package com.example.softwareproject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class VAFController {


    @FXML
    private Button Calculate_VAF;
    @FXML
    private TableView<FactorRow> tableView;

    public void initialize() {
        TableColumn<FactorRow, String> factorColumn = new TableColumn<>("Factor");
        factorColumn.setCellValueFactory(new PropertyValueFactory<>("factorName"));

        TableColumn<FactorRow, String> influenceColumn = new TableColumn<>("Influence Level");
        influenceColumn.setCellValueFactory(new PropertyValueFactory<>("influenceLevel"));
        influenceColumn.setCellFactory(getInfluenceCellFactory());

        tableView.getColumns().addAll(factorColumn, influenceColumn);

        tableView.setItems(FXCollections.observableArrayList(
                new FactorRow("Data Communications"),
                new FactorRow("Distributed Data Processing"),
                new FactorRow("Performance"),
                new FactorRow("Heavily Utilized Hardware"),
                new FactorRow("High Transaction Rates"),
                new FactorRow("Online Data Entry"),
                new FactorRow("End-User Efficiency"),
                new FactorRow("Online Update"),
                new FactorRow("Complex Processing"),
                new FactorRow("Reusability"),
                new FactorRow("Installation Ease"),
                new FactorRow("Operational Ease"),
                new FactorRow("Multiple Sites"),
                new FactorRow("Facilitate Change")
        ));
    }

    private Callback<TableColumn<FactorRow, String>, TableCell<FactorRow, String>> getInfluenceCellFactory() {
        return column -> new TableCell<FactorRow, String>() {
            private final String[] influenceLevels = {
                    "No influence", "Incidental", "Moderate", "Average", "Significant", "Essential"
            };

            private final ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(influenceLevels));

            {
                comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                    if (isEditing()) {
                        commitEdit(newValue);
                    }
                    ((FactorRow) getTableRow().getItem()).setInfluenceLevel(newValue);
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
    private void calculateTDI(ActionEvent event) throws IOException {
        int totalTDI = tableView.getItems().stream()
                .mapToInt(this::getTDIValue)
                .sum();

        System.out.println("Total TDI: " + totalTDI);
        System.out.println("VAF=(TDI*0.01)+0.065 . So VAF=" + (totalTDI * 0.01 + 0.065));

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("UFP.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 720, 480);
        stage.setTitle("e-MED");
        stage.setScene(scene);
        stage.show();
    }



    private int getTDIValue(FactorRow factorRow) {
        String influenceLevel = factorRow.getInfluenceLevel();
        if (influenceLevel != null) {
            switch (influenceLevel) {
                case "No influence":
                    return 0;
                case "Incidental":
                    return 1;
                case "Moderate":
                    return 2;
                case "Average":
                    return 3;
                case "Significant":
                    return 4;
                case "Essential":
                    return 5;
                default:
                    return 0; // Default to 0 if influence level is not recognized
            }
        } else {
            return 0; // Default to 0 if influence level is null
        }
    }

    public static class FactorRow {
        private final StringProperty factorName = new SimpleStringProperty();
        private final StringProperty influenceLevel = new SimpleStringProperty();

        public FactorRow(String factorName) {
            this.factorName.set(factorName);
        }

        public String getFactorName() {
            return factorName.get();
        }

        public StringProperty factorNameProperty() {
            return factorName;
        }

        public String getInfluenceLevel() {
            return influenceLevel.get();
        }

        public StringProperty influenceLevelProperty() {
            return influenceLevel;
        }

        public void setInfluenceLevel(String influenceLevel) {
            this.influenceLevel.set(influenceLevel);
        }
    }


}
