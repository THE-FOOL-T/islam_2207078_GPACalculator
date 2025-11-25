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


}
