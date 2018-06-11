package file.managers;

import javafx.stage.Stage;
import  javafx.stage.FileChooser;
import utill.UserDateInput;


import java.io.*;

public class DateFileManager {
    private FileChooser fileChooser;
    private Stage stage;
    private  String errMessage;
    public DateFileManager(Stage stage) {
        this.stage=stage;
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text file",".txt"),new FileChooser.ExtensionFilter("Binary",".bin"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

    }

    public UserDateInput load() {
        fileChooser.setTitle("Load date");
       throw  new UnsupportedOperationException("Yet to be implemented");
    }
    public void save(UserDateInput userDateInput) {
        errMessage=null;
        fileChooser.setTitle("Load date");
       File selected = fileChooser.showSaveDialog(stage);
       if(selected!=null)
       {
           try {
               checkFile(selected);
           }
           catch (IOException e)
           {
                errMessage= e.getMessage();
                return;
           }

           if(fileChooser.getSelectedExtensionFilter().getDescription().equals(fileChooser.getExtensionFilters().get(0).getDescription()))
           {
                try {
                    textFileSave(selected,userDateInput);
                }catch(IOException e) {
                    errMessage =e.getMessage();
                    //TODO: handle errors in text saving
                }
           }
           else { // Second extension filter
               try{
                   binaryFileSave(selected,userDateInput);
               }catch (IOException e){
                   errMessage =e.getMessage();
                    //TODO: Handle errors in binary saving
               }
           }
       }
           System.out.println(selected.getPath());
           System.out.println(fileChooser.getSelectedExtensionFilter().getDescription());
   }
    private void checkFile(File file)throws  IOException
    {
        if(!file.exists()){file.createNewFile();}
        if(!file.isFile()){throw new FileNotFoundException("Not a file!");}

        //No need to check extensions
        //TODO: ADD name checking

    }

    private void textFileSave(File file,UserDateInput data) throws IOException
    {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file,false)))
        {
            bw.write(data.getStart().toString());
            bw.newLine();
            bw.write(data.getEnd().toString());
            bw.newLine();
            bw.write(Double.toString(data.getValue()));
        }
    }
    private  void binaryFileSave(File file, UserDateInput data)throws  IOException
    {
        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(file,false)))
        {
            dos.writeUTF(data.getStart().toString());
            dos.writeUTF(data.getEnd().toString());
            dos.writeDouble(data.getValue());
        }
    }
    public String getErrMessage()
    {
        return errMessage;
    }



}
