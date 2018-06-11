package file.managers;

import javafx.stage.Stage;
import  javafx.stage.FileChooser;
import utill.Date;
import utill.UserDateInput;


import javax.xml.crypto.Data;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.zip.DataFormatException;

public class DateFileManager extends FileManager {
    private UserDateInput loadedUserDataUserDateInput;

    public DateFileManager(Stage stage) {
        super(stage);
    }

    @Override
    protected void setupFileChooser() {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text file","*.txt"),new FileChooser.ExtensionFilter("Binary","*.bin"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }

    @Override
    protected <T> void selectSavingMethod(File file,String filterDescription, T data)throws  IOException {
        if( data instanceof UserDateInput)
        {
            if (filterDescription.equals(fileChooser.getExtensionFilters().get(0).getDescription())){

                try {
                    textFileSave(file,(UserDateInput)data);
                }catch(IOException e) {
                    errMessage =e.getMessage();
                    //TODO: handle errors in text saving
                }
            }
            else { // Second extension filter
                try {
                    binaryFileSave(file, (UserDateInput) data);
                } catch (IOException e) {
                    errMessage = e.getMessage();
                    //TODO: Handle errors in binary saving
                }
            }
        }
        else {throw  new InvalidObjectException("Not a UserDataInput class");}
    }

    @Override
    protected <T> void selectLoadingMethod(File file, String filterDescription, T data) {


        if (filterDescription.equals(fileChooser.getExtensionFilters().get(0).getDescription())){

            try {
                loadedUserDataUserDateInput = textFileLoad(file);
            }catch(IOException e) {
                errMessage =e.getMessage();
                //TODO: handle errors in text saving
            }
        }
        else { // Second extension filter
            try {
                loadedUserDataUserDateInput= binaryFileLoad(file);
            } catch (IOException e) {
                errMessage = e.getMessage();
                //TODO: Handle errors in binary saving
            }
        }
    }

    private <T extends UserDateInput> void textFileSave(File file, T data) throws IOException
    {
        try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)))
        {

            bw.write(data.getStart().toString());
            bw.newLine();
            bw.write(data.getEnd().toString());
            bw.newLine();
            bw.write(Double.toString(data.getValue()));
            bw.newLine();

        }
    }
    private <T extends UserDateInput> void binaryFileSave(File file, T data)throws  IOException
    {
        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(file,false)))
        {
            dos.writeUTF(data.getStart().toString());
            dos.writeUTF(data.getEnd().toString());
            dos.writeDouble(data.getValue());
        }
    }
    private UserDateInput textFileLoad(File file) throws  IOException
    {
        Date start=null;
        Date end=null;
        double value=0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            start = new Date(LocalDate.parse(br.readLine(),Date.timeFromat));
            end  = new Date(LocalDate.parse(br.readLine(),Date.timeFromat));
            value = Double.parseDouble(br.readLine());
        }
        if(start!=null&&end!=null){
            return new UserDateInput(start,end,value);
        }
        return null;
    }
    private  UserDateInput binaryFileLoad(File file)throws IOException
    {
        Date start=null;
        Date end=null;
        double value=0;
        try(DataInputStream dis = new DataInputStream(new FileInputStream(file))){
            start = new Date(LocalDate.parse(dis.readUTF(),Date.timeFromat));
            end  = new Date(LocalDate.parse(dis.readUTF(),Date.timeFromat));
            value = dis.readDouble();
        }
        if(start!=null&&end!=null){
            return new UserDateInput(start,end,value);
        }
        return null;
    }



    public UserDateInput getLoadedUserDataUserDateInput() {
        return loadedUserDataUserDateInput;
    }
}
