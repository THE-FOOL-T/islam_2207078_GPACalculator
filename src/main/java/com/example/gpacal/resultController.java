package com.example.gpacal;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class resultController {
    @FXML
    private TableView<CourseRow> courseTableView;
    @FXML
    private TableColumn<CourseRow,Integer> serialColumn;
    @FXML
    private TableColumn<CourseRow,String> codeColumn;
    @FXML
    private TableColumn<CourseRow,String> nameColumn;
    @FXML
    private TableColumn<CourseRow,Double> creditColumn;
    @FXML
    private TableColumn<CourseRow,String> teacher1Column;
    @FXML
    private TableColumn<CourseRow,String> teacher2Column;
    @FXML
    private TableColumn<CourseRow,String> gradeColumn;
    @FXML
    private TableColumn<CourseRow,Double> gradePointColumn;
    @FXML
    private Label totalcredL, gpaL, congratsL;
    @FXML
    public void initialize() {
        serialColumn.setCellValueFactory(new PropertyValueFactory<>("serial"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        creditColumn.setCellValueFactory(new PropertyValueFactory<>("credit"));
        teacher1Column.setCellValueFactory(new PropertyValueFactory<>("teacher1"));
        teacher2Column.setCellValueFactory(new PropertyValueFactory<>("teacher2"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
        gradePointColumn.setCellValueFactory(new PropertyValueFactory<>("gradePoint"));

        courseTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        courseTableView.setPrefHeight(TableView.USE_COMPUTED_SIZE);
        courseTableView.setMaxHeight(TableView.USE_COMPUTED_SIZE);
    }

    public void setresult(ObservableList<HelloController.courses> courseList) {
        ObservableList<CourseRow> tableData = FXCollections.observableArrayList();
        double totalCredits = 0;
        double totalPoints = 0;
        int count = 1;

        boolean t1=false;
        boolean t2=false;

        for (HelloController.courses c : courseList) {
            if (c.teacher1!=null && !c.teacher1.trim().isEmpty()) {
                t1=true;
            }
            if (c.teacher2!=null && !c.teacher2.trim().isEmpty()) {
                t2=true;
            }
        }

        teacher1Column.setVisible(t1);
        teacher2Column.setVisible(t2);
        serialColumn.setMinWidth(60);
        codeColumn.setMinWidth(100);
        nameColumn.setMinWidth(200);
        creditColumn.setMinWidth(80);
        gradeColumn.setMinWidth(100);
        gradePointColumn.setMinWidth(100);
        teacher1Column.setMinWidth(130);
        teacher2Column.setMinWidth(130);
        for (HelloController.courses c:courseList) {
            tableData.add(new CourseRow(count++, c.code, c.name, c.credit, c.teacher1, c.teacher2, c.grade, c.gradePoint));
            totalCredits+=c.credit;
            totalPoints+=c.credit*c.gradePoint;
        }
        courseTableView.setItems(tableData);
        double rowHeight = 28.0;
        double headerHeight = 32.0;
        double calculatedHeight = headerHeight + (tableData.size() * rowHeight) + 2;
        courseTableView.setMinHeight(calculatedHeight);
        courseTableView.setPrefHeight(calculatedHeight);
        courseTableView.setMaxHeight(calculatedHeight);
        double gpa;
        if (totalCredits == 0) {
            gpa = 0.0;
        } else {
            gpa=totalPoints/totalCredits;
        }
        totalcredL.setText("Credit Completed : " + String.format("%.2f", totalCredits));
        gpaL.setText("GPA : " + String.format("%.2f", gpa));
        congratsL.setText("Congratulations! Your GPA is shown below.");
    }

    public static class CourseRow {
        private final SimpleIntegerProperty serial;
        private final SimpleStringProperty code;
        private final SimpleStringProperty name;
        private final SimpleDoubleProperty credit;
        private final SimpleStringProperty teacher1;
        private final SimpleStringProperty teacher2;
        private final SimpleStringProperty grade;
        private final SimpleDoubleProperty gradePoint;

        public CourseRow(int serial, String code, String name, double credit, String teacher1, String teacher2, String grade, double gradePoint) {
            this.serial = new SimpleIntegerProperty(serial);
            this.code = new SimpleStringProperty(code);
            this.name = new SimpleStringProperty(name);
            this.credit = new SimpleDoubleProperty(credit);
            this.teacher1 = new SimpleStringProperty(teacher1 == null ? "" : teacher1);
            this.teacher2 = new SimpleStringProperty(teacher2 == null ? "" : teacher2);
            this.grade = new SimpleStringProperty(grade);
            this.gradePoint = new SimpleDoubleProperty(gradePoint);
        }

        public int getSerial() { return serial.get(); }
        public SimpleIntegerProperty serialProperty() { return serial; }

        public String getCode() { return code.get(); }
        public SimpleStringProperty codeProperty() { return code; }

        public String getName() { return name.get(); }
        public SimpleStringProperty nameProperty() { return name; }

        public double getCredit() { return credit.get(); }
        public SimpleDoubleProperty creditProperty() { return credit; }

        public String getTeacher1() { return teacher1.get(); }
        public SimpleStringProperty teacher1Property() { return teacher1; }

        public String getTeacher2() { return teacher2.get(); }
        public SimpleStringProperty teacher2Property() { return teacher2; }

        public String getGrade() { return grade.get(); }
        public SimpleStringProperty gradeProperty() { return grade; }

        public double getGradePoint() { return gradePoint.get(); }
        public SimpleDoubleProperty gradePointProperty() { return gradePoint; }
    }
}