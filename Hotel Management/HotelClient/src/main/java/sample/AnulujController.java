package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;

public class AnulujController {
    @FXML
    TextField textField;
    public void anuluj(ActionEvent event) throws IOException {
        int id = Integer.parseInt(textField.getText());
        JSONObject json = new JSONObject();
        json.put("anuluj",id);
        ServerOperation operation = new ServerOperation(json);
        JSONObject output = operation.getAnswerFromServer();
        if(output.has("blad")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("brak rezerwacji o id " + id + "!");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("usunieto rezerwacje o id " + output.getInt("sukces"));
            alert.showAndWait();
            Node source = (Node)event.getSource();
            Stage stage = (Stage)source.getScene().getWindow();
            stage.close();
        }
    }
}
