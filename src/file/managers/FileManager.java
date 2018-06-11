package file.managers;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class FileManager {
    protected FileChooser fileChooser;
    protected Stage stage;
    protected   String errMessage;

    public FileManager(Stage stage)
    {
        this.stage =stage;
        fileChooser = new FileChooser();
    }
    protected  void setupFileChooser()
    {

    }
    public void changeInitialDiretory(String path) {
        //TODO check validity of path adn call changeInitialDirectory( File )
    }
    public  void changeInitialDirectory(File file) {
        //TODO: implement the initial directory change
    }
    public void save()
    {

    }
    public void load()
    {

    }
    protected  void checkFile(File file)throws IOException
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
