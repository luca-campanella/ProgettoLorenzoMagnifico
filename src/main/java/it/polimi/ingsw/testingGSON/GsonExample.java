package it.polimi.ingsw.testingGSON;

/**
 * Created by higla on 17/05/2017.
 */
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonExample {

    public static void main(String[] args) throws Exception {
            // Configure Gson
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Book.class, new BookDeserializer());
            Gson gson = gsonBuilder.create();

            // The JSON data
            try(Reader reader = new InputStreamReader(GsonExample.class.getResourceAsStream("/Output.json"), "UTF-8")){
                // Parse JSON to Java
                Book book = gson.fromJson(reader, Book.class);
                System.out.println(book);
            }
        }
    }

