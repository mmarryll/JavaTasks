package common.MiniTasks;

import java.io.*;
import java.util.Base64;
import java.util.zip.*;



interface IData{
    String read(String filename);
    void write(String filename, String text);
}

public class ReadWrite implements IData {

    @Override
    public String read(String filename) {
        String text = "";
        try(FileReader reader = new FileReader(filename))
        {
            int c;
            while((c=reader.read())!=-1){
                text += (char)c;
            }

        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        return text;
    }

    @Override
    public void write(String filename, String text) {
        try(FileWriter writer = new FileWriter(filename, false))
        {
            // запись всей строки
            writer.write(text);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}


class DataDecorator implements IData{
    protected IData wrappee;

    DataDecorator(IData data){
        wrappee = data;
    }

    @Override
    public String read(String filename) {
        return wrappee.read(filename);
    }

    @Override
    public void write(String filename, String text) {
        wrappee.write(filename, text);
    }
}

