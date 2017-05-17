package it.polimi.ingsw.testingGSON;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by higla on 17/05/2017.
 */
public class BookDeserializer implements JsonDeserializer<Book> {

    @Override
    public Book deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
                final JsonObject jsonObject = json.getAsJsonObject();
                final JsonElement jsonTitle = jsonObject.get("title");
                final String title = jsonTitle.getAsString();
                final String isbn10 = jsonObject.get("isbn-10").getAsString();
                final String isbn13 = jsonObject.get("isbn-13").getAsString();

                final JsonArray jsonAuthorsArray = jsonObject.get("authors").getAsJsonArray();
                final String[] authors = new String[jsonAuthorsArray.size()];
                for (int i = 0; i < authors.length; i++) {
                    final JsonElement jsonAuthor = jsonAuthorsArray.get(i);
                    authors[i] = jsonAuthor.getAsString();
                }

                final Book book = new Book();
                book.setTitle(title);
                book.setIsbn10(isbn10);
                book.setIsbn13(isbn13);
                book.setAuthors(authors);
                return book;
            }
}