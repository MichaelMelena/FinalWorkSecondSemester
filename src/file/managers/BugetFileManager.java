package file.managers;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.Buget;
import utill.UserDateInput;

import java.io.*;

public class BugetFileManager extends  FileManager{
    private boolean isDaySelected;
    public BugetFileManager(Stage stage) {
        super(stage);

    }

    public void setDaySelected(boolean daySelected) {
        isDaySelected = daySelected;
    }

    public boolean isDaySelected() {
        return isDaySelected;
    }

    @Override
    protected void setupFileChooser() {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text file","*.txt"),
                new FileChooser.ExtensionFilter("Commas separated values","*.csv"),
                new FileChooser.ExtensionFilter("Binary","*.bin"));

    }
    @Override
    protected <T> void selectSavingMethod(File file, String filterDescription, T data) throws IOException {
        if( data instanceof Buget)
        {
            if (filterDescription.equals(fileChooser.getExtensionFilters().get(0).getDescription())){//Text file

                try {
                    if (isDaySelected){
                        textFileSave(file,((Buget) data).printTextValuePerDay());
                    }
                    else {
                        textFileSave(file,((Buget) data).printTextValuePerMonth());
                    }
                }catch(IOException e) {
                    errMessage =e.getMessage();
                    //TODO: handle errors in text saving
                }
            }
            else if (filterDescription.equals(fileChooser.getExtensionFilters().get(1).getDescription())){//CSV file
                try {
                   if(isDaySelected) {
                       textFileSave(file,((Buget) data).printCsvValuePerDay());
                   }
                   else {
                        textFileSave(file,((Buget) data).printCsvValuePerMonth());
                   }
                }catch(IOException e) {
                    errMessage =e.getMessage();
                    //TODO: handle errors in text saving
                }
            }
            else {//binary file
                try {
                    if(isDaySelected) {
                        binaryFileSave(file,((Buget)data).printTextValuePerDay());
                    }else {
                        binaryFileSave(file,((Buget)data).printTextValuePerMonth());
                    }
                } catch (IOException e) {
                    errMessage = e.getMessage();
                    //TODO: Handle errors in binary saving
                }
            }
        }
    }
    private  void textFileSave(File file, String text)throws IOException
    {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            bw.write(text);
        }
    }
    private void binaryFileSave(File file,String text)throws IOException{

        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
            dos.writeUTF(text);
        }
    }

}
