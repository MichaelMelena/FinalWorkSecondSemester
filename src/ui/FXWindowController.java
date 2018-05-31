package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import logic.Buget;
import java.util.ArrayList;


public class FXWindowController {

    @FXML
    private ListView<String> listView;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private Label inputLabel;
    @FXML
    private TextField inputTextField;
    @FXML
    private GridPane inputGridPane;
    @FXML
    private Button inputSave;
    @FXML
    private Button inputLoad;
    @FXML
    private Button inputClear;
    @FXML
    private Button inputSubmit;
    @FXML
    private Label outputLabel;
    @FXML
    private TextArea outputTextArea;
    @FXML
    private GridPane outputGridPane;
    @FXML
    private Button outputClear;
    @FXML
    private Button outputSave;

    private ObservableList<String> bugets;
    public FXWindowController(){}

    public void initialize(){

        ArrayList<String> temp = new ArrayList<>(10);
        for(int i =0;i<40;i++)
        {
            temp.add(Integer.toString(i));
        }

        bugets = FXCollections.observableArrayList(temp);
        listView.setItems(bugets);



    }
    public void saveInput() {

    }
    public void loadInput() {

    }
    public void clearInput(){

    }
    public void submitInput(){
        outputTextArea.appendText("REEEE!");
         outputTextArea.appendText(startDatePicker.getValue().format(utill.Date.timeFromat));

    }
    public void clearOutput(){

    }
    public void saveOutput(){

    }

}
