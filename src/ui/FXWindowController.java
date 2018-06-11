package ui;

import file.managers.BugetFileManager;
import file.managers.DateFileManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logic.Buget;
import utill.*;
import utill.Date;

import java.util.*;


public class FXWindowController {

    private static int availableID=0;
    @FXML
    private  ToggleButton tgBtnA;
    @FXML
    private  ToggleButton tgBtnB;
    @FXML
    private  ToggleButton tgBtnC;
    @FXML
    private  ToggleButton tgBtnD;
    @FXML
    private  ToggleButton tgBtnE;
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


    private Stage primaryStage;
    private Map<String,Buget> bugets;
    private ToggleGroup radioButtonGroup;
    private  ToggleGroup toggleButtonGroup;
    private DateFileManager dateFileManager;
    private BugetFileManager bugetFileManager;
    public FXWindowController(){}
    private int selectedSort;

    public void initialize(){
        selectedSort=0;
        dateFileManager= new DateFileManager(primaryStage);

        setUpRadioButtons();
        setUpToggleButtons();

        bugets = new HashMap<String,Buget>(4);
        /*
        bugets.put("1",new Buget(new Date(1,2,2016),new Date(7,7,2018),1d));
        bugets.put("DALSI",new Buget(new Date(1,2,2016),new Date(7,7,2018),20d));
        */
        updateListView();
    }
    public void saveInput(){
        Buget selectedBuget = bugets.get(listView.getSelectionModel().getSelectedItem());

        if(selectedBuget!=null) {
            dateFileManager.save(selectedBuget.getUserDateInput());
        }
        else {
            outputTextArea.appendText(String.format("%You have to select a date in list view!"));

        }

    }
    public void loadInput() {

    }
    public void clearInput(){
        inputTextField.clear();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);


    }
    public void submitInput(){
        String input =inputTextField.getText().trim();
        Buget newBuget=null;

        UserDateInput temp=null;
        try {
            temp =parseUserDateInput(input);
            newBuget= new Buget(temp);
        }
        catch (IllegalArgumentException e)
        {
            outputTextArea.appendText(String.format("%n Invalid input data!"));
        }


        if(newBuget!=null)
        {
            //TODO: EXTRACT NAME FROM INPUT TEXT-FIELD sDate - eDate
            bugets.put(createName(temp),newBuget);
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
    public void toggleButtonSelected()
    {
        if(tgBtnA.isSelected()){selectedSort=1;}
        else if(tgBtnB.isSelected()){selectedSort=2;}
        else if(tgBtnC.isSelected()){selectedSort=3;}
        else if(tgBtnD.isSelected()){selectedSort=4;}
        else {selectedSort=0;}
        updateListView();

    }
    public void loadListItem()
    {
        //TODO: Loads items into the list
    }
    private void updateListView()
    {   List<String> arraylist =sortBugetKeys();
        if(arraylist==null)
        {
            arraylist = new ArrayList<>(bugets.keySet());
        }
        listView.setItems(FXCollections.observableArrayList(arraylist));
        listView.getSelectionModel().select(null);

    }
    private  void setUpRadioButtons()
    {
        radioButtonGroup = new ToggleGroup();
        rbDays.setToggleGroup(radioButtonGroup);
        rbMonths.setToggleGroup(radioButtonGroup);
        rbDays.setSelected(true);
    }
    private  void setUpToggleButtons()
    {
        toggleButtonGroup = new ToggleGroup();
        tgBtnA.setToggleGroup(toggleButtonGroup);
        tgBtnA.setTooltip(new Tooltip("Sorts by value"));
        tgBtnB.setToggleGroup(toggleButtonGroup);
        tgBtnB.setTooltip(new Tooltip(("Sorts by Start date")));
        tgBtnC.setToggleGroup(toggleButtonGroup);
        tgBtnC.setTooltip(new Tooltip("Sorts by End date"));
        tgBtnD.setToggleGroup(toggleButtonGroup);
        tgBtnD.setTooltip(new Tooltip("Sorts by Time span"));
        tgBtnE.setToggleGroup(toggleButtonGroup);
        tgBtnE.setTooltip(new Tooltip("Sorts by default"));
    }
    private String createName(UserDateInput userDateInput)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(userDateInput.getStart().toString());
        sb.append(" / ");
        sb.append(userDateInput.getEnd().toString());
        sb.append(" :");
        sb.append(userDateInput.getValue());
        new utill.BugetKey(sb.toString());
        return  sb.toString();
    }
    private ArrayList<BugetKey> createBugetKeyList()
    {
        ArrayList<BugetKey> array=new ArrayList<>(4);
        Set<String> temp = bugets.keySet();
        for(String name:temp)
        {
            array.add(new BugetKey(name));
        }
        return  array;
    }
    private List<String>sortBugetKeys()
    {
        ArrayList<BugetKey> arrayList = createBugetKeyList();
        if(selectedSort==1)
        {
            Collections.sort(arrayList);
        }
        else if(selectedSort==2)
        {
            Collections.sort(arrayList,new  BugetKey.startDateComparator());
        }
        else if(selectedSort==3)
        {
            Collections.sort(arrayList,new BugetKey.endDateComparator());
        }
        else if(selectedSort ==4)
        {
            Collections.sort(arrayList,new BugetKey.spanDateComparator());
        }
        else {return null;}
        List<String> out= new ArrayList<>();
        for(BugetKey item: arrayList)
        {
            out.add(item.getText());
        }
        return out;

    }
    private UserDateInput parseUserDateInput(String input)
    {
        double value;
        if(startDatePicker.getValue()!=null && endDatePicker.getValue()!=null)
        {

            //if(!input.matches("[\\d.]+]")){throw new NumberFormatException("Number does not pass the regex");}
             return new UserDateInput(Date.init(startDatePicker.getValue().format(Date.timeFromat)),Date.init(endDatePicker.getValue().format(Date.timeFromat)), Double.parseDouble(input));

        }
        throw new IllegalArgumentException();
    }



}
