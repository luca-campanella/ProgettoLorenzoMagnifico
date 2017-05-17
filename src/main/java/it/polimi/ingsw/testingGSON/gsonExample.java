package it.polimi.ingsw.testingGSON;

/**
 * Created by higla on 17/05/2017.
 */
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class gsonExample {

    public static void main(String[] args) throws IOException {
        try(Reader reader = new InputStreamReader(gsonExample.class.getResourceAsStream("/Output.json"), "UTF-8")){
            Gson gson = new GsonBuilder().create();
            examplePerson p = gson.fromJson(reader, examplePerson.class);
            System.out.println(p);
        }
    }
}
