package ui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import logic.Buget;
import utill.Date;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FXWindowController {

    private static int availableID=0;
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
    private Label outputLabel;
    @FXML
    private TextArea outputTextArea;
    @FXML
    private GridPane outputGridPane;
    @FXML
    private RadioButton rbDays;
    @FXML
    private RadioButton rbMonths;

    private  FileChooser fileChooser;
    private Stage primaryStage;
    private Map<String,Buget> bugets;
    private ToggleGroup radioButtonGroup;
    public FXWindowController(){}

    public void initialize(){

        fileChooser = new FileChooser();

        setUpRadioButtons();

        bugets = new HashMap<String,Buget>(4);
        bugets.put("1",new Buget(new Date(1,2,2016),new Date(7,7,2018),1d));
        bugets.put("DALSI",new Buget(new Date(1,2,2016),new Date(7,7,2018),20d));
        updateListView();
    }
    public void saveInput() {
        fileChooser.showSaveDialog(primaryStage);
    }
    public void loadInput() {
        fileChooser.showOpenDialog(primaryStage);
    }
    public void clearInput(){
        inputTextField.clear();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);


    }
    public void submitInput(){
        String input =inputTextField.getText().trim();
        Buget newBuget=null;
        double value=0;
        boolean isValueSet= false;
        if(startDatePicker.getValue()!=null && endDatePicker.getValue()!=null)
        {
                try
                {
                    //if(!input.matches("[\\d.]+]")){throw new NumberFormatException("Number does not pass the regex");}
                    value = Double.parseDouble(input);
                    isValueSet=true;
                    newBuget=new Buget(new Date(startDatePicker.getValue()),new Date(endDatePicker.getValue()),value);

                }
                catch(NumberFormatException e)
                {
                    //TODO: RED BORDER TO INPUT
                    outputTextArea.appendText(e.getMessage());
                }

        }
        if(newBuget!=null)
        {
            //TODO: EXTRACT NAME FROM INPUT TEXT-FIELD sDate - eDate
            bugets.put(createName(startDatePicker.getValue(),endDatePicker.getValue(),(isValueSet)?value:Double.NaN),newBuget);
            updateListView();
        }

    }
    public void clearOutput(){
        outputTextArea.clear();
    }
    public void saveOutput(){

    }
    public void showListItem()
    {
        String selectedItem =listView.getSelectionModel().getSelectedItem();
        if(selectedItem!=null)
        {
             Buget buget =   bugets.get(selectedItem);
             String output;
             if(rbDays.isSelected())
             {
                 output = buget.printTextValuePerDay();
             }
             else
             {
                 output=buget.printTextValuePerMonth();
             }
             outputTextArea.appendText(output);
        }
    }
    public void radioButtonSelected(){

        //TODO: reaction to rbDays/rbMonths selection

    }
    public void setPrimaryStage(Stage stage)
    {
        this.primaryStage =stage;
    }
    public void removeListItem()
    {
       String temp =  listView.getSelectionModel().getSelectedItem();
       if(temp!=null)
       {
           bugets.remove(temp);
           updateListView();

       }
    }
    private void updateListView()
    {
        ArrayList<String> temp = new ArrayList<>(bugets.keySet());
        listView.setItems(FXCollections.observableArrayList(temp));
        listView.getSelectionModel().select(null);

    }
    private  void setUpRadioButtons()
    {
        radioButtonGroup = new ToggleGroup();
        rbDays.setToggleGroup(radioButtonGroup);
        rbMonths.setToggleGroup(radioButtonGroup);
        rbDays.setSelected(true);
    }
    private String createName(LocalDate start, LocalDate end,double value)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(start.toString());
        sb.append(" / ");
        sb.append(end.toString());
        sb.append(" :");
        sb.append(value);
        return  sb.toString();
    }



}
