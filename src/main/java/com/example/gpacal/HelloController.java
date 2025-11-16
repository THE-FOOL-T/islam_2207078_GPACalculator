package com.example.gpacal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class HelloController {
    @FXML
    private ComboBox<String> grad;
    @FXML
    private ListView<String> cslist;
    @FXML
    private TextField csName,csNo, teach1, teach2, cred;
    @FXML
    private Label csCountL, toalCredL;
    private Label welcomeText;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private final ObservableList<String> csDisplay = FXCollections.observableArrayList();
    private int csCount = 0;
    private int totalCred = 0;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    public void initialize() {
        if (grad!=null) {
            grad.getItems().addAll("A+","A","A-","B+","B","B-","C+","C","C-","D","F");
        }
        if (cslist!=null) {
           cslist.setItems(csDisplay);
        }
        update();
    }

    @FXML
    private void handleAddCourse() {
        String name=csName.getText();
        String code=csNo.getText();
        String creds=cred.getText();
        String grade=grad.getValue();
        if (name==null || code==null || grade==null || creds==null ||
                name.isEmpty() || code.isEmpty() || creds.isEmpty()) {
            return;
        }
        int credit;
        try {
            credit=Integer.parseInt(creds);
        } catch (NumberFormatException e) {
            return;
        }
        csDisplay.add("course name: "+name + "  code: " + code + "  grade:" + grade + "  credit: " + credit + " cr");

        csCount++;
        totalCred+=credit;
        update();
        csName.clear();
        csNo.clear();
        teach1.clear();
        teach2.clear();
        cred.clear();
        grad.getSelectionModel().clearSelection();
    }

    private void update() {
        if (csCountL!=null) {
            csCountL.setText("Total Courses: "+csCount);
        }
        if (toalCredL!=null) {
            toalCredL.setText("Total Credits: "+totalCred);
        }
    }

    public void switchScene(ActionEvent actionEvent) throws IOException {
        root=FXMLLoader.load(Objects.requireNonNull(getClass().getResource("calscene.fxml")));
        stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchResult(ActionEvent event) throws IOException {
        root=FXMLLoader.load(Objects.requireNonNull(getClass().getResource("result.fxml")));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene((scene));
        stage.show();

    }

}
