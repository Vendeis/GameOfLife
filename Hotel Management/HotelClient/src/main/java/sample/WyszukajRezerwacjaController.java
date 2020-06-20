package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;
import java.sql.Date;

public class WyszukajRezerwacjaController {
    @FXML
    private TextField textField;

    @FXML
    private void wyszukaj(ActionEvent event) throws IOException {
        int id = Integer.parseInt(textField.getText());
        JSONObject json = new JSONObject();
        json.put("wyszukaj",id);
        ServerOperation operation = new ServerOperation(json);
        JSONObject output = operation.getAnswerFromServer();

        if(output.has("blad")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("brak rezerwacji o id " + id + "!");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            String s = "Id: " + output.getInt("id") +
                    "\nImie: " + output.getString("imie") +
                    "\nNazwisko: " + output.getString("nazwisko") +
                    "\nNr_pokoju: " + output.getInt("nr_pokoju") +
                    "\nData_przyjazdu: " + Date.valueOf(output.getString("data_przyjazdu")) +
                    "\nData_wyjazdu: " + Date.valueOf(output.getString("data_wyjazdu")) +
                    "\nParking: " + output.getString("parking");
            alert.setContentText(s);
            alert.showAndWait();
            Node source = (Node)event.getSource();
            Stage stage = (Stage)source.getScene().getWindow();
            stage.close();

        }
    }
}

