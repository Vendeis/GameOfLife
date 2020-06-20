package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;

public class RezerwacjaController {
    @FXML
    private TextField imie;
    @FXML
    private TextField nazwisko;
    @FXML
    private DatePicker przyjazd;
    @FXML
    private DatePicker wyjazd;
    @FXML
    private ComboBox pokoje;
    @FXML
    private ComboBox pokojeDeluxe;
    @FXML
    private CheckBox parking;

    private JSONObject input;
    private int pokoj = 0;
    public void initialize(){
      przyjazd.setValue(LocalDate.now());
      wyjazd.setValue(LocalDate.now());
      input = new JSONObject();
    }
    @FXML
    public void wybierzPokoj(ActionEvent event){
        ComboBox comboBox = (ComboBox)event.getSource();

        try {
            if (comboBox.equals(pokoje)) {
                pokoj = Integer.parseInt((String) comboBox.getValue());
                pokojeDeluxe.setValue("-");
            } else if (comboBox.equals(pokojeDeluxe)) {
                pokoj = Integer.parseInt((String) comboBox.getValue());
                pokoje.setValue("-");
            }
        }
        catch (Exception e){}

    }
    @FXML
    public void rezerwuj(ActionEvent event) throws IOException {
        if(imie.getLength() == 0 || nazwisko.getLength() == 0 || pokoj == 0 ||
                przyjazd.getValue().isAfter(wyjazd.getValue()) ||
                przyjazd.getValue().equals(wyjazd.getValue())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Wprowadź poprawne dane!");
            alert.showAndWait();
            return;
        }
        input.put("imie",imie.getCharacters());
        input.put("nazwisko",nazwisko.getCharacters());
        if(pokojeDeluxe.getValue().equals("-"))
            input.put("nr_pokoju",pokoje.getValue());
        else
            input.put("nr_pokoju",pokojeDeluxe.getValue());
        input.put("data_przyjazdu",przyjazd.getValue());
        input.put("data_wyjazdu",wyjazd.getValue());
        if(parking.isSelected())
            input.put("parking","Y");
        else
            input.put("parking","N");


        JSONObject input1 = new JSONObject();
        input1.put("rezerwuj",input);
        ServerOperation operation = new ServerOperation(input1);

        JSONObject output = operation.getAnswerFromServer();
        if(output.has("blad")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText(" Pokój zajęty!");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText(" Zarezerwowano pokoj " + output.getInt("nr_pokoju") +
                    " na " + output.getLong("czas_pobytu") + " dni \n cena wynosi " +
                    output.getInt("cena") + " złotych");
            alert.showAndWait();
            Node source = (Node)event.getSource();
            Stage stage = (Stage)source.getScene().getWindow();
            stage.close();

        }

    }

}
