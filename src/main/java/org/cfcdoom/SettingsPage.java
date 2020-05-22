package org.cfcdoom;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class SettingsPage {

    public Button removePaperButton;
    public ListView papersList;
    public TextField paperName;
    public Button addPaperButton;
    public TextField price;
    public Button backButton;
    public ListView<CardSize> cardSizes;
    public Button importButton;
    public StackPane mainPane;
    private List<CardSize> newCardSizes;
    File myfile = Main.myFile;


    public void initialize() throws IOException {
        loadPapers();
        newCardSizes = new ArrayList<>();

        ObservableList<CardSize> loadedSizes = FXCollections.observableArrayList(CardSize.values());
        cardSizes.setItems(loadedSizes);
        cardSizes.setCellFactory(CheckBoxListCell.forListView(new myCaller()));

    }


    class myCaller implements Callback<CardSize, ObservableValue<Boolean>> {
        @Override
        public ObservableValue<Boolean> call(CardSize param) {
            return param.possible;
        }
    }


    public void importCSV(ActionEvent actionEvent) {

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select CSV File");
        Stage stage = (Stage) mainPane.getScene().getWindow();

        File file = chooser.showOpenDialog(stage);

        if (file.exists() && !file.isDirectory()){
            try {
                List<CardSize> cardList = new ArrayList<>();
                List<Paper> paperList = new ArrayList<>();
                String line[];
                Scanner sc = new Scanner(file);
                double cost;
                while (sc.hasNextLine()){
                    line = sc.nextLine().split(",");
                    for (int i = 2; i < line.length; i++){
                        cardList.add(CardSize.getByName(line[i].trim()));
                    }
                    cardList.removeIf(Predicate.isEqual(null));
                    cost = Double.parseDouble(line[1]);
                    paperList.add(new Paper(line[0],cost,cardList));
                }
                papersList.setItems(FXCollections.observableArrayList(paperList));
                FileOutputStream fos = new FileOutputStream(myfile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(new PaperListWrapper(paperList));
                oos.close();
                fos.close();
            } catch (IOException e) {
                System.out.println("CSV error");
                e.printStackTrace();
            }
        }
    }


    public void removePaper(ActionEvent actionEvent) {
        try {
            List<org.cfcdoom.Paper> papers = papersList.getItems();
            papers.remove(papersList.getSelectionModel().getSelectedItem());
            FileOutputStream fos = new FileOutputStream(myfile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(new PaperListWrapper(papers));
            oos.close();
            fos.close();
            papersList.setItems(FXCollections.observableArrayList(papers));
        } catch (IOException e) {
            System.out.println("404 paper.obj output error");
            e.printStackTrace();

        }
    }


    public void addPaperType(ActionEvent actionEvent) {
        if (checkInputs()){
            try {
                List<org.cfcdoom.Paper> papers = papersList.getItems();
                papers.add(new org.cfcdoom.Paper(paperName.getText(), Double.parseDouble(price.getText()), newCardSizes));
                FileOutputStream fos = new FileOutputStream(myfile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(new PaperListWrapper(papers));
                oos.close();
                fos.close();
                papersList.setItems(FXCollections.observableArrayList(papers));
            } catch (IOException e) {
                System.out.println("404 paper.obj output error");
                e.printStackTrace();

            }
        }
        newCardSizes.clear();
    }

    private void loadPapers() {
        try {
            FileInputStream fis = new FileInputStream(myfile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            PaperListWrapper papers = (PaperListWrapper) ois.readObject();
            ObservableList<org.cfcdoom.Paper> loadedPapers = FXCollections.observableArrayList(papers.getPapers());
            papersList.setItems(loadedPapers);
            ois.close();
            fis.close();
//            papersList.setCellFactory();
        } catch (IOException | ClassNotFoundException e){
            System.out.println("IO exception papers.obj not found: 404");
            e.printStackTrace();
        }
    }

    private boolean checkInputs() {
        try {
            Double.parseDouble(price.getText());
        } catch (Exception e){
            return false;
        }
        for(CardSize x : cardSizes.getItems()){
            if (x.isPossible()){
                newCardSizes.add(x);
            }
        }
        return paperName.getText().length() > 0 && !newCardSizes.isEmpty();
    }

    public void goBack(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            org.cfcdoom.Main.setScene(root);
        } catch (IOException e) {
            System.out.println("could not find Sample.fxml: 404");
        }
    }
}
