package edu.postech.csed332.homework2;

import org.json.*;

import java.io.FileWriter;
import java.io.IOException;


public class JSonIO {

    public void libToJSON(Library obj, String fileName) throws IOException {
        JSONObject lib = new JSONObject();
        lib.put("library", obj);

        try(FileWriter writer = new FileWriter(fileName))
        {
            writer.write(obj.toString());
            writer.flush();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }

    public void elementToJSON(Element obj){
        JSONObject lib = new JSONObject();
        lib.put("library", obj);

        try(FileWriter writer = new FileWriter(fileName))
        {
            writer.write(obj.toString());
            writer.flush();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
