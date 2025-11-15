package com.example.gpacal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloController {
    @FXML
    private Label welcomeText;
      private Stage stage;
      private Scene scene;
      private Parent root;
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
  public void switchScene(ActionEvent actionEvent) throws IOException {
      root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("calscene.fxml")));
      stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
      scene= new Scene(root);
      stage.setScene(scene);
      stage.show();
  }
}
