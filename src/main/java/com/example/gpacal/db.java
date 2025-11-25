package com.example.gpacal;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class db {
    private static final String DB_URL = "jdbc:sqlite:course.db";

    static { init(); }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void init() {
        String sql = "CREATE TABLE IF NOT EXISTS students (roll TEXT PRIMARY KEY, name TEXT NOT NULL, cgpa REAL NOT NULL, dateAdded TEXT NOT NULL)";
        try (Connection conn = getConnection(); Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) { e.printStackTrace(); }
    }
    public static Task<ObservableList<StudentRecord>> fetchAllTask() {
        return new Task<>() {
            @Override
            protected ObservableList<StudentRecord> call() throws Exception {
                ObservableList<StudentRecord> list = FXCollections.observableArrayList();
                String q = "SELECT roll, name, cgpa, dateAdded FROM students ORDER BY roll";
                try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(q); ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String roll = rs.getString("roll");
                        String name = rs.getString("name");
                        double cgpa = rs.getDouble("cgpa");
                        String date = rs.getString("dateAdded");
                        list.add(new StudentRecord(roll, name, cgpa, date));
                    }
                }
                return list;
            }
        };
    }

    public static Task<Boolean> insertStudentTask(String roll, String name, double cgpa) {
        return new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                String q = "INSERT INTO students(roll, name, cgpa, dateAdded) VALUES(?,?,?,?)";
                String date = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
                try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(q)) {
                    ps.setString(1, roll);
                    ps.setString(2, name);
                    ps.setDouble(3, cgpa);
                    ps.setString(4, date);
                    return ps.executeUpdate() > 0;
                }
            }
        };
    }

    public static Task<Boolean> updateStudentTask(String roll, String name, double cgpa) {
        return new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                String q = "UPDATE students SET name=?, cgpa=?, dateAdded=? WHERE roll=?";
                String date = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
                try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(q)) {
                    ps.setString(1, name);
                    ps.setDouble(2, cgpa);
                    ps.setString(3, date);
                    ps.setString(4, roll);
                    return ps.executeUpdate() > 0;
                }
            }
        };
    }

    public static Task<Boolean> deleteStudentTask(String roll) {
        return new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                String q = "DELETE FROM students WHERE roll=?";
                try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(q)) {
                    ps.setString(1, roll);
                    return ps.executeUpdate() > 0;
                }
            }
        };
    }

    public static class StudentRecord {
        private final SimpleStringProperty roll;
        private final SimpleStringProperty name;
        private final SimpleDoubleProperty cgpa;
        private final SimpleStringProperty dateAdded;

        public StudentRecord(String roll, String name, double cgpa, String dateAdded) {
            this.roll = new SimpleStringProperty(roll);
            this.name = new SimpleStringProperty(name);
            this.cgpa = new SimpleDoubleProperty(cgpa);
            this.dateAdded = new SimpleStringProperty(dateAdded);
        }

        public String getRoll() { return roll.get(); }
        public SimpleStringProperty rollProperty() { return roll; }

        public String getName() { return name.get(); }
        public SimpleStringProperty nameProperty() { return name; }

        public double getCgpa() { return cgpa.get(); }
        public SimpleDoubleProperty cgpaProperty() { return cgpa; }

        public String getDateAdded() { return dateAdded.get(); }
        public SimpleStringProperty dateAddedProperty() { return dateAdded; }
    }


}
