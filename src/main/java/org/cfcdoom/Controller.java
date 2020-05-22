package org.cfcdoom;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class Controller {

    @FXML
    public Button submit;
    public ListView resultList;
    public ComboBox colorDropdown;
    public TextField quantity;
    public TextField mailCost;
    public ComboBox paperDropdown;
    public ComboBox sizeDropdown;
    public CheckBox bleedBox;


    File paperFile = Main.myFile;

    ObservableList<Color> colorDropdownList = FXCollections.observableArrayList(Color.values());
    ObservableList<org.cfcdoom.Paper> paperDropdownList;
    ObservableList<CardSize> sizeDropdownList;

    @FXML
    public void initialize() {
        colorDropdown.setItems(colorDropdownList);
        trackPaperCompatibility(paperDropdown);
        checkInputsInt(quantity);

        try {
            FileInputStream fis = new FileInputStream(paperFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            PaperListWrapper papers = (PaperListWrapper) ois.readObject();
            paperDropdownList = FXCollections.observableArrayList(papers.getPapers());
            paperDropdown.setItems(paperDropdownList);
            paperDropdown.setConverter(new StringConverter<org.cfcdoom.Paper>() {
                @Override
                public String toString(org.cfcdoom.Paper object) {
                    return object.getName();
                }
                @Override
                public org.cfcdoom.Paper fromString(String string) {
                    return null;
                }

            });
            ois.close();
            fis.close();

        } catch (IOException | ClassNotFoundException e){
            System.out.println("IO exception papers.obj not found: 404");
        }

    }

    private void trackPaperCompatibility(ComboBox paperDropdown) {
        paperDropdown.valueProperty().addListener(new ChangeListener<org.cfcdoom.Paper>() {
            @Override
            public void changed(ObservableValue<? extends org.cfcdoom.Paper> observable, org.cfcdoom.Paper oldValue, org.cfcdoom.Paper newValue) {
                List<CardSize> availibleCards = observable.getValue().getCompatibleSizes();
                sizeDropdownList = FXCollections.observableArrayList(availibleCards);
                sizeDropdown.setItems(sizeDropdownList);
            }

        });
    }

    public void runCalc(ActionEvent actionEvent) {

        if (checkFeilds()){
            try {
                int upOnSheet;
                if (bleedBox.isSelected()){
                    upOnSheet = ((CardSize) sizeDropdown.getValue()).withBleed;
                } else {
                    upOnSheet = ((CardSize) sizeDropdown.getValue()).noBleed;
                }
                org.cfcdoom.Calculations calculator = new org.cfcdoom.Calculations(
                        Integer.parseInt(quantity.getText()),
                        upOnSheet,
                        Double.parseDouble(mailCost.getText()),
                        (Color) colorDropdown.getValue());

                resultList.getItems().add(0,new Text(String.valueOf(calculator.getTotalCost())));
            } catch (Exception e) {
                resultList.getItems().add(0,new Text("internal error"));
            }

        }

    }

    private boolean checkFeilds() {
        if (mailCost.getText().length() == 0){
            mailCost.setText("0");
        }
        try {
            boolean ret = Integer.parseInt(quantity.getText()) > 0 &&
                    !sizeDropdown.getSelectionModel().isEmpty() &&
                    !paperDropdown.getSelectionModel().isEmpty() &&
                    Double.parseDouble(mailCost.getText()) >= 0 &&
                    !colorDropdown.getSelectionModel().isEmpty();

            return ret;
        } catch (Exception e){
            resultList.getItems().add(0, (new Text(("Invalid Input(s)!"))));
        }
        return false;
    }


    public void checkInputsInt(TextField t){
        t.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    t.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public void goToSettings(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("settingsPage.fxml"));
            org.cfcdoom.Main.setScene(root);
        } catch (IOException e) {
            System.out.println("could not find settings.fxml: 404");
            e.printStackTrace();
        }
    }
}
