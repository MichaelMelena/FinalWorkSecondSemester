package file.managers;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utill.UserDateInput;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class FileManager {
    protected FileChooser fileChooser;
    private Stage stage;
    protected   String errMessage;


    public FileManager(Stage stage)
    {
        this.stage =stage;
        fileChooser = new FileChooser();
        setupFileChooser();
    }
    protected  void setupFileChooser()
    {
        throw  new UnsupportedOperationException("Not implemented");
    }
    public void changeInitialDiretory(String path) {
        //TODO check validity of path adn call changeInitialDirectory( File )
    }
    public  void changeInitialDirectory(File file) {
        //TODO: implement the initial directory change
    }
    public <T> void save(T data) {
        fileManipulation(true,data);
    }
    protected <T>void selectSavingMethod(File file,String filterDescription,T data) throws IOException
    {
        throw  new UnsupportedOperationException("Not implemented!");
    }
    protected <T> void  selectLoadingMethod(File file,String filterDescription,T data)
    {
        throw  new UnsupportedOperationException("Not implemented!");
    }
    public <T> void load(T Data)
    {
        fileManipulation(false,Data);
    }

    protected   <T> void fileManipulation(boolean isSave,T data)
    {
        errMessage=null;

        File selected=null;
        if(isSave){
            fileChooser.setTitle("Save");
           selected =fileChooser.showSaveDialog(stage);
        }
        else {
            fileChooser.setTitle("Load");
            selected = fileChooser.showOpenDialog(stage);
        }

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
            try{
                if(isSave){
                    selectSavingMethod( selected,fileChooser.getSelectedExtensionFilter().getDescription(), data);
                }
                else {
                    selectLoadingMethod( selected,fileChooser.getSelectedExtensionFilter().getDescription(), data);

                }
            }catch (IOException e) {
                errMessage = e.getMessage();
            }
        }
    }
    private   void checkFile(File file)throws IOException
    {
        if(!file.exists()){file.createNewFile();}
        if(!file.isFile()){throw new FileNotFoundException("Not a file!");}
        //No need to check extensions
        //TODO: ADD name checking
    }
    public String getErrMessage()
    {
        return errMessage;
    }

}
