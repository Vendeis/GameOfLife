package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    Button pracownik;
    @FXML
    Button admin;
    @FXML void load(ActionEvent event) throws IOException {
        Button button = (Button)event.getSource();
        if(button.equals(pracownik)){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/sample.fxml"));
            Parent parent = fxmlLoader.load();

            Scene scene2 = new Scene(parent,350,450);
            Stage window = new Stage();
            window.setScene(scene2);
            window.setResizable(false);
            window.show();
        }
        else if(button.equals(admin)){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/admin.fxml"));
            Parent parent = fxmlLoader.load();

            Scene scene2 = new Scene(parent,350,450);
            Stage window = new Stage();
            window.setScene(scene2);
            window.setResizable(false);
            window.show();
        }
    }
}
