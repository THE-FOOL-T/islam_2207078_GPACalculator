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

    private final ObservableList<String> csDisplay=FXCollections.observableArrayList();
    private int csCount=0;
    private double totalCred=0;
    private final ObservableList<courses>  fullcourse=FXCollections.observableArrayList();
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
    private final java.util.Map<String, courses> res = new java.util.HashMap<>();
    @FXML
    private void AddCourse() {
        String name=csName.getText();
        String code=csNo.getText();
        String creds=cred.getText();
        String grade=grad.getValue();
        if (name==null || code==null || grade==null || creds==null ||
                name.isEmpty() || code.isEmpty() || creds.isEmpty()) {
            return;
        }
        double credit;
        try {
            credit=Double.parseDouble(creds);
        } catch (NumberFormatException e) {
            return;
        }
        csDisplay.add("course name: "+name + "  code: " + code + "  grade:" + grade + "  credit: " + credit + " cr");
        csCount++;
        totalCred+=credit;
        update();
        courses cs=new courses(code,name,credit,teach1.getText(),teach2.getText(),grade,gradcal(grade));
        fullcourse.add(cs);
        res.put(code,cs);
        csName.clear();
        csNo.clear();
        teach1.clear();
        teach2.clear();
        cred.clear();
        grad.getSelectionModel().clearSelection();
    }
    public static class courses {
        public final String code, name, teacher1, teacher2, grade;
        public final double credit, gradePoint;
        public courses(String code, String name, double credit, String teacher1, String teacher2, String grade, double gradePoint) {
            this.code=code; this.name=name; this.credit=credit; this.teacher1=teacher1; this.teacher2=teacher2; this.grade=grade; this.gradePoint=gradePoint;
        }
    }
    private void update() {
        if (csCountL!=null) {
            csCountL.setText("Total Courses: "+csCount);
        }
        if (toalCredL!=null) {
            toalCredL.setText("Total Credits: "+totalCred);
        }
    }
    private double gradcal(String grad) {
        switch(grad){
            case "A+":return 4.0;
            case "A":return 3.75;
            case "A-":return 3.5;
            case "B+":return 3.25;
            case "B":return 3.0;
            case "B-":return 2.75;
            case "C+":return 2.5;
            case "C":return 2.25;
            case "D":return 2.0;
            case "F":return 0.0;
            default:return 0.0;
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
        FXMLLoader loader=new FXMLLoader(Objects.requireNonNull(getClass().getResource("result.fxml")));
        root=loader.load();
        resultController rc=loader.getController();
        rc.setresult(fullcourse);
        scene=new Scene(root);
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene((scene));
        stage.show();

    }

}
