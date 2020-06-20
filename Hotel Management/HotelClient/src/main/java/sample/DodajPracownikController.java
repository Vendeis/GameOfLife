package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;

public class DodajPracownikController {
    @FXML
    private TextField imie;
    @FXML
    private TextField nazwisko;
    @FXML
    private TextField pensja;
    @FXML
    private ComboBox stanowisko;
    private JSONObject input;
    public void initialize(){
        input = new JSONObject();
    }
    @FXML
    public void wybierzStanowisko(ActionEvent event){
    }


    @FXML
    public void dodaj(ActionEvent event) throws IOException {
        if(imie.getLength() == 0 || nazwisko.getLength() == 0 || pensja.getLength() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Wprowadź poprawne dane!");
            alert.showAndWait();
            return;
        }
        input.put("imie",imie.getCharacters());
        input.put("nazwisko",nazwisko.getCharacters());
        input.put("stanowisko",stanowisko.getValue());
        input.put("pensja",pensja.getCharacters());

        JSONObject input1 = new JSONObject();
        input1.put("dodaj",input);
        ServerOperation operation = new ServerOperation(input1);

        JSONObject output = operation.getAnswerFromServer();
        if(output.has("blad")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Nie mozna dodac pracownika");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText(" Dodano pracownika");
            alert.showAndWait();
            Node source = (Node)event.getSource();
            Stage stage = (Stage)source.getScene().getWindow();
            stage.close();

        }

    }

}
