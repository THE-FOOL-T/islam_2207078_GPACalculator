package com.example.gpacal;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class dbHelper {
    @FXML private TableView<db.StudentRecord> table;
    @FXML private TableColumn<db.StudentRecord, String> rollCol;
    @FXML private TableColumn<db.StudentRecord, String> nameCol;
    @FXML private TableColumn<db.StudentRecord, Number> cgpaCol;
    @FXML private TableColumn<db.StudentRecord, String> dateCol;

    @FXML private TextField rollField;
    @FXML private TextField nameField;
    @FXML private TextField cgpaField;

    @FXML private Button addBtn, updateBtn, deleteBtn, backBtn;

    @FXML
    public void initialize() {
        rollCol.setCellValueFactory(cell -> cell.getValue().rollProperty());
        nameCol.setCellValueFactory(cell -> cell.getValue().nameProperty());
        cgpaCol.setCellValueFactory(cell -> cell.getValue().cgpaProperty());
        dateCol.setCellValueFactory(cell -> cell.getValue().dateAddedProperty());

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                rollField.setText(newV.getRoll());
                nameField.setText(newV.getName());
                cgpaField.setText(String.valueOf(newV.getCgpa()));
            }
        });

        loadTable();
    }

    private void loadTable() {
        Task<ObservableList<db.StudentRecord>> task = db.fetchAllTask();
        task.setOnSucceeded(e -> table.setItems(task.getValue()));
        new Thread(task).start();
    }

    public void addRecord() {
        String roll = rollField.getText();
        String name = nameField.getText();
        double cgpa = Double.parseDouble(cgpaField.getText());

        Task<Boolean> t = db.insertStudentTask(roll, name, cgpa);
        t.setOnSucceeded(e -> loadTable());
        new Thread(t).start();
    }

    public void updateRecord() {
        String roll = rollField.getText();
        String name = nameField.getText();
        double cgpa = Double.parseDouble(cgpaField.getText());

        Task<Boolean> t = db.updateStudentTask(roll, name, cgpa);
        t.setOnSucceeded(e -> loadTable());
        new Thread(t).start();
    }

    public void deleteRecord() {
        String roll = rollField.getText();

        Task<Boolean> t = db.deleteStudentTask(roll);
        t.setOnSucceeded(e -> loadTable());
        new Thread(t).start();
    }

    public void goBack(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("result.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
